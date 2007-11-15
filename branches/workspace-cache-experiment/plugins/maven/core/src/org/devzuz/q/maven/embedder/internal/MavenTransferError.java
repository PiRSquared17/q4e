/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import org.apache.maven.wagon.events.TransferEvent;
import org.devzuz.q.maven.embedder.IMavenTransferError;

public class MavenTransferError extends AbstractMavenTransferEvent implements IMavenTransferError
{

    public MavenTransferError( TransferEvent event )
    {
        super( event );
    }

    @Override
    public String getDescriptionText()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTypeText()
    {
        return Messages.MavenTransferError_Type;
    }

}
