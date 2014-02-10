package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.MatchersAdditional.has;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.core.CombinableMatcher.both;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.lexicalscope.svm.stack.trace.SMethodName;
import com.lexicalscope.svm.vm.j.code.AsmSMethodName;

public class TraceMatchers {
   public static Matcher<Trace> methodCallOf(
         final Class<?> klass,
         final String methodName,
         final String desc) {
      return methodCallOf(new AsmSMethodName(klass, methodName, desc), someReceiver());
   }

   @SafeVarargs
   public static Matcher<Trace> methodCallOf(
         final Class<?> klass,
         final String methodName,
         final String desc,
         final Matcher<Object>... matchers) {
      return methodCallOf(new AsmSMethodName(klass, methodName, desc), matchers);
   }

   public static Matcher<Trace> methodCallOf(
         final SMethodName methodName) {
      return methodCallOf(methodName, someReceiver());
   }

   @SafeVarargs
   public static Matcher<Trace> methodCallOf(
         final SMethodName methodName,
         final Matcher<Object>... matchers) {
      return both(traceWithName(methodName)).and(traceIsCall()).and(argumentsMatch(matchers));
   }

   @SafeVarargs
   private static Matcher<? super Trace> argumentsMatch(final Matcher<Object> ... matchers) {
      return new TypeSafeDiagnosingMatcher<Trace>(){
         @Override public void describeTo(final Description description) {
            description.appendText("arguments are ");
            String separator = "";
            for (final Matcher<Object> matcher : matchers) {
               description.appendText(separator).appendDescriptionOf(matcher);
               separator = ", ";
            }
            if(matchers.length == 0) {
               description.appendText("none");
            }
         }

         @Override protected boolean matchesSafely(final Trace item, final Description mismatchDescription) {
            final Object[] args = item.args();
            final Matcher<Iterable<Object>> argsMatcher = has(matchers).only().inOrder();
            if(argsMatcher.matches(asList(args))) {
               return true;
            }
            argsMatcher.describeMismatch(args, mismatchDescription);
            return false;
         }};
   }

   public static Matcher<Trace> methodReturnOf(
         final Class<?> klass,
         final String methodName,
         final String desc) {
      return methodReturnOf(new AsmSMethodName(klass, methodName, desc), noArgs());
   }

   @SafeVarargs
   public static Matcher<Trace> methodReturnOf(
         final Class<?> klass,
         final String methodName,
         final String desc,
         final Matcher<Object> ... matchers) {
      return methodReturnOf(new AsmSMethodName(klass, methodName, desc), matchers);
   }

   @SafeVarargs
   public static Matcher<Trace> methodReturnOf(
         final SMethodName methodName,
         final Matcher<Object> ... matchers) {
      return both(traceWithName(methodName)).and(traceIsReturn()).and(argumentsMatch(matchers));
   }

   private static Matcher<? super Trace> traceIsReturn() {
      return new TypeSafeDiagnosingMatcher<Trace>() {
         @Override public void describeTo(final Description description) {
            description.appendText("method return");
         }

         @Override protected boolean matchesSafely(final Trace item, final Description mismatchDescription) {
            if(item.isCall()) {
               mismatchDescription.appendText("method call");
               return false;
            }
            return true;
         }
      };
   }

   private static Matcher<? super Trace> traceIsCall() {
      return new TypeSafeDiagnosingMatcher<Trace>() {
         @Override public void describeTo(final Description description) {
            description.appendText("method call");
         }

         @Override protected boolean matchesSafely(final Trace item, final Description mismatchDescription) {
            if(item.isCall()) {
               return true;
            }
            mismatchDescription.appendText("method return");
            return false;
         }
      };
   }

   private static TypeSafeDiagnosingMatcher<Trace> traceWithName(final SMethodName methodName) {
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

   @SuppressWarnings("unchecked")
   private static Matcher<Object>[] noArgs() {
      return (Matcher<Object>[]) new Matcher<?>[0];
   }

   private static Matcher<Object> someReceiver() {
      return any(Object.class);
   }
}
