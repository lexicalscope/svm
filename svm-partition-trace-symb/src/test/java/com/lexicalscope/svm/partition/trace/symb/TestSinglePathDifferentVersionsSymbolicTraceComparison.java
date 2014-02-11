package com.lexicalscope.svm.partition.trace.symb;

import static com.lexicalscope.svm.partition.trace.PartitionBuilder.partition;
import static com.lexicalscope.svm.partition.trace.PartitionInstrumentation.instrumentPartition;
import static com.lexicalscope.svm.partition.trace.TraceMetaKey.TRACE;
import static com.lexicalscope.svm.partition.trace.symb.SymbolicTraceMatchers.equivalentTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.examples.ExamplesOneMarker;
import com.lexicalscope.svm.examples.ExamplesTwoMarker;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.partition.trace.PartitionBuilder;
import com.lexicalscope.svm.vm.conc.LoadFrom;
import com.lexicalscope.svm.vm.conc.junit.TestEntryPoint;
import com.lexicalscope.svm.vm.conc.junit.VmWrap;
import com.lexicalscope.svm.vm.symb.junit.Fresh;
import com.lexicalscope.svm.vm.symb.junit.SymbVmRule;

public class TestSinglePathDifferentVersionsSymbolicTraceComparison {
   private final PartitionBuilder partition = partition().ofClass(InsidePartition.class);

   @Rule public final SymbVmRule vmRule = new SymbVmRule();
   {
      instrumentPartition(partition, vmRule);
   }

   @LoadFrom(ExamplesOneMarker.class) VmWrap vm1;
   @LoadFrom(ExamplesTwoMarker.class) VmWrap vm2;

   private @Fresh ISymbol symbol1;
   private @Fresh ISymbol symbol2;

   public static class InsidePartition {
      public int multiply(final int x, final int y){
         return x * y;
      }
   }

   public static class OutsidePartition {
      public int entry(final int x, final int y) {
         return new InsidePartition().multiply(x, y);
      }
   }

   @TestEntryPoint public static int callSomeMethods(final int x, final int y) {
      return new OutsidePartition().entry(x, y);
   }

   @Test public void traceFromEquivalentVersionsIsEquivalent() throws Exception {
      vm1.execute(symbol1, symbol2);
      vm2.execute(symbol1, symbol2);

      assertThat(
            vm1.getMeta(TRACE),
            equivalentTo(vmRule, vm2.getMeta(TRACE)));
   }
}
