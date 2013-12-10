package com.lexicalscope.symb.vm;

import java.util.HashMap;
import java.util.Map;

import com.lexicalscope.heap.FastHeap;
import com.lexicalscope.symb.vm.classloader.SClass;

public class StaticsImpl implements Statics {
   // TODO[tim]: need fast-clone version
   private final Map<SClass, Object> defined;
   private final Heap heap;

   public StaticsImpl() {
      this(new FastHeap(), new HashMap<SClass, Object>());
   }

   private StaticsImpl(final Heap heap, final Map<SClass, Object> defined) {
      this.heap = heap;
      this.defined = defined;
   }

   @Override public Statics snapshot() {
      return new StaticsImpl(heap.snapshot(), new HashMap<>(defined));
   }

   @Override public Object defineClass(final SClass klass) {
      if(isDefined(klass)) {
         throw new DuplicateClassDefinitionException(klass);
      }
      final SClassDefinition klassDefinition = new SClassDefinition();
      final Object klassRef = heap.newObject(klassDefinition);
      heap.put(klassRef, 0, klass);
      defined.put(klass, klassRef);
      return klassRef;
   }

   @Override public boolean isDefined(final SClass klass) {
      return defined.containsKey(klass);
   }

   public SClass classDef(final Object klassRef) {
      return (SClass) heap.get(klassRef, 0);
   }
}
