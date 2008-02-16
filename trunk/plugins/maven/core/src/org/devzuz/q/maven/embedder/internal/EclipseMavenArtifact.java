/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.versioning.VersionRange;
import org.devzuz.q.maven.embedder.IMavenArtifact;

public class EclipseMavenArtifact
    implements IMavenArtifact
{

    private String artifactId;

    private String groupId;

    private String id;

    private String version;

    private String scope;

    private String type;

    private String classifier;

    private boolean addedToClasspath;

    private File file;

    private IMavenArtifact parent = null;

    private Set<IMavenArtifact> children = new HashSet<IMavenArtifact>();

    public EclipseMavenArtifact()
    {
    }

    public EclipseMavenArtifact( Artifact artifact )
    {
        fromMaven( artifact );
    }

    public void fromMaven( Artifact artifact )
    {
        setArtifactId( artifact.getArtifactId() );
        setGroupId( artifact.getGroupId() );
        setId( artifact.getId() );
        setVersion( artifact.getVersion() );
        setFile( artifact.getFile() );
        setScope( artifact.getScope() );
        setType( artifact.getType() );
        setClassifier( artifact.getArtifactHandler().getClassifier() );
        setAddedToClasspath( artifact.getArtifactHandler().isAddedToClasspath() );
    }

    public Artifact toMaven()
    {
        Artifact artifact =
            new DefaultArtifact( getGroupId(), getArtifactId(), VersionRange.createFromVersion( getVersion() ),
                                 getScope(), getType(), getClassifier(), null, false );
        return artifact;
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public void setArtifactId( String artifactId )
    {
        this.artifactId = artifactId;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId( String groupId )
    {
        this.groupId = groupId;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    public File getFile()
    {
        return file;
    }

    public void setFile( File file )
    {
        this.file = file;
    }

    public IMavenArtifact getParent()
    {
        return parent;
    }

    public void setParent( IMavenArtifact parent )
    {
        this.parent = parent;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope( String scope )
    {
        this.scope = scope;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public String getClassifier()
    {
        return classifier;
    }

    public void setClassifier( String classifier )
    {
        this.classifier = classifier;
    }

    public boolean isAddedToClasspath()
    {
        return addedToClasspath;
    }

    public void setAddedToClasspath( boolean addedToClasspath )
    {
        this.addedToClasspath = addedToClasspath;
    }

    public Set<IMavenArtifact> getChildren()
    {
        return children;
    }

    public void setChildren( Set<IMavenArtifact> children )
    {
        this.children = children;
    }

    public void addChild( IMavenArtifact child )
    {
        children.add( child );
        child.setParent( this );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof IMavenArtifact ) )
        {
            return false;
        }
        if ( this == obj )
        {
            return true;
        }
        IMavenArtifact rhs = (IMavenArtifact) obj;

        return new EqualsBuilder().
            appendSuper(super.equals(obj)).
            append(getGroupId(), rhs.getGroupId()).
            append(getArtifactId(), rhs.getArtifactId()).
            append(getVersion(), rhs.getVersion()).
            append(getType(), rhs.getType()).
            append(getClassifier(), rhs.getClassifier()).
            isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder( 17, 37 ).
            append( getGroupId() ).
            append( getArtifactId() ).
            append( getVersion() ).
            append( getType() ).
            append( getClassifier() ).
            toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder( this ).
            append( "groupId", getGroupId() ).
            append( "artifactId", getArtifactId() ).
            append( "version", getVersion() ).
            append( "type", getType() ).
            append( "classifier", getClassifier() ).
            toString();
    }
}
