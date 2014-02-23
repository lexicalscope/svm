package com.lexicalscope.svm.vm.symb;

import static com.lexicalscope.svm.j.instruction.symbolic.PcMetaKey.PC;

import com.lexicalscope.svm.heap.HeapFactory;
import com.lexicalscope.svm.j.instruction.symbolic.SymbInstructionFactory;
import com.lexicalscope.svm.vm.conc.FastHeapFactory;
import com.lexicalscope.svm.vm.conc.JvmBuilder;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class SymbVmFactory {
   public static JvmBuilder symbolicVmBuilder(
         final SymbInstructionFactory instructionFactory,
         final FeasibilityChecker feasibilityChecker) {
      final HeapFactory heapFactory;
      if(JvmBuilder.class.desiredAssertionStatus()) {
         heapFactory = new CheckingSymbolicHeapFactory();
      } else {
         heapFactory = new FastHeapFactory();
      }

      final JvmBuilder vmBuilder = new JvmBuilder()
         .searchWith(new FeasibleBranchSearchFactory(feasibilityChecker))
         .instructionFactory(instructionFactory)
         .heapFactory(heapFactory)
         .meta(PC, instructionFactory.initialMeta());
      return vmBuilder;
   }
}
