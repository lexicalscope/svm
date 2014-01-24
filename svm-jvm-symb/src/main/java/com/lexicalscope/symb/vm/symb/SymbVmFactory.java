package com.lexicalscope.symb.vm.symb;

import com.lexicalscope.svm.j.instruction.factory.BaseInstructions;
import com.lexicalscope.svm.j.instruction.symbolic.SymbInstructionFactory;
import com.lexicalscope.svm.j.natives.DefaultNativeMethods;
import com.lexicalscope.symb.classloading.AsmSClassLoader;
import com.lexicalscope.symb.classloading.SClassLoader;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.VmImpl;
import com.lexicalscope.symb.vm.conc.MethodInfo;
import com.lexicalscope.symb.vm.conc.VmFactory;
import com.lexicalscope.symb.vm.j.State;

public class SymbVmFactory {

   public static Vm<State> symbolicVm(
         final SymbInstructionFactory instructionFactory,
         final MethodInfo entryPoint,
         final Object ... args) {
      final SClassLoader classLoader = new AsmSClassLoader(instructionFactory, DefaultNativeMethods.natives());

      final Vm<State> vm = new VmImpl<State>();
      vm.initial(VmFactory.initial(vm, new CheckingSymbolicHeapFactory(), classLoader, new BaseInstructions(instructionFactory), entryPoint, args));
      return vm;
   }

}
