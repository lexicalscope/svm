Java Bytecode Snapshot Vm and Symbolic Executor [![Build Status](https://travis-ci.org/lexicalscope/svm.png?branch=master)](https://travis-ci.org/lexicalscope/svm)
=========================================

If you would like to help out, please contact me or just fork and get going. You will need to use maven 3 to build things, and I have put eclipse config files in the repo so you should be able to get going with eclipse pretty quickly. You might need to pick a particular JDK version to run with (try OpenJDK "1.7.0_51")

Particularly anyone who wants to get more of the bytecode instructions implemented, or who wants to help with java standard library compatiblity, or work on performance/scalability.

I do not plan to add multi-threading support in the near future.

It currently uses microsoft z3, which you will have to download and build. You need the unstable branch for the Java API.
