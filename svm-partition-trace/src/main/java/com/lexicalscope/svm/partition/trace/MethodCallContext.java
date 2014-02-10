package com.lexicalscope.svm.partition.trace;

import com.lexicalscope.svm.vm.j.klass.SClass;

public interface MethodCallContext {
   boolean callingContextIsDynamic();
   boolean receivingContextIsDynamic();
   SClass callerKlass();
   SClass receiverKlass();
}
