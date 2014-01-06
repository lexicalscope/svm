package com.lexicalscope.symb.vm.symbinstructions.symbols;

public interface SymbolVisitor<T, E extends Throwable> {
   T add(ISymbol left, ISymbol right) throws E;
   T and(ISymbol left, ISymbol right) throws E;
   T mul(ISymbol left, ISymbol right) throws E;
   T sub(ISymbol left, ISymbol right) throws E;

   T constant(int val) throws E;

   T ge(ISymbol val) throws E;
   T ge(ISymbol value1, ISymbol value2) throws E;
   T gt(ISymbol value1, ISymbol value2) throws E;
   T le(ISymbol value1, ISymbol value2) throws E;
   T lt(ISymbol value1, ISymbol value2) throws E;
   T ne(ISymbol value1, ISymbol value2) throws E;
   T eq(ISymbol value1, ISymbol value2) throws E;

   T not(ISymbol val) throws E;

   T intSymbol(int name) throws E;

   T tru3() throws E;
   T fals3() throws E;

   T simplified(Object simplification) throws E;
}
