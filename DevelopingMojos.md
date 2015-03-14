# Introduction #

Q for Eclipse can help you develop traditional java applications, as well as [web applications using WTP](http://joakim.erdfelt.com/q4e/q4e-wtp.swf), by handling the resolution of dependencies for your project, no matter if they are local or remote.

This page explains how to achieve the same result when using or developing maven plug-ins.


# The short answer #

It just works, you don't need to do a thing.

Starting with q4e 0.7.0, if a maven execution includes a mojo present on the workspace, it will be used during the build.

# Details #

You'll need at least two projects: one for the mojo and another for testing.

## The mojo project ##

For this example, just create a new maven project `mojodev` using the `maven-archetype-mojo` archetype. Keep the default values for everything.

> ![http://q4e.googlecode.com/svn/wiki/img/DevelopingMojos/NewMojoProject.png](http://q4e.googlecode.com/svn/wiki/img/DevelopingMojos/NewMojoProject.png)

This archetype creates a `touch` goal which creates a file on the target folder named `touch.txt` and with the same string as the contents.

We won't modify it now.

## The test project ##

Now create a new maven project `mojotest`. For simplicity, use the `maven-archetype-quickstart` archetype.

Once the `mojotest` project has been created, we'll bind the `touch` goal on `mojodev` to the `process-resources` phase using this snippet:

```
	<build>
		<plugins>
			<plugin>
				<groupId>mojodev</groupId>
				<artifactId>mojodev</artifactId>
				<version>1.0-SNAPSHOT</version>
				<executions>
					<execution>
						<goals>
							<goal>touch</goal>
						</goals>
						<phase>process-resources</phase>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
```

Save the pom.

## Testing it ##

Any maven goal involving the `process-resources` phase you should run now (for example, the _package_ goal on the context menu) would also invoke this new plug-in. But also any goal used by Q for Eclipse! Lets try that.

Go to the _Project_ menu and _Clean..._ the `mojotest` project.

> ![http://q4e.googlecode.com/svn/wiki/img/DevelopingMojos/GeneratedFiles.png](http://q4e.googlecode.com/svn/wiki/img/DevelopingMojos/GeneratedFiles.png)

There are two things you should notice in the _Package Explorer_ after cleaning.
  1. The mojo project has been packaged for you
  1. A new file `touch.txt` exists on `mojotest/target`

## Making changes ##

Just for having fun, lets change the contents of the generated `touch.xml` file. Open it and keep an eye on it.

Open also `MyMojo.java` in project `mojodev` and change line 61. For example, add the current date to the message.

> ![http://q4e.googlecode.com/svn/wiki/img/DevelopingMojos/EditMyMojo.png](http://q4e.googlecode.com/svn/wiki/img/DevelopingMojos/EditMyMojo.png)

To give it a twist, now right click on `mojotest` and choose _Maven 2 > Package Artifact_.

> ![http://q4e.googlecode.com/svn/wiki/img/DevelopingMojos/PackageArtifact.png](http://q4e.googlecode.com/svn/wiki/img/DevelopingMojos/PackageArtifact.png)

And _voilà!_... the contents of `touch.txt` have changed to include the date.

> ![http://q4e.googlecode.com/svn/wiki/img/DevelopingMojos/ModifiedTouchTxt.png](http://q4e.googlecode.com/svn/wiki/img/DevelopingMojos/ModifiedTouchTxt.png)

## Closing remarks ##

In case you are wondering... the same would have happened if you've chosen to clean the project. But it would have been boring!

Another remark worth making is that the packaging only performed when needed (this is also true for plain old workspace dependencies). If the packaged artifact/mojo is up-to-date or is not needed for the current maven run, no time will be lost packaging it.

Enjoy!