package com.lexicalscope.symb.vm.matchers;

import org.hamcrest.Description;

import com.lexicalscope.Transform;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.symbinstructions.Pc;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;
import com.lexicalscope.symb.z3.FeasibilityChecker;

public class ModelForStateTransform implements Transform<Symbol, State> {
   private final FeasibilityChecker feasibilityChecker;

   public ModelForStateTransform(final FeasibilityChecker feasibilityChecker) {
      this.feasibilityChecker = feasibilityChecker;
   }

   @Override public void describeTo(final Description description) {
      description.appendText("simplified using ").appendValue(feasibilityChecker);
   }

   @Override public Symbol transform(final State item, final Description mismatchDescription) {
      final Object operand = item.op(new PeekOperandOp());

      if(operand instanceof Integer) {
         mismatchDescription.appendText("a value ").appendValue(operand);
         return new IConstSymbol((int) operand);
      }

      final Pc pc = (Pc) item.getMeta();
      final Symbol modelForBv32Expr = feasibilityChecker.modelForBv32Expr((ISymbol) operand, pc);
      mismatchDescription
      .appendText("simplification ")
      .appendValue(modelForBv32Expr)
      .appendText(" of ")
      .appendValue(operand)
      .appendText(" and ")
      .appendValue(pc);
      return modelForBv32Expr;
   }
}
