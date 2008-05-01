/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.lucene;

import java.util.List;

import org.devzuz.q.maven.search.AbstractArtifactSearchProvider;
import org.devzuz.q.maven.search.IArtifactInfo;
import org.devzuz.q.maven.search.ISearchCriteria;

/**
 * A search provider implementation that searches a raw lucene index.
 * 
 * @author Mike Poindexter
 */
public class LuceneArtifactSearchProvider
    extends AbstractArtifactSearchProvider
{

    public void beginInit()
    {
        LuceneSearchPlugin.getLuceneService().fetchIndexes();

    }

    public List<IArtifactInfo> find( ISearchCriteria searchCriteria )
    {
        return LuceneSearchPlugin.getLuceneService().search( searchCriteria );
    }

    public List<IArtifactInfo> findAll()
    {
        return LuceneSearchPlugin.getLuceneService().searchAll();
    }

    public boolean isInitComplete()
    {
        return true;
    }

}
