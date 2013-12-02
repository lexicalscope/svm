Workaround for z3 clone: http://codeplex.codeplex.com/workitem/26133
z3 git: https://git01.codeplex.com/z3

build z3 with:
  git checkout -B unstable origin/unstable
  python scripts/mk_make.py --java
  cd build
  make all examples

mvn install:install-file -Dfile=com.microsoft.z3.jar -DgroupId=com.microsoft -DartifactId=z3 -Dversion=1.61385c8 -Dpackaging=jar

You also need to build z3 for you plaftorm and put libz3 and libz3java somewhere your JVM can find them (or set LD_LIBRARY_PATH or similar) 