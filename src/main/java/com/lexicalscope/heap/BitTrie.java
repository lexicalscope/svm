package com.lexicalscope.heap;

final class BitTrie {
   //  8    7    6    5   4    3     2    1
   // 000-0000-0000-0000-0000-0000-0000-00000

   private static final int _level1Mask = 0b00000000000000000000000000011111;
   private static final int _level2Mask = 0b00000000000000000000000111100000;
   private static final int _level3Mask = 0b00000000000000000001111000000000;

   private static final int level1Width = 32;
   private static final int level2Shift = 5;
   private static final int level2Width = 16;
   private static final int level3Shift = 5 + level2Shift;
   private static final int level3Width = 16;

   // lookup table for getting table level from key
   // http://graphics.stanford.edu/~seander/bithacks.html#IntegerLogLookup
   private static final int[] logTable256 = new int[256];
   static
   {
      logTable256[0] = logTable256[1] = 0;
      for (int i = 2; i < 256; i++)
      {
         logTable256[i] = 1 + logTable256[i / 2];
      }
   }


   Object[][][][][][][][] trie = new Object[8][][][][][][][];
   int free = 1; // start at 1 as 0 is reserved for null

   // because keys are allocated in sequence we only use the bottom left of the
   // tree to start with and expand the tree upward as more keyspace is used
   int highestBit = 0;

   private Object[] root1;
   private Object[][] root2;
   private Object[][][] root3;
   private Object[][][][] root4;
   private Object[][][][][] root5;
   private Object[][][][][][] root6;
   private Object[][][][][][][] root7;
   private Object[][][][][][][][] root8;

   public BitTrie() { this(1); };
   public BitTrie(final int start) {
      free = start;
      highestBit = level(free);
//      trie[0] = new Object[16][][][][][][];
//      trie[0][0] = new Object[16][][][][][];
//      trie[0][0][0] = new Object[16][][][][];
//      trie[0][0][0][0] = new Object[16][][][];
//      trie[0][0][0][0][0] = new Object[16][][];
//      trie[0][0][0][0][0][0] = new Object[16][];
//      trie[0][0][0][0][0][0][0] = new Object[32];

//      root1 = new Object[32];
//      root1 = trie[0][0][0][0][0][0];
//      root2 = trie[0][0][0][0][0];
//      root3 = trie[0][0][0][0];
//      root4 = trie[0][0][0];
//      root5 = trie[0][0];
//      root6 = trie[0];
//      root7 = trie;
   }

   public int insert(final Object value) {
      highestBit = level(free);

      System.out.println("level " + highestBit);
      System.out.println("key " + free);

      Object[] trav = null;
      boolean init = false;
      switch (highestBit) {
         case 12:
         case 11:
         case 10:
         case 9:
            final int level3Offset = (free & _level3Mask) >>> level3Shift;
            if(!init){
               if(root3 == null) {init = true; root3 = new Object[level3Width][][]; root3[0] = root2; root2 = null;} trav = root3;}
            if(trav[level3Offset] == null) { trav[level3Offset] = new Object[level2Width][]; }

            trav = (Object[]) trav[level3Offset];
         case 8:
         case 7:
         case 6:
         case 5:
            final int level2Offset = (free & _level2Mask) >>> level2Shift;
            if(!init){
               if(root2 == null) {init = true; root2 = new Object[level2Width][]; root2[0] = root1; root1 = null;} trav = root2;}
            if(trav[level2Offset] == null) { trav[level2Offset] = new Object[level1Width]; }

            trav = (Object[]) trav[level2Offset];
         case 4:
         case 3:
         case 2:
         case 1:
         case 0:
            if(!init){
               if(root1 == null){init = true; root1 = new Object[level1Width];} trav = root1;
            }
            trav[free & _level1Mask] = value;
      }
      return free++;
   }

   public Object get(final int key) {
      switch (highestBit) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
             return root1[key];
         case 5:
         case 6:
         case 7:
         case 8:
             return root2[(key & _level2Mask) >>> level2Shift][key & _level1Mask];
         case 9:
         case 10:
         case 11:
         case 12:
            return root3[(key & _level3Mask) >>> level3Shift][(key & _level2Mask) >>> level2Shift][key & _level1Mask];
      }
      throw new IndexOutOfBoundsException("the key you have requested is in free space: "+ key);
   }

   public int level(final int v) {
      int t, tt;

      if ((tt = v >>> 16) != 0)
      {
        return (t = tt >>> 8) != 0 ? 24 + logTable256[t] : 16 + logTable256[tt];
      }
      else
      {
        return (t = v >>> 8) != 0 ? 8 + logTable256[t] : logTable256[v];
      }
   }

   public int depth() {
      switch (highestBit) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
             return 1;
         case 5:
         case 6:
         case 7:
         case 8:
             return 2;
         case 9:
         case 10:
         case 11:
         case 12:
            return 3;
      }
      throw new IndexOutOfBoundsException("depth out of bounds");
   }
}
