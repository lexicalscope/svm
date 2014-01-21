package com.lexicalscope.heap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.lexicalscope.rcbittrie.BitTrie;

public class TestBitTrie {
   private final Object value = new Object();
   private final Object value1 = new Object() {@Override public String toString() {return "value1";}};
   private final Object value2 = new Object() {@Override public String toString() {return "value2";}};;

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

   @Test public void canInsertAndOverwrite100bject() {
      final BitTrie trie = new BitTrie();
      for (int i = 0; i < 100; i++) {
         trie.insert(null);
      }

      for (int i = 1; i < 101; i++) {
         trie.insert(i, null);
      }
   }
   @Test public void canAllocate100bject() {
      final BitTrie trie = new BitTrie();
      trie.allocate(100);
   }

   @Test public void object31GoesIn1() {
      testInsertAndSetAt(31, 1);
   }

   @Test public void object32GoesIn2() {
      testInsertAndSetAt(32, 2);
   }

   @Test public void object62GoesIn2() {
      testInsertAndSetAt(63, 2);
   }

   @Test public void object63GoesIn2() {
      testInsertAndSetAt(63, 2);
   }

   @Test public void object64GoesIn2() {
      testInsertAndSetAt(64, 2);
   }

   @Test public void object65GoesIn2() {
      testInsertAndSetAt(65, 2);
   }

   @Test public void object511GoesIn2() {
      testInsertAndSetAt(511, 2);
   }

   @Test public void object512GoesIn3() {
      testInsertAndSetAt(512, 3);
   }

   @Test public void object8191GoesIn3() {
      testInsertAndSetAt(8191, 3);
   }

   @Test public void object8192GoesIn4() {
      testInsertAndSetAt(8192, 4);
   }

   @Test public void object131071GoesIn4() {
      testInsertAndSetAt(131071, 4);
   }

   @Test public void object131072GoesIn5() {
      testInsertAndSetAt(131072, 5);
   }

   @Test public void object2097151GoesIn5() {
      testInsertAndSetAt(2097151, 5);
   }

   @Test public void object2097152GoesIn6() {
      testInsertAndSetAt(2097152, 6);
   }

   @Test public void object33554431GoesIn6() {
      testInsertAndSetAt(33554431, 6);
   }

   @Test public void object33554432GoesIn7() {
      testInsertAndSetAt(33554432, 7);
   }

   @Test public void object536870911GoesIn7() {
      testInsertAndSetAt(536870911, 7);
   }

   @Test public void object536870912GoesIn8() {
      testInsertAndSetAt(536870912, 8);
   }

   @Test public void objectm1GoesIn8() {
      testInsertAndSetAt(-1, 8);
   }

   @Test public void copy1isDistinct() {
      copyIsDistinct(1);
   }

   @Test public void copy31isDistinct() {
      copyIsDistinct(31);
   }

   @Test public void copy32isDistinct() {
      copyIsDistinct(32);
   }

   @Test public void copy512isDistinct() {
      copyIsDistinct(512);
   }

   @Test public void copy8192isDistinct() {
      copyIsDistinct(8192);
   }

   @Test public void copy131072isDistinct() {
      copyIsDistinct(131072);
   }

   @Test public void copy2097152isDistinct() {
      copyIsDistinct(2097152);
   }

   @Test public void copy33554432isDistinct() {
      copyIsDistinct(33554432);
   }

   @Test public void copy536870912isDistinct() {
      copyIsDistinct(536870912);
   }

   private void copyIsDistinct(final int start) {
      final BitTrie trie = new BitTrie(start);
      final int key = trie.insert(value);
      final BitTrie copy = trie.copy();

      assertThat(trie.get(key), equalTo(value));
      assertThat(copy.get(key), equalTo(value));

      assertThat(trie.insert(value1), equalTo(start+1));
      assertThat(copy.insert(value2), equalTo(start+1));

      assertThat(trie.get(start+1), equalTo(value1));
      assertThat(copy.get(start+1), equalTo(value2));
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

   private Object testInsertAndSetAt(final int start, final int depth) {
      final BitTrie trie = new BitTrie(start);
      final int key = trie.insert(value);
      assertThat(key, equalTo(start));
      assertThat(trie.get(key), equalTo(value));
      assertThat(trie.depth(), equalTo(depth));

      assertThat(trie.insert(key, value1), equalTo(value));
      assertThat(trie.get(key), equalTo(value1));
      assertThat(trie.depth(), equalTo(depth));

      return value;
   }

   @Test public void level2offsetOf31is0(){
      assertThat((31 & 0b00000000000000000000000111100000) >>> 5, equalTo(0));
   }

   @Test public void level1offsetOf31is31(){
      assertThat(31 & 0b00000000000000000000000000011111, equalTo(31));
   }

   @Test public void level2offsetOf32is1(){
      assertThat((32 & 0b00000000000000000000000111100000) >>> 5, equalTo(1));
   }

   @Test public void level1offsetOf32is1(){
      assertThat(32 & 0b00000000000000000000000000011111, equalTo(0));
   }

   @Test public void allocate1Skips1(){
      final BitTrie trie = new BitTrie();
      assertThat(trie.allocate(1), equalTo(1));
      assertThat(trie.allocate(1), equalTo(2));
   }

   @Test public void allocate5Skips5(){
      final BitTrie trie = new BitTrie();
      assertThat(trie.allocate(5), equalTo(1));
      assertThat(trie.allocate(1), equalTo(6));
   }
}