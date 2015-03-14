# IP process #

## Dependencies as of July 12 2008 in branch https://q4e.googlecode.com/svn/branches/embedder-upgrade ##

There is an **autometed IP log** for Eclipde IAM: http://www.eclipse.org/projects/ip_log.php?projectid=technology.iam

| **Dependency** | **In Orbit?** | **Needs upgrade?** | **CQ** | **Requested?** | **Approved?** (1) |
|:---------------|:--------------|:-------------------|:-------|:---------------|:------------------|
| com.jcraft.jsch\_0.1.31.jar | Y | N | [2203](https://dev.eclipse.org/ipzilla/show_bug.cgi?id=2203) | Y | Y |
| org.apache.commons.cli\_1.0.0.v200709131616.jar | Y | [Y](http://download.eclipse.org/tools/orbit/downloads/drops/R20080611105805/bundles/org.apache.commons.cli_1.0.0.v20080604-1500.jar) | [1590](https://dev.eclipse.org/ipzilla/show_bug.cgi?id=1590)  | Y | Y |
| org.apache.commons.collections\_3.2.0.jar | Y | [Y](http://download.eclipse.org/tools/orbit/downloads/drops/R20080611105805/bundles/org.apache.commons.collections_3.2.0.v200803061811.jar) | [1909](https://dev.eclipse.org/ipzilla/show_bug.cgi?id=1909) | Y | Y |
| org.apache.commons.lang\_2.1.0.v200709131643.jar | Y | [Y](http://download.eclipse.org/tools/orbit/downloads/drops/R20080611105805/bundles/org.apache.commons.lang_2.1.0.v200803061811.jar) | [1595](https://dev.eclipse.org/ipzilla/show_bug.cgi?id=1595) | Y | Y |
| org.apache.maven.embedder\_2.1.0.659801.jar | N |  |
| org.apache.xerces\_2.8.0.v200705301630.jar | Y | N (Orbit [R200709171314](https://code.google.com/p/q4e/source/detail?r=200709171314)) | [2093](https://dev.eclipse.org/ipzilla/show_bug.cgi?id=2093) | Y | Y |
| org.apache.xml.resolver\_1.1.0.v200705310020.jar | Y | N (Orbit [R200709171314](https://code.google.com/p/q4e/source/detail?r=200709171314)) | [2151](https://dev.eclipse.org/ipzilla/show_bug.cgi?id=2151) | Y | Y |
| org.aspectj.runtime\_1.5.4.200705211336.jar | Y | N |  | request not needed |
| org.w3c.tidy\_0.0.0.4aug2000r7-dev-200711190855.jar | N |  |
| org.apache.maven:maven-embedder:jar:2.1-SNAPSHOT:compile | N |  |
| org.apache.maven:maven-core:jar:2.1-SNAPSHOT:compile | N |  |
| org.apache.maven:maven-workspace:jar:2.1-SNAPSHOT:compile | N |  |
| org.apache.maven:maven-lifecycle:jar:2.1-SNAPSHOT:compile | N |  |
| org.apache.maven:maven-reporting-api:jar:2.1-SNAPSHOT:compile | N |  |
| org.apache.maven.doxia:doxia-sink-api:jar:1.0-alpha-9:compile | N |  |
| org.apache.maven:maven-profile:jar:2.1-SNAPSHOT:compile | N |  |
| org.apache.maven:maven-model:jar:2.1-SNAPSHOT:compile | N |  |
| org.apache.maven.artifact:maven-artifact:jar:3.0-SNAPSHOT:compile | N | to 3.0-alpha-1 |
| org.apache.maven:maven-plugin-api:jar:2.1-SNAPSHOT:compile | N |  |
| org.codehaus.plexus:plexus-interactivity-api:jar:1.0-alpha-6:compile | N |  |
| org.codehaus.plexus:plexus-utils:jar:1.5.1:compile | N |  |
| org.codehaus.plexus:plexus-classworlds:jar:1.2-alpha-12:compile | N |  |
| org.apache.maven:maven-project:jar:2.1-SNAPSHOT:compile | N |  |
| org.codehaus.plexus:plexus-interpolation:jar:1.0-SNAPSHOT:compile | N |  |
| org.apache.maven:maven-toolchain:jar:2.1-SNAPSHOT:compile | N |  |
| org.codehaus.plexus:plexus-container-default:jar:1.0-alpha-46-SNAPSHOT:compile | N |  |
| org.apache.maven.wagon:wagon-http-lightweight:jar:1.0-beta-2:compile | N |  |
| org.apache.maven.wagon:wagon-provider-api:jar:1.0-beta-2:compile | N |  |
| org.apache.maven.wagon:wagon-http-shared:jar:1.0-beta-2:compile | N |  |
| org.apache.maven.wagon:wagon-ssh:jar:1.0-beta-2:compile | N |  |
| org.apache.maven.wagon:wagon-ssh-common:jar:1.0-beta-2:compile | N |  |
| org.apache.maven.wagon:wagon-ssh-external:jar:1.0-beta-2:compile | N |  |
| org.apache.maven.wagon:wagon-file:jar:1.0-beta-2:compile | N |  |
| ./plugins/thirdparty/org.apache.lucene/lucene-core-2.3.2.jar | N | Y |[2725](https://dev.eclipse.org/ipzilla/show_bug.cgi?id=2725) | Y | parallel |
| ./plugins/thirdparty/org.dom4j/dom4j-1.6.1.jar | N | N | [2724](https://dev.eclipse.org/ipzilla/show_bug.cgi?id=2724) | Y | Y |
| ./plugins/thirdparty/org.apache.maven.shared.dependency.tree/lib/maven-dependency-tree-1.1.jar | N | N | [2726](https://dev.eclipse.org/ipzilla/show_bug.cgi?id=2726) | Y | parallel |

  * (1) _parallel_ value in the _approved_ column means we're allowed to use it under the [parallel IP process](http://wiki.eclipse.org/Development_Resources/HOWTO/Parallel_IP_Process).

Several Orbit bundles need to be upgraded to match the downloads from Orbit or make sure they were available in previous release of Orbit

[Orbit downloads](http://download.eclipse.org/tools/orbit/downloads/drops/R20080611105805/)
[Orbit previous build](http://download.eclipse.org/tools/orbit/downloads/drops/R200709171314/)