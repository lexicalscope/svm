package com.lexicalscope.symb.partition.trace;

import static org.hamcrest.core.CombinableMatcher.both;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.lexicalscope.symb.stack.trace.SMethodName;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;

public class TraceMatchers {
   public static Matcher<Trace> methodCallOf(final Class<?> klass, final String methodName, final String desc) {
      return methodCallOf(new AsmSMethodName(klass, methodName, desc));
   }

   public static Matcher<Trace> methodCallOf(final SMethodName methodName) {
      return both(traceWithName(methodName)).and(traceIsCall());
   }

   public static Matcher<Trace> methodReturnOf(final Class<?> klass, final String methodName, final String desc) {
      return methodReturnOf(new AsmSMethodName(klass, methodName, desc));
   }

   public static Matcher<Trace> methodReturnOf(final SMethodName methodName) {
      return both(traceWithName(methodName)).and(traceIsReturn());
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
}
