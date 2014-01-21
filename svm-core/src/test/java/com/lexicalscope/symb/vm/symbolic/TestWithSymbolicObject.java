package com.lexicalscope.symb.vm.symbolic;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.objectweb.asm.Type;

import com.lexicalscope.junit.junitautocloseable.AutoCloseRule;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.SymbVmFactory;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.conc.MethodInfo;
import com.lexicalscope.symb.vm.symbinstructions.SymbInstructionFactory;
import com.lexicalscope.symb.vm.symbinstructions.symbols.OSymbol;
import com.lexicalscope.symb.z3.FeasibilityChecker;

public class TestWithSymbolicObject {
   @Rule public final AutoCloseRule autoCloseRule = new AutoCloseRule();
   private final FeasibilityChecker feasbilityChecker = new FeasibilityChecker();
   private final SymbInstructionFactory instructionFactory = new SymbInstructionFactory(feasbilityChecker);

   private static final String expressionKlassName = Type.getInternalName(SimpleExpression.class);
   private final MethodInfo createMethod = new MethodInfo(WithSymbolicObject.class, "symbolicObject", "(L" + expressionKlassName + ";)I");

   @Test @Ignore public void createSymbolicObject() throws Exception {
      final OSymbol symbol1 = instructionFactory.osymbol(expressionKlassName);

      final Vm<State> vm = SymbVmFactory.symbolicVm(instructionFactory, createMethod, symbol1);
      vm.execute();
   }
}
