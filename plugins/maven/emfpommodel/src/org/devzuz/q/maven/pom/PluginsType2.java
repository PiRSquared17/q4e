/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.devzuz.q.maven.pom;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Plugins Type2</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.devzuz.q.maven.pom.PluginsType2#getPlugin <em>Plugin</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.devzuz.q.maven.pom.PomPackage#getPluginsType2()
 * @model extendedMetaData="name='plugins_._2_._type' kind='elementOnly'"
 * @generated
 */
public interface PluginsType2 extends EObject
{
    /**
     * Returns the value of the '<em><b>Plugin</b></em>' containment reference list.
     * The list contents are of type {@link org.devzuz.q.maven.pom.Plugin}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Plugin</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Plugin</em>' containment reference list.
     * @see org.devzuz.q.maven.pom.PomPackage#getPluginsType2_Plugin()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='plugin' namespace='##targetNamespace'"
     * @generated
     */
    EList<Plugin> getPlugin();

} // PluginsType2
