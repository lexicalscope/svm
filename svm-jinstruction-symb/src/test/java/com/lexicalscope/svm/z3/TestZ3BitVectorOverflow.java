package com.lexicalscope.svm.z3;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.junit.junitautocloseable.AutoCloseRule;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.IAddSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.IConstSymbol;
import com.lexicalscope.svm.z3.FeasibilityChecker;
import com.lexicalscope.svm.z3.FeasibilityChecker.ISimplificationResult;
import com.microsoft.z3.Z3Exception;

public class TestZ3BitVectorOverflow {
   @Rule public final AutoCloseRule autoCloseRule = new AutoCloseRule();
   @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
   @Mock private ISimplificationResult result;

   private final FeasibilityChecker feasbilityChecker = new FeasibilityChecker();

   @Test
   public void testZ3IsWorking() throws Z3Exception {
      context.checking(new Expectations(){{
         oneOf(result).simplifiedToValue(6);
      }});
      feasbilityChecker.simplifyBv32Expr(new IAddSymbol(new IConstSymbol(3), new IConstSymbol(3)), result);
   }

   @Test
   public void testMaxIntPlus1() throws Z3Exception {
      context.checking(new Expectations(){{
         oneOf(result).simplifiedToValue(Integer.MAX_VALUE + 1);
      }});

      assertThat(Integer.MAX_VALUE + 1, equalTo(Integer.MIN_VALUE));
      feasbilityChecker.simplifyBv32Expr(new IAddSymbol(new IConstSymbol(Integer.MAX_VALUE), new IConstSymbol(1)), result);
   }
}
