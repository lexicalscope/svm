package com.lexicalscope.svm.partition.trace.ops;

import static com.lexicalscope.svm.j.instruction.concrete.object.PartitionTagMetaKey.PARTITION_TAG;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.heap.Allocatable;
import com.lexicalscope.svm.heap.ObjectRef;
import com.lexicalscope.svm.partition.spec.CallContext;
import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.JStateAdaptor;
import com.lexicalscope.svm.vm.j.KlassInternalName;
import com.lexicalscope.svm.vm.j.Vop;

public final class NewInstanceVariablePartitionOp implements Vop {
   private final Vop newOp;
   private final KlassInternalName klassDesc;
   private final MatcherPartition partition;

   public NewInstanceVariablePartitionOp(
         final KlassInternalName klassDesc,
         final Vop newInstruction,
         final Matcher<? super CallContext> aMatcher,
         final Matcher<? super CallContext> uMatcher,
         final Object aPartitionTag,
         final Object uPartitionTag) {
      this.klassDesc = klassDesc;
      this.newOp = newInstruction;
      this.partition = new MatcherPartition(aMatcher, uMatcher, aPartitionTag, uPartitionTag);
   }

   public class MatcherPartition {
      private final Matcher<? super CallContext> aMatcher;
      private final Matcher<? super CallContext> uMatcher;
      private final Object aPartitionTag;
      private final Object uPartitionTag;

      public MatcherPartition(
            final Matcher<? super CallContext> aMatcher,
            final Matcher<? super CallContext> uMatcher,
            final Object aPartitionTag,
            final Object uPartitionTag) {
         this.aMatcher = aMatcher;
         this.uMatcher = uMatcher;
         this.aPartitionTag = aPartitionTag;
         this.uPartitionTag = uPartitionTag;
      }

      public Object tagForConstructionOf(final JState ctx, final KlassInternalName klassDesc) {
         final CallContext callContext = new JStateCallContext(ctx, klassDesc);
         Object partitionTag;
         if (aMatcher.matches(callContext)) {
            partitionTag = aPartitionTag;
         } else if (uMatcher.matches(callContext)) {
            partitionTag = uPartitionTag;
         } else {
            partitionTag = ctx.getFrameMeta(PARTITION_TAG);
         }
         return partitionTag;
      }
   }

   @Override public void eval(final JState ctx) {
      newOp.eval(new JStateAdaptor(ctx){
         @Override public ObjectRef newObject(final Allocatable klass) {
            return ctx.newObject(klass, partition.tagForConstructionOf(ctx, klassDesc));
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

   @Override public String toString() {
      return "DYNAMIC PARTITION OBJECT";
   }
}