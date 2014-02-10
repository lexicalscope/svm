package com.lexicalscope.svm.partition.trace.symb;

import java.util.Iterator;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.Symbol;
import com.lexicalscope.svm.partition.trace.Trace;
import com.lexicalscope.svm.partition.trace.TraceBuilder;
import com.lexicalscope.svm.vm.symb.junit.SolverRule;

public class SymbolicTraceMatchers {
   public static Matcher<TraceBuilder> equivalentTo(final SolverRule solver, final TraceBuilder expected) {
      return new TypeSafeDiagnosingMatcher<TraceBuilder>() {
         @Override public void describeTo(final Description description) {
            description.appendText("trace equivalent to ").appendValue(expected);
         }

         @Override protected boolean matchesSafely(final TraceBuilder actual, final Description mismatchDescription) {
            final Iterator<Trace> expectedIterator = expected.build().iterator();
            final Iterator<Trace> actualIterator = actual.build().iterator();
            while(expectedIterator.hasNext() && actualIterator.hasNext()) {
               final Trace expectedTraceElement = expectedIterator.next();
               final Trace actualTraceElement = actualIterator.next();

               if(expectedTraceElement.methodName().equals(actualTraceElement.methodName()) &&
                  expectedTraceElement.isCall() == actualTraceElement.isCall()) {

                  final Object[] actualArgs = actualTraceElement.args();
                  final Object[] expectedArgs = expectedTraceElement.args();
                  if(expectedArgs.length != actualArgs.length) {
                     mismatchDescription
                        .appendText("argument length mismatch ")
                        .appendValue(expectedTraceElement)
                        .appendText(" and ")
                        .appendValue(actualTraceElement);
                     return false;
                  }
                  for (int i = 0; i < expectedArgs.length; i++) {
                     final Object actualArg = actualArgs[i];
                     final Object expectedArg = expectedArgs[i];
                     if(   actualArg instanceof Symbol
                        && expectedArg instanceof Symbol) {
                        if(!solver.equivalant((Symbol) expectedArg, (Symbol) actualArg))
                        {
                           mismatchDescription.appendText("unable to prove equivalence of ")
                              .appendValue(expectedArg)
                              .appendText(" and ")
                              .appendValue(actualArg);
                           return false;
                        }
                     } else if (!actualArg.equals(expectedArg)) {
                        mismatchDescription.appendText("argument mismatch of ")
                           .appendValue(expectedArg.getClass())
                           .appendText(":")
                           .appendValue(expectedArg)
                           .appendText(" and ")
                           .appendValue(actualArg.getClass())
                           .appendText(":")
                           .appendValue(actualArg);
                        return false;
                     }
                  }

               } else {
                  mismatchDescription.appendText("expected ").appendValue(expectedTraceElement);
                  mismatchDescription.appendText("actual ").appendValue(actualTraceElement);
                  return false;
               }
            }
            // when comparing traces we truncate the longest trace to the
            // length of the shortest trace

            // TODO[tim]: take into account program terination in the trace
            return true;
         }
      };
   }
}
