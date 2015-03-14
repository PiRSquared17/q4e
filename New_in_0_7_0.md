This is the list of the most important changes made for the 0.7.0 release.

For a complete list, check the [Status](Status.md) page.

# Features in Q for Eclipse **core** #

## Maven suggestions in the XML editor ##
> When editing `pom.xml` files, values for the GroupId, PackageId and Version tags when adding a dependency are available.

> ![http://q4e.googlecode.com/svn/wiki/img/new070/pomXmlSuggestion.png](http://q4e.googlecode.com/svn/wiki/img/new070/pomXmlSuggestion.png)

> This feature uses the Open Artifact Search Framework outlined below.

## Always updated dependencies ##
> No need to `mvn install` the dependencies on the workspace when running maven goals. The most current version is always used.

> ![http://q4e.googlecode.com/svn/wiki/img/new070/dependencyPackaging.png](http://q4e.googlecode.com/svn/wiki/img/new070/dependencyPackaging.png)

> This means that you can modify a project on your workspace and use it in the surefire tests of a different one depending on it. Instantly.

## Profile activation/deactivation ##
> The profile view allows for quickly enabling and disabling profiles when running maven.

> ![http://q4e.googlecode.com/svn/wiki/img/new070/profilesView.png](http://q4e.googlecode.com/svn/wiki/img/new070/profilesView.png)

## Open Artifact Search Framework ##
> New extension points allow third parties to develop and register search engines for their indexing solution.

> ![http://q4e.googlecode.com/svn/wiki/img/new070/searchEngines.png](http://q4e.googlecode.com/svn/wiki/img/new070/searchEngines.png)

> Several implementations are bundled with the install so you can use it out of the box.

## [Resolve Maven Mojos from the Workspace](DevelopingMojos.md) ##
> It is possible to have maven mojo projects on the workspace and haven them used by q4e.

## [New Archetype Provider for the Archetype Catalog format](Archetype2ArchetypeProvider.md) ##
> The archetype catalog xml format used by the archetype 2.0 plug-in can now be used with q4e for selecting an archetype in the [New Maven Project wizard](NewMavenProject.md).

> This format is used to register an internal catalog by default, which replaces the wiki provider (although it can still be manually added).

## Upgraded embedder ##
> The new embedder contains support for new maven features (like [toolchain](http://docs.codehaus.org/display/MAVEN/Toolchains) and [import scope](http://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#Importing_Dependencies) support). And don't forget about the bug fixes.

# WTP support #

## J2EE Project Dependencies management ##
> Automatic synchronization of J2EE project dependencies from maven dependencies for war projects.

> ![http://q4e.googlecode.com/svn/wiki/img/new070/j2eeDependencies.png](http://q4e.googlecode.com/svn/wiki/img/new070/j2eeDependencies.png)

> All your dependencies in the workspace will be available when running the project on a J2EE server.