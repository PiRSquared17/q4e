# Introduction #

For security reasons, access to the corporate repository is protected by using a certificate.

This document describes how to enable support for this configuration.


# Details #

Certificate management in java is handled by the runtime, so there's really not handled by Q for Eclipse.

You can use any of the _standard_ ways to enable certificates. We list two options below, but [googling](http://tinyurl.com/3tldx6) for your problem should also help.

## Use certificate files ##
When launching eclipse, it is possible to provide [extra arguments](http://help.eclipse.org/help33/index.jsp?topic=/org.eclipse.platform.doc.user/tasks/running_eclipse.htm) on the command line (or shortcut) or by editing the `eclipse.ini` file.

We'll use this parameters to define system properties that will teach the runtime how to find and access your certificate file.

Since we are passing arguments to the virtual machine, make sure they are at the end and after the `--vmargs` parameters.

This is an example of what options you could use on the `eclipse.ini` file.

```
-vmargs
...
-Djavax.net.ssl.trustStore=trust.jks
-Djavax.net.ssl.trustStorePassword=XXX
-Djavax.net.ssl.keyStore=/home/directory/mycertificate.p12                
-Djavax.net.ssl.keyStoreType=pkcs12                    
-Djavax.net.ssl.keyStorePassword=XXXXXX
```

## Import the certificates ##

An alternative option, that does not require any eclipse configuration tweaking, is to import your certificates in the java certificate store using the [keytool](http://java.sun.com/j2se/1.5.0/docs/tooldocs/windows/keytool.html) command.

```
keytool -import -trustcacerts -alias yourAlias -file myCA.crt -keystore jdk1.5.0_15/jre/lib/security/cacerts
```

That way now every application will be aware of your external keystore.