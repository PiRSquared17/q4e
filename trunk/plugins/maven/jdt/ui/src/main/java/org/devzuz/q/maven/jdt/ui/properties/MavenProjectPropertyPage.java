/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.jdt.ui.properties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.lifecycle.MojoBindingUtils;
import org.apache.maven.lifecycle.model.MojoBinding;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.core.builder.MavenIncrementalBuilder;
import org.devzuz.q.maven.jdt.ui.Messages;
import org.devzuz.q.maven.project.properties.MavenProjectPropertiesManager;
import org.devzuz.q.maven.ui.customcomponents.MavenProfileUi;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.PropertyPage;

/**
 * This class provides a property page for displaying the Maven JDT preferences managed by the MavenProjectPropertiesManager.
 * 
 * @author staticsnow@gmail.com
 */
public class MavenProjectPropertyPage
    extends PropertyPage
{

    final Set<String> newExcludedResourceGoals = new HashSet<String>();

    final Set<String> newExcludedTestResourceGoals = new HashSet<String>();

    private MavenProfileUi profileUi;

    @Override
    protected Control createContents( Composite parent )
    {
        try
        {
            final Composite composite = new Composite( parent, SWT.NONE );
            GridLayout rowLayout = new GridLayout( 1, true );
            rowLayout.verticalSpacing = 20;
            composite.setLayout( rowLayout );
            Set<String> excludedResourceGoals =
                MavenProjectPropertiesManager.getInstance().getResourceExcludedGoals( getProject() );
            buildGoalTableRow( composite, Messages.MavenProjectPropertyPage_ExecuteOnResource,
                               newExcludedResourceGoals, excludedResourceGoals, //$NON-NLS-1$
                               MavenIncrementalBuilder.RESOURCES_GOAL );
            Set<String> excludedTestResourceGoals =
                MavenProjectPropertiesManager.getInstance().getTestResourceExcludedGoals( getProject() );
            buildGoalTableRow( composite, Messages.MavenProjectPropertyPage_ExecuteOnTestResource,
                               newExcludedTestResourceGoals, //$NON-NLS-1$
                               excludedTestResourceGoals, MavenIncrementalBuilder.TEST_RESOURCES_GOAL );

            buildProfileRow( composite );

            return composite;
        }
        catch ( CoreException e )
        {
            MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( getProject(), e );
            return null;
        }
    }

    @Override
    public boolean performOk()
    {
        performApply();
        return super.performOk();
    }
    
    @Override
    protected void performApply()
    {
        try
        {
            MavenProjectPropertiesManager.getInstance().setResourceExcludedGoals( getProject(), newExcludedResourceGoals );
            MavenProjectPropertiesManager.getInstance().setTestResourceExcludedGoals( getProject(), newExcludedTestResourceGoals );
            MavenProjectPropertiesManager.getInstance().setActiveProfiles(
                                                                           getProject(),
                                                                           new HashSet<String>( profileUi.getProfiles() ) );
        }
        catch ( CoreException e )
        {
            MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( getProject(), e );
        }
    }

    private void buildProfileRow( Composite parent )
    {
        Composite composite = new Composite( parent, SWT.NONE );
        GridLayout layout = new GridLayout( 2, false );
        GridData layoutData = new GridData( GridData.FILL_BOTH );
        layoutData.grabExcessHorizontalSpace = true;
        layoutData.grabExcessVerticalSpace = true;
        composite.setLayout( layout );
        composite.setLayoutData( layoutData );

        layoutData = new GridData( GridData.FILL, GridData.FILL, true, false, 2, 1 );
        Label label = new Label( composite, SWT.READ_ONLY );
        label.setText( Messages.MavenProfilePreferencePage_Description );
        label.setLayoutData( layoutData );

        List<String> activeProfiles =
            new ArrayList<String>( MavenProjectPropertiesManager.getInstance().getActiveProfiles( getProject() ) );
        profileUi = new MavenProfileUi( composite, activeProfiles, 2 );
        profileUi.draw();
    }

    private void buildGoalTableRow( Composite parent, String label, final Set<String> managedSet,
                                    Set<String> existingExcludes, String phase )
        throws CoreException
    {
        Composite row = new Composite( parent, SWT.NONE );
        GridLayout rowLayout = new GridLayout( 1, true );
        row.setLayout( rowLayout );
        row.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );

        Label resourcesLabel = new Label( row, SWT.READ_ONLY );
        resourcesLabel.setText( label );

        int style =
            SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.HIDE_SELECTION | SWT.CHECK;

        CheckboxTableViewer resourceGoalsCheckTableViewer = CheckboxTableViewer.newCheckList( row, style );
        resourceGoalsCheckTableViewer.getTable().setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
        managedSet.clear();
        managedSet.addAll( existingExcludes );
        List<MojoBinding> allBindings = MavenManager.getMaven().getGoalsForPhase( getMavenProject(), phase, true );
        Set<String> allResourceGoals = new LinkedHashSet<String>();
        for ( MojoBinding mojoBinding : allBindings )
        {
            allResourceGoals.add( MojoBindingUtils.createMojoBindingKey( mojoBinding, true ) );
        }

        for ( String goal : allResourceGoals )
        {
            resourceGoalsCheckTableViewer.add( goal );
            resourceGoalsCheckTableViewer.setChecked( goal, !existingExcludes.contains( goal ) );
        }
        resourceGoalsCheckTableViewer.addCheckStateListener( new ICheckStateListener()
        {
            public void checkStateChanged( CheckStateChangedEvent event )
            {
                if ( event.getChecked() )
                {
                    managedSet.remove( event.getElement() );
                }
                else
                {
                    managedSet.add( (String) event.getElement() );
                }

            }
        } );
    }

    private IProject getProject()
    {
        return (IProject) getElement().getAdapter( IProject.class );
    }

    private IMavenProject getMavenProject()
    {
        try
        {
            return MavenManager.getMavenProjectManager().getMavenProject( getProject(), true );
        }
        catch ( CoreException e )
        {
            /* project doesn't build */
            MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( getProject(), e );
            return null;
        }
    }

}
