
z3 git: https://git01.codeplex.com/z3

There is some problem between git and codeplex, so you may need this workaround for z3 clone: http://codeplex.codeplex.com/workitem/26133
------------------
mkdir z3
cd z3
git init
git remote add -f origin https://git01.codeplex.com/z3
git fetch
git checkout -B master origin/master
------------------

Then:
 
build z3 with unstable:
  git checkout -B unstable origin/unstable
  python scripts/mk_make.py --java
  cd build
  make all examples

Install the Jar somewhere you can find it:
mvn install:install-file -Dfile=com.microsoft.z3.jar -DgroupId=com.microsoft -DartifactId=z3 -Dversion=1.61385c8 -Dpackaging=jar

You also need to build z3 for your plaftorm and put libz3 and libz3java somewhere your JVM can find them (or set LD_LIBRARY_PATH or similar) 


***************
 * Paper on how PEX uses z3: "Satisfiability Modulo Bit-precise Theories for Program Exploration"
 *
 * Z3 guide: http://rise4fun.com/z3/tutorialcontent/guide
***************