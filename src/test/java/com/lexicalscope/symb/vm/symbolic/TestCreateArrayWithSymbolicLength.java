package com.lexicalscope.symb.vm.symbolic;

import org.junit.Test;

import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.MethodInfo;
import com.lexicalscope.symb.vm.symbinstructions.SymbInstructionFactory;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public class TestCreateArrayWithSymbolicLength {
   final MethodInfo createMethod = new MethodInfo(TestCreateArrayWithSymbolicLength.class, "create", "(I)[Ljava/lang/Object;");
   public static Object[] create(final int length) {
      return new Object[length];
   }

   @Test public void createArrayWithSymbolicLength() throws Exception {
      final SymbInstructionFactory instructionFactory = new SymbInstructionFactory();
      final ISymbol symbol1 = instructionFactory.symbol();

      final Vm vm = Vm.vm(instructionFactory, createMethod, symbol1);
      vm.execute();
   }
}
