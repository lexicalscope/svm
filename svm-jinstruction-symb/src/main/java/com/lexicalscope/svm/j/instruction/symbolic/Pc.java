package com.lexicalscope.svm.j.instruction.symbolic;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.symb.state.Snapshotable;

public class Pc implements Snapshotable<Pc> {
   private final List<BoolSymbol> conjunction;

   public Pc(final ArrayList<BoolSymbol> conjunction) {
      this.conjunction = conjunction;
   }

   public Pc() {
      this(new ArrayList<BoolSymbol>());
   }

   public Pc and(final BoolSymbol symbol) {
      conjunction.add(symbol);
      return this;
   }

   @Override
   public Pc snapshot() {
      // we only change the path condition at fork points.
      // so we should be able to do better than this.
      // probably Pc should be immutable.
      return new Pc(new ArrayList<>(conjunction));
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
