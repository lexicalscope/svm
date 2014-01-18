package com.lexicalscope.symb.vm;

import java.util.List;

import com.lexicalscope.symb.klass.SClass;
import com.lexicalscope.symb.state.Snapshotable;


public interface Statics extends Snapshotable<Statics> {
   SClass load(String klassName);

   List<SClass> defineClass(String klass);
   SClass definePrimitiveClass(String klassName);

   StaticsMarker staticsMarker(SClass klass);

   void staticsAt(SClass klass, Object staticsAddress);
   Object whereMyStaticsAt(SClass klass);

   void classAt(SClass klass, Object classAddress);
   Object whereMyClassAt(SClass klass);
   Object whereMyClassAt(String internalName);

   boolean isDefined(String klass);

   SClass classClass();
}
