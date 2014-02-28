package com.lexicalscope.svm.vm.symb.matchers;

import static com.lexicalscope.MatchersAdditional.after;

import org.hamcrest.Matcher;

import com.lexicalscope.MatchersAdditional.TransformMatcherBuilder;
import com.lexicalscope.MemoizeTransform;
import com.lexicalscope.Transform;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.Symbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.SymbolMatchers;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class SymbStateMatchers {

   public static class SimplifyingMatcherBuilder {
      private final FeasibilityChecker feasibilityChecker;

      public SimplifyingMatcherBuilder(final FeasibilityChecker feasibilityChecker) {
         this.feasibilityChecker = feasibilityChecker;
      }

      public Matcher<? super JState> toInt(final int expectedValue) {
         return after(SymbStateMatchers.stateToModel(feasibilityChecker)).matches(SymbolMatchers.symbolEquivalentTo(expectedValue));
      }
   }

   public static SimplifyingMatcherBuilder resultSimplifies(
         final FeasibilityChecker feasibilityChecker) {
      return new SimplifyingMatcherBuilder(feasibilityChecker);
   }

   public static Transform<Symbol,JState> stateToModel(final FeasibilityChecker feasibilityChecker) {
      return new MemoizeTransform<>(new ModelForStateTransform(feasibilityChecker));
   }

   public static TransformMatcherBuilder<Symbol, JState> toModel(final FeasibilityChecker feasibilityChecker) {
      return after(stateToModel(feasibilityChecker));
   }
}
