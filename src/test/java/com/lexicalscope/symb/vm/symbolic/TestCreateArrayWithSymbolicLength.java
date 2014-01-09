package com.lexicalscope.symb.vm.symbolic;

import static com.lexicalscope.symb.vm.matchers.StateMatchers.resultSimplifiesToInt;
import static java.lang.Math.min;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.junit.junitautocloseable.AutoCloseRule;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.MethodInfo;
import com.lexicalscope.symb.vm.symbinstructions.SymbInstructionFactory;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.z3.FeasibilityChecker;

public class TestCreateArrayWithSymbolicLength {
   @Rule public AutoCloseRule autoCloseRule = new AutoCloseRule();
   private final FeasibilityChecker feasbilityChecker = new FeasibilityChecker();
   private final SymbInstructionFactory instructionFactory = new SymbInstructionFactory(feasbilityChecker);

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
         result[array.length - 1 - i] = array[i];
      }

      return result[0];
   }

   @Test public void createArrayWithSymbolicLength() throws Exception {
      final ISymbol symbol1 = instructionFactory.symbol();

      final Vm vm = Vm.vm(instructionFactory, createMethod, symbol1);
      vm.execute();
   }

   @Test public void fillArrayWithSymbolicLength() throws Exception {
      final ISymbol symbol1 = instructionFactory.symbol();

      final Vm vm = Vm.vm(instructionFactory, fillMethod, symbol1);
      vm.execute();
   }

   @Test public void copyBetweenArraysWithSymbolicLength() throws Exception {
      final ISymbol symbol1 = instructionFactory.symbol();

      final Vm vm = Vm.vm(instructionFactory, reverseMethod, symbol1);
      vm.execute();

      for (int i = -3; i < 10; i++) {
         System.out.println(reverseArrayWithSymbolicLength(i));
      }

      //      System.out.println(reverseArrayWithSymbolicLength(4));
      //      System.out.println(vm.results());
      // !!!!!!!!!!!!! Need to assert PC, not just try to simplify state. PC has constraints
      // on i0 which are needed to fully simplify the final symbolic state
      final Collection<State> results = vm.results();
      for (final State state : results) {
      }

      assertThat(results, hasItem(resultSimplifiesToInt(feasbilityChecker, 3)));
   }
}
