/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.exception.handler.internal;

import java.util.List;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.embedder.exception.MarkerInfo;
import org.devzuz.q.maven.embedder.exception.handler.IMavenExceptionHandlerChain;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;

public class XmlPullParserExceptionHandler
    extends DefaultMavenExceptionHandler
{

    public void handle( IProject project, Throwable ex, List<MarkerInfo> markers, IMavenExceptionHandlerChain chain )
    {
        XmlPullParserException e = (XmlPullParserException) ex;
        MarkerInfo markerInfo =
            new MarkerInfo( e.getMessage(), IMarker.SEVERITY_ERROR, e.getLineNumber(), e.getColumnNumber(),
                            e.getColumnNumber() + 1 );
        markers.add( markerInfo );
    }

}