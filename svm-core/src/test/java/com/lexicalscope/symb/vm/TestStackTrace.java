package com.lexicalscope.symb.vm;

import static com.lexicalscope.MatchersAdditional.containsMatching;
import static com.lexicalscope.symb.vm.SStackTraceMatchers.methodNamed;
import static org.hamcrest.MatcherAssert.assertThat;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.classloader.SMethodName;

public class TestStackTrace {
   @Rule public JUnitRuleMockery context = new JUnitRuleMockery();

   @Mock private StackFrame stackFrame0;
   @Mock private StackFrame stackFrame1;
   @Mock private StackFrame stackFrame2;

   @Mock private SMethod method0;
   @Mock private SMethod method1;
   @Mock private SMethod method2;

   @Mock private SMethodName methodName0;
   @Mock private SMethodName methodName1;
   @Mock private SMethodName methodName2;

   @Test public void stackTraceContainsMethodsCalledInReverseOrder() {
      context.checking(new Expectations(){{
         oneOf(stackFrame0).method(); will(returnValue(method0));
         oneOf(stackFrame1).method(); will(returnValue(method1));
         oneOf(stackFrame2).method(); will(returnValue(method2));

         oneOf(method0).name(); will(returnValue(methodName0));
         oneOf(method1).name(); will(returnValue(methodName1));
         oneOf(method2).name(); will(returnValue(methodName2));
      }});

      final DequeStack stack = new DequeStack();

      stack.push(stackFrame0).push(stackFrame1).push(stackFrame2);

      assertThat(stack.trace(), containsMatching(methodNamed(methodName2), methodNamed(methodName1), methodNamed(methodName0)));
   }
}
