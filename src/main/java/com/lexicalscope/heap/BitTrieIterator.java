package com.lexicalscope.heap;

import java.util.Iterator;

public class BitTrieIterator implements Iterator<Object> {

   private BitTrie trie;
   private int currentIndex;
   private int endIndex;

   public BitTrieIterator(BitTrie trie, int endIndex) {
      this.trie = trie;
      this.endIndex = endIndex;
      currentIndex = 0;
   }

   @Override
   public boolean hasNext() {
      return currentIndex <= endIndex;
   }

   @Override
   public Object next() {
      return trie.get(currentIndex++);
   }

   @Override
   public void remove() {
      throw new UnsupportedOperationException();
   }

}
