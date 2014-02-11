package com.lexicalscope.svm.vm.symb;

import com.lexicalscope.svm.heap.HeapFactory;
import com.lexicalscope.svm.j.instruction.symbolic.PcMetaKey;
import com.lexicalscope.svm.j.instruction.symbolic.SymbInstructionFactory;
import com.lexicalscope.svm.vm.conc.FastHeapFactory;
import com.lexicalscope.svm.vm.conc.JvmBuilder;

public class SymbVmFactory {
   public static JvmBuilder symbolicVmBuilder(final SymbInstructionFactory instructionFactory) {
      final HeapFactory heapFactory;
      if(JvmBuilder.class.desiredAssertionStatus()) {
         heapFactory = new CheckingSymbolicHeapFactory();
      } else {
         heapFactory = new FastHeapFactory();
      }

      final JvmBuilder vmBuilder = new JvmBuilder()
         .instructionFactory(instructionFactory)
         .heapFactory(heapFactory)
         .meta(PcMetaKey.PC, instructionFactory.initialMeta());
      return vmBuilder;
   }
}
