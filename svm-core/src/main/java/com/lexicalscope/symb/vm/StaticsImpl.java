package com.lexicalscope.symb.vm;

import static org.objectweb.asm.Type.getInternalName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lexicalscope.symb.classloading.ClassLoaded;
import com.lexicalscope.symb.classloading.SClassLoader;
import com.lexicalscope.symb.klass.SClass;

public class StaticsImpl implements Statics {
   // TODO[tim]: need fast-clone version
   private static final String klassKlassName = getInternalName(Class.class);
   private final Map<String, SClass> defined;

   // TODO[tim]: combine these maps for efficiency
   private final Map<SClass, Object> staticsAddresses;
   private final Map<SClass, Object> classAddresses;

   private final SClassLoader classLoader;

   public StaticsImpl(final SClassLoader classLoader) {
      this(classLoader,
            new HashMap<String, SClass>(),
            new HashMap<SClass, Object>(),
            new HashMap<SClass, Object>());
   }

   private StaticsImpl(
         final SClassLoader classLoader,
         final Map<String, SClass> defined,
         final Map<SClass, Object> staticsAddresses,
         final Map<SClass, Object> classAddresses) {
      this.defined = defined;
      this.classLoader = classLoader;
      this.staticsAddresses = staticsAddresses;
      this.classAddresses = classAddresses;
   }

   @Override public Statics snapshot() {
      return new StaticsImpl(
            classLoader,
            new HashMap<>(defined),
            new HashMap<>(staticsAddresses),
            new HashMap<>(classAddresses));
   }

   @Override public List<SClass> defineClass(final String klassName) {
      if(isDefined(klassName)) {
         throw new DuplicateClassDefinitionException(defined.get(klassName));
      }

      final List<SClass> result = new ArrayList<>();
      classLoader.load(klassName, new ClassLoaded(){
         @Override public void loaded(final SClass klass) {
            if(!defined.containsKey(klass.name())) {
               result.add(cache(klass.name(), klass));
            }
         }});

      return result;
   }

   @Override public SClass definePrimitiveClass(final String klassName) {
      if(isDefined(klassName)) {
         throw new DuplicateClassDefinitionException(defined.get(klassName));
      }

      final PrimitiveSClass result = new PrimitiveSClass(klassName);
      cache(klassName, result);
      return result;
   }

   private SClass cache(final String klassName, final SClass result) {
      defined.put(klassName, result);
      return result;
   }

   @Override public boolean isDefined(final String klass) {
      return defined.containsKey(klass);
   }

   @Override public SClass load(final String klassName) {
      if(!isDefined(klassName)) {
         throw new MissingClassDefinitionException(klassName, defined);
      }
      return defined.get(klassName);
   }

   @Override public void staticsAt(final SClass klass, final Object staticsAddress) {
      assert !staticsAddresses.containsKey(klass);
      staticsAddresses.put(klass, staticsAddress);
   }

   @Override public Object whereMyStaticsAt(final SClass klass) {
      final Object address = staticsAddresses.get(klass);
      if(address == null) {
         throw new IllegalStateException("no statics for " + klass);
      }
      return address;
   }

   @Override public void classAt(final SClass klass, final Object classAddress) {
      assert !classAddresses.containsKey(klass);
      classAddresses.put(klass, classAddress);
   }

   @Override public Object whereMyClassAt(final SClass klass) {
      final Object address = classAddresses.get(klass);
      if(address == null) {
         throw new IllegalStateException("no class for " + klass);
      }
      return address;
   }

   @Override public Object whereMyClassAt(final String internalName) {
      final SClass klass = load(internalName);
      return whereMyClassAt(klass);
   }

   @Override public SClass classClass() {
      return load(klassKlassName);
   }

   @Override public StaticsMarker staticsMarker(final SClass klass) {
      return new StaticsMarker(klass);
   }
}
