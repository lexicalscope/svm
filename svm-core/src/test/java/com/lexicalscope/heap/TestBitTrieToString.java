package com.lexicalscope.heap;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.*;

import org.junit.*;
import org.junit.rules.ExpectedException;

import com.lexicalscope.rcbittrie.BitTrie;

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
   
   @Rule public ExpectedException exception = ExpectedException.none();
   
   @Test public void canOnlyCallNextIfElementsRemainInIterator() {
      final Iterator<Object> it = new BitTrie().iterator();
      it.next();
      exception.expect(NoSuchElementException.class);
      it.next();
   }
   
   @Test public void removeNotSupported() {
      Iterator<Object> it = new BitTrie().iterator();
      exception.expect(UnsupportedOperationException.class);
      it.remove();
   }
   
   @Test public void callingNextOnIteratorAfterInsertionOnTrieCausesAnException() {
      final BitTrie trie = new BitTrie();
      Iterator<Object> it = trie.iterator();
      trie.insert(value1);
      exception.expect(ConcurrentModificationException.class);
      it.next();
   }
}