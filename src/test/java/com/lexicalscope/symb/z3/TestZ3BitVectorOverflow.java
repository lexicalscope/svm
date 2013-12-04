package com.lexicalscope.symb.z3;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.junit.junitautocloseable.AutoCloseRule;
import com.lexicalscope.symb.vm.symbinstructions.symbols.AddSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;
import com.microsoft.z3.Z3Exception;

public class TestZ3BitVectorOverflow {
   @Rule public AutoCloseRule autoCloseRule = new AutoCloseRule();
   private final FeasbilityChecker feasbilityChecker = new FeasbilityChecker();

   @Test
   public void testZ3IsWorking() throws Z3Exception {
      assertThat(feasbilityChecker.simplifyBv32Expr(new AddSymbol(new IConstSymbol(3), new IConstSymbol(3))), equalTo(6));
   }

   @Test
   public void testMaxIntPlus1() throws Z3Exception {
      assertThat(Integer.MAX_VALUE + 1, equalTo(Integer.MIN_VALUE));
      assertThat(feasbilityChecker.simplifyBv32Expr(new AddSymbol(new IConstSymbol(Integer.MAX_VALUE), new IConstSymbol(1))), equalTo(Integer.MAX_VALUE + 1));
   }
}
