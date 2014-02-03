package com.lexicalscope.svm.j.instruction.symbolic.pc;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;

public class Pc {
   private final List<BoolSymbol> conjunction;

   private Pc(final List<BoolSymbol> conjunction) {
      this.conjunction = conjunction;
   }

   public Pc() {
      this(new ArrayList<BoolSymbol>());
   }

   public Pc and(final BoolSymbol symbol) {
      // we only change the path condition at fork points.
      // so we should be able to do better than this.
      final ArrayList<BoolSymbol> extended = new ArrayList<>(conjunction);
      extended.add(symbol);
      return new Pc(extended);
   }

   public <T, E extends Throwable> T accept(final PcVisitor<T, E> visitor) throws E {
      return visitor.conjunction(conjunction);
   }

   private static final Joiner conjJoiner = Joiner.on(" ");

   @Override
   public String toString() {
      return String.format("(AND %s)", conjJoiner.join(conjunction));
   }
}
