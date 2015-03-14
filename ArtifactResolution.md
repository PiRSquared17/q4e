# Introduction #

Resolving artifacts from the workspace allow you to work on two levels:
  1. Compiling with eclipse: if you have a dependency on an artifact on your workspace and using eclipse, the compiler will use your project to satisfy the dependencies.
  1. Using maven: if you launch maven goals from q4e, the artifacts are also resolved from the workspace, without using the local or remote repositories. From 0.7.0, this includes [resolving maven plug-ins from the workspace](DevelopingMojos.md).

## Compiling with eclipse ##

If `project_a` has a dependency on `project_b` and both are on your workspace under q4e control, `project_a` buildpath will be updated to use `project_b`.

This feature is a must for any developer, so there is no need to `mvn install` after every change on `project_a` just to work with `project_b` in Eclipse.

## Compiling with maven ##

Sometimes you just need to use maven.

But (starting with `q4e 0.5.0`) you can still use the projects on your workspace. You can't still avoid calling `mvn install`.

In few words... if you need to execute a goal on any project, use _Maven 2 > Execute goal_ on the project's context menu and you'll save the step of installing to the repository.

[![](http://content.screencast.com/media/0dd2d22a-dc62-4f29-a6b3-0cc89cd586e3_8401be7d-71b4-43db-a2f3-28bc909cc10c_static_0_0_Thumbnail.gif)](http://www.screencast.com/users/amuino/folders/Jing/media/756ea2db-a4ee-4352-802d-176869da774e)

[Watch this screencast to see it in action](http://www.screencast.com/users/amuino/folders/Jing/media/756ea2db-a4ee-4352-802d-176869da774e)

### The long story ###

Not every maven mojo supports running from the workspace.

~~Most notably, plug-ins that fork execution and/or require the presence of jar files will fail.~~

Starting in 0.7.0, Q for Eclipse will automatically package the workspace artifacts when the plug-ins involved in the current build require it. Note that nothing is installed in the repository at this point.

We would be glad to work with maven plug-in authors to help them support working from the workspace with Q for Eclipse.