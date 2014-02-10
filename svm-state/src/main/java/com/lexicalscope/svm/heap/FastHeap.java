package com.lexicalscope.svm.heap;

import com.lexicalscope.rcbittrie.BitTrie;


public class FastHeap implements Heap {
   private final BitTrie trie;

   public FastHeap(final BitTrie trie) {
      this.trie = trie;
   }

   public FastHeap() {
      this(new BitTrie());
   }

   @Override
   public Object newObject(final Allocatable klass) {
      final int key = trie.allocate(klass.allocateSize());
      return new ObjectRef(key);
   }

   @Override
   public void put(final Object obj, final int offset, final Object val) {
      trie.insert(objectForRef(obj) + offset, val);
   }

   @Override
   public Object get(final Object obj, final int offset) {
      assert offset >= 0;
      return trie.get(objectForRef(obj) + offset);
   }

   private int objectForRef(final Object obj) {
      return ((ObjectRef) obj).address();
   }

   @Override public Object nullPointer() {
      return new ObjectRef(trie.nullPointer());
   }

   @Override
   public Heap snapshot() {
      return new FastHeap(trie.copy());
   }

   @Override
   public String toString() {
      return trie.toString();
   }

   @Override public int hashCode(final Object address) {
      return objectForRef(address);
   }
}
