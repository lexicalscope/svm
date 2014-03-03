package com.lexicalscope.svm.partition.trace.symb.search;

import static com.lexicalscope.svm.partition.trace.PartitionBuilder.partition;
import static com.lexicalscope.svm.partition.trace.PartitionInstrumentation.instrumentPartition;
import static com.lexicalscope.svm.partition.trace.TraceMetaKey.TRACE;
import static com.lexicalscope.svm.partition.trace.symb.SymbolicTraceMatchers.equivalentTo;
import static com.lexicalscope.svm.vm.conc.junit.ClassStateTag.tag;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.examples.ExamplesOneMarker;
import com.lexicalscope.svm.examples.ExamplesTwoMarker;
import com.lexicalscope.svm.examples.icompare.working.InsidePartition;
import com.lexicalscope.svm.examples.icompare.working.OutsidePartition;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.partition.trace.PartitionBuilder;
import com.lexicalscope.svm.vm.symb.junit.Fresh;
import com.lexicalscope.svm.vm.symb.junit.SymbVmRule;

public class TestGuidedSearch {
   private final PartitionBuilder partition = partition().ofClass(InsidePartition.class);

   @Rule public final SymbVmRule vm = new SymbVmRule(
         ExamplesOneMarker.class,
         ExamplesTwoMarker.class);
   {
      instrumentPartition(partition, vm);
      vm.entryPoint(OutsidePartition.class, "callSomeMethods", "(II)I");
   }

   private @Fresh ISymbol symbol1;
   private @Fresh ISymbol symbol2;

   @Test @Ignore public void pathsExploredPairwise() throws Exception {
      vm.execute(symbol1, symbol2);

      assertThat(
            vm.getMeta(tag(ExamplesOneMarker.class), TRACE),
            equivalentTo(vm, vm.getMeta(tag(ExamplesTwoMarker.class), TRACE)));
   }
}
