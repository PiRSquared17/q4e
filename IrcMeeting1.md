# Participants #

  * Abel Muiño
  * Allan Ramirez
  * Jake Pezaro
  * Erle Mantos

# Topic #

## Times and Dates for IRC meetings ##

  * Times should  "rotate" so timezones are right for people in just 2 locations (America & Europe, Europe & Asia, Asia & America)

## Releasing 0.8.0 ##

  * Wait for all "significant contributions" to be merged (currently XML editing and synchronization code in [the forms-xml-editor-sync branch](http://code.google.com/p/q4e/source/browse/branches/forms-xml-editor-sync/) )
  * Sumbit of CQ's to eclipse for review is currently broken (see [issue 240999](https://bugs.eclipse.org/bugs/show_bug.cgi?id=240999))

## Performance ##
> Some problems with the perceived and real performsnce
  * Workspace locking
  * Work done by the incremental builder
  * Cleaning the embedder

Lines of work:
  * Don't lock the workspace, lock on maven embedder instead: [Issue 509](http://code.google.com/p/q4e/issues/detail?id=509)
  * Set up a performance regression test: [Issue 510](http://code.google.com/p/q4e/issues/detail?id=509)
  * Develop a pom diff generator to optimize the processing of changes: [Issue 508](http://code.google.com/p/q4e/issues/detail?id=508)