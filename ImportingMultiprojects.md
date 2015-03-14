# Introduction #

The inability of having nested projects in Eclipse is a well known problem of the Eclipse IDE that doesn't seem it's going to be fixed until Eclipse 4.0

# Our solution #

Starting in Q4E 0.5.0 you can import the parent poms in a nested mutiproject setup, just check the field "Import Parent" in the Import Maven Project wizard

  * myproject/pom.xml
  * myproject/moduleA/pom.xml
  * myproject/moduleB/pom.xml

The problem comes with other plugins like Subclipse, that will not activate themselves in the modules projects, only in the parent one. If you try to activate it in all the projects then synchronization problems occur. Some users have reported that the Subversive plugin may behave better than Subclipse.

## A possible better approach ##

Use a [flat project layout](http://maven.apache.org/plugins/maven-eclipse-plugin/reactor.html#Flat%20Project%20Layout) so parent pom is at the same level as the modules

  * myproject/parent/pom.xml
  * myproject/moduleA/pom.xml
  * myproject/moduleB/pom.xml

This setup will require extra configuration in the poms

  * In the parent, modules need to be referred as `../moduleA`
  * In the modules, you have to add the `<relativePath>../parent/pom.xml</relativePath>` entry to the parent section. [Example](http://maven.apache.org/guides/introduction/introduction-to-the-pom.html#Example_2).