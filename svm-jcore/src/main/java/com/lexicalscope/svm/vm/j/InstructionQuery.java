package com.lexicalscope.svm.vm.j;

import com.lexicalscope.svm.vm.j.klass.SFieldName;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public interface InstructionQuery<T> {
   T nativ3();
   T synthetic();

   T methodentry(SMethodDescriptor methodName);
   T methodexit();

   T arraycopy();
   T arraylength();
   T arrayload();
   T arraystore();
   T newarray();

   @Deprecated T branch();
   T f2i();
   T iinc();
   T i2f();
   T i2l();
   T checkcast();

   T getstatic();
   T instance0f();

   T l2i();
   T lcmp();
   T lushr();

   T invokevirtual(SMethodDescriptor methodName);
   T invokestatic(SMethodDescriptor methodName);
   T invokespecial(SMethodDescriptor methodName);
   T invokeinterface(SMethodDescriptor methodName);

   T aconst_null();
   T getfield(SFieldName name);
   T putfield(SFieldName name);
   @Deprecated T binaryop();
   @Deprecated T nularyop();
   @Deprecated T unaryop();

   T objectpoolload();
   T stringpoolload();

   @Deprecated T load(int var);
   @Deprecated T store(int var);
   T dup_x1();
   T pop();
   T r3turn(SMethodDescriptor methodName, int returnCount);
   T dup();
   T ifacmpeq();
   T ifle();
   T ifacmpne();
   T ifeq();
   T iflt();
   T ifne();
   T ifnonnull();
   T ifnull();
   @Deprecated T icmp();
   T ifgt();
   T got0();
   T ifge();
   T putstatic();

   T newobject(KlassInternalName klassDesc);
   T loadarg(Object value);
}
