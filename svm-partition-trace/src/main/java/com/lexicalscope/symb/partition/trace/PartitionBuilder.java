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

   public Matcher<? super State> dynamicExactMatcher() {
      return new TypeSafeDiagnosingMatcher<State>() {
         @Override public void describeTo(final Description description) {
            description.appendText("(receiver in) xor (caller in) : ").appendValue(klasses);
         }

         @Override protected boolean matchesSafely(final State item, final Description mismatchDescription) {
            final StackFrame previousFrame = item.previousFrame();
            final StackFrame currentFrame = item.currentFrame();
            if(previousFrame.isDynamic() && currentFrame.isDynamic()) {
               final SClass callerKlass = frameReceiver(item, previousFrame);
               final SClass receiverKlass = frameReceiver(item, currentFrame);
               return klasses.contains(callerKlass.name()) ^ klasses.contains(receiverKlass.name());
            }
            // TODO[tim]: need a strategy for static methods
            return false;
         }

         private SClass frameReceiver(final State item, final StackFrame previousFrame) {
            return (SClass) item.get(previousFrame.local(0), SClass.OBJECT_MARKER_OFFSET);
         }
      };
   }

   public Matcher<? super SMethodDescriptor> staticOverApproximateMatcher() {
      return new TypeSafeDiagnosingMatcher<SMethodDescriptor>() {
         @Override public void describeTo(final Description description) {
            description.appendText("class in: ").appendValue(klasses);
         }

         @Override protected boolean matchesSafely(final SMethodDescriptor item, final Description mismatchDescription) {
            return klasses.contains(item.klassName());
         }
      };
   }
}
