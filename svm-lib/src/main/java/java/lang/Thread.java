package java.lang;

public class Thread implements Runnable {
   @Override public void run() {
      // this thread does nothing
   }

   public static native Thread currentThread();

   public long getId() {
      // we only have one thread
      return 1L;
   }
}
