Released on May 6th, 2009

The 0.9.0 release is the first release after moving the code to the Eclipse Foundation as **[Eclipse IAM](http://www.eclipse.org/iam/)**

**If you had Q4E version 0.8.1 or lower installed read the [Upgrading to Eclipse IAM instructions](UpgradingToEclipseIAM.md)**

Plugin now targets Eclipse 3.4.x, although some features can still work in 3.3.x

[List of features](http://wiki.eclipse.org/What%27s_in_IAM) in the Eclipse IAM site

Some changes in this version:

  * Reworked the builder for improved performance
    * Changes to pom sections that don't affect the build won't trigger it
  * More performance improvements
  * [Archiva search integration](http://wiki.eclipse.org/Archiva_Search_Integration), find real time what's available in your Archiva repository
  * Form based POM editor
  * Buttons to view parent pom and effective pom from editor
  * Edit settings file from within Eclipse
  * Sync eclipse compiler config with maven-compiler-plugin config
  * Nexus repository manager index support in dependency search
  * Integration with Eclipse Buckminster
  * ... and a total of 77 bugs fixed


For a complete list of changes, check the <a href='https://bugs.eclipse.org/bugs/buglist.cgi?query_format=advanced&short_desc_type=allwordssubstr&short_desc=&product=IAM&target_milestone=0.9.0-q4e&long_desc_type=allwordssubstr&long_desc=&bug_file_loc_type=allwordssubstr&bug_file_loc=&status_whiteboard_type=allwordssubstr&status_whiteboard=&keywords_type=allwords&keywords=&bug_status=RESOLVED&bug_status=VERIFIED&bug_status=CLOSED&resolution=FIXED&emailtype1=substring&email1=&emailtype2=substring&email2=&bugidtype=include&bug_id=&votes=&chfieldfrom=&chfieldto=Now&chfieldvalue=&cmdtype=doit&order=Reuse+same+sort+as+last+time&field0-0-0=noop&type0-0-0=noop&value0-0-0='>Changelog</a>