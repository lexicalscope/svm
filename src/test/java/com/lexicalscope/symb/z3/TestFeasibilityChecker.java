package com.lexicalscope.symb.z3;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.junit.junitautocloseable.AutoCloseRule;
import com.microsoft.z3.BitVecNum;
import com.microsoft.z3.BitVecSort;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntNum;
import com.microsoft.z3.Sort;
import com.microsoft.z3.Z3Exception;

public class TestFeasibilityChecker {
   @Rule public AutoCloseRule autoCloseRule = new AutoCloseRule();
   private final FeasibilityChecker feasbilityChecker = new FeasibilityChecker();

   @Test
   public void testZ3IsWorking() throws Z3Exception {
      feasbilityChecker.checkZ3IsWorking();
   }

   @Test
   public void testDeclareConstantArrayBv() throws Z3Exception {
      final Context ctx = feasbilityChecker.ctx();
      final BitVecNum zero = ctx.mkBV(0, 32);
      final BitVecNum one = ctx.mkBV(1, 32);
      final BitVecNum two = ctx.mkBV(2, 32);

      final BitVecSort sort = ctx.mkBitVecSort(32);
      final BoolExpr expression = ctx.mkEq(zero, ctx.mkSelect(ctx.mkStore(ctx.mkConstArray(sort, zero), one, two), zero));
      assertThat(expression.toString(), feasbilityChecker.check(expression));
   }

   @Test
   public void testDeclareConstantArrayInt() throws Z3Exception {
      final Context ctx = feasbilityChecker.ctx();
      final IntNum zero = ctx.mkInt(0);
      final IntNum one = ctx.mkInt(1);
      final IntNum two = ctx.mkInt(2);

      final Sort sort = zero.getSort();
      final BoolExpr expression = ctx.mkEq(zero, ctx.mkSelect(ctx.mkStore(ctx.mkConstArray(sort, zero), one, two), zero));
      assertThat(expression.toString(), feasbilityChecker.check(expression));
   }
}
