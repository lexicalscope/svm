package com.lexicalscope.svm.j.instruction.symbolic.pc;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;

public interface PcVisitor<T, E extends Throwable> {
   T symbol(BoolSymbol conjunction) throws E;
}
