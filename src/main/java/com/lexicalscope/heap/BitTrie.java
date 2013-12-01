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
   private Node1 root1;
   private Node2 root2;
   private Node3 root3;
   private Node4 root4;
   private Node5 root5;
   private Node6 root6;
   private Node7 root7;
   private Node8 root8;

   private static final class Node1 {
      public Node1() {
         this(new Object[level1Width]);
      }

      public Node1(final Object[] clone) {
         d = clone;
      }

      Object[] d;
      int c = 1;
   }

   private static final class Node2 {
      public Node2() {
         this(new Node1[level2Width]);
      }

      public Node2(final Node1[] clone) {
         d = clone;
      }

      Node1[] d;
      int c = 1;
   }

   private static final class Node3 {
      public Node3() {
         this(new Node2[level3Width]);
      }

      public Node3(final Node2[] clone) {
         d = clone;
      }

      Node2[] d;
      int c = 1;
   }

   private static final class Node4 {
      public Node4() {
         this(new Node3[level4Width]);
      }

      public Node4(final Node3[] clone) {
         d = clone;
      }

      Node3[] d;
      int c = 1;
   }

   private static final class Node5 {
      public Node5() {
         this(new Node4[level5Width]);
      }

      public Node5(final Node4[] clone) {
         d = clone;
      }

      Node4[] d;
      int c = 1;
   }

   private static final class Node6 {
      public Node6() {
         this(new Node5[level6Width]);
      }

      public Node6(final Node5[] clone) {
         d = clone;
      }

      Node5[] d;
      int c = 1;
   }

   private static final class Node7 {
      public Node7() {
         this(new Node6[level7Width]);
      }

      public Node7(final Node6[] clone) {
         d = clone;
      }

      Node6[] d;
      int c = 1;
   }

   private static final class Node8 {
      public Node8() {
         this(new Node7[level8Width]);
      }

      public Node8(final Node7[] clone) {
         d = clone;
      }

      Node7[] d;
      int c = 1;
   }

   public BitTrie() { this(1); };
   public BitTrie(final int start) {
      free = start;
      highestBit = level(free - 1);
   }

   public int insert(final Object value) {
      assert root1 != null ^ root2 != null ^ root3 != null ^ root4 != null ^ root5 != null ^ root6 != null ^ root7 != null ^ root8 != null
           || root1 == null && root2 == null && root3 == null && root4 == null && root5 == null && root6 == null && root7 == null && root8 == null;

      if(free == 0) throw new IndexOutOfBoundsException("BitTrie is full");
      highestBit = level(free);

      Node8 trav8 = null;
      Node7 trav7 = null;
      Node6 trav6 = null;
      Node5 trav5 = null;
      Node4 trav4 = null;
      Node3 trav3 = null;
      Node2 trav2 = null;
      Node1 trav1 = null;

      boolean init = false;
      switch (highestBit) {
         case 31:
         case 30:
         case 29:
            final int level8Offset = (free & _level8Mask) >>> level8Shift;
            init = true;
            if(root8 == null) {root8 = new Node8(); root8.d[0] = root7; root7 = null;}
            if(root8.c > 1) {final Node8 copy = new Node8(root8.d.clone()); root8.c--; root8 = copy; for(int i = 0; i < root8.d.length; i++){if(root8.d[i] != null){root8.d[i].c++;}}}
            trav8 = root8;
            if(trav8.d[level8Offset] == null) { trav8.d[level8Offset] = new Node7(); }

            trav7 = trav8.d[level8Offset];
            if(trav7.c > 1) {final Node7 copy = new Node7(trav7.d.clone()); trav7.c--; trav8.d[level8Offset] = trav7 = copy; for(int i = 0; i < trav7.d.length; i++){if(trav7.d[i] != null){trav7.d[i].c++;}}}
         case 28:
         case 27:
         case 26:
         case 25:
            final int level7Offset = (free & _level7Mask) >>> level7Shift;
            if(!init){
               init = true;
               if(root7 == null) {root7 = new Node7(); root7.d[0] = root6; root6 = null;}
               if(root7.c > 1) {final Node7 copy = new Node7(root7.d.clone()); root7.c--; root7 = copy; for(int i = 0; i < root7.d.length; i++){if(root7.d[i] != null){root7.d[i].c++;}}}
               trav7 = root7;}
            if(trav7.d[level7Offset] == null) { trav7.d[level7Offset] = new Node6(); }

            trav6 = trav7.d[level7Offset];
            if(trav6.c > 1) {final Node6 copy = new Node6(trav6.d.clone()); trav6.c--; trav7.d[level7Offset] = trav6 = copy; for(int i = 0; i < trav6.d.length; i++){if(trav6.d[i] != null){trav6.d[i].c++;}}}
         case 24:
         case 23:
         case 22:
         case 21:
            final int level6Offset = (free & _level6Mask) >>> level6Shift;
            if(!init){
               init = true;
               if(root6 == null) {root6 = new Node6(); root6.d[0] = root5; root5 = null;}
               if(root6.c > 1) {final Node6 copy = new Node6(root6.d.clone()); root6.c--; root6 = copy; for(int i = 0; i < root6.d.length; i++){if(root6.d[i] != null){root6.d[i].c++;}}}
               trav6 = root6;}
            if(trav6.d[level6Offset] == null) { trav6.d[level6Offset] = new Node5(); }

            trav5 = trav6.d[level6Offset];
            if(trav5.c > 1) {final Node5 copy = new Node5(trav5.d.clone()); trav5.c--; trav6.d[level6Offset] = trav5 = copy; for(int i = 0; i < trav5.d.length; i++){if(trav5.d[i] != null){trav5.d[i].c++;}}}
         case 20:
         case 19:
         case 18:
         case 17:
            final int level5Offset = (free & _level5Mask) >>> level5Shift;
            if(!init){
               init = true;
               if(root5 == null) {root5 = new Node5(); root5.d[0] = root4; root4 = null;}
               if(root5.c > 1) {final Node5 copy = new Node5(root5.d.clone()); root5.c--; root5 = copy; for(int i = 0; i < root5.d.length; i++){if(root5.d[i] != null){root5.d[i].c++;}}}
               trav5 = root5;}
            if(trav5.d[level5Offset] == null) { trav5.d[level5Offset] = new Node4(); }

            trav4 = trav5.d[level5Offset];
            if(trav4.c > 1) {final Node4 copy = new Node4(trav4.d.clone()); trav4.c--; trav5.d[level5Offset] = trav4 = copy; for(int i = 0; i < trav4.d.length; i++){if(trav4.d[i] != null){trav4.d[i].c++;}}}
         case 16:
         case 15:
         case 14:
         case 13:
            final int level4Offset = (free & _level4Mask) >>> level4Shift;
            if(!init){
               init = true;
               if(root4 == null) {root4 = new Node4(); root4.d[0] = root3; root3 = null;}
               if(root4.c > 1) {final Node4 copy = new Node4(root4.d.clone()); root4.c--; root4 = copy; for(int i = 0; i < root4.d.length; i++){if(root4.d[i] != null){root4.d[i].c++;}}}
               trav4 = root4;}
            if(trav4.d[level4Offset] == null) { trav4.d[level4Offset] = new Node3(); }

            trav3 = trav4.d[level4Offset];
            if(trav3.c > 1) {final Node3 copy = new Node3(trav3.d.clone()); trav3.c--; trav4.d[level4Offset] = trav3 = copy; for(int i = 0; i < trav3.d.length; i++){if(trav3.d[i] != null){trav3.d[i].c++;}}}
         case 12:
         case 11:
         case 10:
         case 9:
            final int level3Offset = (free & _level3Mask) >>> level3Shift;
            if(!init){
               init = true;
               if(root3 == null) {root3 = new Node3(); root3.d[0] = root2; root2 = null;}
               if(root3.c > 1) {final Node3 copy = new Node3(root3.d.clone()); root3.c--; root3 = copy; for(int i = 0; i < root3.d.length; i++){if(root3.d[i] != null){root3.d[i].c++;}}}
               trav3 = root3;}
            if(trav3.d[level3Offset] == null) { trav3.d[level3Offset] = new Node2(); }

            trav2 = trav3.d[level3Offset];
            if(trav2.c > 1) {final Node2 copy = new Node2(trav2.d.clone()); trav2.c--; trav3.d[level3Offset] = trav2 = copy; for(int i = 0; i < trav2.d.length; i++){if(trav2.d[i] != null){trav2.d[i].c++;}}}
         case 8:
         case 7:
         case 6:
         case 5:
            final int level2Offset = (free & _level2Mask) >>> level2Shift;
            if(!init){
               init = true;
               if(root2 == null) {root2 = new Node2(); root2.d[0] = root1; root1 = null;}
               if(root2.c > 1) {final Node2 copy = new Node2(root2.d.clone()); root2.c--; root2 = copy; for(int i = 0; i < root2.d.length; i++){if(root2.d[i] != null){root2.d[i].c++;}}}
               trav2 = root2;}
            if(trav2.d[level2Offset] == null) { trav2.d[level2Offset] = new Node1(); }

            trav1 = trav2.d[level2Offset];
            if(trav1.c > 1) {final Node1 copy = new Node1(trav1.d.clone()); trav1.c--; trav2.d[level2Offset] = trav1 = copy;}
         case 4:
         case 3:
         case 2:
         case 1:
         case 0:
            if(!init){
               init = true;
               if(root1 == null){root1 = new Node1(); root1.d = new Object[level1Width];}
               if(root1.c > 1) {final Node1 copy = new Node1(root1.d.clone()); root1.c--; root1 = copy;}
               trav1 = root1;

            }
            trav1.d[free & _level1Mask] = value;
      }

      // we expect this to overflow, as we are using our 32 bit address as unsigned
      //               MAXINT == 01111111111111111111111111111111
      // MAXINT + 1 == MININT == 10000000000000000000000000000000
      // ie.  MAXINT path is left right right right right right right right
      // then MININT path is right left left left left left left left
      return free++;
   }

   public Object get(final int key) {
      if(key == 0) { return null; };
      Node7 trav7 = null;
      Node6 trav6 = null;
      Node5 trav5 = null;
      Node4 trav4 = null;
      Node3 trav3 = null;
      Node2 trav2 = null;
      Node1 trav1 = null;

      switch (highestBit) {
         case 31:
         case 30:
         case 29:
            trav7 = root8.d[(key & _level8Mask) >>> level8Shift];
         case 28:
         case 27:
         case 26:
         case 25:
            if(trav7 == null) trav7 = root7;
             trav6 = trav7.d[(key & _level7Mask) >>> level7Shift];
         case 24:
         case 23:
         case 22:
         case 21:
            if(trav6 == null) trav6 = root6;
             trav5 = trav6.d[(key & _level6Mask) >>> level6Shift];
         case 20:
         case 19:
         case 18:
         case 17:
            if(trav5 == null) trav5 = root5;
             trav4 = trav5.d[(key & _level5Mask) >>> level5Shift];
         case 16:
         case 15:
         case 14:
         case 13:
            if(trav4 == null) trav4 = root4;
            trav3 = trav4.d[(key & _level4Mask) >>> level4Shift];
         case 12:
         case 11:
         case 10:
         case 9:
            if(trav3 == null) trav3 = root3;
            trav2 = trav3.d[(key & _level3Mask) >>> level3Shift];
         case 8:
         case 7:
         case 6:
         case 5:
            if(trav2 == null) trav2 = root2;
            trav1 = trav2.d[(key & _level2Mask) >>> level2Shift];
         case 4:
         case 3:
         case 2:
         case 1:
         case 0:
            if(trav1 == null) trav1 = root1;
            return trav1.d[key & _level1Mask];
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

   public BitTrie copy() {
      final BitTrie result = new BitTrie(free);
      switch (highestBit) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
             result.root1 = root1;
             root1.c++;
             break;
         case 5:
         case 6:
         case 7:
         case 8:
            result.root2 = root2;
            root2.c++;
            break;
         case 9:
         case 10:
         case 11:
         case 12:
            result.root3 = root3;
            root3.c++;
            break;
         case 13:
         case 14:
         case 15:
         case 16:
            result.root4 = root4;
            root4.c++;
            break;
         case 17:
         case 18:
         case 19:
         case 20:
            result.root5 = root5;
            root5.c++;
            break;
         case 21:
         case 22:
         case 23:
         case 24:
            result.root6 = root6;
            root6.c++;
            break;
         case 25:
         case 26:
         case 27:
         case 28:
            result.root7 = root7;
            root7.c++;
            break;
         case 29:
         case 30:
         case 31:
            result.root8 = root8;
            root8.c++;
            break;
      }
      return result;
   }
}
