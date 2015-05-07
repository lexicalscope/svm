package com.lexicalscope.svm.classloading;

import static com.lexicalscope.svm.vm.j.InstructionCode.*;
import static com.lexicalscope.svm.vm.j.InstructionSequenceMatcher.instructionSequence;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.klass.SMethod;

public class TestLineNumbers {
   private final SClassLoader sClassLoader = new AsmSClassLoader();

   public static class LineNumbers {
      public int aMethod(
            final int x,
            final int y) {
         if(x == y) {
            return 10;
         }
         return 11;
      }
   }

   final SMethod virtualMethod = sClassLoader.virtualMethod(LineNumbers.class, "aMethod", "(II)I");

   @Test public void canObtainMethodNamesOfVirtualMethod() {
      final Instruction entry = virtualMethod.entry();

      assertThat(entry, instructionSequence(
            methodentry,
            iload,
            iload,
            ificmpne,
            bipush,
            return1,
            bipush,
            return1,
            methodexit));
   }
}
