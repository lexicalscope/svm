package com.lexicalscope.symb.vm.matchers;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;

public class StateMatchers {

   public static Matcher<? super State> operandEqual(final Object val) {
      return new TypeSafeDiagnosingMatcher<State>(State.class) {
         @Override
         public void describeTo(final Description description) {
            description.appendText("state with top of operand stack equal to ").appendValue(val);
         }

         @Override
         protected boolean matchesSafely(final State item, final Description mismatchDescription) {
            final Object operand = (int) item.op(new StackFrameOp<Object>() {
               @Override
               public Object eval(final StackFrame stackFrame) {
                  return (int) stackFrame.peekOperand();
               }
            });
            System.out.println("!!!!! " + operand);
            mismatchDescription.appendText("state with top of operand stack ").appendValue(operand);
            return equalTo(val).matches(operand);
         }
      };
   }

}
