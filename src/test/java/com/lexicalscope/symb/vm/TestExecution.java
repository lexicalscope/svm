package com.lexicalscope.symb.vm;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.symb.vm.instructions.Terminate;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;
import com.lexicalscope.symb.vm.matchers.StateMatchers;
import com.lexicalscope.symb.vm.stackFrameOps.PopOperand;

public class TestExecution {
   private final Vm vm = new Vm();
   @Test
   public void executeEmptyMainMethod() {
      final State initial = vm.initial("com/lexicalscope/symb/vm/EmptyStaticMethod", "main", "()V");

      assertNormalTerminiation(vm.execute(initial));
   }

   @Test
   public void executeStaticAddMethod() {
      final State initial = vm.initial("com/lexicalscope/symb/vm/StaticAddMethod", "add", "(II)I");
      initial.op(new StackFrameOp<Void>() {
         @Override
         public Void eval(final StackFrame stackFrame) {
            stackFrame.loadConst(1);
            stackFrame.loadConst(2);
            return null;
         }
      });
      final State result = vm.execute(initial);

      assertThat(result, StateMatchers.operandEqual(3));

      result.op(new PopOperand());
      assertNormalTerminiation(result);
   }

   private void assertNormalTerminiation(final State result) {
      // check stack has one frame
      result.op(new StackFrameOp<Void>() {
         @Override
         public Void eval(final StackFrame stackFrame) {
            assert stackFrame.instruction().equals(new Terminate());
            return null;
         }
      });
   }
}
