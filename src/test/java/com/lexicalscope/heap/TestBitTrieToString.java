package com.lexicalscope.heap;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

import static org.junit.Assert.assertThat;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class TestBitTrieToString {
   private final Object value1 = new Object() {@Override public String toString() {return "value1";}};
   private final Object value2 = new Object() {@Override public String toString() {return "value2";}};
 
   @Test public void bitTrieIteratorGivesItemsInOrder() {
      final BitTrie trie = new BitTrie();
      trie.insert(value1);
      trie.insert(value2);
      assertThat(trie, contains(null, value1, value2));
   }
 
   @Test public void bitTrieIteratorGivesInitialNullsWhenStartIsNot1() {
      final BitTrie trie = new BitTrie(3);
      trie.insert(value1);
      trie.insert(value2);
      assertThat(trie, contains(null, null, null, value1, value2));
   }
   
   @Test public void bitTrieToStringPresentsOrderedStringRepresentationsOfItems() {
      final BitTrie trie = new BitTrie(1);
      trie.insert(value1);
      trie.insert(value2);
      assertThat(trie.toString(), equalTo("[null, value1, value2]"));  
   }
   
   @Test(expected=NoSuchElementException.class) 
   public void canOnlyCallNextIfElementsRemainInIterator() {
      final Iterator<Object> it = new BitTrie().iterator();
      it.next();
      it.next();
   }
   
   @Test(expected=UnsupportedOperationException.class) 
   public void removeNotSupported() {
      new BitTrie().iterator().remove();
   }
   
   @Test(expected=ConcurrentModificationException.class)
   public void callingNextOnIteratorAfterInsertionOnTrieCausesAnException() {
      final BitTrie trie = new BitTrie();
      Iterator<Object> it = trie.iterator();
      trie.insert(value1);
      it.next();
   }
}