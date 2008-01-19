/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.actions;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenExecutionParameter;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.views.MavenEventView;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public abstract class AbstractMavenAction
    implements IObjectActionDelegate
{

    /**
     * @deprecated use {@link IMavenProject#POM_FILENAME}
     */
    @Deprecated
    public static final String POM_XML = IMavenProject.POM_FILENAME;

    private IStructuredSelection selection;

    public AbstractMavenAction()
    {
        super();
    }

    public void setActivePart( IAction action, IWorkbenchPart targetPart )
    {

    }

    public IMavenProject getMavenProject()
    {
        Object obj = selection.iterator().next();
        if ( obj instanceof IAdaptable )
        {
            IMavenProject mavenProject = (IMavenProject) ( (IAdaptable) obj ).getAdapter( IMavenProject.class );
            return mavenProject;
        }
        else
            return null;
    }

    public void selectionChanged( IAction action, ISelection selection )
    {
        if ( selection instanceof IStructuredSelection )
        {
            this.selection = (IStructuredSelection) selection;
        }
    }

    public void run( IAction action )
    {
        IWorkbenchWindow ww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        IWorkbenchPage wp = ww.getActivePage();

        try
        {
            wp.showView( MavenEventView.VIEW_ID );
        }
        catch ( PartInitException e )
        {
            MavenUiActivator.getLogger().log( "Unable to open Maven Event View", e );
            return;
        }

        try
        {
            runInternal( action );
        }
        catch ( CoreException e )
        {
            MavenUiActivator.getLogger().log( e );
        }
    }

    protected void runInternal( IAction action ) throws CoreException
    {
    }

    protected IStructuredSelection getSelection()
    {
        return selection;
    }

    /**
     * Creates default maven parameters including information about recursion from the preferences.
     * 
     * @return the maven parameters, configured for recursion as specified on maven preferences.
     */
    protected MavenExecutionParameter getDefaultParameters()
    {
        MavenExecutionParameter parameters = MavenExecutionParameter.newDefaultMavenExecutionParameter();
        parameters.setRecursive( MavenManager.getMavenPreferenceManager().isRecursive() );
        return parameters;
    }
}