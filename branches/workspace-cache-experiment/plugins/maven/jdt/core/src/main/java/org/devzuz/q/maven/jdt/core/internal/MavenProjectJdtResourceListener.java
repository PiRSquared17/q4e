/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.internal;

import org.devzuz.q.maven.jdt.core.MavenClasspathHelper;
import org.devzuz.q.maven.jdt.core.MavenJdtCoreActivator;
import org.devzuz.q.maven.jdt.core.MavenNatureHelper;
import org.devzuz.q.maven.jdt.core.classpath.container.MavenClasspathContainer;
import org.devzuz.q.maven.jdt.core.classpath.container.UpdateClasspathJob;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

public class MavenProjectJdtResourceListener implements IResourceChangeListener
{
    private static String POM_XML = "pom.xml";

    public MavenProjectJdtResourceListener()
    {
    }

    public void resourceChanged( IResourceChangeEvent event )
    {
        if ( event.getType() == IResourceChangeEvent.POST_CHANGE )
        {
            for ( IResourceDelta delta : event.getDelta().getAffectedChildren() )
            {
                // for open and close events
                if ( delta.getFlags() == IResourceDelta.OPEN && delta.getResource() instanceof IProject )
                {
                    IProject project = (IProject) delta.getResource();
                    if ( MavenJdtCoreActivator.getDefault().isDebugging() )
                    {
                        MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Received open event for ",
                                                     project );
                    }
                    // check if its an "open" event and the project is managed by maven.
                    if ( project.isOpen() && isMavenManagedProject( project ) )
                    {
                        updateProjectsClasspathWithProject( ResourcesPlugin.getWorkspace().getRoot().getProjects(),
                                                            project );
                    }
                    else
                    {
                        if ( MavenJdtCoreActivator.getDefault().isDebugging() )
                        {
                            MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER,
                                                         "Skipping because it has no pom.xml: " + project );
                        }
                    }
                }
            }
        }
        else if ( ( event.getType() == IResourceChangeEvent.PRE_CLOSE )
                        || ( event.getType() == IResourceChangeEvent.PRE_DELETE ) )
        {
            /* IResourceChangeEvent documents that an IProject is always returned */
            IProject project = (IProject) event.getResource();

            if ( MavenJdtCoreActivator.getDefault().isDebugging() )
            {
                MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Received close/delete event for ",
                                             project );
            }

            if ( project.isOpen() && isMavenManagedProject( project.getProject() ) )
            {
                updateProjectsClasspathWithProject( ResourcesPlugin.getWorkspace().getRoot().getProjects(),
                                                    project.getProject() );
            }
            else
            {
                if ( MavenJdtCoreActivator.getDefault().isDebugging() )
                {
                    MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER,
                                                 "Skipping because it has no pom.xml: " + project );
                }
            }
        }
    }

    public static void updateProjectsClasspathWithProject( IProject[] iprojects, IProject iresProject )
    {
        for ( IProject iproject : iprojects )
        {
            if ( iproject.isOpen() && !( iresProject.equals( iproject.getProject() ) ) )
            {
                boolean isMavenNatureEnabled = false;
                try
                {
                    isMavenNatureEnabled = MavenNatureHelper.hasMavenNature( iproject );
                }
                catch ( CoreException e )
                {
                    MavenJdtCoreActivator.getLogger().log( "Could not read nature for project: " + iproject, e );
                }
                if ( isMavenNatureEnabled )
                {
                    try
                    {
                        /* Get current entries. */
                        IClasspathEntry[] classPathEntries = getCurrentClasspathEntries( iproject );
                        for ( IClasspathEntry classPathEntry : classPathEntries )
                        {
                            if ( classpathEqualsProject( classPathEntry, iresProject ) )
                            {
                                if ( MavenJdtCoreActivator.getDefault().isDebugging() )
                                {
                                    MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER,
                                                                 "Scheduling update for ", iproject );
                                }
                                new UpdateClasspathJob( iproject ).schedule();
                                break;
                            }
                        }
                    }
                    catch ( JavaModelException e )
                    {
                        // Could not get project's classpath, ignore and try next.
                        MavenJdtCoreActivator.getLogger().log( "Could not read classpath for project: " + iproject, e );
                    }
                }
            }
        }
    }

    /**
     * Returns the classpath entries managed by the current {@link MavenClasspathContainer} associated with the project.
     * 
     * @param iproject
     *            the maven-enabled project.
     * @return the classpath entries managed by the {@link MavenClasspathContainer}
     * @throws JavaModelException
     *             if a problem reading the classpath containers is detected.
     */
    private static IClasspathEntry[] getCurrentClasspathEntries( IProject iproject ) throws JavaModelException
    {
        /* Assume it is a java project. */
        IJavaProject javaProject = JavaCore.create( iproject );
        /* Find maven classpath container */
        IClasspathContainer classpathContainer =
            JavaCore.getClasspathContainer( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER_PATH, javaProject );
        /* Get current entries. */

        return classpathContainer.getClasspathEntries();
    }

    private static boolean classpathEqualsProject( IClasspathEntry classpath, IProject project )
    {
        if ( classpath.getEntryKind() == IClasspathEntry.CPE_PROJECT )
        {
            return classpath.getPath().lastSegment().equals( project.getName() );
        }
        else if ( classpath.getEntryKind() == IClasspathEntry.CPE_LIBRARY )
        {
            return MavenClasspathHelper.getMavenProjectInfo( classpath ).equals( MavenClasspathHelper.getMavenProjectInfo( project ) );
        }

        return false;
    }

    /**
     * Checks if the project is a maven project managed by q4e. This is used to check if maven classpaths need to be
     * recalculated when the project is opened/closed/deleted.
     * 
     * @param project
     *            the project.
     * @return <code>true</code> if the project is managed by q4e.
     */
    private boolean isMavenManagedProject( IProject project )
    {
        // TODO: Check if the project has the q4e maven nature??: MavenNatureHelper.hasMavenNature( project );
        return project.getFile( POM_XML ).exists();
    }
}
