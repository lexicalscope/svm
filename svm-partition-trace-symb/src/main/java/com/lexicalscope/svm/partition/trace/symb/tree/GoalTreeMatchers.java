package com.lexicalscope.svm.partition.trace.symb.tree;

import static org.hamcrest.core.CombinableMatcher.both;

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

   public static <T> Matcher<GoalTree<T, ?>> hasReached(final T goal) {
      return new TypeSafeDiagnosingMatcher<GoalTree<T, ?>>() {
         @Override public void describeTo(final Description description) {
            description.appendText("reached sub goal ").appendValue(goal);
         }

         @Override protected boolean matchesSafely(
               final GoalTree<T, ?> item,
               final Description mismatchDescription) {
            mismatchDescription.appendValue(item);
            return item.hasReached(goal);
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

   public static Matcher<? super GoalTreeCorrespondence<?, ?>> hasChildCorrespondence(final Matcher<? super GoalTreeCorrespondence<?, ?>> childMatcher) {
      return new TypeSafeDiagnosingMatcher<GoalTreeCorrespondence<?, ?>>() {
         @Override public void describeTo(final Description description) {
            description.appendText("correspondence with child matching ").appendDescriptionOf(childMatcher);
         }

         @Override protected boolean matchesSafely(final GoalTreeCorrespondence<?, ?> item, final Description mismatchDescription) {
            mismatchDescription.appendValue(item);
            return item.hasChild(childMatcher);
         }
      };
   }

   public static Matcher<? super GoalTreeCorrespondence<?, ?>> hasChildCorrespondences(final int count) {
      return new TypeSafeDiagnosingMatcher<GoalTreeCorrespondence<?, ?>>() {
         @Override public void describeTo(final Description description) {
            description.appendText("correspondence with child count ").appendValue(count);
         }

         @Override protected boolean matchesSafely(
               final GoalTreeCorrespondence<?, ?> item,
               final Description mismatchDescription) {
            mismatchDescription.appendValue(item);
            return item.childCount() == count;
         }
      };
   }

   public static Matcher<? super GoalTreeCorrespondence<?, ?>> isOpenLeaf() {
      return both(isOpen()).and(isLeaf());
   }

   public static Matcher<? super GoalTreeCorrespondence<?, ?>> isOpen() {
      return new TypeSafeDiagnosingMatcher<GoalTreeCorrespondence<?, ?>>(){
         @Override public void describeTo(final Description description) {
            description.appendText("correspondence with open nodes");
         }

         @Override protected boolean matchesSafely(final GoalTreeCorrespondence<?, ?> item, final Description mismatchDescription) {
            final boolean result = item.isOpen();
            if(!result) {
               mismatchDescription.appendValue(item);
            }
            return result;
         }};
   }

   public static Matcher<? super GoalTreeCorrespondence<?, ?>> isLeaf() {
      return new TypeSafeDiagnosingMatcher<GoalTreeCorrespondence<?, ?>>(){
         @Override public void describeTo(final Description description) {
            description.appendText("correspondence with no children");
         }

         @Override protected boolean matchesSafely(final GoalTreeCorrespondence<?, ?> item, final Description mismatchDescription) {
            final boolean result = !item.hasChildren();
            if(!result) {
               mismatchDescription.appendValue(item);
            }
            return result;
         }};
   }
}
