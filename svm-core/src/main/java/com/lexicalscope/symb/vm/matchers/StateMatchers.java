package com.lexicalscope.symb.vm.matchers;

import static com.lexicalscope.MatchersAdditional.after;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.CombinableMatcher.both;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.lexicalscope.MatchersAdditional;
import com.lexicalscope.MatchersAdditional.CollectionMatcherBuilder;
import com.lexicalscope.MatchersAdditional.TransformMatcherBuilder;
import com.lexicalscope.MemoizeTransform;
import com.lexicalscope.Transform;
import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.FlowNode;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.TerminateInstruction;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.SymbolMatchers;
import com.lexicalscope.symb.z3.FeasibilityChecker;

public class StateMatchers {
   public static Matcher<State> operandEqual(final Object expected) {
      return operandMatching(equalTo(expected));
   }

   public static class SimplifyingMatcherBuilder {
      private final FeasibilityChecker feasibilityChecker;

      public SimplifyingMatcherBuilder(final FeasibilityChecker feasibilityChecker) {
         this.feasibilityChecker = feasibilityChecker;
      }

      public Matcher<? super State> toInt(final int expectedValue) {
         return after(stateToModel(feasibilityChecker)).matches(SymbolMatchers.symbolEquivalentTo(expectedValue));
      }
   }

   public static SimplifyingMatcherBuilder resultSimplifies(
         final FeasibilityChecker feasibilityChecker) {
      return new SimplifyingMatcherBuilder(feasibilityChecker);
   }

   public static Transform<Symbol,State> stateToModel(final FeasibilityChecker feasibilityChecker) {
      return new MemoizeTransform<>(new ModelForStateTransform(feasibilityChecker));
   }

   public static CollectionMatcherBuilder<Symbol, FlowNode<State>> flowNodeToModel(final FeasibilityChecker feasibilityChecker) {
      return flowNodeToState().then(stateToModel(feasibilityChecker));
   }

   private static TransformMatcherBuilder<State, FlowNode<State>> flowNodeToState() {
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
            final Object operand = item.op(new PeekOperandOp(), null);
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
                     public InstructionNode eval(Vm<State> vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
                        return (InstructionNode) stackFrame.instruction();
                     }
                  }, null);
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
            final int actualSize = item.op(new Op<Integer>() {
               @Override public Integer eval(Vm<State> vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
                  return stack.size();
               }
            }, null);
            mismatchDescription.appendText("state with ")
            .appendValue(actualSize).appendText(" stack frames");
            return equalTo(expectedSize).matches(actualSize);
         }
      };
   }

   public static Matcher<? super FlowNode<State>> normalTerminiationWithResult(final Object result) {
      return both(normalTerminiation()).and(flowNodeToState().matches(operandEqual(result)));
   }

   public static Matcher<? super State> normalTerminiationWithResultMatching(final Matcher<Object> matcher) {
      return both(normalTerminiation()).and(flowNodeToState().matches(operandMatching(matcher)));
   }

   public static Matcher<? super FlowNode<State>> normalTerminiation() {
      return flowNodeToState().matches(both(instructionEqual(new TerminateInstruction())).and(stackSize(1)));
   }
}
