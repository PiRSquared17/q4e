# Introduction #

Since q4e 0.3.0, it is possible to analyze a project dependencies in a graphical way. This page documents the usage of this new tool.

# An example #

![http://q4e.googlecode.com/svn/wiki/img/DependencyGraphViewer/q4e_dependency_viewer.png](http://q4e.googlecode.com/svn/wiki/img/DependencyGraphViewer/q4e_dependency_viewer.png)

# Details #

The dependency viewer can be invoked from the context menu of any q4e-enabled project.

It displays a set of boxes representing the artifacts on which the project depends. Each box contains the name of the artifact. Different colors are used for the background to represent different types of dependencies.

  * The green box is the project (archiva-common in the example). It is displayed in the center of the graph.
  * Blue boxes are run-time dependencies.
  * Dark yellow (?) boxes represent test-scope dependencies (in this case, junit 3.8.1).
  * The yellow box marks the selected dependency.

Arrows betwen boxes show the dependencies, including the version. The thicker lines represent direct dependencies, while the thinner ones represent transitive dependencies (dependencies of dependencies) for the project.

It is possible to reorder the boxes by using drag and drop.
