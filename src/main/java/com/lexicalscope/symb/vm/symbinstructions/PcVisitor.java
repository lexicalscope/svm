package com.lexicalscope.symb.vm.symbinstructions;

import java.util.List;

import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public interface PcVisitor<T, E extends Throwable> {
   T conjunction(List<ISymbol> conjunction) throws E;
}
