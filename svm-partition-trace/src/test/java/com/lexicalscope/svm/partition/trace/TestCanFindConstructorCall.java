package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.svm.j.instruction.instrumentation.InstructionCounting.countIs;
import static com.lexicalscope.svm.vm.j.code.AsmSMethodName.method;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.svm.classloading.AsmSClassLoader;
import com.lexicalscope.svm.classloading.SClassLoader;
import com.lexicalscope.svm.j.instruction.instrumentation.InstructionCounting;
import com.lexicalscope.svm.j.instruction.instrumentation.finders.FindConstructorCall;
import com.lexicalscope.svm.vm.j.klass.SClass;
import com.lexicalscope.svm.vm.j.klass.SMethod;


public class TestCanFindConstructorCall {
   private final SClassLoader classLoader = new AsmSClassLoader();

   public static class ClassUnderConstruction {

   }

   public static class ClassCallingConstructor {
      public void method() {
         new ClassUnderConstruction();
      }
   }

   @Test public void constructorCallCanBeFound() {
      final SClass loaded = classLoader.load(ClassCallingConstructor.class);
      final SMethod method = loaded.virtualMethod(method(ClassCallingConstructor.class, "method", "()V"));

      final InstructionCounting counting = new InstructionCounting();
      new FindConstructorCall(ClassUnderConstruction.class, counting).findInstruction(method);

      assertThat("found constructor", counting, countIs(1));
   }
}
