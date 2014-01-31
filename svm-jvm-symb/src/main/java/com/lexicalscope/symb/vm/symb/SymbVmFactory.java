package com.lexicalscope.symb.vm.symb;

import com.lexicalscope.svm.j.instruction.symbolic.SymbInstructionFactory;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.conc.JvmBuilder;
import com.lexicalscope.symb.vm.conc.MethodInfo;
import com.lexicalscope.symb.vm.j.State;

public class SymbVmFactory {
   public static Vm<State> symbolicVm(
         final SymbInstructionFactory instructionFactory,
         final MethodInfo entryPoint,
         final Object ... args) {
      return new JvmBuilder()
         .instructionFactory(instructionFactory)
         .heapFactory(new CheckingSymbolicHeapFactory())
         .build2(entryPoint, args);
   }
}
