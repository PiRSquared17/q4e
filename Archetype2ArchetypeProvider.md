# Introduction #

The maven-archetype plug-in defines an xml format for archetype catalogs, starting at version 2.0.

This format can be used with q4e, in addition to [other archetype sources](CustomArchetypeList.md) to make archetypes available in the [New Maven Project Wizard](NewMavenProject.md).


# Details #

The archetype-catalog format is defined in the [documentation for the maven-archetype plug-in](http://maven.apache.org/plugins/maven-archetype-plugin/specification/archetype-catalog.html).

New catalogs can be registered through the Prefereces.

Check _Maven > Archetype Preferences_.

Select _New..._ and, on the drop down, choose _Archetype 2.0 Catalog_

Now you can choose to use the built-in catalog (it's configured by default, but in case you've deleted it, is easy to add again), a local catalog (a file on your filesystem) or a catalog located at a given URL.

![http://q4e.googlecode.com/svn/wiki/img/archetype/ArchetypeCatalogConfig.png](http://q4e.googlecode.com/svn/wiki/img/archetype/ArchetypeCatalogConfig.png)

Note that `file://` urls can be used for local files, but the _local catalog_ option provides a file browser which makes selection much easier.

If you're not sure if you typed the right url, press the _Validate_ button and the wizard will inform you if it is not available to read the catalog.