package com.lexicalscope.symb.vm.classloader;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class SClassMatchers {
   public static int withIndex(final int i) {
      return i;
   }

   public static Matcher<? super SClass> hasField(final String name, final int withIndex) {
      return hasField(null, name, withIndex);
   }

   public static Matcher<? super SClass> hasStaticField(final String name, final int withIndex) {
      return new TypeSafeDiagnosingMatcher<SClass>(SClass.class) {
         @Override
         public void describeTo(final Description description) {
            description.appendText("field called ").appendValue(name).appendText(" at index ").appendValue(withIndex);
         }

         @Override
         protected boolean matchesSafely(final SClass item, final Description mismatchDescription) {
            final String definedIn = item.name();
            final SFieldName qualifiedFieldName = new SFieldName(definedIn, name);

            mismatchDescription.appendValue(item);
            return item.hasStaticField(qualifiedFieldName) && item.staticFieldIndex(qualifiedFieldName) == withIndex;
         }
      };
   }

   public static Matcher<? super SClass> hasField(final SClass definingClass, final String name, final int withIndex) {
      return new TypeSafeDiagnosingMatcher<SClass>(SClass.class) {
         @Override
         public void describeTo(final Description description) {
            description.appendText("field called ").appendValue(name).appendText(" at index ").appendValue(withIndex);
         }

         @Override
         protected boolean matchesSafely(final SClass item, final Description mismatchDescription) {
            final String definedIn = (definingClass != null ? definingClass : item).name();
            final SFieldName qualifiedFieldName = new SFieldName(definedIn, name);

            mismatchDescription.appendValue(item);
            return item.hasField(qualifiedFieldName) && item.fieldIndex(qualifiedFieldName) == withIndex;
         }
      };
   }

   public static Matcher<? super SClass> hasSuperclass(final Matcher<SClass> matcher) {
      return new TypeSafeDiagnosingMatcher<SClass>(SClass.class) {
         @Override
         public void describeTo(final Description description) {
            description.appendText("class with superclass matching ").appendDescriptionOf(matcher);
         }

         @Override
         protected boolean matchesSafely(final SClass item, final Description mismatchDescription) {
            mismatchDescription.appendText("class with superclass ").appendValue(item.superclass());
            return matcher.matches(item.superclass());
         }};
   }
}
