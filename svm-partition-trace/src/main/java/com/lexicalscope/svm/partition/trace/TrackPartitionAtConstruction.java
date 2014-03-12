package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.svm.j.instruction.concrete.object.PartitionTagMetaKey.PARTITION_TAG;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.heap.Allocatable;
import com.lexicalscope.svm.heap.ObjectRef;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.instruction.instrumentation.Instrumentor;
import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.JStateAdaptor;
import com.lexicalscope.svm.vm.j.Vop;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public class TrackPartitionAtConstruction implements Instrumentor {
   private static class NewInstanceSamePartitionOp implements Vop {
      private final Vop newOp;

      public NewInstanceSamePartitionOp(final Vop newOp) {
         this.newOp = newOp;
      }

      @Override public void eval(final JState ctx) {
         newOp.eval(new JStateAdaptor(ctx){
            @Override public ObjectRef newObject(final Allocatable klass) {
               return ctx.newObject(klass, ctx.getFrameMeta(PARTITION_TAG));
            }

            @Override public ObjectRef newObject(final Allocatable klass, final Object tag) {
               assert false : "new operation must not provide a custom tag";
               return super.newObject(klass, tag);
            }
         });
      }

      @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
         return newOp.query(instructionQuery);
      }
   }

   private static final class NewInstanceNewPartitionOp implements Vop {
      private final Vop newOp;
      private final Object partitionTag;

      public NewInstanceNewPartitionOp(final Vop newInstruction, final Object partitionTag) {
         this.newOp = newInstruction;
         this.partitionTag = partitionTag;
      }

      @Override public void eval(final JState ctx) {
         System.out.println("constructing in " + partitionTag);
         newOp.eval(new JStateAdaptor(ctx){
            @Override public ObjectRef newObject(final Allocatable klass) {
               System.out.println("constructing " + klass + " in " + partitionTag);
               return ctx.newObject(klass, partitionTag);
            }
         });
      }

      @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
         return newOp.query(instructionQuery);
      }
   }

   private final Object aPart = new Object(){ @Override public String toString() { return "aPart"; }};
   private final Object uPart = new Object(){ @Override public String toString() { return "uPart"; }};

   private final Matcher<String> aPartNewInstanceMatcher;
   private final Matcher<String> uPartNewInstanceMatcher;

   public TrackPartitionAtConstruction(
         final Matcher<String> aPartNewInstanceMatcher,
         final Matcher<String> uPartNewInstanceMatcher) {
      this.aPartNewInstanceMatcher = aPartNewInstanceMatcher;
      this.uPartNewInstanceMatcher = uPartNewInstanceMatcher;
   }

   public static Instrumentor constructionOf(final PartitionBuilder aPart, final PartitionBuilder uPart) {
      return constructionOf(
            aPart.staticNewInstanceMatcher(),
            uPart.staticNewInstanceMatcher());
   }

   private static Instrumentor constructionOf(final Matcher<String> aPartNewInstanceMatcher, final Matcher<String> uPartNewInstanceMatcher) {
      return new TrackPartitionAtConstruction(aPartNewInstanceMatcher, uPartNewInstanceMatcher);
   }

   @Override public Instruction instrument(
         final InstructionSource instructions,
         final SMethodDescriptor method,
         final Instruction methodEntry) {
      for (final Instruction instruction : methodEntry) {
         instruction.query(new InstructionQueryAdapter<Void>() {
            @Override public Void newobject(final String klassDesc) {
               final Vop op;
               System.out.println("instrument " + klassDesc + " obj");
               if(aPartNewInstanceMatcher.matches(klassDesc)) {
                  System.out.println("instrument a obj yo");
                  op = new NewInstanceNewPartitionOp(instruction.op(), aPart);
               } else if (uPartNewInstanceMatcher.matches(klassDesc)) {
                  System.out.println("instrument u obj yo");
                  op = new NewInstanceNewPartitionOp(instruction.op(), uPart);
               } else {
                  System.out.println("new " + klassDesc + " obj yo");
                  op = new NewInstanceSamePartitionOp(instruction.op());
               }
               instruction.replaceOp(op);
               return null;
            }
         });
      }

      return methodEntry;
   }
}
