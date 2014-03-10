package com.lexicalscope.svm.partition.trace.symb.search;

import static com.lexicalscope.svm.partition.trace.PartitionBuilder.partition;
import static com.lexicalscope.svm.partition.trace.PartitionInstrumentation.instrumentPartition;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.examples.ExamplesOneMarker;
import com.lexicalscope.svm.examples.ExamplesTwoMarker;
import com.lexicalscope.svm.examples.isort.broken.OutsidePartition;
import com.lexicalscope.svm.examples.isort.broken.SortedList;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.partition.trace.symb.tree.GuidedStateSearchFactory;
import com.lexicalscope.svm.vm.symb.junit.Fresh;
import com.lexicalscope.svm.vm.symb.junit.SymbVmRule;

public class TestSortedListRefactoringWorking {
   @Rule public final SymbVmRule vm = new SymbVmRule(ExamplesOneMarker.class, ExamplesTwoMarker.class);
   {
      instrumentPartition(partition().ofClass(SortedList.class), vm);
      vm.entryPoint(OutsidePartition.class, "callSomeMethods", "(II)I");
      vm.builder().searchWith(new GuidedStateSearchFactory(vm.feasbilityChecker()));
   }

   private @Fresh ISymbol symbol1;
   private @Fresh ISymbol symbol2;

   @Test @Ignore public void pathsExploredPairwise() throws Exception {
      vm.execute(symbol1, symbol2);
   }
}
