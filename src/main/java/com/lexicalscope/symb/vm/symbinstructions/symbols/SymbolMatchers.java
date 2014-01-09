package com.lexicalscope.symb.vm.symbinstructions.symbols;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.lexicalscope.symb.z3.FeasibilityChecker;
import com.lexicalscope.symb.z3.FeasibilityChecker.ISimplificationResult;

public class SymbolMatchers {
   public static Matcher<Object> simplifiesToInt(final FeasibilityChecker feasbilityChecker, final int expected) {
      return new TypeSafeDiagnosingMatcher<Object>(ISymbol.class) {
         @Override public void describeTo(final Description description) {
            description.appendText("symbol simplifies to ").appendValue(expected);
         }

         @Override protected boolean matchesSafely(final Object item, final Description mismatchDescription) {
            final boolean[] result = new boolean[1];
            feasbilityChecker.simplifyBv32Expr((ISymbol) item, new ISimplificationResult(){
               @Override public void simplifiedToValue(final int actual) {
                  mismatchDescription.appendText("simplified to int ").appendValue(actual);
                  result[0] = actual == expected;
               }

               @Override public void simplified(final ISymbol simplification) {
                  mismatchDescription.appendText("simplified to symbol ").appendValue(simplification);
                  result[0] = false;
               }});
            return result[0];
         }
      };
   }
}
