package com.lexicalscope.symb.vm.j;

import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;
import com.lexicalscope.symb.vm.j.j.klass.SFieldName;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class JavaConstants {
   public static final String CLINIT = "<clinit>";
   public static final String INIT = "<init>";
   public static final String NOARGS_VOID_DESC = "()V";
   public static final String STRING_CLASS = "java/lang/String";
   public static final String THREAD_CLASS = "java/lang/Thread";
   public static final String CLASS_CLASS = "java/lang/Class";
   public static final String OBJECT_CLASS = "java/lang/Object";
   public static final SMethodDescriptor CLASS_DEFAULT_CONSTRUCTOR = new AsmSMethodName(CLASS_CLASS, INIT, NOARGS_VOID_DESC);
   public static final SMethodDescriptor GET_CLASS = new AsmSMethodName(OBJECT_CLASS, "getClass", "()Ljava/lang/Class;");
   public static final SMethodDescriptor TO_STRING = new AsmSMethodName(OBJECT_CLASS, "toString", "()Ljava/lang/String;");
   public static final SFieldName STRING_VALUE_FIELD = new SFieldName(STRING_CLASS, "value");
}
