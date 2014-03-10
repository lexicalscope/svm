package com.lexicalscope.svm.vm.j;

import java.util.List;

import com.lexicalscope.svm.heap.ObjectRef;
import com.lexicalscope.svm.state.Snapshotable;
import com.lexicalscope.svm.vm.j.klass.SClass;


public interface Statics extends Snapshotable<Statics> {
   Object INTRINSIC_TAG = new Object();

   SClass load(String klassName);

   List<SClass> defineClass(String klass);
   SClass definePrimitiveClass(String klassName);

   StaticsMarker staticsMarker(SClass klass);

   void staticsAt(SClass klass, ObjectRef staticsAddress);
   ObjectRef whereMyStaticsAt(SClass klass);

   void classAt(SClass klass, ObjectRef classAddress);
   ObjectRef whereMyClassAt(SClass klass);
   ObjectRef whereMyClassAt(String internalName);

   boolean isDefined(String klass);

   SClass classClass();
}
