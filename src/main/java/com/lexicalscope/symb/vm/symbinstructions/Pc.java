package com.lexicalscope.symb.vm.symbinstructions;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.lexicalscope.symb.vm.Snapshotable;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;

public class Pc implements Snapshotable<Pc> {
   private final List<Symbol> conjunction;

   public Pc(final ArrayList<Symbol> conjunction) {
      this.conjunction = conjunction;
   }

   public Pc() {
      this(new ArrayList<Symbol>());
   }

   public Pc and(final Symbol symbol) {
      conjunction.add(symbol);
      return this;
   }

   @Override
   public Pc snapshot() {
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
