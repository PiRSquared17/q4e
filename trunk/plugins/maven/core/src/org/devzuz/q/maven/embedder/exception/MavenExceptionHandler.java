/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.embedder.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.metadata.ArtifactMetadataRetrievalException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.extension.ExtensionScanningException;
import org.apache.maven.lifecycle.LifecycleExecutionException;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.exception.handler.internal.MavenExceptionHandlerChain;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Handles the Maven exceptions to provide a meaningful message to the user in the best way possible
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public class MavenExceptionHandler
{
    /**
     * Old marker id for backwards compatibility
     */
    private static final String OLD_MARKER_ID = "org.devzuz.q.maven.jdt.core.pomproblemmarker";

    private final Set<Class<? extends Exception>> EXCEPTIONS_TO_EXPAND = new HashSet<Class<? extends Exception>>();

    public MavenExceptionHandler()
    {
        EXCEPTIONS_TO_EXPAND.add( LifecycleExecutionException.class );
        EXCEPTIONS_TO_EXPAND.add( ArtifactMetadataRetrievalException.class );
        EXCEPTIONS_TO_EXPAND.add( ArtifactResolutionException.class );
        EXCEPTIONS_TO_EXPAND.add( ExtensionScanningException.class );
    }

    public void handle( IProject project, Collection<Exception> exceptions )
    {
        List<MarkerInfo> markerInfos = new LinkedList<MarkerInfo>();
        for ( Exception e : exceptions )
        {
            doHandle( project, e, markerInfos );
        }
        markPom( project, markerInfos );
    }

    /**
     * Marks a single error in the pom.xml for the given project. Note that this method removes any other marker in the
     * pom.xml.
     * 
     * @param project the project where pom.xml is contained.
     * @param msg the error message to display in the marker.
     */
    public void error( final IProject project, final String msg )
    {
        error( project, Collections.singletonList( msg ) );
    }

    /**
     * Marks a single warning in the pom.xml for the given project. Note that this method removes any other marker in
     * the pom.xml.
     * 
     * @param project the project where pom.xml is contained.
     * @param msg the warning message to display in the marker.
     */
    public void warning( final IProject project, final String msg )
    {
        warning( project, Collections.singletonList( msg ) );
    }

    /**
     * Marks several errors in the pom.xml for the given project. Note that this method removes any other markers in the
     * pom.xml.
     * 
     * @param project the project where pom.xml is contained.
     * @param msgs the error messages to display in the marker.
     */
    public void error( final IProject project, final List<String> msgs )
    {
        List<MarkerInfo> markerInfos = new ArrayList<MarkerInfo>( msgs.size() );
        for ( String msg : msgs )
        {
            markerInfos.add( new MarkerInfo( msg, IMarker.SEVERITY_ERROR ) );
        }
        markPom( project, markerInfos );
    }

    /**
     * Marks several warnings in the pom.xml for the given project. Note that this method removes any other markers in
     * the pom.xml.
     * 
     * @param project the project where pom.xml is contained.
     * @param msgs the warning messages to display in the marker.
     */
    public void warning( final IProject project, final List<String> msgs )
    {
        List<MarkerInfo> markerInfos = new ArrayList<MarkerInfo>( msgs.size() );
        for ( String msg : msgs )
        {
            markerInfos.add( new MarkerInfo( msg, IMarker.SEVERITY_WARNING ) );
        }
        markPom( project, markerInfos );
    }

    public void handle( IProject project, Throwable e )
    {
        LinkedList<MarkerInfo> markerInfos = new LinkedList<MarkerInfo>();
        doHandle( project, e, markerInfos );
        markPom( project, markerInfos );
    }

    private void doHandle( IProject project, Throwable e, List<MarkerInfo> markers )
    {
        MavenExceptionHandlerChain chain = new MavenExceptionHandlerChain( e );
        chain.doHandle( project, markers );
    }

    private void markPom( final IProject project, final List<MarkerInfo> markerInfos )
    {
        final IFile pom = project.getFile( IMavenProject.POM_FILENAME );

        IWorkspaceRunnable r = new IWorkspaceRunnable()
        {
            public void run( IProgressMonitor monitor )
                throws CoreException
            {
                pom.deleteMarkers( MavenCoreActivator.MARKER_ID, true, IResource.DEPTH_INFINITE );
                /* delete old markers from previous versions */
                pom.deleteMarkers( OLD_MARKER_ID, true, IResource.DEPTH_INFINITE );

                for ( MarkerInfo markerInfo : markerInfos )
                {
                    try
                    {
                        IMarker marker = pom.createMarker( MavenCoreActivator.MARKER_ID );
                        marker.setAttribute( IMarker.MESSAGE, markerInfo.getMessage() );
                        marker.setAttribute( IMarker.SEVERITY, markerInfo.getSeverity() );
                        marker.setAttribute( IMarker.LINE_NUMBER, markerInfo.getLineNumber() );
                        marker.setAttribute( IMarker.CHAR_START, markerInfo.getCharStart() );
                        marker.setAttribute( IMarker.CHAR_END, markerInfo.getCharEnd() );
                    }
                    catch ( CoreException ce )
                    {
                        MavenCoreActivator.getLogger().log( ce );
                    }
                }
            }
        };

        try
        {
            pom.getWorkspace().run( r, null, IWorkspace.AVOID_UPDATE, null );
        }
        catch ( CoreException ce )
        {
            MavenCoreActivator.getLogger().log( ce );
        }
    }

    Throwable getCause( Throwable e )
    {
        Throwable cause = e;

        /* CoreException is special as we can not call getCause and we need to access the status for the cause */
        if ( e instanceof CoreException )
        {
            cause = ( (CoreException) e ).getStatus().getException();
            if ( cause == null )
            {
                return e;
            }
        }

        while ( ( cause.getCause() != null ) && EXCEPTIONS_TO_EXPAND.contains( cause.getClass() ) )
        {
            cause = cause.getCause();
        }

        return cause;
    }
}
