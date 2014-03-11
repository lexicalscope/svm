package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.instruction.instrumentation.Instrumentor;
import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public class TrackPartitionAtConstruction implements Instrumentor {
   private static class NewInstanceSamePartitionOp implements Vop {
      @Override public void eval(final JState ctx) {

      }

      @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
         // TODO Auto-generated method stub
         return null;
      }
   }

   private static final class NewInstanceNewPartitionOp implements Vop {
      public NewInstanceNewPartitionOp(final Object aPart) {
         // TODO Auto-generated constructor stub
      }

      @Override public void eval(final JState ctx) {
         // TODO Auto-generated method stub

      }

      @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
         // TODO Auto-generated method stub
         return null;
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
               if(aPartNewInstanceMatcher.matches(klassDesc)) {
                  op = new NewInstanceNewPartitionOp(aPart);
               } else if (uPartNewInstanceMatcher.matches(klassDesc)) {
                  op = new NewInstanceNewPartitionOp(uPart);
               } else {
                  op = new NewInstanceSamePartitionOp();
               }
               instruction.insertNext(statements(instructions).
                     linearOp(op).
                     buildInstruction());
               return null;
            }
         });
      }

      return methodEntry;
   }
}
