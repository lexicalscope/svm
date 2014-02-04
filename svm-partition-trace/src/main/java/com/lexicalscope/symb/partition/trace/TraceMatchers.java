package com.lexicalscope.symb.partition.trace;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.lexicalscope.symb.stack.trace.SMethodName;

public class TraceMatchers {
   public static Matcher<Trace> methodCallOf(final SMethodName methodName) {
      return new TypeSafeDiagnosingMatcher<Trace>() {
         @Override public void describeTo(final Description description) {
            description.appendText("trace element with name ").appendValue(methodName);
         }

         @Override protected boolean matchesSafely(final Trace item, final Description mismatchDescription) {
            mismatchDescription.appendText("trace element with name ").appendValue(item.methodName());
            return item.methodName().equals(methodName);
         }
      };
   }
}
