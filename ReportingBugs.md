# Introduction #

The q4e developers test each release, add new features, provide support on the lists, review bug reports... That's quite a lot of work!

One place where you can help is in providing good reports when entering a new bug in [IAM's Bugzilla at the Eclipse Foundation](https://bugs.eclipse.org/bugs/enter_bug.cgi?product=IAM).


# Is it a Q4E bug ? #

First step is to check if the issue is really in Q4E code. There are many libraries used by the plugin, and the problem may be elsewhere.

We request that everybody check their problem against the command line version of Maven. For instance if you have a problem running the Maven install goal in Eclipse, please try `mvn install` in the command line. Unfortunately the Maven libraries used by Q4E are not yet released and you should use the command line Maven version `2.1.0-xyz` that matches your Q4E version.

To find what version you need to download, in Eclipse, go to Help - About Eclipse Platform - Plug-in details and find the version for the plugin with id `org.apache.maven.embedder`, then go to [Downloads](http://code.google.com/p/q4e/downloads/list) and get the one that matches. Remember to set the M2\_HOME system variable pointing to the directory where you install it and add M2\_HOME/bin to the PATH before any other Maven installation you already have.

If you experience the same problem with the command line version then we are afraid the problem is not in Q4E, but in Maven itself and you should report the issue to the Maven developers through the [Maven Developer List](http://maven.apache.org/mail-lists.html) or the [issue tracker](http://maven.apache.org/issue-tracking.html). Make sure you reference the version that has the problem for faster resolution.

If you don't experience the issue in the command line version please continue reading and remember the version you used for testing, you will need it.


# What is a good report? #

It's very simple:

Provide **all** the information the developer's will need for finding the same bug on his computer. Those are **great** bug reports.

The following list contains the typical information required for great bug reports. In some circumstances, some of them might not be available. Telling so in the issue report is usually good enough.

  * **Version information:** If you got q4e from the update site, which version was it? If you've built it from SVN, which revision have you checked out?
    * Include the version  of the Maven command line binary you used to determine that it isn't a Maven command line bug (see previous section)
    * For accurate version reporting, use the _About Eclipse Platform_ dialog (in the _Help_ menu for Windows and Linux, _Eclipse_ menu for mac) and choose _Configuration Details_. Copy the displayed text to a text file and attach it to the issue report.
    * It is always nice to read the summary of this information in the bug report, so if you know it, state the q4e and eclipse version you're using.
  * **Error messages and exception traces**, reported on the Eclipse Error Log or on the Maven Events View. Both allow copy&paste or exporting its contents to a file.
    * If you're able to reproduce the problem, it is most useful if you clear the views before, so you only send the output generated from your test.
  * **A test case**. Can be one of the following:
    * an eclipse workspace. In such case, just attach a zip file to the issue when creating it.
    * a publicly available project. The developers will need the repository and version/tag to checkout
    * some files providing a simplified test case. This is the **best** of all options.

It is also appropriate to include long stacktraces as attachments instead of as part of the comments. That makes the issue page more readable, specially when the number of comments starts to grow. It might be useful to include a couple of lines to point at something, though.