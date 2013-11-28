package com.lexicalscope.symb.vm.symbinstructions;

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.symb.vm.Snapshotable;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;

public class Pc implements Snapshotable {
   private final List<Symbol> conjunction;

   public Pc(final ArrayList<Symbol> conjunction) {
      this.conjunction = conjunction;
   }

   public Pc() {
      this(new ArrayList<Symbol>());
   }

   public void and(final Symbol symbol) {
      conjunction.add(symbol);
   }

   @Override
   public Pc snapshot() {
      return new Pc(new ArrayList<>(conjunction));
   }
}
