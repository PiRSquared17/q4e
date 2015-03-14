# Introduction #

We will cover the architecture of the project


# Slides #

[Introduction to Q](http://q4e.googlecode.com/svn/trunk/docs/Q.odp)


# Architecture #

![http://q4e.googlecode.com/svn/trunk/docs/architecture.png](http://q4e.googlecode.com/svn/trunk/docs/architecture.png)

The modules in italic are experimental

## Maven core Eclipse wrapper ##

  * `org.devzuz.q.maven.core`
    * Wraps the Maven Embedder API to add Eclipse specific operations
    * Adds functionality not currently present in the Maven Embedder

## JDT integration ##

  * `org.devzuz.q.maven.jdt.core`
    * Maven nature
    * Classpath container
  * `org.devzuz.q.maven.jdt.ui`
    * Enabel/Disable nature menu actions
    * Launch configuration
    * POM editor
    * Import project job
    * Project scanner

## User interface ##

  * `org.devzuz.q.maven.ui`
    * Menu actions for goals
    * Dependency graph
    * Repository indexer (should be elsewhere?)
    * Manage dependencies dialog
    * Archetype list dialog
    * Global/project preference pages
    * Maven event view

  * `org.devzuz.q.maven.wizard`
    * New Maven project from archetype wizard
    * Import wizard

  * `org.devzuz.q.maven.dependencyviewer`
    * Dependency visualization

## Feature and update site ##

  * `org.devzuz.q.update`
  * `org.devzuz.q.feature`

## Third party libraries ##

Features and plugins created to use third party libraries

  * `org.apache.maven.embedder`
    * The maven embedder, allows to run Maven embedded inside Eclipse
  * `org.devzuz.q.feature.maven`
    * Feature that wraps the embedder
  * `org.apache.commons.collections`
    * Commons collections feature
  * `org.prefuse` and `org.prefuse.feature`
    * **Replaced by zest in 0.3.0 and later**. Another graph visualization toolkit.
  * `org.w3c.tidy`
    * Used by the maven embedder.

## Third party plug-ins ##

These plug-ins are provided by third parties and used in q4e.
  * `org.eclipse.zest.core` and `org.eclipse.zest.layouts`
    * Provided by [Eclipse GEF](http://www.eclipse.org/gef/). Support for displaying graphs. Used for dependency visualization.
  * `org.aspectj.runtime`
    * Provided by [Eclipse AJDT](http://www.eclipse.org/ajdt/). Used by the maven embedder.
  * `org.apache.commons.cli`
    * Provided by [Eclipse Orbit](http://download.eclipse.org/tools/orbit/). Used by the maven embedder.
  * `org.apache.commons.lang`
    * Provided by [Apache Felix Commons](http://cwiki.apache.org/FELIX/apache-felix-commons.html). Used by the maven embedder.

## Experimental ##
**Future work:** These plug-ins may not be working properly, require additional dependencies or contain other errors. By the moment, just close the project and ignore them.

### _Target platform provisioner_ ###

  * `org.devzuz.q.maven.pde.targetplatform`

### _Eclipse Wagon_ ###

  * `org.devzuz.q.maven.wagonp`

### _ECF Provider_ ###

  * `org.devzuz.q.ecf.provider.filetransfer`

### _EMF model and editor_ ###

  * `maven-model-emf`
  * `maven-model-emf.edit`
  * `maven-model-emf.editor`
  * `maven-model-emf.tests`

### _Buckminster integration_ ###

  * `org.devzuz.q.buckminster.maven`


### _Repository integration_ ###

APIs and code to integrate with Maven repositories

  * `org.devzuz.q.repository.api`
  * `org.devzuz.q.repository.core`
  * `org.devzuz.q.repository.ui`

# License header #

```
/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
```

Eclipse can be configured to use this license in every new file created.

  * Open eclipse preferences
  * Go to _Java > Code Style > Code Templates_
  * Select _Comments > Files_
  * Edit the comment and paste the provided text

![http://q4e.googlecode.com/svn/wiki/img/DevelopmentGuide/license-template-comment.png](http://q4e.googlecode.com/svn/wiki/img/DevelopmentGuide/license-template-comment.png)

A template can also be defined in _Java > Editor > Templates_.
  * Create a new template for the java context
  * Give it an easy to remember name (like `license`)
  * Use the license text as the content.
  * You can now type `license` and press _CTRL+Space_ to have the license text automatically inserted.

This is an example:

![http://q4e.googlecode.com/svn/wiki/img/DevelopmentGuide/license-template.png](http://q4e.googlecode.com/svn/wiki/img/DevelopmentGuide/license-template.png)

# Coding conventions #

Q for Eclipse uses the [maven coding conventions](http://maven.apache.org/developers/committer-environment.html).

Download the eclipse formatter settings ([maven-eclipse-codestyle.xml](http://maven.apache.org/developers/maven-eclipse-codestyle.xml)) and import them from _Java > Code Style > Formatter_.

![http://q4e.googlecode.com/svn/wiki/img/DevelopmentGuide/maven-formatter.png](http://q4e.googlecode.com/svn/wiki/img/DevelopmentGuide/maven-formatter.png)

From now on, every time the code is formatted, it will be adjusted to the right conventions (this and other cleanups can be automated so they occur on every save by using the _Java > Editor > Save Actions_ preferences).

# Generating an update site #
  1. Open `site.xml`
  1. Click "_Add Feature..._"
  1. Search for "`*q.feature`"
  1. OK
  1. Select the new entry "`org.devzuz.q.feature (0.6.0.qualifier)`" under `Community` (the specific version number should match the current development)
  1. Click on "_Build_"
  1. After the build, the entry will have been renamed to "`features/org.q.devzuz.feature_0.6.0.XXX`" where "XXX" is the build time/date.
  1. Do the same for "`*.q.dep`". It will end up under "`Optional`".

# Making a release #

  * change all versions x.y.z.qualifier to x2.y2.z2.qualifier, where x2.y2.z2 is the new version to release
    * this probably happens right after making the previous release
    * to change all plugin versions at a time you can go to the feature, set the new version, and in the plugins tab click on versions and force feature version into plugins
  * in the update site project http://q4e.googlecode.com/svn/trunk/updatesite/
    * Add the features with the new versions you set (x2.y2.z2.qualifier)
    * click build in each of the new features (or alternatively click build all)
  * tag sources
  * the update site now has the new plugins

