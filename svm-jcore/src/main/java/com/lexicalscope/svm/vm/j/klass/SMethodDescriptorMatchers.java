package com.lexicalscope.svm.vm.j.klass;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class SMethodDescriptorMatchers {
   public static Matcher<SMethodDescriptor> anyMethod() {
      return new TypeSafeDiagnosingMatcher<SMethodDescriptor>() {
         @Override public void describeTo(final Description description) {
            description.appendText("all methods");
         }

         @Override protected boolean matchesSafely(final SMethodDescriptor item, final Description mismatchDescription) {
            return true;
         }
      };
   }
}
