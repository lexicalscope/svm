package com.lexicalscope.symb.vm;

import static com.lexicalscope.MatchersAdditional.containsMatching;
import static com.lexicalscope.symb.stack.trace.SStackTraceMatchers.methodNamed;
import static org.hamcrest.MatcherAssert.assertThat;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.symb.state.SMethodName;
import com.lexicalscope.symb.vm.DequeStack;
import com.lexicalscope.symb.vm.StackFrame;

public class TestStackTrace {
   @Rule public JUnitRuleMockery context = new JUnitRuleMockery();

   @Mock private StackFrame stackFrame0;
   @Mock private StackFrame stackFrame1;
   @Mock private StackFrame stackFrame2;

   @Mock private SMethodName methodName0;
   @Mock private SMethodName methodName1;
   @Mock private SMethodName methodName2;

   @Test public void stackTraceContainsMethodsCalledInReverseOrder() {
      context.checking(new Expectations(){{
         oneOf(stackFrame0).methodName(); will(returnValue(methodName0));
         oneOf(stackFrame1).methodName(); will(returnValue(methodName1));
         oneOf(stackFrame2).methodName(); will(returnValue(methodName2));
      }});

      final DequeStack stack = new DequeStack();

      stack.push(stackFrame0).push(stackFrame1).push(stackFrame2);

      assertThat(stack.trace(), containsMatching(methodNamed(methodName2), methodNamed(methodName1), methodNamed(methodName0)));
   }
}
