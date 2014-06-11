package com.lexicalscope.svm.partition.trace;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.instruction.instrumentation.Instrumentor;
import com.lexicalscope.svm.partition.trace.ops.NewInstanceNewPartitionOp;
import com.lexicalscope.svm.partition.trace.ops.NewInstanceSamePartitionOp;
import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.Vop;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public class TrackPartitionAtConstruction implements Instrumentor {
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
               if(aPartNewInstanceMatcher.matches(klassDesc)) {
                  op = new NewInstanceNewPartitionOp(instruction.op(), aPart);
               } else if (uPartNewInstanceMatcher.matches(klassDesc)) {
                  op = new NewInstanceNewPartitionOp(instruction.op(), uPart);
               } else {
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
