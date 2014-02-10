package com.lexicalscope.svm.classloading;

import static com.lexicalscope.svm.vm.j.InstructionCode.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

import com.lexicalscope.svm.classloading.AsmSClassLoader;
import com.lexicalscope.svm.classloading.SClassLoader;
import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.InstructionCode;
import com.lexicalscope.svm.vm.j.code.AsmSMethodName;
import com.lexicalscope.svm.vm.j.klass.SMethod;

public class TestMethodLinking {
   public static class MethodWithTwoReturns {
      public int twoReturns(final int x) {
         if(3 == x) {
            return 1;
         }
         return 2;
      }
   }

   private final SClassLoader sClassLoader = new AsmSClassLoader();
   private final SMethod withTwoReturns = sClassLoader.
         load(MethodWithTwoReturns.class).
         declaredMethod(new AsmSMethodName(MethodWithTwoReturns.class, "twoReturns", "(I)I"));

   @Test public void linkedMethodIsTree() throws Exception {
      final Instruction entry = withTwoReturns.entry();
      assertThat(entry, instructionSequence(
            iconst_3,
            iload,
            ificmpne,
            iconst_1,
            return1,
            iconst_2,
            return1,
            methodexit));
   }

   private Matcher<Instruction> instructionSequence(final InstructionCode ... codes) {
      return new TypeSafeDiagnosingMatcher<Instruction>() {
         @Override public void describeTo(final Description description) {
            description.appendText("instruction sequence ").appendValue(codes);
         }

         @Override protected boolean matchesSafely(final Instruction item, final Description mismatchDescription) {
            Instruction cur = item;

            for (final InstructionCode code : codes) {
               mismatchDescription.appendValue(cur.code());
               if(!cur.code().equals(code)) {
                  return false;
               }
               if(!cur.code().equals(methodexit)) {
                  cur = cur.next();
               } else {
                  break;
               }
            }
            return true;
         }
      };
   }
}
