/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.devzuz.q.maven.embedder.internal.EclipseMaven;
import org.devzuz.q.maven.embedder.internal.TraceOption;
import org.devzuz.q.maven.embedder.log.EclipseLogger;
import org.devzuz.q.maven.embedder.log.Logger;
import org.eclipse.core.internal.runtime.Log;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class MavenCoreActivator implements BundleActivator
{

    // The plug-in ID
    public static final String PLUGIN_ID = "org.devzuz.q.maven.core";

    /**
     * Prefix string for all trace options
     */
    public static final String PLUGIN_GLOBAL_TRACE_OPTION = PLUGIN_ID + "/debug";

    /**
     * Formatter for times displayed in traces.
     */
    private static DateFormat TIME_FORMAT;

    // The shared instance
    private static MavenCoreActivator plugin;

    private EclipseMaven mavenInstance;

    private MavenProjectManager projectManager;

    private MavenPreferenceManager preferenceManager;

    private Logger logger;

    /**
     * The constructor
     */
    public MavenCoreActivator()
    {
        plugin = this;
    }

    public void start( BundleContext context ) throws Exception
    {
        logger = new EclipseLogger( PLUGIN_ID, new Log( context.getBundle() ) );

        // Initialize the maven preference manager
        preferenceManager =
            new MavenPreferenceManager( new ScopedPreferenceStore( new InstanceScope(),
                                                                   context.getBundle().getSymbolicName() ) );

        // Initialize the maven instance
        mavenInstance = new EclipseMaven();
        mavenInstance.start();

        // Initialize the maven workspace projects manager
        projectManager = new MavenProjectManager( ResourcesPlugin.getWorkspace() );
    }

    public void stop( BundleContext context ) throws Exception
    {
        plugin = null;
        mavenInstance.stop();
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static MavenCoreActivator getDefault()
    {
        return plugin;
    }

    public IMaven getMavenInstance()
    {
        return mavenInstance;
    }

    public MavenProjectManager getMavenProjectManager()
    {
        return projectManager;
    }

    public MavenPreferenceManager getMavenPreferenceManager()
    {
        return preferenceManager;
    }

    void setMavenInstance( EclipseMaven mavenInstance )
    {
        this.mavenInstance = mavenInstance;
    }

    public static Logger getLogger()
    {
        return getDefault().logger;
    }

    /**
     * Sends trace messages to stdout if the specified trace option is enabled.
     * 
     * This is intended only for developing and debugging. The use of variable number of parameters avoids the cost of
     * building the message when the debug option is not enabled.
     * 
     * @param traceOption
     *            the trace option enabling the message trace.
     * @param messageParts
     *            a variable number of objects with each part of the message to display.
     */
    public static void trace( TraceOption traceOption, Object... messageParts )
    {
        if ( !Platform.inDebugMode() )
        {
            return;
        }
        String globalTraceValue = Platform.getDebugOption( PLUGIN_GLOBAL_TRACE_OPTION );
        if ( null == globalTraceValue || !globalTraceValue.equals( "true" ) )
        {
            return;
        }
        String value = Platform.getDebugOption( traceOption.getValue() );
        if ( null != value && value.equals( "true" ) )
        {
            String timingValue = Platform.getDebugOption( PLUGIN_GLOBAL_TRACE_OPTION + "/timing" );
            if ( null != timingValue && timingValue.equals( "true" ) )
            {
                if ( null == TIME_FORMAT )
                {
                    TIME_FORMAT = new SimpleDateFormat( "HH:mm:ss.SSS  " );
                }
                System.out.print( TIME_FORMAT.format( new Date() ) );
            }
            System.out.print( "[" );
            System.out.print( traceOption );
            System.out.print( "] " );
            for ( int i = 0; i < messageParts.length; i++ )
            {
                System.out.print( messageParts[i] );
            }
            System.out.println();
        }
    }
}
