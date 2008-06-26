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
 * A representation of the model object '<em><b>Developer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 3.0.0+
 * 
 *         Information about one of the committers on this project.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.devzuz.q.maven.pom.Developer#getId <em>Id</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Developer#getName <em>Name</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Developer#getEmail <em>Email</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Developer#getUrl <em>Url</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Developer#getOrganization <em>Organization</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Developer#getOrganizationUrl <em>Organization Url</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Developer#getRoles <em>Roles</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Developer#getTimezone <em>Timezone</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Developer#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.devzuz.q.maven.pom.PomPackage#getDeveloper()
 * @model extendedMetaData="name='Developer' kind='elementOnly'"
 * @generated
 */
public interface Developer extends EObject
{
    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * The unique ID of the developer in the SCM.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getDeveloper_Id()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='id' namespace='##targetNamespace'"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Developer#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * The full name of the contributor.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getDeveloper_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Developer#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Email</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * The email address of the contributor.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Email</em>' attribute.
     * @see #setEmail(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getDeveloper_Email()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='email' namespace='##targetNamespace'"
     * @generated
     */
    String getEmail();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Developer#getEmail <em>Email</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Email</em>' attribute.
     * @see #getEmail()
     * @generated
     */
    void setEmail(String value);

    /**
     * Returns the value of the '<em><b>Url</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * The URL for the homepage of the contributor.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Url</em>' attribute.
     * @see #setUrl(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getDeveloper_Url()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='url' namespace='##targetNamespace'"
     * @generated
     */
    String getUrl();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Developer#getUrl <em>Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Url</em>' attribute.
     * @see #getUrl()
     * @generated
     */
    void setUrl(String value);

    /**
     * Returns the value of the '<em><b>Organization</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * The organization to which the contributor belongs.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Organization</em>' attribute.
     * @see #setOrganization(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getDeveloper_Organization()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='organization' namespace='##targetNamespace'"
     * @generated
     */
    String getOrganization();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Developer#getOrganization <em>Organization</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Organization</em>' attribute.
     * @see #getOrganization()
     * @generated
     */
    void setOrganization(String value);

    /**
     * Returns the value of the '<em><b>Organization Url</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * The URL of the organization.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Organization Url</em>' attribute.
     * @see #setOrganizationUrl(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getDeveloper_OrganizationUrl()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='organizationUrl' namespace='##targetNamespace'"
     * @generated
     */
    String getOrganizationUrl();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Developer#getOrganizationUrl <em>Organization Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Organization Url</em>' attribute.
     * @see #getOrganizationUrl()
     * @generated
     */
    void setOrganizationUrl(String value);

    /**
     * Returns the value of the '<em><b>Roles</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * 
     *             The roles the contributor plays in the project.  Each role is
     *             described by a &lt;code&gt;role&lt;/code&gt; element, the body of which is a
     *             role name. This can also be used to describe the contribution.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Roles</em>' containment reference.
     * @see #isSetRoles()
     * @see #unsetRoles()
     * @see #setRoles(RolesType1)
     * @see org.devzuz.q.maven.pom.PomPackage#getDeveloper_Roles()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='roles' namespace='##targetNamespace'"
     * @generated
     */
    RolesType1 getRoles();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Developer#getRoles <em>Roles</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Roles</em>' containment reference.
     * @see #isSetRoles()
     * @see #unsetRoles()
     * @see #getRoles()
     * @generated
     */
    void setRoles(RolesType1 value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Developer#getRoles <em>Roles</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetRoles()
     * @see #getRoles()
     * @see #setRoles(RolesType1)
     * @generated
     */
    void unsetRoles();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Developer#getRoles <em>Roles</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Roles</em>' containment reference is set.
     * @see #unsetRoles()
     * @see #getRoles()
     * @see #setRoles(RolesType1)
     * @generated
     */
    boolean isSetRoles();

    /**
     * Returns the value of the '<em><b>Timezone</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * 
     *             The timezone the contributor is in. This is a number in the range -11 to 12.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Timezone</em>' attribute.
     * @see #setTimezone(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getDeveloper_Timezone()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='timezone' namespace='##targetNamespace'"
     * @generated
     */
    String getTimezone();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Developer#getTimezone <em>Timezone</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Timezone</em>' attribute.
     * @see #getTimezone()
     * @generated
     */
    void setTimezone(String value);

    /**
     * Returns the value of the '<em><b>Properties</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * 
     *             Properties about the contributor, such as an instant messenger handle.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Properties</em>' containment reference.
     * @see #isSetProperties()
     * @see #unsetProperties()
     * @see #setProperties(PropertiesType3)
     * @see org.devzuz.q.maven.pom.PomPackage#getDeveloper_Properties()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='properties' namespace='##targetNamespace'"
     * @generated
     */
    PropertiesType3 getProperties();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Developer#getProperties <em>Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Properties</em>' containment reference.
     * @see #isSetProperties()
     * @see #unsetProperties()
     * @see #getProperties()
     * @generated
     */
    void setProperties(PropertiesType3 value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Developer#getProperties <em>Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetProperties()
     * @see #getProperties()
     * @see #setProperties(PropertiesType3)
     * @generated
     */
    void unsetProperties();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Developer#getProperties <em>Properties</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Properties</em>' containment reference is set.
     * @see #unsetProperties()
     * @see #getProperties()
     * @see #setProperties(PropertiesType3)
     * @generated
     */
    boolean isSetProperties();

} // Developer
