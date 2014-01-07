package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.BoolSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.SymbolVisitor;

public class TrueSymbol implements BoolSymbol {
   @Override public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.tru3();
   }
}
