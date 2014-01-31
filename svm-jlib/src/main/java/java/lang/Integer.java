package java.lang;

public class Integer {
   public Integer(final int i) { }

   @SuppressWarnings("unused")
   private static class IntegerCache {
      static final int low = -128;
      static final int high;
      static final Integer cache[];

      static {
         high = 127;

         cache = new Integer[high - low + 1];
         int j = low;
         for(int k = 0; k < cache.length; k++) {
            cache[k] = new java.lang.Integer(j++);
         }
      }

      private IntegerCache() {}
   }
}
