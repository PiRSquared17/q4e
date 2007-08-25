/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.views;

import java.util.Observable;

import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.devzuz.q.maven.embedder.IMavenEvent;
import org.devzuz.q.maven.embedder.IMavenListener;

/**
 * The maven event store keeps a reference to a limited number of maven events.
 * 
 * The store is used as the data model for the Maven Event View.
 * 
 * @author carlossg
 * @author Abel Mui�o <amuino@gmail.com>
 */
public class MavenEventStore extends Observable implements IMavenListener
{

    /* TODO allow user customization */
    private static final int BUFFER_SIZE = 50000;

    private CircularFifoBuffer events = new CircularFifoBuffer( BUFFER_SIZE );

    public void handleEvent( IMavenEvent event )
    {
        events.add( event );
        this.notifyListeners();
    }

    public void dispose()
    {
        events.clear();
        this.notifyListeners();
    }

    /**
     * Obtains every maven event in the store.
     * 
     * @return a copy of the stored maven events.
     */
    @SuppressWarnings( "unchecked" )
    public IMavenEvent[] getEvents()
    {
        return (IMavenEvent[]) events.toArray( new IMavenEvent[events.size()] );
    }

    /**
     * Notifies every observer that a new event has arrived.
     */
    public void notifyListeners()
    {
        this.setChanged();
        this.notifyObservers();
        // changed state will be automatically reset by notifyObservers
    }
}
