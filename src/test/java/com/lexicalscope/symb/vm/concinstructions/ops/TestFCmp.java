package com.lexicalscope.symb.vm.concinstructions.ops;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.lexicalscope.symb.vm.instructions.ops.FCmpGOperator;
import com.lexicalscope.symb.vm.instructions.ops.FCmpLOperator;

public class TestFCmp {
   private static final Object ONE = 1;
   private static final Object MINUS_ONE = -1;

   @Test public void v1GreaterThanV2IsOne() {
      assertThat(new FCmpGOperator().eval(1f, 0f), equalTo(ONE));
      assertThat(new FCmpLOperator().eval(1f, 0f), equalTo(ONE));
   }

   @Test public void v1GreaterLessV2IsMinusOne() {
      assertThat(new FCmpGOperator().eval(0f, 1f), equalTo(MINUS_ONE));
      assertThat(new FCmpLOperator().eval(0f, 1f), equalTo(MINUS_ONE));
   }

   @Test public void numCmpNaNIsCorrect() {
      assertThat(new FCmpGOperator().eval(1f, Float.NaN), equalTo(ONE));
      assertThat(new FCmpLOperator().eval(1f, Float.NaN), equalTo(MINUS_ONE));
      assertThat(new FCmpGOperator().eval(Float.NaN, 1f), equalTo(ONE));
      assertThat(new FCmpLOperator().eval(Float.NaN, 1f), equalTo(MINUS_ONE));
      assertThat(new FCmpGOperator().eval(Float.NaN, Float.NaN), equalTo(ONE));
      assertThat(new FCmpLOperator().eval(Float.NaN, Float.NaN), equalTo(MINUS_ONE));
   }

}
