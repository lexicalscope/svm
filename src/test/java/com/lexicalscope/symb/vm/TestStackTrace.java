package com.lexicalscope.symb.vm;

import static com.lexicalscope.MatchersAdditional.containsMatching;
import static com.lexicalscope.symb.vm.SStackTraceMatchers.methodNamed;
import static org.hamcrest.MatcherAssert.assertThat;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.MatchersAdditional;
import com.lexicalscope.symb.vm.classloader.SMethodName;

public class TestStackTrace {
   @Rule public JUnitRuleMockery context = new JUnitRuleMockery();

   @Mock private StackFrame stackFrame0;
   @Mock private StackFrame stackFrame1;
   @Mock private StackFrame stackFrame2;

   private final SMethodName name0 = new SMethodName("Foo0", "bar0", "()V");
   private final SMethodName name1 = new SMethodName("Foo1", "bar1", "()V");
   private final SMethodName name2 = new SMethodName("Foo2", "bar2", "()V");
   
   @Test public void foo() {
      context.checking(new Expectations(){{
         oneOf(stackFrame0).method(); will(returnValue(name0));
         oneOf(stackFrame1).method(); will(returnValue(name1));
         oneOf(stackFrame2).method(); will(returnValue(name2));
      }});
      
      final DequeStack stack = new DequeStack();

      stack.push(stackFrame0).push(stackFrame1).push(stackFrame2);

      assertThat(stack.trace(), containsMatching(methodNamed(name2), methodNamed(name1), methodNamed(name0)));
   }
}
