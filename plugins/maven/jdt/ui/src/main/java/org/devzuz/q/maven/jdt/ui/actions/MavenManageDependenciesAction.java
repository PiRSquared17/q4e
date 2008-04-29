/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.actions;

import org.devzuz.q.maven.jdt.core.MavenNatureHelper;
import org.devzuz.q.maven.jdt.ui.MavenJdtUiActivator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionDelegate;

public class MavenManageDependenciesAction extends AbstractMavenAction implements IActionDelegate
{

    @Override
    /**
     * Update checked status based on the currently selected project.
     */
    public void selectionChanged( IAction action, ISelection selection )
    {
        super.selectionChanged( action, selection );
        if ( getProjects().size() == 1 )
        {
            IProject project = getProjects().iterator().next();
            if ( project.isOpen() )
            {
                try
                {
                    if ( MavenNatureHelper.hasMavenNature( project ) )
                    {
                        action.setChecked( true );
                    }
                    else
                    {
                        action.setChecked( false );
                    }
                }
                catch ( CoreException e )
                {
                    MavenJdtUiActivator.getLogger().log( "Unable to check nature on project: " + project, e );
                }
            }
            else
            {
                action.setChecked( false );
            }
        }
    }

    @Override
    protected void runInternal( IAction action ) throws CoreException
    {
        for ( IProject project : getProjects() )
        {
            if ( project.isOpen() )
            {
                if ( MavenNatureHelper.hasMavenNature( project ) )
                {
                    MavenNatureHelper.removeNature( project );
                    action.setChecked( false );
                }
                else
                {
                    MavenNatureHelper.addNature( project );
                    action.setChecked( true );
                }
            }
        }
    }
}
