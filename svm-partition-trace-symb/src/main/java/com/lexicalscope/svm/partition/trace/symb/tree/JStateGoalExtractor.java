package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.j.instruction.symbolic.PcMetaKey.PC;
import static com.lexicalscope.svm.partition.trace.TraceMetaKey.TRACE;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.partition.trace.Trace;
import com.lexicalscope.svm.search.GoalExtractor;
import com.lexicalscope.svm.vm.j.JState;

public class JStateGoalExtractor implements GoalExtractor<Trace, JState> {
   @Override public Trace goal(final JState state) {
      return state.getMeta(TRACE);
   }

   @Override public BoolSymbol pc(final JState state) {
      return state.getMeta(PC);
   }
}
