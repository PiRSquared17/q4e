/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.internal;

import org.apache.maven.wagon.events.TransferEvent;
import org.devzuz.q.maven.embedder.IMavenTransferError;
import org.devzuz.q.maven.embedder.Severity;

/**
 * Event fired if there's an error during the download of the file, after a {@link MavenTransferStarted} event. If
 * there's an error before the transfer starts and after a {@link MavenTransferInitated} event, ie. the file is not
 * found, then this event is not fired.
 */
public class MavenTransferError extends AbstractMavenTransferEvent implements IMavenTransferError
{

    public MavenTransferError( TransferEvent event )
    {
        super( event );
    }

    @Override
    public String getDescriptionText()
    {
        return getDescriptionText( Messages.MavenTransferError_Description );
    }

    @Override
    public String getTypeText()
    {
        return Messages.MavenTransferError_Type;
    }

    @Override
    public Severity getSeverity()
    {
        return Severity.error;
    }
}
