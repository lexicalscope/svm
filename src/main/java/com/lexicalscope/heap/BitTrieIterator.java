package com.lexicalscope.heap;

import java.util.Iterator;

public class BitTrieIterator implements Iterator<Object> {

   private BitTrie trie;
   private int start;
   private int current;

   public BitTrieIterator(BitTrie trie, int start) {
      this.trie = trie;
      this.start = start;
      current = 1;
   }

   @Override
   public boolean hasNext() {
      return current < start || null != trie.get(current);
   }

   @Override
   public Object next() {
      return current++ < start ? null : trie.get(current-1);
   }

   @Override
   public void remove() {
      throw new UnsupportedOperationException();
   }

}
