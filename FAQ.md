# Frequently asked questions about Q for Eclipse #

## What can this Eclipse plugin do? ##

It will allow you to run Maven goals from the Eclipse IDE, create new Maven projects using archetypes, import Maven projects without any intermediate steps, view the dependencies of your project in a graph,... we will keep adding features with the time

## How do I install it? ##

Refer to [Installation](Installation.md)

## Why the plugin is not at the Eclipse Foundation? ##

It is, although renamed as [Eclipse IAM](http://eclipse.org/iam).

The code is already hosted at eclipse, and the Eclipse bugzilla and wiki will replace the documentation hosted here.

## What are the differences between this plugin and m2eclipse? ##

The objective of this Q for Eclipse was to be part of the [Eclipse Foundation](http://www.eclipse.org), at a time were m2eclipse was not considering that option.

We still have that objective and thanks to the sponsorship of [DevZuz](http://www.devzuz.com), an [Eclipse Strategic Developer Member](http://www.eclipse.org/membership/showMember.php?member_id=824) we are in a good position to achieve this goal. So far Q4E has been proposed as a Eclipse Foundation official project, [Eclipse IAM](http://www.eclipse.org/proposals/iam).

After the m2eclipse rewrite, most of the architectural differences are blurred, and we are actively seeking to find some common ground in eclipse-maven integration efforts between both plug-ins.

In any case the underlying Maven libraries used for both plugins are the same (the Maven Embedder) and both plugin developers collaborate in its development.


Some technical differences still hold, thought:

  * Q4E is event-oriented.
  * Q4E uses the maven embedder for every task. We believe that this provides deeper integration and better performance.

You can also check a [third-party comparison of m2eclipse and Q4E at Maven User's wiki](http://docs.codehaus.org/display/MAVENUSER/Eclipse+Integration) (any user can add & edit data to the comparison).

## Can I run Maven 2.0.x or a different Maven installation with Q? ##

No. Q4E runs Maven in process, not forking a new jvm, so it uses the libraries included in Q4E.

Why? performance, event and error handling, simplicity...

For instance, if you have a Maven project open in Eclipse but not installed in your repository Q4E will work with it from the Eclispe workspace because we can intercept the calls in the Maven libraries.

Another example, if your build fails with any error we can build a meaningful message and add an error marker to Eclipse without having to parse the Maven standard and error output and figuring out what went wrong.

## Resources folders are not created as source folders when importing/enabling Maven 2 projects ##

It is actually by design.  In version 0.5.0 functionality was added so
that Q4E uses Maven itself to perform the processing of resources.
This allows include/exclude elements and property replacement
filtering to be performed.

Whenever you change the POM resource
configuration, do a clean/manual build, or modify a resource that is
matched by the resource includes in the POM Q4E will invoke the Maven
resources:resources or resources:testResources goals as appropriate.

If you look at the Maven Events view you should be able to see this
process happening. There is also some work in progress to allow other
plugins bound to the process-resources and process-test-resources
phases to operate, so things like build numbering, ant scripts that
copy resources, etc, work.


## Why the name Q? ##

Q, named after the [special agent Q](http://en.wikipedia.org/wiki/Q_(James_Bond)) in the James Bond books and movies. No double meaning or anything like that, we are just James Bond fans ;)