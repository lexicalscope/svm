/*
 * Copyright 2008-2013 Don Brown
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lexicalscope.svm.z3.plugin;

import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * Put Z3 libs somewhere
 *
 * @goal install-z3
 * @phase process-resources
 * @requiresDependencyResolution compile
 */
public class Z3Mojo extends AbstractMojo {
   /**
    * The project currently being build.
    *
    * @parameter property="project"
    * @required
    * @readonly
    */
   private MavenProject mavenProject;

   /**
    * The current Maven session.
    *
    * @parameter property="session"
    * @required
    * @readonly
    */
   private MavenSession mavenSession;

   /**
    * The Maven BuildPluginManager component.
    *
    * @component
    * @required
    */
   private BuildPluginManager pluginManager;

   @Override public void execute() throws MojoExecutionException {
      getLog().info("installing Z3 native libraries");
      executeMojo(
            plugin(
                  groupId("org.apache.maven.plugins"),
                  artifactId("maven-dependency-plugin"),
                  version("2.8")),
            goal("unpack"),
            configuration(
                  element(name("artifactItems"),
                        element(name("artifactItem"),
                              element(name("groupId"), "com.lexicalscope.symb"),
                              element(name("artifactId"), "svm-smt-z3"),
                              element(name("version"), "${project.version}"),
                              element(name("type"), "jar"))),
                  element(name("outputDirectory"), "${project.build.outputDirectory}"),
                  element(name("includes"), "**/*.so"),
                  element(name("overWriteReleases"), "false"),
                  element(name("overWriteSnapshots"), "true")),
            executionEnvironment(mavenProject, mavenSession, pluginManager));
   }
}
