package com.lexicalscope.svm.j.instruction.symbolic.symbols;

public interface Symbol {
   <T, E extends Throwable> T accept(SymbolVisitor<T, E> visitor) throws E;
}
