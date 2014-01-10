package com.lexicalscope.symb.vm.symbinstructions.symbols;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;


public class SymbolMatchers {

   public static Matcher<Symbol> symbolEquivalentTo(final int expectedValue) {
      return new TypeSafeDiagnosingMatcher<Symbol>(Symbol.class) {
         @Override
         public void describeTo(final Description description) {
            description.appendText(
                  "symbol equivalent to ")
                  .appendValue(expectedValue);
         }
   
         @Override
         protected boolean matchesSafely(
               final Symbol item,
               final Description mismatchDescription) {
            if(item instanceof IConstSymbol) {
               mismatchDescription.appendText("trivial symbol ").appendValue(item);
               return expectedValue == ((IConstSymbol) item).val();
            }
            mismatchDescription.appendText("non-trivial symbol ").appendValue(item);
            return false;
         }
      };
   }

}
