package com.lexicalscope.svm.partition.trace.symb;

import static com.lexicalscope.svm.partition.trace.TraceBuilder.trace;
import static com.lexicalscope.svm.partition.trace.symb.SymbolicTraceMatchers.equivalentTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.IAddSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.IConstSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.IMulSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.partition.trace.TraceBuilder;
import com.lexicalscope.svm.vm.symb.junit.Fresh;
import com.lexicalscope.svm.vm.symb.junit.SolverRule;

public class TestSymbolicTraceEquivalence {
   @Rule public final SolverRule solver = new SolverRule();
   public static class Example { }

   private @Fresh ISymbol symbol1;
   private @Fresh ISymbol symbol2;

   private final Object address = new Object();
   private TraceBuilder doubleByMultiplying;
   private TraceBuilder doubleByAdding;
   private TraceBuilder trebleByMultiplying;

   @Before public void createTraces() {
      doubleByMultiplying = trace()
            .methodCall(Example.class, "double", "(I)I", address, symbol1)
            .methodReturn(Example.class, "double", "(I)I", new IMulSymbol(symbol1, new IConstSymbol(2)));

      doubleByAdding = trace()
            .methodCall(Example.class, "double", "(I)I", address, symbol1)
            .methodReturn(Example.class, "double", "(I)I", new IAddSymbol(symbol1, symbol1));

      trebleByMultiplying = trace()
            .methodCall(Example.class, "double", "(I)I", address, symbol1)
            .methodReturn(Example.class, "double", "(I)I", new IMulSymbol(symbol1, new IConstSymbol(3)));
   }

   @Test public void twoWaysOfDoublingAreEquivalent() throws Exception {
      assertThat(doubleByMultiplying, equivalentTo(solver, doubleByAdding));
   }

   @Test public void twoWaysOfDoublingInNotEquivalentToTrebeling() throws Exception {
      assertThat(trebleByMultiplying, not(equivalentTo(solver, doubleByAdding)));
   }
}
