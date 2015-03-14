Q is an Apache Maven plugin for [Eclipse](http://www.eclipse.org) that will let you use [Maven 2](http://maven.apache.org) from the Eclipse IDE

This plugin better integrates the development and building processes by bringing Maven into the Integrated Development Environment (IDE).

# News #

## Jan 20th, 2010: Eclipse IAM 0.11.0 ##

We are glad to announce the public availability of IAM 0.11.0

This new version includes most notably
  * [WAR overlay support](http://wiki.eclipse.org/IAM_WTP_support)
  * Improved WAR and EAR WTP facets configuration
  * bugfixes

P2 Update site is published at: http://q4e.googlecode.com/svn/trunk/updatesite-iam/

The list of changes is available on the eclipse wiki [eclipse wiki](http://wiki.eclipse.org/New_on_IAM_0.11.0).


## Jun 30th, 2009: Eclipse IAM 0.10.0, Galileo edition! ##

Eclipse IAM has been updated to **work with** Galileo (Eclipse 3.5) and to **use** some of the new projects on this eclipse release train. Ganymede users(Eclipse 3.4) should make sure they have added all the update sites listed in the [installation instructions](Installation.md).

Adopters of the latest and greatest Eclipse Galileo can install from the update site [as usual](Installation.md).

If upgrading from Q4E 0.8.1 or earlier, follow these [extra steps](UpgradingToEclipseIAM.md).

See [what's new in 0.10.0](http://wiki.eclipse.org/New_on_IAM_0.10.0) on the Eclipse wiki

_Note that this is an unofficial Eclipse IAM release to allow our users to enjoy the progress made until we complete the move to the foundation_


## May 6th, 2009: New major release 0.9.0 ##

After some work moving to the Eclipse Foundation we have made the first release of Eclipse IAM.

Major improvements are improved performance (less builds are triggered) and form based pom editors, with the ability of opening parent poms, effective pom, or settings file.

Read the [installation instructions](Installation.md) as we are using a new update site to avoid confusion with previous Q4E releases. If you are upgrading from a previous version of Q4E you will need to do [some extra steps](UpgradingToEclipseIAM.md)

See [what's new in 0.9.0](New_in_0_9_0.md)

_Note that this is an unofficial Eclipse IAM release to allow our users to enjoy the progress made until we complete the move to the foundation_


## Dec 17th, 2008: New maintenance release 0.8.1 ##

While we walk the path to making Eclipse IAM snapshots available to you, we've released a new q4e release with a minor bugfix to behave more nicely with the central repository, reducing the number of downloads of the central index file.
Please install and help the maven repo maintainers conserve bandwidth.

## Codebase hosted at eclipse ##

The Eclipse Foundation has accepted q4e in their subversions. Development of q4e will now continue at [Eclipse IAM](http://eclipse.org/iam).

**Please use [IAM bugzilla](https://bugs.eclipse.org/bugs/enter_bug.cgi?product=IAM)** for new reporting new issues and suggestions. You can also help us by reporting your favorite tickets in bugzilla (if you do so, please let us know on the original ticket!).

Latest version can be checked out from http://dev.eclipse.org/svnroot/technology/org.eclipse.iam

## Oct 16th, 2008: Q4E and Eclipse IAM at ApacheCon US and Eclipse Summit Europe ##

In the next weeks the project will be present at two important conferences where [Abel Muiño](http://ramblingabout.wordpress.com/) and [Carlos Sanchez](http://blog.csanchez.org/2008/10/14/q4e-and-eclipse-iam-news-and-talks/) will be talking about the project features, status, future direction,...

**ApacheCon US**, New Orleans, November 6th

**[Carlos Sanchez](http://csanchez.org/)**: **[Q4E and Eclipse IAM, Maven integration for Eclipse](http://us.apachecon.com/c/acus2008/sessions/51)**

"Q for Eclipse", now Eclipse IAM (Eclipse Integration for Apache Maven), is a new Open Source project that integrates Apache Maven and the Eclipse IDE for faster, more agile, and more productive development. The plugin allows you to run Maven from the IDE, import existing Maven projects without intermediate steps, create new projects using Maven archetypes, synchronize dependency management, search artifact repositories for dependencies that are automatically downloaded, view a graph of dependencies and more! Join us to discover how to take advantage of all these features, as well as how they can help you to improve your development process.

_[Carlos Sanchez](http://csanchez.org/) has been involved in Open Source for nearly ten years. He has specialized in solving a variety of business challenges in a wide variety of industries, including e-commerce, financial services, telecommunications, and software development. A member of the Apache Maven Project Management Committee (PMC), Carlos is one of the maintainers of the central Maven repository and a co-author of "Better Builds with Maven," the first book about Maven 2. He is currently Director at Exist Global in Los Angeles. Carlos received his Computer Engineering degree at the University of Coruña, Spain, and enjoys traveling and discovering new places._

**Eclipse Summit Europe**, Ludwigsburg, Germany, November 20th

**[Abel Muiño](http://ramblingabout.wordpress.com/)**: **[IAM new & noteworthy](http://www.eclipsecon.org/summiteurope2008/sessions?id=159)**

Eclipse IAM (Integration for Apache Maven) is a new eclipse technology project in the eclipse incubator providing integration of maven in eclipse. In this talk we will review what features are already present on Eclipse IAM, what is on the roadmap and how users and adopters can configure and extend it.

_[Abel Muiño](http://ramblingabout.wordpress.com/) is the project lead for Eclipse IAM (Integration of Apache Maven) and principal architect for Berggi Inc. For the past 5 years, he has worked as java developer and architect in Data Integration, Security and Services for Mobile Devices._


## Oct 14th, 2008: We are in the process of moving to Eclipse Foundation ##

Long time no news, but still progress being done. Development still continues here while we are going through the IP process moving the plugin components to the Eclipse Foundation infrastructure, carefully to avoid disruptions. it just takes some time so we want to make sure everything continues working in the meantime.

## Sep 8th, 2008: 0.8.0 released! ##

0.8.0 is mainly a maintenance release with a number of bugfixes.

[What's new](New_in_0_8_0.md)

[Installation instructions](Installation.md).


## Jun 21th, 2008: Eclipse IAM project is complete ##

The Eclipse webmasters have completed the creation of the IAM project!

We'll be moving our infrastructure to the Eclipse servers during the following months.

We already have a project [summary page](http://www.eclipse.org/projects/project_summary.php?projectid=technology.iam), our [space on the eclipsepedia](http://wiki.eclipse.org/IAM), [user newsgrup](http://www.eclipse.org/newsportal/thread.php?group=eclipse.technology.iam) and [developer's mailing list](https://dev.eclipse.org/mailman/listinfo/iam-dev).

## Jun 2nd, 2008: 0.7.0 released! ##

This is a new mayor release with many new features and enhancements.

[What's new](New_in_0_7_0.md)

[Installation instructions](Installation.md).

## May 20th, 2008: Eclipse IAM project proposal approved ##

The Eclipse IAM project proposal (based on Q4E) has been approved. We'll be starting to create the necessary infrastructure in the Eclipse Foundation over the next weeks and we expect no disruption on users due to the migration.

## May 13th, 2008: IAM project proposal moved to creation review ##

We're proud to announce that the IAM project proposal is preparing for the creation review to be held on May 20th.

IAM is the eclipse project proposal that will provide deep integration for maven on the Eclipse Foundation. When approved, Q for Eclipse will be donated to IAM and continue its development on the Eclipse Foundation.

The creation review slides are available from the [IAM project proposal](http://www.eclipse.org/proposals/iam/) at eclipse.

Feedback on IAM can be provided on its specific newsgroup at Eclipse (you need to [register](http://www.eclipse.org/newsgroups/register.php))
  * server: news.eclipse.org
  * group: eclipse.technology.iam
  * [Web interface](http://www.eclipse.org/newsportal/thread.php?group=eclipse.technology.iam)

# Features #

  * running Maven goals from the IDE
  * dependency managing using the Maven POM, with automatic download of dependencies
  * keeping Eclipse classpath synchronized with Maven POM
  * [dependency graphing](DependencyGraphViewer.md)
  * direct import of Maven 2 projects
  * wizard for creation of new projects using the archetype mechanism
  * modular approach to improve reusability by other Eclipse projects
  * [ability to import parent projects (pom projects)](ImportingMultiprojects.md) (from 0.5.0)
  * ability to cancel maven builds (from 0.5.0)
  * dependency analysis tooling (from 0.5.0)
  * [WTP](http://www.screencast.com/users/amuino/folders/Jing/media/5af1cc9a-a6dd-4571-be54-c471a0de9377) support (from 0.6.0)


# License #

[Eclipse Public License (EPL)](http://www.eclipse.org/legal/epl-v10.html)

# Supporters #

[![](http://www.exist.com/images/exist_logo.jpg)](http://www.exist.com) provides developer time to work on this project.

YourKit is kindly supporting open source projects with its full-featured Java Profiler.
YourKit, LLC is creator of innovative and intelligent tools for profiling
Java and .NET applications. Take a look at YourKit's leading software products:
[YourKit Java Profiler](http://www.yourkit.com/java/profiler/index.jsp) and
[YourKit .NET Profiler](http://www.yourkit.com/.net/profiler/index.jsp).