package com.lexicalscope.svm.partition.trace.symb;

import static com.lexicalscope.svm.partition.trace.TraceBuilder.trace;
import static com.lexicalscope.svm.partition.trace.symb.SymbolicTraceMatchers.equivalentTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.partition.trace.TraceBuilder;
import com.lexicalscope.svm.vm.symb.junit.Fresh;
import com.lexicalscope.svm.vm.symb.junit.SolverRule;

public class TestTraceEquivalenceModuloAliasing {
   @Rule public final SolverRule solver = new SolverRule();
   public static class Example { }

   private @Fresh ISymbol symbol1;
   private @Fresh ISymbol symbol2;

   private final Object address1 = new Object();
   private final Object address2 = new Object();
   private final Object address3 = new Object();

   private TraceBuilder receiverAliasesParameterV1;
   private TraceBuilder receiverAliasesParameterV2;
   private TraceBuilder receiverDoesNotAliasParameter;

   @Before public void createTraces() {
      receiverAliasesParameterV1 = trace()
            .methodCall(Example.class, "call", "(Ljava/lang/Object;)V", address1, address1)
            .methodReturn(Example.class, "call", "(Ljava/lang/Object;)V");

      receiverAliasesParameterV2 = trace()
            .methodCall(Example.class, "call", "(Ljava/lang/Object;)V", address2, address2)
            .methodReturn(Example.class, "call", "(Ljava/lang/Object;)V");

      receiverDoesNotAliasParameter = trace()
            .methodCall(Example.class, "call", "(Ljava/lang/Object;)V", address1, address3)
            .methodReturn(Example.class, "call", "(Ljava/lang/Object;)V");
   }

   @Test public void sameAliasingAreEquivalent() throws Exception {
      assertThat(receiverAliasesParameterV1, equivalentTo(solver, receiverAliasesParameterV2));
   }

   @Test public void differentAliasingAreNotEquivalent() throws Exception {
      assertThat(receiverAliasesParameterV1, not(equivalentTo(solver, receiverDoesNotAliasParameter)));
      assertThat(receiverAliasesParameterV2, not(equivalentTo(solver, receiverDoesNotAliasParameter)));
   }
}
