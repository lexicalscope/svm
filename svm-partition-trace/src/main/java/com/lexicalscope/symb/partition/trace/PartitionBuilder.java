package com.lexicalscope.symb.partition.trace;

import static org.objectweb.asm.Type.getInternalName;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.j.klass.SClass;
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
            final StackFrame previousFrame = item.previousFrame();
            final StackFrame currentFrame = item.currentFrame();
            if(previousFrame.isDynamic() && currentFrame.isDynamic()) {
               final SClass callerKlass = frameReceiver(item, previousFrame);
               final SClass receiverKlass = frameReceiver(item, currentFrame);
               return klasses.contains(callerKlass.name()) ^ klasses.contains(receiverKlass.name());
            }
            return false;
         }

         public SClass frameReceiver(final State item, final StackFrame previousFrame) {
            return (SClass) item.get(previousFrame.local(0), SClass.OBJECT_MARKER_OFFSET);
         }

         public SMethodDescriptor methodName(final StackFrame stackFrame) {
            return (SMethodDescriptor) stackFrame.context();
         }
      };
   }
}
