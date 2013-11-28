package com.lexicalscope.symb.vm.symbinstructions;

import java.util.List;

import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;

public interface PcVisitor<T, E extends Throwable> {
   T conjunction(List<Symbol> conjunction) throws E;
}
