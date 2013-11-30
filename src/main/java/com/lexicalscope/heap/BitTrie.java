package com.lexicalscope.heap;

/**
 * Here be dragons.
 *
 * @author tim
 */
final class BitTrie {
   //  8    7    6    5   4    3     2    1
   // 000-0000-0000-0000-0000-0000-0000-00000

   // These could be calculated, but have written them as constants to help the compiler.
   // I do not now if this makes any difference.

   // Trie level bit masks. Each address is a direct encoding of the location of the value
   // associated with the address in the trie. Precisely, the address is a packed sequences
   // of offsets, one for each level.
   private static final int _level1Mask = 0b00000000000000000000000000011111;
   private static final int _level2Mask = 0b00000000000000000000000111100000;
   private static final int _level3Mask = 0b00000000000000000001111000000000;
   private static final int _level4Mask = 0b00000000000000011110000000000000;
   private static final int _level5Mask = 0b00000000000111100000000000000000;
   private static final int _level6Mask = 0b00000001111000000000000000000000;
   private static final int _level7Mask = 0b00011110000000000000000000000000;
   private static final int _level8Mask = 0b11100000000000000000000000000000;

   // The levels are different widths.
   private static final int level1Width = 32;
   private static final int level2Width = 16;
   private static final int level3Width = 16;
   private static final int level4Width = 16;
   private static final int level5Width = 16;
   private static final int level6Width = 16;
   private static final int level7Width = 16;
   private static final int level8Width = 8;

   // The shifts move the relevant bits into the rightmost position so they can
   // be used as an index
   private static final int level2Shift = 5;
   private static final int level3Shift = 4 + level2Shift;
   private static final int level4Shift = 4 + level3Shift;
   private static final int level5Shift = 4 + level4Shift;
   private static final int level6Shift = 4 + level5Shift;
   private static final int level7Shift = 4 + level6Shift;
   private static final int level8Shift = 4 + level7Shift;

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


   private int free = 1; // start at 1 as 0 is reserved for null

   // because keys are allocated in sequence we only use the bottom left of the
   // tree to start with and expand the tree upward as more keyspace is used
   private int highestBit = 0;

   // we keep track of the root at each level to avoid excessive copying
   // when we give out a key that requires a new level in the tree we
   // used the next "root" and clear the old
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
   }

   public int insert(final Object value) {
      if(free == 0) throw new IndexOutOfBoundsException("BitTrie is full");
      highestBit = level(free);

      Object[] trav1 = null;
      Object[][] trav2 = null;
      Object[][][] trav3 = null;
      Object[][][][] trav4 = null;
      Object[][][][][] trav5 = null;
      Object[][][][][][] trav6 = null;
      Object[][][][][][][] trav7 = null;
      Object[][][][][][][][] trav8 = null;

      boolean init = false;
      switch (highestBit) {
         case 31:
         case 30:
         case 29:
            final int level8Offset = (free & _level8Mask) >>> level8Shift;
            if(!init){
               if(root8 == null) {init = true; root8 = new Object[level8Width][][][][][][][]; root8[0] = root7; root7 = null;} trav8 = root8;}
            if(trav8[level8Offset] == null) { trav8[level8Offset] = new Object[level7Width][][][][][][]; }

            trav7 = trav8[level8Offset];
         case 28:
         case 27:
         case 26:
         case 25:
            final int level7Offset = (free & _level7Mask) >>> level7Shift;
            if(!init){
               if(root7 == null) {init = true; root7 = new Object[level7Width][][][][][][]; root7[0] = root6; root6 = null;} trav7 = root7;}
            if(trav7[level7Offset] == null) { trav7[level7Offset] = new Object[level6Width][][][][][]; }

            trav6 = trav7[level7Offset];
         case 24:
         case 23:
         case 22:
         case 21:
            final int level6Offset = (free & _level6Mask) >>> level6Shift;
            if(!init){
               if(root6 == null) {init = true; root6 = new Object[level6Width][][][][][]; root6[0] = root5; root5 = null;} trav6 = root6;}
            if(trav6[level6Offset] == null) { trav6[level6Offset] = new Object[level5Width][][][][]; }

            trav5 = trav6[level6Offset];
         case 20:
         case 19:
         case 18:
         case 17:
            final int level5Offset = (free & _level5Mask) >>> level5Shift;
            if(!init){
               if(root5 == null) {init = true; root5 = new Object[level5Width][][][][]; root5[0] = root4; root4 = null;} trav5 = root5;}
            if(trav5[level5Offset] == null) { trav5[level5Offset] = new Object[level4Width][][][]; }

            trav4 = trav5[level5Offset];
         case 16:
         case 15:
         case 14:
         case 13:
            final int level4Offset = (free & _level4Mask) >>> level4Shift;
            if(!init){
               if(root4 == null) {init = true; root4 = new Object[level4Width][][][]; root4[0] = root3; root3 = null;} trav4 = root4;}
            if(trav4[level4Offset] == null) { trav4[level4Offset] = new Object[level3Width][][]; }

            trav3 = trav4[level4Offset];
         case 12:
         case 11:
         case 10:
         case 9:
            final int level3Offset = (free & _level3Mask) >>> level3Shift;
            if(!init){
               if(root3 == null) {init = true; root3 = new Object[level3Width][][]; root3[0] = root2; root2 = null;} trav3 = root3;}
            if(trav3[level3Offset] == null) { trav3[level3Offset] = new Object[level2Width][]; }

            trav2 = trav3[level3Offset];
         case 8:
         case 7:
         case 6:
         case 5:
            final int level2Offset = (free & _level2Mask) >>> level2Shift;
            if(!init){
               if(root2 == null) {init = true; root2 = new Object[level2Width][]; root2[0] = root1; root1 = null;} trav2 = root2;}
            if(trav2[level2Offset] == null) { trav2[level2Offset] = new Object[level1Width]; }

            trav1 = trav2[level2Offset];
         case 4:
         case 3:
         case 2:
         case 1:
         case 0:
            if(!init){
               if(root1 == null){init = true; root1 = new Object[level1Width];} trav1 = root1;
            }
            trav1[free & _level1Mask] = value;
      }

      // we expect this to overflow, as we are using our 32 bit address as unsigned
      //               MAXINT == 01111111111111111111111111111111
      // MAXINT + 1 == MININT == 10000000000000000000000000000000
      // ie.  MAXINT path is left right right right right right right right
      // then MININT path is right left left left left left left left
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
         case 13:
         case 14:
         case 15:
         case 16:
            return root4[(key & _level4Mask) >>> level4Shift][(key & _level3Mask) >>> level3Shift][(key & _level2Mask) >>> level2Shift][key & _level1Mask];
         case 17:
         case 18:
         case 19:
         case 20:
            return root5[(key & _level5Mask) >>> level5Shift][(key & _level4Mask) >>> level4Shift][(key & _level3Mask) >>> level3Shift][(key & _level2Mask) >>> level2Shift][key & _level1Mask];
         case 21:
         case 22:
         case 23:
         case 24:
            return root6[(key & _level6Mask) >>> level6Shift][(key & _level5Mask) >>> level5Shift][(key & _level4Mask) >>> level4Shift][(key & _level3Mask) >>> level3Shift][(key & _level2Mask) >>> level2Shift][key & _level1Mask];
         case 25:
         case 26:
         case 27:
         case 28:
            return root7[(key & _level7Mask) >>> level7Shift][(key & _level6Mask) >>> level6Shift][(key & _level5Mask) >>> level5Shift][(key & _level4Mask) >>> level4Shift][(key & _level3Mask) >>> level3Shift][(key & _level2Mask) >>> level2Shift][key & _level1Mask];
         case 29:
         case 30:
         case 31:
            return root8[(key & _level8Mask) >>> level8Shift][(key & _level7Mask) >>> level7Shift][(key & _level6Mask) >>> level6Shift][(key & _level5Mask) >>> level5Shift][(key & _level4Mask) >>> level4Shift][(key & _level3Mask) >>> level3Shift][(key & _level2Mask) >>> level2Shift][key & _level1Mask];
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
         case 13:
         case 14:
         case 15:
         case 16:
            return 4;
         case 17:
         case 18:
         case 19:
         case 20:
            return 5;
         case 21:
         case 22:
         case 23:
         case 24:
            return 6;
         case 25:
         case 26:
         case 27:
         case 28:
            return 7;
         case 29:
         case 30:
         case 31:
            return 8;
      }
      throw new IndexOutOfBoundsException("depth out of bounds");
   }
}
