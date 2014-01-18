package com.lexicalscope.symb.vm.classloader;

import java.util.HashMap;
import java.util.Map;

import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.symb.vm.classloader.asm.AsmSClass;

public class CachingByteCodeReader implements ByteCodeReader {
   // TODO[tim]: should probably allow class definitions to be garbage collected using weak references
   private final Map<String, AsmSClass> classCache = new HashMap<>();
   private final ResourceByteCodeReader byteCodeReader;

   public CachingByteCodeReader(final Instructions instructions) {
      byteCodeReader = new ResourceByteCodeReader(instructions);
   }

   @Override
   public AsmSClass load(final SClassLoader classLoader, final String name, final ClassLoaded classLoaded) {
      assert name != null;
      AsmSClass result = classCache.get(name);
      if(result == null) {
         result = byteCodeReader.load(classLoader, name, classLoaded);
         classCache.put(name, result);
      } else {
         // TODO[tim]: simplify this, this cache is shared across multiple snapshots
         // the cache in Statics gives semantics to the classloading
         classLoaded.loaded(result);
      }
      return result;
   }
}
