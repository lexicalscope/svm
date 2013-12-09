package com.lexicalscope.symb.vm.matchers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.CombinableMatcher.both;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.TerminateInstruction;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;
import com.lexicalscope.symb.vm.instructions.ops.StackOp;

public class StateMatchers {
	public static Matcher<? super State> operandEqual(final Object val) {
		return new TypeSafeDiagnosingMatcher<State>(State.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendText(
						"state with top of operand stack equal to ")
						.appendValue(val);
			}

			@Override
			protected boolean matchesSafely(final State item,
					final Description mismatchDescription) {
				final Object operand = item
						.op(new StackFrameOp<Object>() {
							@Override
							public Object eval(final StackFrame stackFrame) {
								return stackFrame.peek();
							}
						});
				mismatchDescription.appendText(
						"state with top of operand stack equal to ")
						.appendValue(operand);
				return equalTo(val).matches(operand);
			}
		};
	}

	public static Matcher<? super State> instructionEqual(
			final InstructionNode expectedInstruction) {
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
						.op(new StackFrameOp<InstructionNode>() {
							@Override
							public InstructionNode eval(final StackFrame stackFrame) {
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

	public static Matcher<? super State> normalTerminiation() {
		return both(instructionEqual(new TerminateInstruction())).and(stackSize(1));
	}
}
