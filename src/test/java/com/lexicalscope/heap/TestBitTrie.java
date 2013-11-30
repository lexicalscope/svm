package com.lexicalscope.heap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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

   @Test public void startAt32ObjectGoesNextPage() {
      final BitTrie trie = new BitTrie(32);
      final int key = trie.insert(value);
      assertThat(key, equalTo(32));
      assertThat(trie.get(key), equalTo(value));
      assertThat(trie.depth(), equalTo(2));
   }

   @Test public void startAt511ObjectGoesInLevel2() {
      final BitTrie trie = new BitTrie(511);
      final int key = trie.insert(value);
      assertThat(key, equalTo(511));
      assertThat(trie.get(key), equalTo(value));
      assertThat(trie.depth(), equalTo(2));
   }

   @Test public void startAt512ObjectGoesNextPage() {
      final BitTrie trie = new BitTrie(512);
      final int key = trie.insert(value);
      assertThat(key, equalTo(512));
      assertThat(trie.get(key), equalTo(value));
      assertThat(trie.depth(), equalTo(3));
   }
}
