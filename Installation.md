# Update site #

**Q4E is now [Eclipse IAM](http://www.eclipse.org/iam/)**

Releases will still be published in this site until full migration to the Eclipse Foundation happens.

**If you had Q4E version 0.8.1 or lower read the [Upgrading to Eclipse IAM instructions](UpgradingToEclipseIAM.md)**

## `http://q4e.googlecode.com/svn/trunk/updatesite-iam/` ##

We recommend installing into _Eclipse IDE for Java EE Developers_ as it already includes most of the dependencies.


## Eclipse 3.5 Galileo and 3.6 Helios ##

Go to Help - Install New Software

Click on _Add_ and add the Eclipse IAM update site at `http://q4e.googlecode.com/svn/trunk/updatesite-iam/`

Make sure that _Contact all update sites during install to find required software_ is checked and the _Galileo_ or _Helios_ Update Sites (depending on your eclipse version) are enabled in _Available Software Sites_ preference page.


## Eclipse 3.4 Ganymede ##

Go to Help - Software Updates - Available Software

Click on _Manage Sites - Add_ and add the Eclipse IAM update site at `http://q4e.googlecode.com/svn/trunk/updatesite-iam/`

Also add the following Update Sites, or make sure they are checked in the _Manage Sites_ screen, depending on your version of Eclipse

  * Ganymede Update Site `http://download.eclipse.org/releases/ganymede` (checked by default)
  * `http://download.eclipse.org/modeling/emft/updates/releases/`
  * `http://download.eclipse.org/tools/gef/updates/releases/`
  * `http://download.eclipse.org/technology/subversive/0.7/update-site/` (needed for Team Support integration, see below)

# Optional integrations #

For the optional integration features you'll need to add some extra update sites to install the requirements.

## Team Support (Subversive) integration requirements ##

You may need these update sites

  * [Latest installation instructions](http://www.polarion.com/products/svn/subversive.php?src=eclipseproject)
  * `http://download.eclipse.org/technology/subversive/0.7/update-site/`
  * `http://www.polarion.org/projects/subversive/download/eclipse/2.0/update-site/`

## Buckminster integration requirements ##

**No longer maintained**

  * [Latest installation instructions](http://www.eclipse.org/buckminster/downloads.html)
  * `http://download.eclipse.org/tools/buckminster/updates-3.4`


# Minimum Installation Requirements #
The following is required to use the Q for Eclipse Plugin:
  * Eclipse 3.4 http://download.eclipse.org/eclipse/downloads/
  * Java 5 http://java.sun.com/


# Verifying the installation #

Follow this procedure to verify that the Q for Eclipse Plugin successfully installed on Eclipse:

  1. After Eclipse has been restarted, click on Help > About Eclipse SDK.
  1. On the pop-up, click Plug-in Details. A list of all plugins installed on Eclipse will appear.
  1. Scroll down and look for the Eclipse IAM Plugin you just installed.


# Recommended third party plugins #

We suggest the installation of the [Web Tools Platform](http://www.eclipse.org/webtools/) XML editor so you can benefit from auto completion, error checking,... while editing the pom.xml files.

You can install it from the Eclipse Update site that you should already have in your  sites list, or following the instructions in the [WTP downloads page](http://download.eclipse.org/webtools/downloads/)

In your poms make sure you use the full header with the schema declaration:

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

<!-- your pom contents -->

</project>
```


# Development builds #

There's also a developer's update site used for testing prereleases at `http://q4e.googlecode.com/svn/trunk/updatesite-dev/`


# Questions #

if you have any questions or experience problems, please give us your feedback at [our user newsgroup](http://www.eclipse.org/newsportal/thread.php?group=eclipse.technology.iam)