package com.lexicalscope.symb.partition.trace;

import static org.objectweb.asm.Type.getInternalName;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class PartitionBuilder {
   private final Set<String> klasses = new HashSet<>();

   public static PartitionBuilder partition() {
      return new PartitionBuilder();
   }

   public PartitionBuilder ofClass(final Class<?> klass) {
      klasses.add(getInternalName(klass));
      return this;
   }

   public Matcher<? super State> build() {
      return new TypeSafeDiagnosingMatcher<State>() {

         @Override public void describeTo(final Description description) {
            description.appendText("class in ").appendValue(klasses);
         }

         @Override protected boolean matchesSafely(final State item, final Description mismatchDescription) {
            final String callerKlass = methodName(item.previousFrame()).klassName();
            final String receiverKlass = methodName(item.currentFrame()).klassName();
            return klasses.contains(callerKlass) ^ klasses.contains(receiverKlass);
         }

         public SMethodDescriptor methodName(final StackFrame stackFrame) {
            return (SMethodDescriptor) stackFrame.context();
         }
      };
   }
}
