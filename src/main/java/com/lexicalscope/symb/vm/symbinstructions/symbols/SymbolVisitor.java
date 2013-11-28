package com.lexicalscope.symb.vm.symbinstructions.symbols;

public interface SymbolVisitor<T, E extends Throwable> {
   T add(Symbol left, Symbol right) throws E;

   T constant(int val) throws E;

   T ge(Symbol val) throws E;

   T mul(Symbol left, Symbol right) throws E;

   T not(Symbol val) throws E;

   T sub(Symbol left, Symbol right) throws E;

   T intSymbol(int name) throws E;;
}
