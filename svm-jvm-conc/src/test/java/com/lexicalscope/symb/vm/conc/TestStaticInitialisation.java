package com.lexicalscope.symb.vm.conc;

import static com.lexicalscope.symb.vm.conc.VmFactory.concreteVm;
import static com.lexicalscope.symb.vm.j.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.conc.MethodInfo;
import com.lexicalscope.symb.vm.j.State;

public class TestStaticInitialisation {
   private final MethodInfo returnStaticFieldValue = new MethodInfo(StaticField.class, "getX", "()I");

   public static class StaticField {
      public static int x = 5;

      public static int getX() {
         return x;
      }

      public static void setX(final int x) {
         StaticField.x = x;
      }
   }

   @Test public void getStaticFieldViaStaticMethod() {
      final Vm<State> vm = concreteVm(returnStaticFieldValue);
      assertThat(vm.execute(), normalTerminiationWithResult(5));
   }
}
