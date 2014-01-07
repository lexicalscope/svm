package com.lexicalscope.symb.vm.symbolic;

import static com.lexicalscope.symb.vm.matchers.StateMatchers.normalTerminiationWithResult;
import static java.lang.Math.min;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

import org.junit.Test;

import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.MethodInfo;
import com.lexicalscope.symb.vm.symbinstructions.SymbInstructionFactory;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.MulSymbol;

public class TestCreateArrayWithSymbolicLength {
   final MethodInfo createMethod = new MethodInfo(TestCreateArrayWithSymbolicLength.class, "create", "(I)[Ljava/lang/Object;");
   final MethodInfo fillMethod = new MethodInfo(TestCreateArrayWithSymbolicLength.class, "fillArrayWithSymbolicLength", "(I)[I");
   final MethodInfo reverseMethod = new MethodInfo(TestCreateArrayWithSymbolicLength.class, "reverseArrayWithSymbolicLength", "(I)I");

   public static Object[] create(final int length) {
      return new Object[length];
   }

   public static int[] fillArrayWithSymbolicLength(final int length) {
      final int[] array = new int[length];
      for (int i = 0; i < min(4, array.length); i++) {
         array[i] = i;
      }
      return array;
   }

   public static int reverseArrayWithSymbolicLength(final int length) {
      if(length <= 0) {
         return 0;
      }

      final int[] array = new int[length];
      for (int i = 0; i < min(4, array.length); i++) {
         array[i] = i;
      }

      final int[] result = new int[length];
      for (int i = 0; i < min(4, array.length); i++) {
         result[3 - i] = array[i] * -1;
      }

      return result[0];
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

   @Test public void copyBetweenArraysWithSymbolicLength() throws Exception {
      final SymbInstructionFactory instructionFactory = new SymbInstructionFactory();
      final ISymbol symbol1 = instructionFactory.symbol();

      final Vm vm = Vm.vm(instructionFactory, reverseMethod, symbol1);
      vm.execute();

      assertThat(vm.results(), hasItem(normalTerminiationWithResult(new MulSymbol(symbol1, new IConstSymbol(-1)))));
   }
}
