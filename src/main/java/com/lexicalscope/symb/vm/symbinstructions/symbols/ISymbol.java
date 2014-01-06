package com.lexicalscope.symb.vm.symbinstructions.symbols;


public interface ISymbol {
   <T, E extends Throwable> T accept(SymbolVisitor<T, E> visitor) throws E;
}
