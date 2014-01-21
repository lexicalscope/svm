package com.lexicalscope.symb.vm.symbinstructions;

import java.util.List;

import com.lexicalscope.symb.vm.symbinstructions.symbols.BoolSymbol;

public interface PcVisitor<T, E extends Throwable> {
   T conjunction(List<BoolSymbol> conjunction) throws E;
}
