# Introduction #

The Analyse Dependencies view shows a tree view of a project's direct and transitive dependencies which can be used in debugging common dependency problems.  In addition it also provides some advanced dependency management features.

# Debugging Features #

  * Provides an unfiltered view on a project's dependency tree.
  * Locate source of dependencies with multiple versions in a given dependency tree.
  * Pinpoint all sources for a given transitive dependency.
  * Determine why a certain dependency or version is being included or excluded.

# Dependency Management Features #

  * Exclude all instances of a selected dependency from the `dependency` and `dependencyManagement` sections of the pom.
  * Force the version of a given artifact using the `dependencyManagement` section of the pom.

# View Details & Terminology #

![http://q4e.googlecode.com/svn/wiki/img/DependencyAnalysis/view-guide.gif](http://q4e.googlecode.com/svn/wiki/img/DependencyAnalysis/view-guide.gif)

## Primary Selection ##
This is the item that you have selected, it could be in any of the three panes.  It will be coloured orange.

## Secondary Selections ##
These are the items that are "related" to your primary selection.  For example if you select a particular version from the Versions Pane, the related items are the corresponding artifact in the Artifacts Pane and the instances of that version in the Instances Pane.  These will be coloured yellow.

## Tertiary Selections ##
These only appear in the Instances Pane and are coloured light yellow.  They represent the parent instances of the currently selected Instances.  They are there primarily to indicate the path from the tree root to selected instances.

## Instances Pane ##
Shows the tree of transitive dependencies.  Each item in the tree is an _instance_ of a dependency, and a given dependency may appear many times in the same tree.  Selecting an item in this pane will highlight the corresponding version and artifact in the other panes.

## Versions Pane ##
Shows a sortable list of all dependencies including the version of the artifact.  The Instances column shows a count of the number of times that a given dependency appears in the dependency tree.  Selecting an item in this pane will highlight the corresponding instances in the Instances Pane and artifact in the Artifacts Pane.

## Artifacts Pane ##
Shows a sortable list of all dependencies **without** the version information.  The versions column indicates the number of different versions of that artifact in the dependency tree, which is very useful for finding artifacts with more than one version.  Selecting an item in this pane will highlight the corresponding versions and instances in the other panes.

# Using the Dependency Management Features #

In order to use the dependency management features you simply need to right-click an artifact in the Artifacts Pane and select the option that you want.  Both of these options will alter the `dependencyManagement` and `dependency` sections of the project's pom file.  Please note that after executing one of these features you must re-analyse the dependencies in order to see your changes reflected in the viewer, you may also want to reformat the pom file (CTRL+SHIFT+F).

![http://q4e.googlecode.com/svn/wiki/img/DependencyAnalysis/right-click-menu.gif](http://q4e.googlecode.com/svn/wiki/img/DependencyAnalysis/right-click-menu.gif)

## Force Version ##

Force Version utilises maven's `dependencyManagement` section of the pom.xml to ensure that the version of the selected artifact is a specific version, or within a certain version range.  Selecting the Force Version option from the right-click menu will show the below dialog, from which the required version can be specified.  Selecting cancel will cancel the operation without making any changes.

![http://q4e.googlecode.com/svn/wiki/img/DependencyAnalysis/force-version-dialog.gif](http://q4e.googlecode.com/svn/wiki/img/DependencyAnalysis/force-version-dialog.gif)

The Currently Selected item is the version that maven has currently resolved.  All other versions present in the tree will be listed.  The "Other" text field allows you to either enter a version not already in the tree or a version range expression.

## Exclude All ##

Exclude All utilises maven's `dependencyManagement` and `dependency` sections of the pom.xml to ensure that the selected artifact is completely removed from the dependency tree.  Selecting the Exclude All option from the right-click menu will show the below dialog.  Selecting cancel will cancel the operation without making any changes.

![http://q4e.googlecode.com/svn/wiki/img/DependencyAnalysis/exclude-all-dialog.gif](http://q4e.googlecode.com/svn/wiki/img/DependencyAnalysis/exclude-all-dialog.gif)

# Extensibility #

The dependency analysis view exposes the Artifact, Version and Instance objects for extension.  Providing a context menu item for these items involves:

  * Add an `objectContribution` entry to the `org.eclipse.ui.popupMenus` eclipse extension point.
  * The `objectClass` should be one of:
    * `org.devzuz.q.maven.dependency.analysis.model.Artifact`
    * `org.devzuz.q.maven.dependency.analysis.model.Version`
    * `org.devzuz.q.maven.dependency.analysis.model.Interface`
  * The `action.class` must extend `org.eclipse.ui.IObjectActionDelegate`
  * The action class must implement the selectionChanged method and keep track of the selection as per below:
```
    public void selectionChanged( IAction action, ISelection selection )
    {
        if ( selection instanceof IStructuredSelection )
        {
            this.selection = (IStructuredSelection) selection;
        }
    }
```
  * The selected items can be accessed from the IStructuredSelection object
  * The action class must use the interfaces defined in `org.devzuz.q.maven.dependency.analysis.extension` as the model objects are not available externally.  This is by design, so prefer adding functionality to the interfaces to making the model classes accessable outside the dependency analysis bundle

## Extensability Examples ##

Examples of how to declare extensions can be found in the [plugin.xml](http://code.google.com/p/q4e/source/browse/trunk/plugins/maven/dependency-analysis/plugin.xml).  Examples of the actions can be found at [Exclude All Action](http://code.google.com/p/q4e/source/browse/trunk/plugins/maven/dependency-analysis/src/main/java/org/devzuz/q/maven/dependency/analysis/actions/ExcludeAllAction.java) and [Force Version Action](http://code.google.com/p/q4e/source/browse/trunk/plugins/maven/dependency-analysis/src/main/java/org/devzuz/q/maven/dependency/analysis/actions/ForceVersionAction.java).