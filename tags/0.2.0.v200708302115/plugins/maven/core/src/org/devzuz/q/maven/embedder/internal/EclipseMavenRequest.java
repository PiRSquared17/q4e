/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.io.PrintStream;

import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.devzuz.q.maven.embedder.Activator;
import org.devzuz.q.maven.embedder.internal.stream.EclipseMavenErrorEventPropagator;
import org.devzuz.q.maven.embedder.internal.stream.EclipseMavenInfoEventPropagator;
import org.devzuz.q.maven.embedder.internal.stream.MavenThreadPrintStream;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * A EclipseMavenRequest provides the mechanism for scheduling a MavenExecutionRequest through the instance of the
 * EclipseMaven
 * 
 * @author pdodds
 */
public class EclipseMavenRequest extends Job
{

    private EclipseMaven maven;

    private MavenExecutionRequest request;

    public EclipseMavenRequest( String name, EclipseMaven maven, MavenExecutionRequest request )
    {
        super( name );
        this.maven = maven;
        this.request = request;
    }

    @Override
    protected IStatus run( IProgressMonitor monitor )
    {

        // TODO the number should be the number of projects in the reactor * maven phases to execute
        monitor.beginTask( "Maven build", 100 );
        monitor.setTaskName( "Maven " + request.getGoals() );

        // TODO add a listener to maven that will call monitor.worked() for each project or phase completed and poll
        // monitor.isCancelled

        // FileMavenListener listener = new FileMavenListener();
        // maven.addEventListener(listener);

        PrintStream out = System.out;
        PrintStream err = System.err;
        try
        {
            System.setOut( new MavenThreadPrintStream( getThread(),
                                                       new EclipseMavenInfoEventPropagator( maven.getEventPropagator(),
                                                                                            out ), out ) );
            System.setErr( new MavenThreadPrintStream(
                                                       getThread(),
                                                       new EclipseMavenErrorEventPropagator(
                                                                                             maven.getEventPropagator(),
                                                                                             err ), err ) );

            MavenExecutionResult status = this.maven.executeRequest( this.request );
            if ( ( status.getExceptions() != null ) && ( status.getExceptions().size() > 0 ) )
            {
                // TODO somehow store all exceptions and not just the first one
                return new Status( IStatus.ERROR, Activator.PLUGIN_ID, "Errors during Maven execution",
                                   (Exception) status.getExceptions().get( 0 ) );
            }

            return new Status( IStatus.OK, Activator.PLUGIN_ID, "Success" );
        }
        finally
        {
            System.setOut( out );
            System.setErr( err );
            // maven.removeEventListener(listener);
            // listener.dispose();

            monitor.done();
        }
    }

}