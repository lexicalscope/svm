package com.lexicalscope.svm.partition.trace.symb.search.fakes;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;


public class FakeExecutionBuilder {
   public static FakeState term(final String name) {
      return new FakeStateTerminal(name);
   }

   public static FakeState goal(final String name, final FakeState successor) {
      return new FakeStateGoal(name, successor);
   }

   public static Matcher<FakeState> goalCalled(final String name) {
      return new TypeSafeDiagnosingMatcher<FakeState>() {
         @Override public void describeTo(final Description description) {
            description.appendText(String.format("GOAL(%)", name));
         }

         @Override protected boolean matchesSafely(final FakeState item, final Description mismatchDescription) {
            if(item instanceof FakeStateGoal) {
               final FakeStateGoal expectedGoal = (FakeStateGoal) item;
               mismatchDescription.appendValue(expectedGoal);
               return expectedGoal.name().equals(name);
            }
            mismatchDescription.appendValue(item);
            return false;
         }
      };
   }

   public static FakeExecuteToBranch branch(final FakeState leftSuccessor, final FakeState rightSuccessor) {
      return new FakeExecuteToBranch(leftSuccessor, rightSuccessor);
   }
}
