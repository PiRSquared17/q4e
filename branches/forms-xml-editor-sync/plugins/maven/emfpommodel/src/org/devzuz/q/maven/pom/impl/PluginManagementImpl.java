/**
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 *
 * $Id$
 */
package org.devzuz.q.maven.pom.impl;

import java.util.Collection;

import org.devzuz.q.maven.pom.Plugin;
import org.devzuz.q.maven.pom.PluginManagement;
import org.devzuz.q.maven.pom.PomPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Plugin Management</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.devzuz.q.maven.pom.impl.PluginManagementImpl#getPlugins <em>Plugins</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class PluginManagementImpl
    extends EObjectImpl
    implements PluginManagement
{
    /**
     * The cached value of the '{@link #getPlugins() <em>Plugins</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getPlugins()
     * @generated
     * @ordered
     */
    protected EList<Plugin> plugins;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected PluginManagementImpl()
    {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass()
    {
        return PomPackage.Literals.PLUGIN_MANAGEMENT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EList<Plugin> getPlugins()
    {
        if ( plugins == null )
        {
            plugins = new EObjectContainmentEList<Plugin>( Plugin.class, this, PomPackage.PLUGIN_MANAGEMENT__PLUGINS );
        }
        return plugins;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove( InternalEObject otherEnd, int featureID, NotificationChain msgs )
    {
        switch ( featureID )
        {
            case PomPackage.PLUGIN_MANAGEMENT__PLUGINS:
                return ( (InternalEList<?>) getPlugins() ).basicRemove( otherEnd, msgs );
        }
        return super.eInverseRemove( otherEnd, featureID, msgs );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet( int featureID, boolean resolve, boolean coreType )
    {
        switch ( featureID )
        {
            case PomPackage.PLUGIN_MANAGEMENT__PLUGINS:
                return getPlugins();
        }
        return super.eGet( featureID, resolve, coreType );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public void eSet( int featureID, Object newValue )
    {
        switch ( featureID )
        {
            case PomPackage.PLUGIN_MANAGEMENT__PLUGINS:
                getPlugins().clear();
                getPlugins().addAll( (Collection<? extends Plugin>) newValue );
                return;
        }
        super.eSet( featureID, newValue );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset( int featureID )
    {
        switch ( featureID )
        {
            case PomPackage.PLUGIN_MANAGEMENT__PLUGINS:
                getPlugins().clear();
                return;
        }
        super.eUnset( featureID );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet( int featureID )
    {
        switch ( featureID )
        {
            case PomPackage.PLUGIN_MANAGEMENT__PLUGINS:
                return plugins != null && !plugins.isEmpty();
        }
        return super.eIsSet( featureID );
    }

} // PluginManagementImpl
