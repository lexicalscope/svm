package com.lexicalscope.svm.vm.symb.matchers;

import static com.lexicalscope.svm.j.instruction.symbolic.PcMetaKey.PC;

import org.hamcrest.Description;

import com.lexicalscope.Transform;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.IConstSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.Symbol;
import com.lexicalscope.svm.vm.j.State;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class ModelForStateTransform implements Transform<Symbol, State> {
   private final FeasibilityChecker feasibilityChecker;

   public ModelForStateTransform(final FeasibilityChecker feasibilityChecker) {
      this.feasibilityChecker = feasibilityChecker;
   }

   @Override public void describeTo(final Description description) {
      description.appendText("simplified using ").appendValue(feasibilityChecker);
   }

   @Override public Symbol transform(final State item, final Description mismatchDescription) {
      final Object operand = item.peekOperand();

      if(operand instanceof Integer) {
         mismatchDescription.appendText("a value ").appendValue(operand);
         return new IConstSymbol((int) operand);
      }

      final BoolSymbol pc = item.getMeta(PC);
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
