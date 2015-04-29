package com.lexicalscope.svm.vm.j.queries;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.objectweb.asm.Type;

import com.lexicalscope.svm.vm.j.InstructionQueryAdapter;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public class IsConstructorCall extends InstructionQueryAdapter<Boolean> {
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