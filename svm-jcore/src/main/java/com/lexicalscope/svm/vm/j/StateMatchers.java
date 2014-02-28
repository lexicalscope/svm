package com.lexicalscope.svm.vm.j;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.CombinableMatcher.both;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class StateMatchers {
   public static Matcher<JState> operandEqual(final Object expected) {
      return operandMatching(equalTo(expected));
   }

   private static Matcher<JState> operandMatching(final Matcher<Object> expectedMatcher) {
      return new TypeSafeDiagnosingMatcher<JState>(JState.class) {
         @Override
         public void describeTo(final Description description) {
            description.appendText(
                  "state with top of operand stack matching ")
                  .appendDescriptionOf(expectedMatcher);
         }

         @Override
         protected boolean matchesSafely(final JState item,
               final Description mismatchDescription) {
            final Object operand = item.peekOperand();
            mismatchDescription.appendText("state with top of operand stack ");
            expectedMatcher.describeMismatch(operand, mismatchDescription);
            return expectedMatcher.matches(operand);
         }
      };
   }

   public static Matcher<? super JState> instructionEqual(final Instruction expectedInstruction) {
      return new TypeSafeDiagnosingMatcher<JState>(JState.class) {
         @Override
         public void describeTo(final Description description) {
            description.appendText("state with next instruction equal to ")
            .appendValue(expectedInstruction);
         }

         @Override
         protected boolean matchesSafely(final JState item,
               final Description mismatchDescription) {
            final Instruction instruction = item.instruction();
            mismatchDescription.appendText("state with next instruction ")
            .appendValue(instruction);
            return equalTo(expectedInstruction).matches(instruction);
         }
      };
   }

   public static Matcher<? super JState> terminalInstruction() {
      return new TypeSafeDiagnosingMatcher<JState>(JState.class) {
         @Override
         public void describeTo(final Description description) {
            description.appendText("instruction with no successor");
         }

         @Override
         protected boolean matchesSafely(final JState item,
               final Description mismatchDescription) {
            final Instruction instruction = item.instruction();
            mismatchDescription.appendText("instruction with successor ")
            .appendValue(instruction);
            return !instruction.hasNext();
         }
      };
   }

   public static Matcher<? super JState> stackSize(final int expectedSize) {
      return new TypeSafeDiagnosingMatcher<JState>(JState.class) {
         @Override
         public void describeTo(final Description description) {
            description.appendText("state with ").appendValue(expectedSize)
            .appendText(" stack frames");
         }

         @Override
         protected boolean matchesSafely(final JState item,
               final Description mismatchDescription) {
            final int actualSize = item.stack().size();
            mismatchDescription.appendText("state with ")
            .appendValue(actualSize).appendText(" stack frames");
            return equalTo(expectedSize).matches(actualSize);
         }
      };
   }

   public static Matcher<? super JState> normalTerminiationWithResult(final Object result) {
      return both(normalTerminiation()).and(operandEqual(result));
   }

   public static Matcher<? super JState> normalTerminiationWithResultMatching(final Matcher<Object> matcher) {
      return both(normalTerminiation()).and(operandMatching(matcher));
   }

   public static Matcher<? super JState> normalTerminiation() {
      return both(terminalInstruction()).and(stackSize(1));
   }
}
