package com.lexicalscope.svm.vm.j.queries;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.objectweb.asm.Type;

import com.lexicalscope.svm.vm.j.InstructionQueryAdapter;

public class IsNewInstruction extends InstructionQueryAdapter<Boolean> {
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