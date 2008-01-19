/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.validation;

import org.apache.maven.pom400.ConfigurationType4;

/**
 * A sample validator interface for {@link org.apache.maven.pom400.Notifier}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface NotifierValidator {
	boolean validate();

	boolean validateType(String value);
	boolean validateSendOnError(boolean value);
	boolean validateSendOnFailure(boolean value);
	boolean validateSendOnSuccess(boolean value);
	boolean validateSendOnWarning(boolean value);
	boolean validateAddress(String value);
	boolean validateConfiguration(ConfigurationType4 value);
}