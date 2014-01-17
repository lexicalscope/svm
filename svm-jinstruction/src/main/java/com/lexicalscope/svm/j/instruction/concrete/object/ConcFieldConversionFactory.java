package com.lexicalscope.svm.j.instruction.concrete.object;


public class ConcFieldConversionFactory implements FieldConversionFactory {
   private static final class IntToInt implements FieldConversion {
      @Override public Object convert(final Object val) {
         assert val instanceof Integer;
         return val;
      }
   }

   private static final class NoConversion implements FieldConversion {
      @Override public Object convert(final Object val) {
         return val;
      }
   }

   private static final class IntToChar implements FieldConversion {
      @Override public Object convert(final Object val) {
         return (char)(int) val;
      }
   }

   private static final class CharToInt implements FieldConversion {
      @Override public Object convert(final Object val) {
         return (int)(char) val;
      }
   }

   @Override
   public NoConversion noConversion() {
      return new NoConversion();
   }

   @Override
   public IntToInt intToInt() {
      return new IntToInt();
   }

   @Override
   public IntToChar intToChar() {
      return new IntToChar();
   }

   @Override public FieldConversion charToInt() {
      return new CharToInt();
   }
}