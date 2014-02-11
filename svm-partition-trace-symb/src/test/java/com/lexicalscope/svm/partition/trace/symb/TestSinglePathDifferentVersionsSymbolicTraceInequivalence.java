package com.lexicalscope.svm.partition.trace.symb;

import static com.lexicalscope.svm.partition.trace.PartitionBuilder.partition;
import static com.lexicalscope.svm.partition.trace.PartitionInstrumentation.instrumentPartition;
import static com.lexicalscope.svm.partition.trace.TraceMetaKey.TRACE;
import static com.lexicalscope.svm.partition.trace.symb.SymbolicTraceMatchers.equivalentTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.examples.ExamplesOneMarker;
import com.lexicalscope.svm.examples.ExamplesTwoMarker;
import com.lexicalscope.svm.examples.doubler.broken.InsidePartition;
import com.lexicalscope.svm.examples.doubler.broken.OutsidePartition;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.partition.trace.PartitionBuilder;
import com.lexicalscope.svm.vm.conc.LoadFrom;
import com.lexicalscope.svm.vm.conc.MethodInfo;
import com.lexicalscope.svm.vm.conc.junit.VmWrap;
import com.lexicalscope.svm.vm.symb.junit.Fresh;
import com.lexicalscope.svm.vm.symb.junit.SymbVmRule;

public class TestSinglePathDifferentVersionsSymbolicTraceInequivalence {
   private final PartitionBuilder partition = partition().ofClass(InsidePartition.class);

   @Rule public final SymbVmRule vmRule = new SymbVmRule();
   {
      instrumentPartition(partition, vmRule);
      vmRule.entryPoint(new MethodInfo(OutsidePartition.class, "callSomeMethods", "(I)I"));
   }

   @LoadFrom(ExamplesOneMarker.class) private VmWrap vm1;
   @LoadFrom(ExamplesTwoMarker.class) private VmWrap vm2;

   private @Fresh ISymbol symbol1;
   private @Fresh ISymbol symbol2;

   @Test public void traceFromNonEquivalentVersionsIsNotEquivalent() throws Exception {
      vm1.execute(symbol1, symbol2);
      vm2.execute(symbol1, symbol2);

      assertThat(
            vm1.getMeta(TRACE),
            not(equivalentTo(vmRule, vm2.getMeta(TRACE))));
   }
}
