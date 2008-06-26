/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.devzuz.q.maven.pom;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Plugin Management</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 4.0.0
 * 
 *         Section for management of default plugin information for use in a group of POMs.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.devzuz.q.maven.pom.PluginManagement#getPlugins <em>Plugins</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.devzuz.q.maven.pom.PomPackage#getPluginManagement()
 * @model extendedMetaData="name='PluginManagement' kind='elementOnly'"
 * @generated
 */
public interface PluginManagement extends EObject
{
    /**
     * Returns the value of the '<em><b>Plugins</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 4.0.0
     * 
     *             The list of plugins to use.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Plugins</em>' containment reference.
     * @see #setPlugins(PluginsType3)
     * @see org.devzuz.q.maven.pom.PomPackage#getPluginManagement_Plugins()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='plugins' namespace='##targetNamespace'"
     * @generated
     */
    PluginsType3 getPlugins();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.PluginManagement#getPlugins <em>Plugins</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Plugins</em>' containment reference.
     * @see #getPlugins()
     * @generated
     */
    void setPlugins(PluginsType3 value);

} // PluginManagement
