package com.lexicalscope.svm.search;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;

public interface SearchMetaExtractor<T, S> {
   void configureInitial(S state);
   T goal(S state);
   BoolSymbol pc(S state);
}
