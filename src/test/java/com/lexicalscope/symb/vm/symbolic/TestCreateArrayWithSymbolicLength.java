package com.lexicalscope.symb.vm.symbolic;

import static java.lang.Math.min;

import org.junit.Test;

import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.MethodInfo;
import com.lexicalscope.symb.vm.symbinstructions.SymbInstructionFactory;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public class TestCreateArrayWithSymbolicLength {
   final MethodInfo createMethod = new MethodInfo(TestCreateArrayWithSymbolicLength.class, "create", "(I)[Ljava/lang/Object;");
   final MethodInfo fillMethod = new MethodInfo(TestCreateArrayWithSymbolicLength.class, "fillArrayWithSymbolicLength", "(I)[Ljava/lang/Object;");

   public static Object[] create(final int length) {
      return new Object[length];
   }

   public static Object[] fillArrayWithSymbolicLength(final int length) {
      final Object[] array = create(length);
      for (int i = 0; i < min(4, array.length); i++) {
         array[i] = i;
      }
      return array;
   }

   @Test public void createArrayWithSymbolicLength() throws Exception {
      final SymbInstructionFactory instructionFactory = new SymbInstructionFactory();
      final ISymbol symbol1 = instructionFactory.symbol();

      final Vm vm = Vm.vm(instructionFactory, createMethod, symbol1);
      vm.execute();

   }

   @Test public void fillArrayWithSymbolicLength() throws Exception {
      final SymbInstructionFactory instructionFactory = new SymbInstructionFactory();
      final ISymbol symbol1 = instructionFactory.symbol();

      final Vm vm = Vm.vm(instructionFactory, fillMethod, symbol1);
      vm.execute();
   }
}
