package com.lexicalscope.svm.search2;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;

public interface GoalExtractor<T, S> {
   T goal(S state);
   BoolSymbol pc(S state);
}
