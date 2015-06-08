package com.lexicalscope.svm.search2;

import static com.lexicalscope.svm.partition.trace.TraceBuilder.terminateTrace;
import static com.lexicalscope.svm.search2.ListStatesCollection.removeWithSwap;
import static com.lexicalscope.svm.search2.Side.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.partition.trace.Trace;
import com.lexicalscope.svm.partition.trace.symb.tree.TraceMetaExtractor;
import com.lexicalscope.svm.search.GuidedSearchObserver;
import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class TreeSearch implements StateSearch<JState>, TraceTreeObserver {
   private final List<JState> results = new ArrayList<>();

   private final Randomiser randomiser;
   private final GuidedSearchObserver observer;
   private final FeasibilityChecker feasibilityChecker;
   private final TraceMetaExtractor metaExtractor;

   private final TraceTree tt;
   private final TraceTreeSearchState searchState;

   private JState pendingJState;

   private boolean pstateGiven;
   private boolean qstateGiven;

   private TraceTree selectedTree;

   private int selectedTreeIndex;


   public TreeSearch(
         final GuidedSearchObserver observer,
         final FeasibilityChecker feasibilityChecker,
         final Randomiser randomiser,
         final StatesCollectionFactory stateSelection) {
      this.observer = observer;
      this.feasibilityChecker = feasibilityChecker;
      this.randomiser = randomiser;
      this.tt = new TraceTree(stateSelection, this);
      this.searchState = new TraceTreeSearchState(tt);
      metaExtractor = new TraceMetaExtractor();
   }

   @Override public JState pendingState() {
      if(pendingJState == null) {
         if(searchState.currentSide().equals(PSIDE) && anyQStatesAvailable()) {
            pickAQstate();
         } else if (searchState.currentSide().equals(QSIDE) && anyPStatesAvailable()) {
            pickAPstate();
         } else if (anyPStatesAvailable()) {
            pickAPstate();
         } else if (anyQStatesAvailable()) {
            pickAQstate();
         }

         if(pendingJState != null) {
            observer.picked(pendingJState, searchState.currentSide());
         }
      }
      return pendingJState;
   }

   private void pickAQstate() {
      selectAnotherTree(qstatesAvailable);
      pendingJState = selectedTree.qStates().pickState();

      searchState.search(QSIDE, selectedTree);
   }

   private void pickAPstate() {
      selectAnotherTree(pstatesAvailable);
      pendingJState = selectedTree.pStates().pickState();

      searchState.search(PSIDE, selectedTree);
   }

   public void selectAnotherTree(final List<TraceTree> withStatesAvailable) {
      selectedTreeIndex = randomiser.random(withStatesAvailable.size());
      selectedTree = withStatesAvailable.get(selectedTreeIndex);
   }

   @Override public void reachedLeaf() {
      pendingJState.complete();
      observer.leaf(pendingJState);
      results.add(pendingJState);

      final Trace goal = metaExtractor.goal(pendingJState);
      pushGoalToCurrentNode(terminateTrace(goal));

      pendingJState = null;
   }

   @Override public void fork(final JState parent, final JState[] states) {
      assert states.length == 2;
      observer.forkAt(parent);
      pushStateToSearchLater(searchState.currentNode(), states[0]);
      pushStateToSearchLater(searchState.currentNode(), states[1]);
      pendingJState = null;
   }

   @Override public void forkDisjoined(final JState parent, final JState[] states) {
      fork(parent, states);
   }

   @Override public void goal() {
      observer.goal(pendingJState);

      final Trace goal = metaExtractor.goal(pendingJState);
      final TraceTree child = pushGoalToCurrentNode(goal);

      pushStateToSearchLater(child, pendingJState);

      pendingJState = null;
   }

   private TraceTree pushGoalToCurrentNode(final Trace goal) {
      final TraceTree child = searchState.currentNode().child(goal);

      switch (searchState.currentSide()) {
         case PSIDE:
            child.disjoinP(metaExtractor.pc(pendingJState));
            break;

         case QSIDE:
            child.disjoinQ(metaExtractor.pc(pendingJState));
            break;
      }
      new TraceTreeChildMismatchDetector(feasibilityChecker).mismatch(searchState.currentNode(), new MismatchReport(){
         @Override public void mismatch(final FeasibilityChecker checker, final Trace nodeTraceP, final BoolSymbol pPc, final Trace nodeTraceQ, final BoolSymbol qPc) {
            throw new PartitionViolationException(checker, nodeTraceP, pPc, nodeTraceQ, qPc);
         }});
      return child;
   }

   private void pushStateToSearchLater(final TraceTree node, final JState state) {
      switch (searchState.currentSide()) {
         case PSIDE:
            node.pState(state);
            break;

         case QSIDE:
            node.qState(state);
            break;
      }
   }

   @Override public JState firstResult() {
      if (results.size() == 0) {
         return null;
      }
      return results.get(0);
   }

   @Override public Collection<JState> results() {
      return results;
   }

   @Override public void consider(final JState state) {
      if(!pstateGiven && !qstateGiven) {
         metaExtractor.configureInitial(state);
         tt.pState(state);
         pstateGiven = true;
      } else if (!qstateGiven) {
         metaExtractor.configureInitial(state);
         tt.qState(state);
         qstateGiven = true;
      } else {
         throw new IllegalStateException("only one pstate and one qstate can be considered");
      }
   }

   private final List<TraceTree> pstatesAvailable = new ArrayList<TraceTree>();
   private final List<TraceTree> qstatesAvailable = new ArrayList<TraceTree>();

   @Override public void pstateAvailable(final TraceTree traceTree) {
      pstatesAvailable.add(traceTree);
   }

   @Override public void pstateUnavailable(final TraceTree traceTree) {
      assert selectedTree == traceTree;
      final TraceTree removed = removeWithSwap(selectedTreeIndex, pstatesAvailable);
      assert removed == traceTree;
   }

   @Override public void qstateAvailable(final TraceTree traceTree) {
      qstatesAvailable.add(traceTree);
   }

   @Override public void qstateUnavailable(final TraceTree traceTree) {
      assert selectedTree == traceTree;
      final TraceTree removed = removeWithSwap(selectedTreeIndex, qstatesAvailable);
      assert removed == traceTree;
   }

   private boolean anyPStatesAvailable() {
      return !pstatesAvailable.isEmpty();
   }

   private boolean anyQStatesAvailable() {
      return !qstatesAvailable.isEmpty();
   }
}
