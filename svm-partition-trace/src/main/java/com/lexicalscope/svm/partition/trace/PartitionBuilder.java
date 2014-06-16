package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.svm.partition.spec.MatchersSpec.*;
import static org.objectweb.asm.Type.getInternalName;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.heap.ObjectRef;
import com.lexicalscope.svm.partition.spec.CallContext;
import com.lexicalscope.svm.stack.StackFrame;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.klass.SClass;

public class PartitionBuilder {
   private final Set<String> klasses = new HashSet<>();

   public static PartitionBuilder partition() {
      return new PartitionBuilder();
   }

   public PartitionBuilder ofClass(final Class<?> klass) {
      klasses.add(getInternalName(klass));
      return this;
   }

   public Matcher<? super CallContext> newInstanceMatcher() {
      return receiver(klassIn(klasses));
      /*return new TypeSafeDiagnosingMatcher<String>() {
         @Override public void describeTo(final Description description) {
            description.appendText("new instance of : ").appendValue(klasses);
         }

         @Override protected boolean matchesSafely(final String item, final Description mismatchDescription) {
            return klasses.contains(item);
         }
      };*/
   }

   /*
   private Matcher<? super MethodCallContext> dynamicExactMatcher() {
      return new TypeSafeDiagnosingMatcher<MethodCallContext>() {
         @Override public void describeTo(final Description description) {
            description.appendText("(receiver in) xor (caller in) : ").appendValue(klasses);
         }

         @Override protected boolean matchesSafely(final MethodCallContext item, final Description mismatchDescription) {
            if(item.callingContextIsDynamic() && item.receivingContextIsDynamic()) {
               return klasses.contains(item.callerKlass().name())
                    ^ klasses.contains(item.receiverKlass().name());
            }
            // TODO[tim]: need a strategy for static methods
            return false;
         }
      };
   }*/

   /*
   public Matcher<? super JState> dynamicExactCallinMatcher() {
      final Matcher<? super MethodCallContext> dynamicExactMatcher = dynamicExactMatcher();
      return new TypeSafeDiagnosingMatcher<JState>() {
         @Override public void describeTo(final Description description) {
            description.appendDescriptionOf(dynamicExactMatcher);
         }

         @Override protected boolean matchesSafely(final JState item, final Description mismatchDescription) {
            final StackFrame previousFrame = item.previousFrame();
            final StackFrame currentFrame = item.currentFrame();

            return dynamicExactMatcher.matches(new MethodCallContext() {
               @Override public boolean receivingContextIsDynamic() {
                  return currentFrame.isDynamic();
               }

               @Override public SClass receiverKlass() {
                  return frameReceiver(item, currentFrame);
               }

               @Override public boolean callingContextIsDynamic() {
                  return previousFrame.isDynamic();
               }

               @Override public SClass callerKlass() {
                  return frameReceiver(item, previousFrame);
               }
            });
         }
      };
   }*/

   /*
   public Matcher<? super InstrumentationContext> dynamicExactCallbackMatcher() {
      final Matcher<? super MethodCallContext> dynamicExactMatcher = dynamicExactMatcher();
      return new TypeSafeDiagnosingMatcher<InstrumentationContext>() {
         @Override public void describeTo(final Description description) {
            description.appendDescriptionOf(dynamicExactMatcher);
         }

         @Override protected boolean matchesSafely(final InstrumentationContext item, final Description mismatchDescription) {
            final StackFrame previousFrame = item.currentFrame();

            return dynamicExactMatcher.matches(new MethodCallContext() {
               @Override public boolean receivingContextIsDynamic() {
                  return true;
               }

               @Override public SClass receiverKlass() {
                  return item.receiverKlass();
               }

               @Override public boolean callingContextIsDynamic() {
                  return previousFrame.isDynamic();
               }

               @Override public SClass callerKlass() {
                  return frameReceiver(item.state(), previousFrame);
               }

            });
         }
      };
   }
   */
   private SClass frameReceiver(final JState item, final StackFrame previousFrame) {
      return (SClass) item.get((ObjectRef) previousFrame.local(0), SClass.OBJECT_MARKER_OFFSET);
   }

   /*
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
   */
}
