package com.lexicalscope.symb.vm;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.lexicalscope.symb.vm.classloader.SMethodName;

public class SStackTraceMatchers {
   public static Matcher<? super SStackTraceElement> methodNamed(final SMethodName expected) {
      return new TypeSafeDiagnosingMatcher<SStackTraceElement>(SStackTraceElement.class) {
         @Override public void describeTo(Description description) {
            description.appendText("trace element ").appendValue(expected);
         }

         @Override protected boolean matchesSafely(SStackTraceElement actual, Description mismatchDescription) {
            mismatchDescription.appendText("trace element ").appendValue(actual.name());
            return expected.equals(actual.name());
         }
      };
   }
}
