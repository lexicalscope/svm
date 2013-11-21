package com.lexicalscope.symb.vm.classloader;

import java.util.List;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class SClass {
   private final ClassNode classNode;

   public SClass(final ClassNode classNode) {
      this.classNode = classNode;
   }

   public SMethod mainMethod() {
       for (final MethodNode method : methods()) {
          if(method.name.equals("main") && Type.getArgumentTypes(method.desc).length == 1)
          {
             return new SMethod(method);
          }
      }
      throw new SMethodNotFoundException("main");
   }

   @SuppressWarnings("unchecked")
   private List<MethodNode> methods() {
      return classNode.methods;
   }

   public SMethod staticMethod(final String name, final String desc) {
      for (final MethodNode method : methods()) {
         if(method.name.equals(name) && method.desc.equals(desc))
         {
            return new SMethod(method);
         }
     }
     throw new SMethodNotFoundException("main");
   }
}
