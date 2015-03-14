# Introduction #

So you've decided to help with q4e, right? Don't worry, this page will make the initial steps as easy and painless as possible.

After following the instructions. you'll have a working Eclipse workspace with the latest version of q4e. You'll be also able to launch Eclipse with q4e and your changes, so you can test your patches, contributions... or just experiment with the current version of the source code.

Two options will be described:
  * Use the same Eclipse for developing and testing. In this scenario, you will install q4e dependencies on the Eclipse you use for developing.
  * Use an Eclipse installation for developing and another one for testing. This will allow you to test in a clean Eclipse install, as well as testing on different versions.

In both cases, you'll need to get the code from subversion (you'll need a [subversion](http://subversion.tigris.org/) client).

# Getting the latest code #
If you are a tester/contributor without commit rights...
```
svn co http://q4e.googlecode.com/svn/trunk q4e
```

If you are a committer, use `https`...
```
svn co https://q4e.googlecode.com/svn/trunk q4e
```

To get the contents of the sandbox

```
svn co http://q4e.googlecode.com/svn/sandbox q4e
```
or
```
svn co https://q4e.googlecode.com/svn/sandbox q4e-sandbox
```



# Set up your Eclipse #

There are two paths you can follow: develop and test against the same Eclipse install or use a different one for developing and testing.


Regardless of which path you choose, you will need to **install PDE on your development Eclipse platform**.


## Same Eclipse ##

If you want to use the same eclipse for developing and testing, your first step must be to get all the dependencies installed.

If you're not done so yet, install the latest release of q4e from the update site: `http://q4e.googlecode.com/svn/trunk/updatesite/`
Install now the pre-release version (in case there are newer version of the dependencies there): `http://q4e.googlecode.com/svn/trunk/updatesite-dev/`

You're now ready to import the code in the workspace. See below.

## Different Eclipses ##

Having a copy of Eclipse for your development and a different one for testing can help in making sure that your changes do not introduce new dependencies and work on a new installation.

This alternative is somewhat more involved, but also offers more flexibility.

First, you'll need to [download a clean Eclipse](http://download.eclipse.org/eclipse/downloads/). You'll need at least the latest stable release (3.3.1.1). Just for fun, you could also get the latest 3.4 milestone.

The Eclipses you've downloaded will be the test platforms. Uncompress them.

In your development platform...
  * Create a new Project (it does not need to be a Java Project). You can call it `TargetPlatforms`.
  * In this project, create a new Target Definition (_New_ > _Other... >_Plug-in Development_>_Target Definition_). Name it `Eclipse 3.3.1.1.target`.
  * Open the Target Definition.
    * Fill in a name (`Eclipse 3.3.1.1` will be ok).
    * In_Target Location_, specify the test platform you've downloaded and uncompressed.
    * In_Additional Locations_, we need to add the q4e specific dependencies. We'll use the ones checked out from subversion:
      * Add the checked out `updatesite` folder.
      * Add the checked out `updatesite-dev` folder.
    * Change to the_Content_tab and check_Include all plug-ins from the specified target_.
  * Save._

![http://q4e.googlecode.com/svn/wiki/img/workingWithSources/TargetPlatform.png](http://q4e.googlecode.com/svn/wiki/img/workingWithSources/TargetPlatform.png)

You can enable this target platform at any time by clicking on the link _Set as Target Platform_ in the top-right corner. If you have different target platforms you can switch from one to another in the same way. If you need to add third party dependencies, just add them as  you did with q4e.

You're done. Let's import some code.

# Importing the code in the workspace #

  * _File_ > _Import..._ > _Existing Projects into Workspace_
  * Select the `q4e` folder created during the subversion checkout.
  * Eclipse will find all available projects. Import them all.

# Testing _q4e_ #
Now you can do your changes and run with

  * _Run_ > _Open Debug Dialog..._
  * Right click on _Eclipse application_, select _New_
  * Give it a name
  * In the plugin tabs make sure all the org.devzuz.q plugins are checked in the Workspace section and unchecked in the Target platform section
  * Click the Debug button

A new Eclipse instance is launched with all the plugins you checked, including the Q ones built from sources