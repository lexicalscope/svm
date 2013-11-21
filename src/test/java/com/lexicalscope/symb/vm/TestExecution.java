package com.lexicalscope.symb.vm;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.lexicalscope.symb.vm.instructions.Terminate;

public class TestExecution {
   @Test public void executeEmptyMainMethod()  {
      final State initial = State.initial("com/lexicalscope/symb/vm/EmptyStaticMethod", "main", "()V").loadConst(1).loadConst(1);

      assertNormalTerminiation(new Vm().execute(initial));
   }

   @Test public void executeStaticAddMethod()  {
      final State initial = State.initial("com/lexicalscope/symb/vm/StaticAddMethod", "add", "(II)I").loadConst(1).loadConst(2);
      final State result = new Vm().execute(initial);

      assertThat(result.peekOperand(), equalTo((Object) 3)) ;
      assertNormalTerminiation(result.popOperand());
   }

   void foo()
   {
      StaticAddMethod.add(1, 2);
   }

   private void assertNormalTerminiation(final State result) {
      assertThat(result.stack(), equalTo(new Stack(new Terminate())));
   }
}
