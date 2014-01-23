package com.lexicalscope.svm.j.instruction.symbolic;

import java.util.List;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;

public interface PcVisitor<T, E extends Throwable> {
   T conjunction(List<BoolSymbol> conjunction) throws E;
}
