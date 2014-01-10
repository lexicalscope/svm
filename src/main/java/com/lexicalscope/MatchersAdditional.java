package com.lexicalscope;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class MatchersAdditional {
   public interface Transform<T, S> extends SelfDescribing {
      T transform(S item, Description mismatchDescription);
   }

   public static final class TransformMatcherBuilder<T, S> {
      private final Transform<T, S> transform;

      public TransformMatcherBuilder(final Transform<T, S> transform) {
         this.transform = transform;
      }

      public org.hamcrest.Matcher<S> matches(final org.hamcrest.Matcher<T> matcher) {
         return adaptMatcherWithTransform(transform, matcher);
      }
   }

   @SafeVarargs public static <E> org.hamcrest.Matcher<java.lang.Iterable<? extends E>> containsMatching(final org.hamcrest.Matcher<? super E>... itemMatchers) {
      return Matchers.contains(itemMatchers);
   }

   public static <T, S> org.hamcrest.Matcher<S> adaptMatcherWithTransform(
         final Transform<T, S> transform,
         final org.hamcrest.Matcher<T> matcher) {
      return new TypeSafeDiagnosingMatcher<S>() {
         @Override public void describeTo(final Description description) {
            description
            .appendText("when transformed by ")
            .appendDescriptionOf(transform)
            .appendText(" then ")
            .appendDescriptionOf(matcher);
         }

         @Override protected boolean matchesSafely(final S item, final Description mismatchDescription) {
            mismatchDescription.appendText("when transformed by ");
            final T itemT = transform.transform(item, mismatchDescription);

            mismatchDescription.appendText(" then ");
            matcher.describeMismatch(itemT, mismatchDescription);

            return matcher.matches(itemT);
         }
      };
   }

   public static <T, S> TransformMatcherBuilder<T, S> after(final Transform<T, S> transform) {
      return new TransformMatcherBuilder<>(transform);
   }
}
