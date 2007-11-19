/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.validation;

import org.apache.maven.pom400.ExcludesType;
import org.apache.maven.pom400.IncludesType;

/**
 * A sample validator interface for {@link org.apache.maven.pom400.Resource}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ResourceValidator {
	boolean validate();

	boolean validateTargetPath(String value);
	boolean validateFiltering(boolean value);
	boolean validateDirectory(String value);
	boolean validateIncludes(IncludesType value);
	boolean validateExcludes(ExcludesType value);
}
