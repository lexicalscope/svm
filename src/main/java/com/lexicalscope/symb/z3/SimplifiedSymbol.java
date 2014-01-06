package com.lexicalscope.symb.z3;

import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.SymbolVisitor;

/**
 * A symbol that was simplified by the solver. Will have to be cast depending on the solver.
 * 
 * @author tim
 */
public class SimplifiedSymbol implements ISymbol {
   private final Object simplification;

   public SimplifiedSymbol(final Object simplification) {
      this.simplification = simplification;
   }

   @Override public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.simplified(simplification);
   }
}
