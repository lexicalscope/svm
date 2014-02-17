package com.lexicalscope.svm.search;

import java.util.Random;

public class RandomSeedPseudoRandomiser implements Randomiser {
   private final Random random;

   public RandomSeedPseudoRandomiser() {
      random = new Random();
   }

   @Override public int random(final int count) {
      return random.nextInt(count);
   }
}
