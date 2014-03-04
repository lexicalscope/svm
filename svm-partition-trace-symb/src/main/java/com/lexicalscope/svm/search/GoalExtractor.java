package com.lexicalscope.svm.search;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;

public interface GoalExtractor<T, S> {
   T goal(S state);
   BoolSymbol pc(S state);
}
