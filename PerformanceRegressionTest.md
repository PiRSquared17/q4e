# Performance regression test #

This document describes how to set up and execute a performance test with q4e.

The test is to be run with a _real_ eclipse with q4e (i.e. not on a runtime workbench) to avoid any overhead.

This environment properties are used to refer to system-dependant locations.

| `$TEST_BASE` | base folder where the test will be run |
|:-------------|:---------------------------------------|
| `$ECLIPSE_TEST_HOME` | base folder where eclipse is installed (with q4e installed) |

## Collecting results ##

Please use this [Google Spreadsheet](http://spreadsheets.google.com/ccc?key=pVVP0XaCIaduJQa7RKloQAw&inv=q4e-dev@googlegroups.com&t=2349835949762579839&guest) for entering the results.

You must be logged in to Google for using it.

## Set up ##

These steps are to be performed just once.

### Check out the sources ###
```
cd $TEST_BASE
svn co http://svn.eu.apache.org/repos/asf/cocoon/trunk@678123 cocoon-r678123
```

**Note:** A tag is used to achieve reproducible results.

### Prepare trace file ###

You'll need a trace configuration file with this contents:

**`trace.options`**
```
# Global trace switch for this plug-in
org.devzuz.q.maven.jdt.ui/debug=true
# Trace for events related to importing projects
org.devzuz.q.maven.jdt.ui/debug/import=true
# This switch enables/disables time information in every other trace 
org.devzuz.q.maven.jdt.ui/debug/timing=true

# TRACE CLASSPATH UPDATES and INCREMENTAL BUILDER
org.devzuz.q.maven.jdt.core/debug=true
# Trace for events related to classpath updates
org.devzuz.q.maven.jdt.core/debug/classpathUpdate=true
# Trace for events related to the maven incremental builder
org.devzuz.q.maven.jdt.core/debug/mavenBuilder=true
# This switch enables/disables time information in every other trace 
```

See [Debugging](Debugging.md) for more information.

### Populate your local repository ###

To make sure that we are not including the download times in out tests, download all dependencies.

Install all artifacts to avoid environment differences when one tester has some dependencies in the local repository and another does not.

```
cd $TEST_BASE/cocoon-r678123
export MAVEN_OPTS=-Xmx512m
mvn -P allblocks install -DdownloadSources=true -Dmaven.test.skip=true
mvn clean
```

Go for some coffee, this will take some time.

## Running the test ##

### Make a copy of the original sources ###
This is required to make sure that the code imported into eclipse
does not have the eclipse generated files (`.classpath`, `.project`, `.settings/`).

```
cd $TEST_BASE
rm -rf cocoon-test
cp -r cocoon-r678123 cocoon-test
```

### Clean the workspace ###

```
rm -rf $TEST_BASE/workspace
```

### Launch Eclipse with traces enabled ###

**Notes**:

  * Increase memory to 1024 megabytes to avoid measuring gc collection. It will also be interesting to compare the results with other memory sizes.
  * Set `MaxPermSize` to the same value used by eclipse-jee in Ganymede.

```
$ECLIPSE_TEST_HOME/eclipse -debug $TEST_BASE/trace.options -data $TEST_BASE/workspace -vmargs -Xmx1024m -XX:MaxPermSize=256m > trace-`date +%Y%m%d-%H%M%S`.log
```

Now configure your environment for launching the tests:
  * Hide the _Events_ view
  * Configure Maven offline mode as desired (select offline to ignore network latency).

Select _File > Import... > Maven 2 Project_, check the _Import Parent Projects_ checkbox and import the `cocoon-test` folder.

Do some other thing for a while... until the import, classpath updates and builds are done.

## Analyze the logs ##

This is a partial example of the generated output.
```
15:10:12.579  [PROJECT_IMPORT] Starting ...
...
15:22:56.339  [PROJECT_IMPORT] Done ...
15:23:07.524  [CLASSPATH_UPDATE] New classpath for project cocoon-maven-plugin
15:23:07.524  [CLASSPATH_UPDATE] Refreshing classpath for maven project cocoon-maven-plugin - Processing 32 artifacts
15:23:18.750  [CLASSPATH_UPDATE] New classpath for project cocoon-forms-impl
15:23:18.750  [CLASSPATH_UPDATE] Refreshing classpath for maven project cocoon-forms-impl - Processing 61 artifacts
...
17:01:51.693  [MAVEN_INCREMENTAL_BUILDER] Processing resources on P/cocoon-block-deployment : process-resources
17:01:53.398  [MAVEN_INCREMENTAL_BUILDER] Processing resources on P/cocoon-block-deployment : process-test-resources
17:01:57.959  [MAVEN_INCREMENTAL_BUILDER] Processing resources on P/cocoon-maven-plugin : process-resources
17:02:00.257  [MAVEN_INCREMENTAL_BUILDER] Processing resources on P/cocoon-maven-plugin : process-test-resources
...
```

  * The `[PROJECT_IMPORT]` lines allow measuring the time used in importing the projects.
  * Then, each project classpath is computed.
  * Finally, the projects are built.

### Time used in the import ###

Get the start and end of the project import.
```
grep PROJECT_IMPORT trace-20080720-215653.log | cut -c -80
```

```
22:37:35.664  [PROJECT_IMPORT] Starting [org.apache.maven.model.Model@baba00:/Us
22:48:14.165  [PROJECT_IMPORT] Done [org.apache.maven.model.Model@baba00:/Users/
```

Total import time: 10m 29s

### Time until last classpath update ###
Get the last update:
```
grep CLASSPATH_UPDATE trace-20080720-215653.log | cut -c -80 | tail -n 1 
```

```
23:43:30.714  [CLASSPATH_UPDATE] Refreshing classpath for maven project cocoon-2
```

Total classpath update time: 55m 16s (from end of project import)

### Time used until last incremental build ###

Get the last build event.

```
grep MAVEN_INCREMENTAL_BUILDER trace-20080720-215653.log | cut -c -80 | tail -n 1 
```

```
23:49:24.217  [MAVEN_INCREMENTAL_BUILDER] Processing resources on P/cocoon-webap
```

Total build time: 05m 54s (from last classpath update)

### Total time ###

Sum of the above measures: 1h 11m 49s.

**IMPORTANT NOTE:** Take these numbers with a grain of salt, they are not accurate since CLASSPATH\_UPDATEs and MAVEN\_INCREMENTAL\_BUILDERs are run in burst (i.e. the last CLASSPATH\_UPDATE comes after several MAVEN\_INCREMENTAL\_BUILDER events).

Review the generated logs to get some insight into what's really going on.