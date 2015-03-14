# Introduction #

When creating a release, the following steps are needed.

  1. Create tag.
  1. Checkout tag (if needed).
    * Use the name: `a.b.c.vyyyyMMddHHmm`
  1. Build update site for the tag
  1. Create and commit the developer's update site at `http://q4e.googlecode.com/svn/trunk/updatesite-dev/`.
  1. Announce to dev list
  1. Basic testing of the release in the tagged version (maybe with help of users).
    * This can last for a some days, so early adopters can provide feedback (maybe doing the above steps on a Friday, this way users/contributors can do tests at home).
  1. If we didn't break anything... Copy update site to trunk (maybe on a Wednesday).
  1. Announce to users list.
  1. Prepare plug-in and feature versions for the next release.

# Detailed checks #

The above summary is useful as a checklist, to make sure that everything is done. The specific details are explained in the next sections.

## Create tag ##

The tag must be created under `tags`in subversion.

The tag name must be `q4e-version.vdate`:
  * _q4e-version_ is in the `mayor.minor.bugfix` format: `0.2.3`.
  * _date_ is the release date (in the UTC timezone) and uses the format `yyyyMMddHHmm`... which means:
    * The year, with 4 digits.
    * The month, with 2 digits.
    * The day of month, with 2 digits.
    * The hour, in 24 hours format and 2 digits.
    * The minute, with 2 digits.

For example:
```
0.2.3.v200710232136
```

An example of the command to run would be (from the checked-out source root).
```
svn copy https://q4e.googlecode.com/svn/trunk \
  https://q4e.googlecode.com/svn/tags/0.3.0.v200711192308 \
  -m "tag for pre-releasing to dev site"
```
## Check out tag ##
This ensures that the release is built from the tag contents, so it can be rebuilt if needed.

## Build update site from the tag ##

**TODO:** Document the steps

## Create and commit the update site ##

Once the update site is built from the tag, it must be published in the _snapshots_ update site.

This is achieved by copying the generated site in the `updatesite-dev` folder in subversion's trunk.

**TODO:** Is more detail needed?

## Announce to the developers list ##

**Subject:** `[ANN] Pre-release X.Y.Z.vDATE is available for testing.`

Include information about:
  * Planned public release date.
  * What's new/changed (most relevant).
  * Important fixes
  * Important outstanding issues

And encourage users to report regressions.

## Test! ##

Until the release date, developers should concentrate on testing. The community will surely be helping too.

If severe problems are found during this phase, the public release can be delayed, the problems resolved and the release process started over.

## Publish ##
Once the prerelease has been tested, copy it to the main update site.

**TODO: More details.**

## Announce ##
Do an announcement on the users' list.

## Prepare for the next release ##
Increase the versions of the plug-ins.

