package com.lexicalscope.symb.classloading;

import static org.objectweb.asm.Type.getInternalName;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.symb.classloading.asm.AsmSClass;
import com.lexicalscope.symb.classloading.asm.AsmSClassFactory;
import com.lexicalscope.symb.vm.j.j.klass.SClass;

public class ResourceByteCodeReader implements ByteCodeReader {
	private final Instructions instructions;

	public ResourceByteCodeReader(final Instructions instructions) {
		this.instructions = instructions;
	}

	@Override
	public AsmSClass load(final SClassLoader classLoader, final String name,
			final ClassLoaded classLoaded) {
		if (name == null) {
			return null;
		}

		final URL classUrl;
		if (name.equals(getInternalName(Thread.class))
				|| name.equals("java/lang/Integer$IntegerCache")) {
			classUrl = loadLocalVersion(name);
		} else {
			classUrl = classUrlFromClassPath(name);
		}

		try {
			if (classUrl == null) {
				throw new SClassNotFoundException(name);
			}

			final ClassNode classNode = loadClassBytecodeFromUrl(classUrl);
			final SClass superclass = classNode.superName != null ? classLoader
					.load(classNode.superName, classLoaded) : null;

			@SuppressWarnings("unchecked")
			final List<String> interfaceNames = classNode.interfaces;
			final List<SClass> interfaces = new ArrayList<>();
			for (final String interfaceName : interfaceNames) {
				interfaces.add(classLoader.load(interfaceName, classLoaded));
			}

			final AsmSClass result = AsmSClassFactory.newSClass(classLoader,
					instructions, classUrl, classNode, superclass, interfaces);
			classLoaded.loaded(result);
			return result;
		} catch (final IOException e) {
			throw new SClassLoadingFailException(name + " from " + classUrl, e);
		}
	}

	private URL loadLocalVersion(final String name) {
	   final String relativeLocation = "lib/" + name + ".class";
		try {
		   final URL baseLocation = ResourceByteCodeReader.class.getProtectionDomain()
               .getCodeSource().getLocation();
		   if(baseLocation.getProtocol().equals("file")) {
		      final File baseFile = new File(baseLocation.toURI());
		      if(baseFile.isFile() && baseFile.getName().endsWith("jar")) {
		         return new URL("jar:" + baseLocation + "!/" + relativeLocation);
		      }
		   }
         return new URL(baseLocation, relativeLocation);
		} catch (final MalformedURLException e) {
		   throw new SClassLoadingFailException(name, e);
		} catch (final URISyntaxException e) {
		   throw new SClassLoadingFailException(name, e);
      }
	}

	private URL classUrlFromClassPath(final String name) {
		final URL classUrl = this.getClass().getClassLoader()
				.getResource(name.replace(".", File.separator) + ".class");
		return classUrl;
	}

	private ClassNode loadClassBytecodeFromUrl(final URL classUrl)
			throws IOException {
		final ClassNode classNode = new ClassNode();
		final InputStream in = classUrl.openStream();
		try {
			new ClassReader(in).accept(classNode, 0);
		} finally {
			in.close();
		}
		return classNode;
	}
}
