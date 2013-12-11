package com.lexicalscope;

import org.hamcrest.Matchers;

public class GeneralMatchers {
   @SafeVarargs public static <E> org.hamcrest.Matcher<java.lang.Iterable<? extends E>> containsMatching(final org.hamcrest.Matcher<? super E>... itemMatchers) {
      return Matchers.contains(itemMatchers);
   }
}
