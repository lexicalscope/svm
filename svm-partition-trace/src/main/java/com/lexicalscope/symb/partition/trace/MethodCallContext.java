package com.lexicalscope.symb.partition.trace;

import com.lexicalscope.symb.vm.j.j.klass.SClass;

public interface MethodCallContext {
   boolean callingContextIsDynamic();
   boolean receivingContextIsDynamic();
   SClass callerKlass();
   SClass receiverKlass();
}
