package com.lexicalscope.svm.versions;

public class Version implements AutoCloseable{
   private static int version = Integer.MAX_VALUE;

   public static boolean atMost(final int query)
   {
      return version <= query;
   }

   public static Version at(final int version) {
      Version.version = version;
      return new Version();
   }

   @Override public void close() {
         Version.version = Integer.MAX_VALUE;
   }
}

