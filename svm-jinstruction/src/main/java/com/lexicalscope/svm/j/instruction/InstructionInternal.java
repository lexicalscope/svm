package com.lexicalscope.svm.j.instruction;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.InstructionCode;
import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;



/**
 * A none leaf node, which should have one or two successors
 *
 * @author tim
 */
public class InstructionInternal implements Instruction {
   private static TerminateInstruction terminate = new TerminateInstruction();

   private final Vop op;
   private Instruction prev;
   private Instruction next;
   private Instruction target;

   private final InstructionCode code;

   private final List<Instruction> targetOf = new LinkedList<>();

   public InstructionInternal(final Vop instruction, final InstructionCode code) {
      this.op = instruction;
      this.code = code;

      next = terminate;
      target = terminate;
   }

   @Override public void eval(final JState ctx) {
      assert next != null;
      op.eval(ctx);
   }

   @Override public Instruction append(final Instruction instruction) {
      if(next.equals(terminate)) {
         instruction.prevIs(this);
         next = instruction;
      } else {
         next.append(instruction);
      }
      return this;
   }

   @Override public Instruction next() {
      return next;
   }

   @Override public boolean hasNext() {
      assert next != null;
      return next != null;
   }

   @Override public Instruction jmpTarget() {
      return target;
   }

   @Override public void jmpTarget(final Instruction instruction) {
      target = instruction;
      instruction.targetOf(this);
   }

   @Override public String toString() {
      return String.format("%s", op.toString(), next);
   }

   @Override public InstructionCode code() {
      return code;
   }

   @Override public void prevIs(final Instruction instruction) {
      prev = instruction;
   }

   @Override public void insertNext(final Instruction node) {
      node.append(next);
      next = node;
      node.prevIs(this);
   }

   @Override public void insertHere(final Instruction node) {
      if(prev != null) {
         prev.insertNext(node);
      } else {
         node.append(this);
      }
      for (final Instruction comeFrom : targetOf) {
         comeFrom.jmpTarget(node);
      }
      targetOf.clear();
   }

   @Override public Instruction prev() {
      return prev;
   }

   @Override public Collection<Instruction> targetOf() {
      return targetOf;
   }

   @Override public void targetOf(final Instruction instruction) {
      targetOf.add(instruction);
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return op.query(instructionQuery);
   }

   @Override public Iterator<Instruction> iterator() {
      return new Iterator<Instruction>() {
         Instruction cur = InstructionInternal.this;
         @Override public boolean hasNext() {
            return cur.hasNext();
         }

         @Override public Instruction next() {
            return cur = cur.next();
         }

         @Override public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
