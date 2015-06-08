package com.lexicalscope.svm.search2;

import java.util.List;

public interface TreeSearchStateSelection {
   StatesCollection statesCollection(TraceTreeSideObserver ttObserver);

   TraceTree node(List<TraceTree> statesAvailable);
}
