package com.lexicalscope.svm.partition.trace.symb.tree;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;

public class GoalTreeMatchers {
   public static Matcher<GoalTree<?, ?>> childrenCover(final BoolSymbol pc) {
      return new TypeSafeDiagnosingMatcher<GoalTree<?, ?>>() {
         @Override public void describeTo(final Description description) {
            description.appendText("node with chidren that cover ").appendValue(pc);
         }

         @Override protected boolean matchesSafely(final GoalTree<?, ?> item, final Description mismatchDescription) {
            mismatchDescription.appendValue(item);
            return item.childrenCover(pc);
         }
      };
   }

   public static Matcher<GoalTree<?, ?>> hasChild(final Matcher<? super GoalTree<?, ?>> childMatcher) {
      return new TypeSafeDiagnosingMatcher<GoalTree<?, ?>>() {
         @Override public void describeTo(final Description description) {
            description.appendText("child matching ").appendDescriptionOf(childMatcher);
         }

         @Override protected boolean matchesSafely(final GoalTree<?, ?> item, final Description mismatchDescription) {
            mismatchDescription.appendValue(item);
            return item.hasChild(childMatcher);
         }
      };
   }

   public static <S> Matcher<GoalTree<?, S>> hasOpenNode(final Matcher<S> childMatcher) {
      return new TypeSafeDiagnosingMatcher<GoalTree<?, S>>() {
         @Override public void describeTo(final Description description) {
            description.appendText("child matching ").appendDescriptionOf(childMatcher);
         }

         @Override protected boolean matchesSafely(final GoalTree<?, S> item, final Description mismatchDescription) {
            mismatchDescription.appendValue(item);
            return item.hasOpenNode(childMatcher);
         }
      };
   }

   public static Matcher<InputSubset> covers(final BoolSymbol pc) {
      return new TypeSafeDiagnosingMatcher<InputSubset>() {
         @Override public void describeTo(final Description description) {
            description.appendText("goal tree that covers ").appendValue(pc);
         }

         @Override protected boolean matchesSafely(final InputSubset item, final Description mismatchDescription) {
            mismatchDescription.appendText("goal tree that covers ").appendValue(item.pc());
            return item.covers(pc);
         }
      };
   }
}
