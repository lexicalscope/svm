package com.lexicalscope.svm.vm.j;

import static com.lexicalscope.svm.vm.j.InstructionCode.methodexit;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public final class InstructionSequenceMatcher extends TypeSafeDiagnosingMatcher<Instruction> {
   private final InstructionCode[] codes;

   InstructionSequenceMatcher(final InstructionCode[] codes) {
      this.codes = codes;
   }

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

   public static Matcher<Instruction> instructionSequence(final InstructionCode ... codes) {
      return new InstructionSequenceMatcher(codes);
   }
}