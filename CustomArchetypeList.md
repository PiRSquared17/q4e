# Introduction #

Q4E comes configured with two default archetype list providers: the _Wiki Provider_ and the _Archetype 2.0 Catalog Provider_.

This document describes the use of the wiki provider to parse custom archetype lists based on regular expressions.

However, if you feel comfortable with xml formats, it is recommended that you use the [Archetype 2.0 Catalog Provider](Archetype2ArchetypeProvider.md) to define your custom archetype lists.

# The default _Wiki Provider_ #

When creating a new _Wiki Provider_, it will provide default values for using the contents of [this wiki page](http://docs.codehaus.org/display/MAVENUSER/Archetypes+List) (more precisely, [the source form](http://docs.codehaus.org/pages/viewpagesrc.action?pageId=48400) of the page) to create the list of available archetypes presented by the new maven project wizard.

The _Wiki Provider_ can be configured for accessing other URLs, so you can provide your own list of archetypes. Accepted URL protocols are `http://`, `https://` and also `file://` (so you can have your local archetype list).


This provider understands files with the following format:

```
<br>| Artifact Id | Group Id | Version | Repository | Description |
<br>| Artifact Id 2 | Group Id 2 | Version 2 | Repository 2 | Description 2 |
...
<br>| Artifact Id n | Group Id n | Version n | Repository n | Description n |
```

**Note:** The `<br>` must be typed literally at the start of every line, and the first pipe must be just after it, without whitespace in between.

For example:
```
<br>|my-artifact|my.group|1.1|[http://my.repo.com/is/cool]|My nice description|
<br>|my-other-artifact|my.group|3.1|[http://my.repo.com/is/cool]|Brief description|
```


It is possible to add and remove archetype list providers.

# Making a custom archetype list #

It is possible to use any archetype list available to q4e for use in the New Project Wizard. The list can be in any format as long as it has the 4 required fields (description is optional):
  * Archetype's Artifact Id
  * Archetype's Group Id
  * Archetype's Version
  * Archetype's remote repository
  * Description - this is optional, but will be displayed to the end user on the new project wizard.

## Configuring a wiki provider for your list ##

This is the easiest way of using a custom archetype list with q4e.

Lets start with a simple CSV file:
```
#
# This text will not match the regular expression, so it will be ignored
#
my-archetype-id;my-archetype-group;1.0.0;http://example.com/repo;Sample archetype
different-id;different-group;1.2.1;http://example.com/anotherRepo;Second sample
```

In this file, we define two new archetypes: `my-archetype-id` and `different-id`.

Now we will configure a wiki provider which can parse the file. Go to _Preferences > Maven  > Archetype Preferences_.

![http://q4e.googlecode.com/svn/wiki/img/archetype/Archetype-Preferences.png](http://q4e.googlecode.com/svn/wiki/img/archetype/Archetype-Preferences.png)

Click _New_. In the wizard that opens, fill a name for the archetype provider and select the `Wiki` type.

![http://q4e.googlecode.com/svn/wiki/img/archetype/New-Archetype-Source.png](http://q4e.googlecode.com/svn/wiki/img/archetype/New-Archetype-Source.png)

Now click _Next_. Another page allows us to select an url where the file can be found. For simplicity, lets enter a url on our filesystem (in the example: `file:/opt/m2/archetypes.csv`).

The tricky part now is finding a regular expression that matches our file format. One possible expression for our CSV file is: `([^;\n]+);([^;\n]+);([^;\n]+);([^;\n]+);([^;\n]*)\n`.

![http://q4e.googlecode.com/svn/wiki/img/archetype/New-Archetype-Source-Options.png](http://q4e.googlecode.com/svn/wiki/img/archetype/New-Archetype-Source-Options.png)

That's all, the new archetype list will be available in the new project wizard:

![http://q4e.googlecode.com/svn/wiki/img/archetype/NewProjectArchetypeList.png](http://q4e.googlecode.com/svn/wiki/img/archetype/NewProjectArchetypeList.png)

## Custom archetype provider ##

It is also possible to implement a new archetype provider in java by contributing to the    [org.devzuz.q.maven.ui.archetypeProvider](http://code.google.com/p/q4e/source/browse/trunk/plugins/maven/ui/schema/org.devzuz.q.maven.ui.archetypeProvider.exsd) extension point.

The archetype provider must implement [IArchetypeProvider](http://code.google.com/p/q4e/source/browse/trunk/plugins/maven/ui/src/main/java/org/devzuz/q/maven/ui/archetype/provider/IArchetypeProvider.java) (you can use [AbstractArchetypeProvider](http://code.google.com/p/q4e/source/browse/trunk/plugins/maven/ui/src/main/java/org/devzuz/q/maven/ui/archetype/provider/AbstractArchetypeProvider.java) to have default get/set/toString methods).

If the user needs to provide some configuration data, an `ui-builder` (a class implementing [IArchetypeProviderUIBuilder](http://code.google.com/p/q4e/source/browse/trunk/plugins/maven/ui/src/main/java/org/devzuz/q/maven/ui/archetype/provider/IArchetypeProviderUIBuilder.java)) can also be registered.

Finally, a name for the archetype `type` must be provided. This name will be displayed in the user interface.

You can use the [implementation of the Wiki Provider](http://code.google.com/p/q4e/source/browse/trunk/plugins/maven/ui/src/main/java/org/devzuz/q/maven/ui/archetype/provider/impl/) as an example.