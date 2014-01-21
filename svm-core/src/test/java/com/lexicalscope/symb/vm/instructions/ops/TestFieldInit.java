package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.HeapMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.Type;

import com.lexicalscope.svm.j.instruction.concrete.object.NewObjectOp;
import com.lexicalscope.symb.heap.FastHeap;
import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.klass.SClass;
import com.lexicalscope.symb.stack.SnapshotableStackFrame;
import com.lexicalscope.symb.vm.DequeStack;
import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.StaticsImpl;
import com.lexicalscope.symb.vm.classloader.AsmSClassLoader;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

public class TestFieldInit {
   private final SClassLoader classLoader = new AsmSClassLoader();
   private final StaticsImpl statics = new StaticsImpl(classLoader);
   private final String klassName = Type.getInternalName(ClassWithAllTypesOfFields.class);

   private final Heap heap = new FastHeap();
   private SClass sClass;
   private Object newObject;

   @Before public void before() {
      final List<SClass> klasses = statics.defineClass(klassName);
      assertThat(klasses, Matchers.hasSize(2));
      sClass = klasses.get(1);

      newObject = new NewObjectOp(klassName).eval(new StateImpl(null, statics, new DequeStack(new SnapshotableStackFrame(null, null, 0, 1)), heap, null));
   }

   @Test public void intFieldInit() {
      checkFieldInit("intField", 0);
   }

   @Test public void charFieldInit() {
      checkFieldInit("charField", (char) 0);
   }

   @Test public void byteFieldInit() {
      checkFieldInit("byteField", (byte) 0);
   }

   @Test public void shortFieldInit() {
      checkFieldInit("shortField", (short) 0);
   }

   @Test public void longFieldInit() {
      checkFieldInit("longField", 0L);
   }

   @Test public void floatFieldInit() {
      checkFieldInit("floatField", (float) 0);
   }

   @Test public void doubleFieldInit() {
      checkFieldInit("doubleField", (double) 0);
   }

   @Test public void objectFieldInit() {
      checkFieldInit("objectField", heap.nullPointer());
   }

   private void checkFieldInit(final String fieldName, final Object value) {
      assertThat(heap, contains(heapLocation(newObject, sClass, fieldName), value));
   }
}
