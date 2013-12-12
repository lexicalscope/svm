package com.lexicalscope.symb.vm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lexicalscope.symb.vm.classloader.ClassLoaded;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SMethod;

public class StaticsImpl implements Statics {
   // TODO[tim]: need fast-clone version
   private final Map<String, SClass> defined;
   private final Map<SClass, Object> staticsAddresses;

   private final SClassLoader classLoader;

   public StaticsImpl(final SClassLoader classLoader) {
      this(classLoader, new HashMap<String, SClass>(), new HashMap<SClass, Object>());
   }

   private StaticsImpl(final SClassLoader classLoader, final Map<String, SClass> defined, final Map<SClass, Object> staticsAddresses) {
      this.defined = defined;
      this.classLoader = classLoader;
      this.staticsAddresses = staticsAddresses;
   }

   @Override public Statics snapshot() {
      return new StaticsImpl(classLoader, new HashMap<>(defined), new HashMap<>(staticsAddresses));
   }

   @Override public List<SClass> defineClass(final String klassName) {
      if(isDefined(klassName)) {
         throw new DuplicateClassDefinitionException(defined.get(klassName));
      }

      final List<SClass> result = new ArrayList<>();
      classLoader.load(klassName, new ClassLoaded(){
         @Override public void loaded(final SClass klass) {
            if(!defined.containsKey(klass.name())) {
               defined.put(klass.name(), klass);
               result.add(klass);
            }
         }});

      return result;
   }

   @Override public boolean isDefined(final String klass) {
      return defined.containsKey(klass);
   }

   @Override public SClass load(final String klassName) {
      if(!isDefined(klassName)) {
         throw new MissingClassDefinitionException(klassName);
      }
      return defined.get(klassName);
   }

   @Override public SMethod loadMethod(final String klassName, final String name, final String desc) {
      if(!isDefined(klassName)) {
         throw new MissingClassDefinitionException(klassName);
      }
      return load(klassName).staticMethod(name, desc);
   }

   @Override public void staticsAt(final SClass klass, final Object staticsAddress) {
      assert !staticsAddresses.containsKey(klass);
      staticsAddresses.put(klass, staticsAddress);
   }

   @Override public Object whereMyStaticsAt(final SClass klass) {
      final Object address = staticsAddresses.get(klass);
      if(address == null) throw new IllegalStateException("no statics for " + klass);
      return address;
   }
}
