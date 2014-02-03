package com.lexicalscope.symb.vm.conc;

import static com.lexicalscope.symb.vm.j.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.symb.vm.conc.junit.TestEntryPoint;
import com.lexicalscope.symb.vm.conc.junit.VmRule;

public class TestStaticInitialisation {
   @Rule public final VmRule vm = new VmRule();

   public static class StaticField {
      public static int x = 5;

      public static int getX() {
         return x;
      }

      public static void setX(final int x) {
         StaticField.x = x;
      }
   }

   @TestEntryPoint public static int getStaticField() {
      return StaticField.getX();
   }

   @Test public void getStaticFieldViaStaticMethod() {
      assertThat(vm.execute(), normalTerminiationWithResult(5));
   }
}
