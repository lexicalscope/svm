package com.lexicalscope.svm.partition.trace.symb.search2;

import static com.lexicalscope.svm.partition.trace.TraceBuilder.trace;
import static com.lexicalscope.svm.partition.trace.symb.search2.TraceTree.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.partition.trace.Trace;
import com.lexicalscope.svm.vm.j.JState;

public class TestTraceTree {
   @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
   @Mock public JState state01;
   @Mock private TraceTreeObserver ttObserver;

   private final TraceTree tt = new TraceTree();

   @Before public void createTraceTree() {
      tt.listener(ttObserver);
   }

   @Test public void rootOfTraceTreeIsEmptyTrace() {
      assertThat(tt, nodeTrace(trace().build()));
   }

   @Test public void treeNodeCanHoldPStates() {
      context.checking(new Expectations(){{
         oneOf(ttObserver).stateAdded(tt, state01);
      }});

      tt.pState(state01);
      assertThat(tt, containsPState(state01));
      assertThat(tt, noQStates());
   }

   @Test public void treeNodeCanHoldQStates() {
      context.checking(new Expectations(){{
         oneOf(ttObserver).stateAdded(tt, state01);
      }});

      tt.qState(state01);
      assertThat(tt, containsQState(state01));
      assertThat(tt, noPStates());
   }

   @Test public void treeNodeCanHoldAChild() {
      final Trace trace = trace().methodCall(Object.class, "<init>", "()V", new Object()).build();

      final TraceTree child = tt.child(trace);

      assertThat(child, nodeTrace(trace));
      assertThat(tt, containsChild(child));
   }

   @Test public void treeNodeCanHoldManyChildren() {
      final Trace trace1 = trace().methodCall(Object.class, "<init>", "()V", new Object()).build();
      final Trace trace2 = trace().methodCall(String.class, "<init>", "()V", new Object()).build();

      final TraceTree child1 = tt.child(trace1);
      final TraceTree child2 = tt.child(trace2);

      assertThat(child1, not(sameInstance(child2)));
      assertThat(tt, children(contains(child1, child2)));
   }

   @Test public void duplicatePathCreatesOneChild() {
      final Trace trace1 = trace().methodCall(Object.class, "<init>", "()V", new Object()).build();
      final Trace trace2 = trace().methodCall(Object.class, "<init>", "()V", new Object()).build();

      final TraceTree child1 = tt.child(trace1);
      final TraceTree child2 = tt.child(trace2);

      assertThat(trace1, equalTo(trace2));
      assertThat(child1, sameInstance(child2));
      assertThat(tt, children(contains(child1)));
   }
}

