package com.lexicalscope.svm.search2;

import java.util.List;

import com.lexicalscope.svm.vm.j.JState;

public interface TreeSearchStateSelection {
   TraceTree qnode(List<TraceTree> qstatesAvailable);
   JState qstate(TraceTree selectedTree, StatesCollection states);

   TraceTree pnode(List<TraceTree> pstatesAvailable);
   JState pstate(TraceTree selectedTree, StatesCollection states);
}
