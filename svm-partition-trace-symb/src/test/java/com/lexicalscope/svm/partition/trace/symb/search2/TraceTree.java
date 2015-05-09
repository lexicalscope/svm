package com.lexicalscope.svm.partition.trace.symb.search2;

import static com.lexicalscope.svm.partition.trace.TraceBuilder.trace;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import com.lexicalscope.svm.partition.trace.Trace;
import com.lexicalscope.svm.vm.j.JState;

public class TraceTree {
   private final Trace nodeTrace;
   private final Collection<JState> pStates = new ArrayList<>();
   private final Collection<JState> qStates = new ArrayList<>();
   private final LinkedHashMap<Trace, TraceTree> children = new LinkedHashMap<>();

   public TraceTree() {
      this(trace().build());
   }

   public TraceTree(final Trace nodeTrace) {
      this.nodeTrace = nodeTrace;
   }

   public Trace nodeTrace() {
      return nodeTrace;
   }

   protected Collection<JState> pStates() {
      return pStates;
   }

   public void pState(final JState state) {
      pStates.add(state);
   }

   public void qState(final JState state) {
      qStates.add(state);
   }

   public Collection<JState> qStates() {
      return qStates;
   }

   public TraceTree child(final Trace trace) {
      if(!children.containsKey(trace)) {
         children.put(trace, new TraceTree(trace));
      }
      return children.get(trace);
   }

   protected Collection<TraceTree> children() {
      return children.values();
   }

   private static FeatureMatcher<TraceTree, Collection<JState>> pStates(final Matcher<? super Collection<JState>> contains) {
      return new FeatureMatcher<TraceTree, Collection<JState>>(contains, "p state collection", "pStates") {
         @Override protected Collection<JState> featureValueOf(final TraceTree actual) {
            return actual.pStates();
         }
      };
   }

   private static FeatureMatcher<TraceTree, Collection<JState>> qStates(final Matcher<? super Collection<JState>> contains) {
      return new FeatureMatcher<TraceTree, Collection<JState>>(contains,
            "q state collection",
            "qStates") {
         @Override protected Collection<JState> featureValueOf(final TraceTree actual) {
            return actual.qStates();
         }
      };
   }

   public static Matcher<TraceTree> containsPState(final JState state) {
      return pStates(contains(state));
   }

   public static Matcher<TraceTree> containsQState(final JState state) {
      return qStates(contains(state));
   }

   public static Matcher<? super TraceTree> noQStates() {
      return qStates(emptyCollectionOf(JState.class));
   }

   public static Matcher<? super TraceTree> noPStates() {
      return pStates(emptyCollectionOf(JState.class));
   }

   public static Matcher<? super TraceTree> nodeTrace(final Trace trace) {
      return nodeTrace(equalTo(trace));
   }

   private static Matcher<TraceTree> nodeTrace(final Matcher<Trace> equalTo) {
      return new FeatureMatcher<TraceTree, Trace>(equalTo, "trace of node", "nodeTrace") {
         @Override protected Trace featureValueOf(final TraceTree actual) {
            return actual.nodeTrace();
         }
      };
   }

   public static Matcher<? super TraceTree> containsChild(final TraceTree child) {
      return children(contains(child));
   }

   public static Matcher<TraceTree> children(final Matcher<? super Collection<TraceTree>> contains) {
      return new FeatureMatcher<TraceTree, Collection<TraceTree>>(contains, "tree children", "treeChildren") {
         @Override protected Collection<TraceTree> featureValueOf(final TraceTree actual) {
            return actual.children();
         }
      };
   }
}