package com.lexicalscope.svm.j.instruction.symbolic.pc;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.AndSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.TrueSymbol;

public class Pc {
   private final BoolSymbol symbol;

   private Pc(final BoolSymbol symbol) {
      this.symbol = symbol;
   }

   public Pc() {
      this(new TrueSymbol());
   }

   public Pc and(final BoolSymbol conjunct) {
      // we only change the path condition at fork points.
      // so we should be able to do better than this.
      return new Pc(new AndSymbol(symbol, conjunct));
   }

   public Pc and(final Pc other) {
      return new Pc(new AndSymbol(symbol, other.symbol));
   }

   public <T, E extends Throwable> T accept(final PcVisitor<T, E> visitor) throws E {
      return visitor.symbol(symbol);
   }

   @Override
   public String toString() {
      return symbol.toString();
   }
}
