package org.apache.maven.artifact.deployer;

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

import org.apache.maven.artifact.AbstractArtifactComponentTestCase;
import org.apache.maven.artifact.Artifact;

import java.io.File;

/**
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 * @version $Id: ArtifactDeployerTest.java 592288 2007-11-06 05:36:13Z jvanzyl $
 */
public class ArtifactDeployerTest
    extends AbstractArtifactComponentTestCase
{
    private ArtifactDeployer artifactDeployer;

    protected void setUp()
        throws Exception
    {
        super.setUp();

        artifactDeployer = (ArtifactDeployer) lookup( ArtifactDeployer.ROLE );
    }

    protected String component()
    {
        return "deployer";
    }

    public void testArtifactInstallation()
        throws Exception
    {
        String artifactBasedir = new File( getBasedir(), "src/test/resources/artifact-install" ).getAbsolutePath();

        Artifact artifact = createArtifact( "artifact", "1.0" );

        File file = new File( artifactBasedir, "artifact-1.0.jar" );

        artifactDeployer.deploy( file, artifact, remoteRepository(), localRepository() );

        assertRemoteArtifactPresent( artifact );
    }

    public void testArtifactDeploymentForArtifactThatHasAlreadyBeenDeployed()
        throws Exception
    {
        String artifactBasedir = new File( getBasedir(), "src/test/resources/artifact-install" ).getAbsolutePath();

        Artifact artifact = createArtifact( "artifact", "10.1.3" );

        File file = new File( artifactBasedir, "artifact-10.1.3.jar" );

        try
        {
            artifactDeployer.deploy( file, artifact, remoteRepository(), localRepository() );
        }
        catch( ArtifactDeploymentException e )
        {
            fail( "Should have failed due to previous deployment of artifact." );
        }
    }
}