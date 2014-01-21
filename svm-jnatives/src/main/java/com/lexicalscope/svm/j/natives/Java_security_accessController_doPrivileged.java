package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.symb.vm.MethodBody;

public class Java_security_accessController_doPrivileged extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_security_accessController_doPrivileged() {
      super("java/security/AccessController", "doPrivileged", "(Ljava/security/PrivilegedAction;)Ljava/lang/Object;");
   }

   @Override public MethodBody instructions(final Instructions instructions) {
      return instructions.statements().maxStack(1).maxLocals(1).aload(0).invokeInterface("java/security/PrivilegedAction", "run", "()Ljava/lang/Object;").return1().build();
   }
}