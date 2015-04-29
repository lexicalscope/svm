package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.svm.vm.j.code.AsmSMethodName.method;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.objectweb.asm.Type;

import com.lexicalscope.svm.classloading.AsmSClassLoader;
import com.lexicalscope.svm.classloading.SClassLoader;
import com.lexicalscope.svm.j.instruction.instrumentation.InstructionFinder;
import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.klass.SClass;
import com.lexicalscope.svm.vm.j.klass.SMethod;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;


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
      final boolean foundConstructor = new FindConstructorCall().findInstruction(method);
      assertThat("found constructor", foundConstructor);
   }

   public static class FindConstructorCall implements InstructionFinder {
      @Override public boolean findInstruction(final SMethod method) {
         boolean foundNewInstruction = false;
         boolean foundConstructor = false;
         for (final Instruction instruction : method.entry()) {
            if(!foundNewInstruction && instruction.query(new IsNewInstruction(ClassUnderConstruction.class)))
            {
               foundNewInstruction = true;
            }
            else if(foundNewInstruction)
            {
               assert !instruction.query(new IsNewInstruction(ClassUnderConstruction.class)) :
                  "found two new instructions with no constructor inbetween";

               if(instruction.query(new IsConstructorCall(ClassUnderConstruction.class)))
               {
                  foundConstructor = true;
               }
               else
               {
                  assert !instruction.query(new IsConstructorCall(Matchers.any(String.class))) :
                     "found the wrong constructor after a new instruction";
               }
            }
         }
         return foundConstructor;
      }
   }

   public static class IsNewInstruction extends InstructionQueryAdapter<Boolean> {
      private final Matcher<String> klassInternalNameMatcher;

      public IsNewInstruction(final Class<?> klass) {
         this(Type.getInternalName(klass));
      }

      public IsNewInstruction(final String klassInternalName) {
         this(Matchers.equalTo(klassInternalName));
      }

      public IsNewInstruction(final Matcher<String> klassInternalNameMatcher) {
         this.klassInternalNameMatcher = klassInternalNameMatcher;
      }

      @Override public Boolean newobject(final String klassDesc) {
         return klassInternalNameMatcher.matches(klassDesc);
      }

      @Override protected Boolean defaultResult() {
         return false;
      }
   }

   public static class IsConstructorCall extends InstructionQueryAdapter<Boolean> {
      private final Matcher<String> klassInternalNameMatcher;

      public IsConstructorCall(final Class<?> klass) {
         this(Type.getInternalName(klass));
      }

      public IsConstructorCall(final String klassInternalName) {
         this(Matchers.equalTo(klassInternalName));
      }

      public IsConstructorCall(final Matcher<String> klassInternalNameMatcher) {
         this.klassInternalNameMatcher = klassInternalNameMatcher;
      }

      @Override public Boolean invokespecial(final SMethodDescriptor methodName) {
         return methodName.declaredOn(klassInternalNameMatcher) && methodName.isConstructor();
      }

      @Override protected Boolean defaultResult() {
         return false;
      }
   }
}
