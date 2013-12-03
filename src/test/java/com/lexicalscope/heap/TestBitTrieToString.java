package com.lexicalscope.heap;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestBitTrieToString {
   private final Object value1 = new Object() {@Override public String toString() {return "value1";}};
   private final Object value2 = new Object() {@Override public String toString() {return "value2";}};
 
   @Test public void bitTrieIteratorGivesItemsInOrder() {
      final BitTrie trie = new BitTrie();
      trie.insert(value1);
      trie.insert(value2);
      assertThat(trie, contains(value1, value2));
   }
 
   @Test public void bitTrieIteratorGivesInitialNullsWhenStartIsNot1() {
      final BitTrie trie = new BitTrie(3);
      trie.insert(value1);
      trie.insert(value2);
      assertThat(trie, contains(null, null, value1, value2));
   }
   
   @Test
   public void bitTrieToStringPresentsOrderedStringRepresentationsOfItems() {
      final BitTrie trie = new BitTrie(2);
      trie.insert(value1);
      trie.insert(value2);
      assertThat(trie.toString(), equalTo("[null, value1, value2]"));  
   }
}