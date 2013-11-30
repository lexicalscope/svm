package com.lexicalscope.heap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestBitTrie {
   private final Object value = new Object();

   @Test public void firstObjectGoesBottomLeft() {
      final BitTrie trie = new BitTrie();
      final int key = trie.insert(value);
      assertThat(trie.get(key), equalTo(value));
   }

   @Test public void object32GoesNextPage() {
      final BitTrie trie = new BitTrie();
      for (int i = 0; i < 31; i++) {
         trie.insert(null);
      }
      final int key = trie.insert(value);
      assertThat(trie.get(key), equalTo(value));
   }

   @Test public void object31GoesIn1() {
      testInsertAt(31, 1);
   }

   @Test public void object32GoesIn2() {
      testInsertAt(32, 2);
   }

   @Test public void object511GoesIn2() {
      testInsertAt(511, 2);
   }

   @Test public void object512GoesIn3() {
      testInsertAt(512, 3);
   }

   @Test public void object8191GoesIn3() {
      testInsertAt(8191, 3);
   }

   @Test public void object8192GoesIn4() {
      testInsertAt(8192, 4);
   }

   @Test public void object131071GoesIn4() {
      testInsertAt(131071, 4);
   }

   @Test public void object131072GoesIn5() {
      testInsertAt(131072, 5);
   }

   @Test public void object2097151GoesIn5() {
      testInsertAt(2097151, 5);
   }

   @Test public void object2097152GoesIn6() {
      testInsertAt(2097152, 6);
   }

   @Test public void object33554431GoesIn6() {
      testInsertAt(33554431, 6);
   }

   @Test public void object33554432GoesIn7() {
      testInsertAt(33554432, 7);
   }

   @Test public void object536870911GoesIn7() {
      testInsertAt(536870911, 7);
   }

   @Test public void object536870912GoesIn8() {
      testInsertAt(536870912, 8);
   }

   @Test public void objectm1GoesIn8() {
      testInsertAt(-1, 8);
   }

   @Test public void objectm1IsLastObject() {
      final BitTrie trie = new BitTrie(-1);
      assertThat(trie.insert(value), equalTo(-1));
      try{
         trie.insert(value);
         fail("insert past max key");
      } catch (final IndexOutOfBoundsException e) {
         // OK
      }
   }

   @Test public void objectMaxIntOverflowsCorrectly() {
      final BitTrie trie = new BitTrie(Integer.MAX_VALUE);
      assertThat(trie.insert(value), equalTo(Integer.MAX_VALUE));
      assertThat(trie.insert(value), equalTo(Integer.MIN_VALUE));
   }

   private void testInsertAt(final int start, final int depth) {
      final BitTrie trie = new BitTrie(start);
      final int key = trie.insert(value);
      assertThat(key, equalTo(start));
      assertThat(trie.get(key), equalTo(value));
      assertThat(trie.depth(), equalTo(depth));
   }
}
