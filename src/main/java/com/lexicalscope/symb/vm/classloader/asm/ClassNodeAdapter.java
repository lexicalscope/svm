package com.lexicalscope.symb.vm.classloader.asm;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import com.lexicalscope.symb.vm.JavaConstants;

public class ClassNodeAdapter {
   private final ClassNode classNode;

   public ClassNodeAdapter(final ClassNode classNode) {
      this.classNode = classNode;
   }

   @SuppressWarnings("unchecked")
   public List<FieldNode> fields() {
      if(classNode.name.equals(JavaConstants.CLASS_CLASS)) {
         final List<FieldNode> result = new ArrayList<>(classNode.fields);
         result.add(new FieldNode(Opcodes.ACC_PRIVATE | Opcodes.ACC_FINAL, AsmSClass.internalClassPointer.getName(), Type.getDescriptor(Object.class), "Ljava/lang/Object;", null));
         return result;
      }
      return classNode.fields;
   }
}
