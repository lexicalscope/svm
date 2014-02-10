package com.lexicalscope.svm.vm.symb;

import com.lexicalscope.svm.heap.HeapFactory;
import com.lexicalscope.svm.j.instruction.symbolic.PcMetaKey;
import com.lexicalscope.svm.j.instruction.symbolic.SymbInstructionFactory;
import com.lexicalscope.svm.vm.Vm;
import com.lexicalscope.svm.vm.conc.FastHeapFactory;
import com.lexicalscope.svm.vm.conc.JvmBuilder;
import com.lexicalscope.svm.vm.conc.MethodInfo;
import com.lexicalscope.svm.vm.j.State;

public class SymbVmFactory {
   public static Vm<State> symbolicVm(
         final SymbInstructionFactory instructionFactory,
         final MethodInfo entryPoint,
         final Object ... args) {
      return symbolicVmBuilder(instructionFactory).build(entryPoint, args);
   }

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