/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.projectimport;

import java.io.File;
import java.util.Collection;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.jdt.ui.Activator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class ProjectScannerJob extends Job
{
    private File directoryToScan;
    private Collection<IMavenProject> projects;
    
    public ProjectScannerJob( String name )
    {
        super(name);
    }
    
    public ProjectScannerJob( String name, File file )
    {
        super(name);
        directoryToScan = file;
    }
    
    public void setDirectory( File file )
    {
        directoryToScan = file;
    }

    public Collection<IMavenProject> getProjects()
    {
        return projects;
    }
    
    @Override
    protected IStatus run(IProgressMonitor monitor)
    {
        ProjectScanner scanner = new ProjectScanner();
        
        monitor.setTaskName( "Scanning for Maven 2 Projects ...");
        
        try
        {
            projects = scanner.scanFolder( directoryToScan, monitor );
        }
        catch ( InterruptedException e )
        {
            return new Status( IStatus.CANCEL, Activator.PLUGIN_ID, "Cancelled" );
        }
        
        return new Status( IStatus.OK, Activator.PLUGIN_ID, "Ok" );
    }
}
