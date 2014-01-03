package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SMethodName;

public class JavaConstants {
   public static final String CLINIT = "<clinit>";
   public static final String INIT = "<init>";
   public static final String NOARGS_VOID_DESC = "()V";
   public static final String STRING_CLASS = "java/lang/String";
   public static final String THREAD_CLASS = "java/lang/Thread";
   public static final String CLASS_CLASS = "java/lang/Class";
   public static final SMethodName CLASS_DEFAULT_CONSTRUCTOR = new SMethodName(CLASS_CLASS, INIT, NOARGS_VOID_DESC);
}
