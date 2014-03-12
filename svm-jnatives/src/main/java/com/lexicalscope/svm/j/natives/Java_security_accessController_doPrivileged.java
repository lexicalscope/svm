package com.lexicalscope.svm.j.natives;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.vm.j.MethodBody;

public class Java_security_accessController_doPrivileged extends AbstractNativeMethodDef implements NativeMethodDef {
   public Java_security_accessController_doPrivileged() {
      super("java/security/AccessController", "doPrivileged", "(Ljava/security/PrivilegedAction;)Ljava/lang/Object;");
   }

   @Override public MethodBody instructions(final InstructionSource instructions) {
      return statements(instructions).maxStack(1).maxLocals(1).aload(0).invokeInterface("java/security/PrivilegedAction", "run", "()Ljava/lang/Object;").return1(name()).build();
   }
}
