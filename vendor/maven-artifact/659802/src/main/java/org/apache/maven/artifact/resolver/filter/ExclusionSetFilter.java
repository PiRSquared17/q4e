package org.apache.maven.artifact.resolver.filter;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.artifact.Artifact;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 * @version $Id: ExclusionSetFilter.java 658725 2008-05-21 15:23:31Z jvanzyl $
 */
public class ExclusionSetFilter
    implements ArtifactFilter
{
    private Set<String> excludes;

    public ExclusionSetFilter( String[] excludes )
    {
        this.excludes = new HashSet<String>( Arrays.asList( excludes ) );
    }

    public ExclusionSetFilter( Set<String> excludes )
    {
        this.excludes = excludes;
    }

    public boolean include( Artifact artifact )
    {
        return !excludes.contains(artifact.getArtifactId());
    }
}
