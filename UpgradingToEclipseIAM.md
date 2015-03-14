# Introduction #

After the development has moved to the Eclipse Foundation the new project Eclipse IAM had to introduce some changes, specifically naming, that require a little more work to upgrade than usual.


# Upgrading from Q4E to Eclipse IAM #


Eclipse IAM builds are still published in the Q4E update site for the time being.

  * Select all projects managed with Q4E, right click and deselect Maven 2 -> "Use Maven dependency management"
  * Uninstall previous versions of Q4E
    * Help - Software Updates - Installed software
    * Select all entries starting with "Q for Eclipse" and the one "Pom XML Editor Feature"
    * Click uninstall
  * Delete `org.devzuz.q.*` files and directories from your eclipse/plugins directory if still present
  * Restart Eclipse with the -clean parameter
  * Install Eclipse IAM as described in [Installation](Installation.md)
  * Select all projects that you want to handle with IAM, right click and select Maven 2 -> "Use Maven dependency management"