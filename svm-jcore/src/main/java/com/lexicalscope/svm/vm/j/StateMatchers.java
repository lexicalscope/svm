package com.lexicalscope.svm.vm.j;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.CombinableMatcher.both;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.lexicalscope.MatchersAdditional;
import com.lexicalscope.MatchersAdditional.TransformMatcherBuilder;
import com.lexicalscope.svm.vm.FlowNodeToState;

public class StateMatchers {
   public static Matcher<State> operandEqual(final Object expected) {
      return operandMatching(equalTo(expected));
   }

   @Deprecated
   public static TransformMatcherBuilder<State, State> flowNodeToState() {
      return MatchersAdditional.after(new FlowNodeToState<State>());
   }

   private static Matcher<State> operandMatching(final Matcher<Object> expectedMatcher) {
      return new TypeSafeDiagnosingMatcher<State>(State.class) {
         @Override
         public void describeTo(final Description description) {
            description.appendText(
                  "state with top of operand stack matching ")
                  .appendDescriptionOf(expectedMatcher);
         }

         @Override
         protected boolean matchesSafely(final State item,
               final Description mismatchDescription) {
            final Object operand = item.peekOperand();
            mismatchDescription.appendText("state with top of operand stack ");
            expectedMatcher.describeMismatch(operand, mismatchDescription);
            return expectedMatcher.matches(operand);
         }
      };
   }

   public static Matcher<? super State> instructionEqual(final Instruction expectedInstruction) {
      return new TypeSafeDiagnosingMatcher<State>(State.class) {
         @Override
         public void describeTo(final Description description) {
            description.appendText("state with next instruction equal to ")
            .appendValue(expectedInstruction);
         }

         @Override
         protected boolean matchesSafely(final State item,
               final Description mismatchDescription) {
            final Instruction instruction = item.instruction();
            mismatchDescription.appendText("state with next instruction ")
            .appendValue(instruction);
            return equalTo(expectedInstruction).matches(instruction);
         }
      };
   }

   public static Matcher<? super State> terminalInstruction() {
      return new TypeSafeDiagnosingMatcher<State>(State.class) {
         @Override
         public void describeTo(final Description description) {
            description.appendText("instruction with no successor");
         }

         @Override
         protected boolean matchesSafely(final State item,
               final Description mismatchDescription) {
            final Instruction instruction = item.instruction();
            mismatchDescription.appendText("instruction with successor ")
            .appendValue(instruction);
            return !instruction.hasNext();
         }
      };
   }

   public static Matcher<? super State> stackSize(final int expectedSize) {
      return new TypeSafeDiagnosingMatcher<State>(State.class) {
         @Override
         public void describeTo(final Description description) {
            description.appendText("state with ").appendValue(expectedSize)
            .appendText(" stack frames");
         }

         @Override
         protected boolean matchesSafely(final State item,
               final Description mismatchDescription) {
            final int actualSize = item.stack().size();
            mismatchDescription.appendText("state with ")
            .appendValue(actualSize).appendText(" stack frames");
            return equalTo(expectedSize).matches(actualSize);
         }
      };
   }

   public static Matcher<? super State> normalTerminiationWithResult(final Object result) {
      return both(normalTerminiation()).and(flowNodeToState().matches(operandEqual(result)));
   }

   public static Matcher<? super State> normalTerminiationWithResultMatching(final Matcher<Object> matcher) {
      return both(normalTerminiation()).and(flowNodeToState().matches(operandMatching(matcher)));
   }

   public static Matcher<? super State> normalTerminiation() {
      return flowNodeToState().matches(both(terminalInstruction()).and(stackSize(1)));
   }
}
