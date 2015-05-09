package com.lexicalscope.svm.search2;

import static com.lexicalscope.svm.search2.Side.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.lexicalscope.svm.partition.trace.Trace;
import com.lexicalscope.svm.partition.trace.symb.tree.TraceMetaExtractor;
import com.lexicalscope.svm.search.GuidedSearchObserver;
import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class TreeSearch2 implements StateSearch<JState> {
   private final List<JState> results = new ArrayList<>();

   private final Randomiser randomiser;
   private final GuidedSearchObserver observer;
   private final FeasibilityChecker feasibilityChecker;
   private final TraceMetaExtractor metaExtractor;

   private final TraceTree tt;
   private final TraceTreeTracker tracker;
   private final TraceTreeSearchState searchState;

   private JState pending;

   private boolean pstateGiven;
   private boolean qstateGiven;


   public TreeSearch2(
         final GuidedSearchObserver observer,
         final FeasibilityChecker feasibilityChecker,
         final Randomiser randomiser) {
      this.observer = observer;
      this.feasibilityChecker = feasibilityChecker;
      this.randomiser = randomiser;
      this.tt = new TraceTree();
      this.tracker = new TraceTreeTracker();
      tt.listener(tracker);
      this.searchState = new TraceTreeSearchState(tt);
      metaExtractor = new TraceMetaExtractor();
   }

   @Override public JState pendingState() {
      if(pending == null) {
         if(searchState.currentSide().equals(PSIDE) && tracker.anyPStatesAvailable()) {
            pickAPstate();
         } else if (searchState.currentSide().equals(QSIDE) && tracker.anyQStatesAvailable()) {
            pickAQstate();
         } else if (tracker.anyPStatesAvailable()) {
            pickAPstate();
         } else if (tracker.anyQStatesAvailable()) {
            pickAQstate();
         }

         if(pending != null) {
            observer.picked(pending, searchState.currentSide());
         }
      }
      return pending;
   }

   private void pickAQstate() {
      final int node = randomiser.random(tracker.qstatesAvailable().size());
      final TraceTree selectedTree = tracker.qstatesAvailable().get(node);

      final int state = randomiser.random(selectedTree.qStates().size());
      pending = selectedTree.removeQState(state);

      searchState.search(QSIDE, selectedTree);
   }

   private void pickAPstate() {
      final int node = randomiser.random(tracker.pstatesAvailable().size());
      final TraceTree selectedTree = tracker.pstatesAvailable().get(node);

      final int state = randomiser.random(selectedTree.pStates().size());
      pending = selectedTree.removePState(state);

      searchState.search(PSIDE, selectedTree);
   }

   @Override public void reachedLeaf() {
      observer.leaf(pending);
      results.add(pending);
      pending = null;
   }

   @Override public void fork(final JState parent, final JState[] states) {
      assert states.length == 2;
      observer.leaf(parent);
      pushStateToSearchLater(searchState.currentNode(), states[0]);
      pushStateToSearchLater(searchState.currentNode(), states[1]);
      pending = null;
   }

   @Override public void goal() {
      observer.goal(pending);
      final Trace goal = metaExtractor.goal(pending);
      final TraceTree child = searchState.currentNode().child(goal);
      pushStateToSearchLater(child, pending);
      pending = null;
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
}
