package com.lexicalscope.symb.vm;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class SStackTraceMatchers {
   public static Matcher<? super SStackTraceElement> methodNamed(final SMethodName expected) {
      return new TypeSafeDiagnosingMatcher<SStackTraceElement>(SStackTraceElement.class) {
         @Override public void describeTo(final Description description) {
            description.appendText("trace element ").appendValue(expected);
         }

         @Override protected boolean matchesSafely(final SStackTraceElement actual, final Description mismatchDescription) {
            mismatchDescription.appendText("trace element ").appendValue(actual.method());
            return expected.equals(actual.method());
         }
      };
   }
}
