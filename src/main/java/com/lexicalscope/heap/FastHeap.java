package com.lexicalscope.heap;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.ObjectRef;
import com.lexicalscope.symb.vm.SObject;

public class FastHeap implements Heap {
   private final BitTrie trie;

   public FastHeap(final BitTrie trie) {
      this.trie = trie;
   }

   public FastHeap() {
      this(new BitTrie());
   }

   @Override
   public Object newObject() {
      final int key = trie.insert(new SObject());
      return new ObjectRef(key);
   }

   @Override
   public void put(final Object obj, final String string, final Object val) {
      objectForRef(obj).put(string, val);
   }

   @Override
   public Object get(final Object obj, final String string) {
      return objectForRef(obj).get(string);
   }

   private SObject objectForRef(final Object obj) {
      return (SObject) trie.get(((ObjectRef) obj).address());
   }

   @Override
   public Heap snapshot() {
      return new FastHeap(trie.copy());
   }
}
