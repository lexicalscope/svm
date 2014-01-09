package com.lexicalscope.symb.vm.matchers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.CombinableMatcher.both;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.StackOp;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.TerminateInstruction;
import com.lexicalscope.symb.vm.symbinstructions.Pc;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.z3.FeasibilityChecker;
import com.lexicalscope.symb.z3.FeasibilityChecker.ISimplificationResult;

public class StateMatchers {
   public static Matcher<? super State> operandEqual(final Object expected) {
      return operandMatching(equalTo(expected));
   }

   public static Matcher<? super State> resultSimplifiesToInt(
         final FeasibilityChecker feasibilityChecker,
         final int expectedValue) {
      return new TypeSafeDiagnosingMatcher<State>(State.class) {
         @Override
         public void describeTo(final Description description) {
            description.appendText(
                  "state with top of operand stack that simplifies to ")
                  .appendValue(expectedValue);
         }

         @Override
         protected boolean matchesSafely(final State item,
               final Description mismatchDescription) {
            final Object operand = item.op(new PeekOperandOp());
            if(operand instanceof Integer) {
               return expectedValue == (int) operand;
            }

            final Pc pc = (Pc) item.getMeta();

            final boolean[] result = new boolean[1];
            feasibilityChecker.simplifyBv32Expr((ISymbol) operand, pc, new ISimplificationResult(){
               @Override public void simplifiedToValue(final int value) {
                  mismatchDescription.appendText("state with top of operand stack simplifies to ").appendValue(value);
                  result[0] = value == expectedValue;
               }

               @Override public void simplified(final ISymbol simplification) {
                  mismatchDescription.appendText("state with top of operand stack simplifies to ").appendValue(simplification);
                  result[0] = false;
               }});

            return result[0];
         }
      };
   }

   private static Matcher<? super State> operandMatching(final Matcher<Object> expectedMatcher) {
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
            final Object operand = item.op(new PeekOperandOp());
            mismatchDescription.appendText("state with top of operand stack ");
            expectedMatcher.describeMismatch(operand, mismatchDescription);
            return expectedMatcher.matches(operand);
         }
      };
   }

   public static Matcher<? super State> instructionEqual(final InstructionNode expectedInstruction) {
      return new TypeSafeDiagnosingMatcher<State>(State.class) {
         @Override
         public void describeTo(final Description description) {
            description.appendText("state with next instruction equal to ")
            .appendValue(expectedInstruction);
         }

         @Override
         protected boolean matchesSafely(final State item,
               final Description mismatchDescription) {
            final InstructionNode instruction = item
                  .op(new Op<InstructionNode>() {
                     @Override
                     public InstructionNode eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
                        return stackFrame.instruction();
                     }
                  });
            mismatchDescription.appendText("state with next instruction ")
            .appendValue(instruction);
            return equalTo(expectedInstruction).matches(instruction);
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
            final int actualSize = item.op(new StackOp<Integer>() {
               @Override
               public Integer eval(final Stack stack) {
                  return stack.size();
               }
            });
            mismatchDescription.appendText("state with ")
            .appendValue(actualSize).appendText(" stack frames");
            return equalTo(expectedSize).matches(actualSize);
         }
      };
   }

   public static Matcher<? super State> normalTerminiationWithResult(final Object result) {
      return both(normalTerminiation()).and(operandEqual(result));
   }

   public static Matcher<? super State> normalTerminiationWithResultMatching(final Matcher<Object> matcher) {
      return both(normalTerminiation()).and(operandMatching(matcher));
   }

   public static Matcher<? super State> normalTerminiation() {
      return both(instructionEqual(new TerminateInstruction())).and(stackSize(1));
   }
}
