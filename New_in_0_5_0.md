This is the list of the most important changes made for the 0.5.0 release.

For a complete list, check the [Status](Status.md) page.

  * We have a **new committer**. Please welcome Jake Pezaro.

  * Ability to [import pom projects](ImportingMultiprojects.md)

  * Maven execution can now be canceled. The points where it checks for cancellation are at the start of each phase (plugin execution), before downloading each file and for each log call. Therefore note that you won't be able to stop a running plugin if it does not send any value to the log (quite strange in any case).

  * New [dependency analysis view](DependencyAnalysis.md)! (Props to Jake) It is now possible to display the project dependencies and analyze them to assess where the actual dependencies and versions come from.

  * Improved handling of resources:
    * If filtering is configured for resources, it is honored.
    * Inclussion/Exclussion patterns for resources are honored. Now it is possible to share a folder for java sources and resources.
    * **Note** that resources are no longer added to the build path as source folders.

  * The [dependency graph](DependencyGraphViewer.md) is no longer a pop up window. It has its own Eclipse view.

  * Allow maven goals to [use artifacts in the workspace](ArtifactResolution.md) when launched from q4e, even if they are not installed on the repository.