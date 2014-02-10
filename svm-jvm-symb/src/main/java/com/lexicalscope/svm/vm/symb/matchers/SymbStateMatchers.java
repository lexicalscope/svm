package com.lexicalscope.svm.vm.symb.matchers;

import static com.lexicalscope.MatchersAdditional.after;

import org.hamcrest.Matcher;

import com.lexicalscope.MatchersAdditional.CollectionMatcherBuilder;
import com.lexicalscope.MemoizeTransform;
import com.lexicalscope.Transform;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.Symbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.SymbolMatchers;
import com.lexicalscope.svm.vm.FlowNode;
import com.lexicalscope.svm.vm.j.State;
import com.lexicalscope.svm.vm.j.StateMatchers;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class SymbStateMatchers {

   public static class SimplifyingMatcherBuilder {
      private final FeasibilityChecker feasibilityChecker;

      public SimplifyingMatcherBuilder(final FeasibilityChecker feasibilityChecker) {
         this.feasibilityChecker = feasibilityChecker;
      }

      public Matcher<? super State> toInt(final int expectedValue) {
         return after(SymbStateMatchers.stateToModel(feasibilityChecker)).matches(SymbolMatchers.symbolEquivalentTo(expectedValue));
      }
   }

   public static SimplifyingMatcherBuilder resultSimplifies(
         final FeasibilityChecker feasibilityChecker) {
      return new SimplifyingMatcherBuilder(feasibilityChecker);
   }

   public static Transform<Symbol,State> stateToModel(final FeasibilityChecker feasibilityChecker) {
      return new MemoizeTransform<>(new ModelForStateTransform(feasibilityChecker));
   }

   public static CollectionMatcherBuilder<Symbol, FlowNode<State>> flowNodeToModel(final FeasibilityChecker feasibilityChecker) {
      return StateMatchers.flowNodeToState().then(stateToModel(feasibilityChecker));
   }
}
