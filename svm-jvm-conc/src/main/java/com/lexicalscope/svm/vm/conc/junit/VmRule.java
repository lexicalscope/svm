package com.lexicalscope.svm.vm.conc.junit;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static com.lexicalscope.svm.classloading.ClasspathClassRepository.classpathClassRepostory;
import static org.objectweb.asm.Type.getMethodDescriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.lexicalscope.fluentreflection.FluentAnnotated;
import com.lexicalscope.fluentreflection.FluentField;
import com.lexicalscope.fluentreflection.FluentMethod;
import com.lexicalscope.fluentreflection.FluentObject;
import com.lexicalscope.fluentreflection.FluentReflection;
import com.lexicalscope.fluentreflection.ReflectionMatcher;
import com.lexicalscope.svm.classloading.ClassSource;
import com.lexicalscope.svm.classloading.ClasspathClassRepository;
import com.lexicalscope.svm.vm.Vm;
import com.lexicalscope.svm.vm.conc.InitialStateBuilder;
import com.lexicalscope.svm.vm.conc.JvmBuilder;
import com.lexicalscope.svm.vm.conc.LoadFrom;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.StateTag;
import com.lexicalscope.svm.vm.j.code.AsmSMethodName;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public class VmRule implements MethodRule {
   private final ReflectionMatcher<FluentAnnotated> annotatedWithTestPointEntry = annotatedWith(TestEntryPoint.class);
   private final JvmBuilder jvmBuilder;
   private final Map<String, SMethodDescriptor> entryPoints = new HashMap<>();
   private SMethodDescriptor entryPoint;

   private final List<Vm<JState>> vm = new ArrayList<>();
   private ClassSource[] classSources = new ClassSource[]{new ClasspathClassRepository()};
   private StateTag[] tags = new StateTag[]{new StateTag() {}};

   public VmRule() {
      this(new JvmBuilder());
   }

   public VmRule(final JvmBuilder jvmBuilder) {
      this.jvmBuilder = jvmBuilder;
   }

   public JvmBuilder builder() {
      return jvmBuilder;
   }

   public InitialStateBuilder initialStateBuilder() {
      return builder().initialState();
   }

   @Override public final Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
      return new Statement() {

         @Override public void evaluate() throws Throwable {
            final FluentObject<Object> object = object(target);
            findEntryPoints(object);
            findEntryPoint(object);
            configureTarget(object);
            configureVms(object);

            base.evaluate();
            cleanup();
         }

         private void configureVms(final FluentObject<Object> object) {
            final List<FluentField> fields = object.fields(hasType(VmWrap.class));
            for (final FluentField field : fields) {
               field.call(new VmWrap(VmRule.this, field.annotation(LoadFrom.class)));
            }
         }

         private void findEntryPoint(final FluentObject<Object> object) {
            final FluentMethod targetMethod = FluentReflection.method(method.getMethod());
            if(targetMethod.annotatedWith(WithEntryPoint.class)) {
               final String requiredEntryPoint = targetMethod.annotation(WithEntryPoint.class).value();
               entryPoint = entryPoints.get(requiredEntryPoint);
            } else if(entryPoints.size() == 1) {
               entryPoint = entryPoints.values().iterator().next();
            }
            if(entryPoint == null) {
               throw new AssertionError("no entry point found");
            }
         }

         private void findEntryPoints(final FluentObject<Object> object) {
            for (final FluentMethod entryPointMethod : object.reflectedClass().methods(annotatedWithTestPointEntry)) {
               entryPoints.put(entryPointMethod.name(), new AsmSMethodName(
                     object.classUnderReflection(),
                     entryPointMethod.name(),
                     getMethodDescriptor(entryPointMethod.member())));
            }
         }
      };
   }

   protected void cleanup() {
      // can be overridden
   }

   protected void configureTarget(final FluentObject<Object> object) {
      // can be overridden
   }

   public final void entryPoint(final Class<?> klass, final String name, final String desc) {
      this.entryPoint = new AsmSMethodName(klass, name, desc);
   }

   public final Vm<JState> build(final Object[] args) {
      return jvmBuilder.build(tags, classSources, entryPoint, args);
   }

   public void loadFrom(final Class<?>[] loadFromWhereverTheseWereLoaded) {
      classSources = new ClassSource[loadFromWhereverTheseWereLoaded.length];
      tags = new StateTag[loadFromWhereverTheseWereLoaded.length];
      for (int i = 0; i < classSources.length; i++) {
         classSources[i] = classpathClassRepostory(loadFromWhereverTheseWereLoaded[i]);
         tags[i] = new ClassStateTag(loadFromWhereverTheseWereLoaded[i]);
      }
   }

   public final JState execute(final Object ... args) {
      if(vm.isEmpty()) {
         vm.add(build(args));
      }
      return vm.get(0).execute();
   }

   public final JState result() {
      return vm.get(0).result();
   }

   public final Collection<JState> results() {
      return vm.get(0).results();
   }
}
