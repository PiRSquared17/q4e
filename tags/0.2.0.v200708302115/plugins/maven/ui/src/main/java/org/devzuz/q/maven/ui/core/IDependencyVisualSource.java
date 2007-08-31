/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.devzuz.q.maven.ui.core;

import java.io.InputStream;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.eclipse.core.runtime.CoreException;

public interface IDependencyVisualSource
{
    public InputStream getGraphData( IMavenProject project ) throws CoreException;
}
