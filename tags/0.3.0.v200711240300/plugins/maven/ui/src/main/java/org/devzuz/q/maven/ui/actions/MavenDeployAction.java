/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.actions;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Shell;

public class MavenDeployAction extends AbstractMavenAction
{

    protected void configureShell( Shell shell )
    {
        // super.configureShell(shell);

        // Set the title bar text
        shell.setText( "Show Progress" );
    }

    @Override
    protected void runInternal( IAction action ) throws CoreException
    {
        IMavenProject project = getMavenProject();
        if ( project != null )
        {
            MavenManager.getMaven().deploy( project, getDefaultParameters() );
        }
    }

}
