/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.views;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenExecutionParameter;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.preferences.MavenPreferenceManager;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

// TODO: Remove before 0.4.0. Could not find any use for this class before 0.3.0.
@Deprecated
public class ProgressBarDialog implements IRunnableWithProgress
{
    private int TOTAL_TIME;

    private static final int INCREMENT = 500;

    private IMavenProject project;

    private String goal;

    private Properties properties;

    public ProgressBarDialog( IMavenProject project, String goal )
    {
        this.project = project;
        this.goal = goal;
        this.properties = null;
        // TOTAL_TIME = project.getArtifactId().length();
    }

    public ProgressBarDialog( IMavenProject project, String goal, Properties properties )
    {
        this.project = project;
        this.goal = goal;
        this.properties = properties;
        // TOTAL_TIME = project.getArtifactId().length();
    }

    public void run( IProgressMonitor monitor ) throws InvocationTargetException, InterruptedException
    {
        // getTotalTime();
        monitor.beginTask( "Running operation", TOTAL_TIME );
        for ( int total = 0; total < TOTAL_TIME && !monitor.isCanceled(); total += INCREMENT )
        {
            // System.out.println("TOTAL TIME = " + list.length + "/" + eventTableViewer.getLabelProvider());
            Thread.sleep( INCREMENT );
            monitor.worked( INCREMENT );
            try
            {
                MavenExecutionParameter parameters =
                    MavenExecutionParameter.newDefaultMavenExecutionParameter( properties );
                parameters.setRecursive( MavenPreferenceManager.getMavenPreferenceManager().isRecursive() );
                if ( goal.equals( "deploy" ) )
                {
                    MavenManager.getMaven().deploy( project, parameters );
                }
                else if ( goal.equals( "install" ) )
                {
                    MavenManager.getMaven().install( project, parameters );
                }
                else
                {
                    MavenManager.getMaven().scheduleGoal( project, goal, parameters );
                }
            }
            catch ( CoreException e )
            {
                // TODO Auto-generated catch block
                MavenUiActivator.getLogger().error( "In ProgressBarDialog.run() - " + e.toString() );
            }
        }

        monitor.done();
        if ( monitor.isCanceled() )
            throw new InterruptedException( "The operation was cancelled" );
    }
    // TODO : Not used, don't know why.
    /*
     * private void getTotalTime() { // TODO Auto-generated method stub TOTAL_TIME = project.getArtifactId().length(); }
     */
}