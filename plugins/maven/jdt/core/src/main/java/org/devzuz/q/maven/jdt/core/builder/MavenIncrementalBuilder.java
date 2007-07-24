/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.builder;

import java.util.Map;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.jdt.core.Activator;
import org.devzuz.q.maven.jdt.core.classpath.container.MavenClasspathContainer;
import org.devzuz.q.maven.jdt.core.classpath.container.UpdateClasspathJob;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * Maven builder that will update the classpath container when pom changes
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public class MavenIncrementalBuilder
    extends IncrementalProjectBuilder
{

    public static final String MAVEN_INCREMENTAL_BUILDER_ID = Activator.PLUGIN_ID + ".mavenIncrementalBuilder"; //$NON-NLS-1$

    @Override
    protected IProject[] build( int kind, Map args, IProgressMonitor monitor )
        throws CoreException
    {
        if ( ( kind == INCREMENTAL_BUILD ) || ( kind == AUTO_BUILD ) )
        {
            IResourceDelta delta = getDelta( getProject() );

            Path pomPath = new Path( IMavenProject.POM_FILENAME );
            IResourceDelta member = delta.findMember( pomPath );
            if ( member != null )
            {
                onPomChange( member.getResource().getProject() );
            }
        }
        else
        {
            onPomChange( getProject() );
        }
        return null;
    }

    private void onPomChange( IProject project )
    {
        /* don't schedule the job or will trigger a infinite loop */
        new UpdateClasspathJob( project ).run( new NullProgressMonitor() );
    }

    private MavenClasspathContainer getClasspathContainer()
    {
        IJavaProject javaProject = JavaCore.create( getProject() );
        if ( javaProject != null )
        {
            try
            {
                IClasspathContainer container = JavaCore
                    .getClasspathContainer( new Path( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER ), javaProject );
                return (MavenClasspathContainer) container;
            }
            catch ( JavaModelException e )
            {
                Activator.getLogger().log( e );
            }
        }
        return null;
    }

}
