/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ConfigurationTreeContentProvider
    implements ITreeContentProvider
{    
    private Map<Object, Object> childParentMap;
    
    private DomContainer domContainer;
    
    public ConfigurationTreeContentProvider( DomContainer dom )
    {
        setDomContainer( dom );
    }
    
    public DomContainer getDomContainer()
    {
        return domContainer;
    }

    @SuppressWarnings("unchecked")
    public void setDomContainer( DomContainer container )
    {
        this.domContainer = container;
        
        if( childParentMap == null )
        {
            childParentMap = new LinkedHashMap<Object, Object>();
        }
        else
        {
            childParentMap.clear();
        }
        
        setDomObjects( domContainer );
    }
    
    private void setDomObjects( DomContainer domContainer )
    {       
        Xpp3Dom dom = domContainer.getDom();
        if( dom != null )
        {
            childParentMap.put( dom, domContainer );
            addChildrenToMap( childParentMap , dom );
        }
    }
    
    /*
     * private void addChildrenToMap( Map<Object, Object> map , Xpp3Dom dom ) { if( dom.getChildCount() > 0 ) { for(
     * Xpp3Dom child : dom.getChildren() ) { if( child.getChildCount() > 0 ) { map.put( child , dom ); addChildrenToMap(
     * map , child ); } } } }
     */
    private void addChildrenToMap( Map<Object, Object> map, Xpp3Dom dom )
    {
        for ( Xpp3Dom child : dom.getChildren() )
        {
            System.out.println("addChildrenToMap " + dom.getValue() + " " + dom.getName() );
            map.put( child, dom );
            addChildrenToMap( map, child );
        }
    }

    @SuppressWarnings("unchecked")
    public Object[] getChildren( Object parentElement )
    {
        List< Object > children = new ArrayList< Object >();
        
        if( childParentMap.containsValue( parentElement ) )
        {
            for( Object object :  childParentMap.entrySet() )
            {
                Map.Entry< Object , Object > entry = ( Map.Entry< Object , Object > ) object;
                if( entry.getValue() == parentElement )
                {
                    children.add( entry.getKey() );
                }
            }
        }
        
        return children.toArray();
    }

    public Object getParent( Object element )
    {   
        return childParentMap.get( element );
    }

    public boolean hasChildren( Object element )
    {
        return childParentMap.containsValue( element );
    }

    public Object[] getElements( Object inputElement )
    {
        return getChildren( inputElement );
    }

    public void dispose()
    {
        // TODO Auto-generated method stub
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
    {
        // TODO Auto-generated method stub
    }
}
