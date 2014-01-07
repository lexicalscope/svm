package com.lexicalscope.symb.vm.symbinstructions.symbols;

public interface Symbol {
   <T, E extends Throwable> T accept(SymbolVisitor<T, E> visitor) throws E;
}
