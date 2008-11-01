/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.maven.artifact.Artifact;
import org.devzuz.q.maven.embedder.internal.TraceOption;
import org.devzuz.q.maven.embedder.nature.MavenNatureHelper;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;

public class MavenProjectManager
{
    /*
     * after a failed build don't try to rebuild unless this amount of time has passed (a new snapshot could have been
     * deployed for instance)
     */
    private static final long DEFAULT_REFRESHING_WINDOW = 60 * 1000;

    private final Map<IProject, MavenProjectCachedInfo> mavenProjects;

    /**
     * 
     * @author amuino
     */
    // FIX for 371: Since ConcurrentHashMap does not allow null values, we define this special value
    private final MavenProjectCachedInfo NULL_CACHED_INFO = new MavenProjectCachedInfo( null, false, 0, true );

    /**
     * <b>Not intended to be used by client code</b>
     * 
     * @see MavenManager#getMavenProjectManager()
     */
    protected MavenProjectManager()
    {
        // FIX for 371: Make the map concurrent
        // Default to 20 projects, 0.75 load factor (default), 1 expected writer, many readers
        mavenProjects = new ConcurrentHashMap<IProject, MavenProjectCachedInfo>( 20, 0.75f, 1 );
    }

    /**
     * <b>Not intended to be used by client code</b>
     * 
     * This constructor initializes the cache with the current maven projects in the workspace. This maven projects are
     * not yet resolved and will be resolved when actually needed.
     * 
     * @see MavenManager#getMavenProjectManager()
     * @param workspace
     * @throws CoreException
     */
    protected MavenProjectManager( IWorkspace workspace ) throws CoreException
    {
        this();
        for ( IProject project : workspace.getRoot().getProjects() )
        {
            if ( project.isOpen() && MavenNatureHelper.getInstance().hasQ4ENature( project ) )
            {
                // put project to cache
                addMavenProject( project );
            }
        }
    }

    /**
     * This method will add the given project to the cache with a null IMavenProject resolvedTransitively set to false
     * 
     * Use this method if the project is in the workspace but you want to resolve its IMavenProject only when it's
     * really needed.
     * 
     * @param project
     *            The IProject this project represents in the workspace
     */
    public void addMavenProject( IProject project )
    {
        addCachedInfo( project, NULL_CACHED_INFO );
    }

    /**
     * This method will add the given project and mavenProject to the cache with resolvedTransitively set to false
     * 
     * @param project
     *            The IProject this project represents in the workspace
     * @param mavenProject
     *            The IMavenProject associated with the IProject
     */
    public void addMavenProject( IProject project, IMavenProject mavenProject )
    {
        addMavenProject( project, mavenProject, false );
    }

    /**
     * This method will add the given project and mavenProject to the cache
     * 
     * @param project
     *            The IProject this project represents in the workspace
     * @param mavenProject
     *            The IMavenProject associated with the IProject
     * @param resolvedTransitively
     *            If this IMavenProject was resolved transitively
     */
    public void addMavenProject( IProject project, IMavenProject mavenProject, boolean resolvedTransitively )
    {
        try
        {
            /* Add information we want to be persisted with the IProject */
            setPersistentProperties( project, mavenProject );
        }
        catch ( CoreException e )
        {
            MavenCoreActivator.getLogger().log(
                                                "Unable to persist important information to Project '"
                                                                + project.getName() + "'", e );
        }

        /* Create the cache object and add to cache */
        MavenProjectCachedInfo cachedProject =
            MavenProjectCachedInfo.newMavenProjectCachedInfo( mavenProject, resolvedTransitively, getModificationStamp( project ) );

        addCachedInfo( project, cachedProject );
    }

    /**
     * Remove the given IProject from the cache
     * 
     * @param project The IProject this project represents in the workspace
     */
    public void removeMavenProject( IProject project )
    {
        MavenCoreActivator.trace( TraceOption.PROJECT_CACHE, "Removing project from cache: ", project );
        mavenProjects.remove( project );
    }

    /**
     * Sets the given IProject from the cache to a modified state. This has the effect of the IMavenProject getting
     * renewed from the maven embedder if getMavenProject(...) is called on it.
     * 
     * @param project
     *            The IProject this project represents in the workspace
     */
    public void setMavenProjectModified( IProject project )
    {
        MavenProjectCachedInfo cachedProject = mavenProjects.get( project );
        if ( cachedProject != null )
        {
            cachedProject.setModified( true );
        }
    }

    /**
     * Retrieves the IMavenProject for the given IProject from the cache or the maven embedder following a set of rules.
     * 
     * @param project
     *            The IProject this project represents in the workspace
     * @param resolveTransitively
     *            Should it be retrieved with transitive dependencies resolved?
     * @return The maven project, null if the project is in error
     * @throws CoreException
     */
    public IMavenProject getMavenProject( IProject project, boolean resolveTransitively ) throws CoreException
    {
        if ( !project.exists() || !project.isOpen() )
        {
            mavenProjects.remove( project );
            return null;
        }

        MavenProjectCachedInfo cachedProject = mavenProjects.get( project );

        // If we haven't cached the project yet or if the maven project's pom was modified or
        // if we have cached the project but it's not resolved transitively and resolveTransitively is true
        if ( cachedProject == null )
        {
            MavenCoreActivator.trace( TraceOption.PROJECT_CACHE, "Project not in cache: ", project );
            cachedProject = refreshProjectInCache( project, resolveTransitively );
        }
        else if ( cachedProject.isModified() )
        {
            MavenCoreActivator.trace( TraceOption.PROJECT_CACHE, "Cached project was modified: ", project );
            cachedProject = refreshProjectInCache( project, resolveTransitively );
        }
        else if ( ( cachedProject.resolvedTransitively == false ) && ( resolveTransitively == true ) )
        {
            MavenCoreActivator.trace( TraceOption.PROJECT_CACHE,
                                      "Project cached without transitive dependencies needs them now: ", project );
            cachedProject = refreshProjectInCache( project, resolveTransitively );
        }
        else if ( cachedProject.getMavenProject() == null )
        {
            /* project was in error last time */
            long cacheStamp = cachedProject.getModificationStamp();
            long pomStamp = getModificationStamp( project );
            if ( ( pomStamp - cacheStamp ) > 0 )
            {
                MavenCoreActivator.trace( TraceOption.PROJECT_CACHE,
                                          "Project was in error last time, pom was modified since, rebuilding: ",
                                          project );
                cachedProject = refreshProjectInCache( project, resolveTransitively );
            }
            else
            {
                long lastBuildStamp = cachedProject.getLastBuildStamp();
                long currentStamp = new Date().getTime();
                if ( ( currentStamp - lastBuildStamp ) > DEFAULT_REFRESHING_WINDOW )
                {
                    MavenCoreActivator.trace(
                                              TraceOption.PROJECT_CACHE,
                                              "Project was in error last time, no change but rebuilding due to timing: ",
                                              project );
                    cachedProject = refreshProjectInCache( project, resolveTransitively );
                }
                else
                {
                    MavenCoreActivator.trace( TraceOption.PROJECT_CACHE,
                                              "Project was in error last time, no changes, not rebuilding: ", project );
                }
            }
        }

        return cachedProject.getMavenProject();
    }

    private long getModificationStamp( IProject project )
    {
        return project.getFile( IMavenProject.POM_FILENAME ).getModificationStamp();
    }

    private MavenProjectCachedInfo refreshProjectInCache( IProject project, boolean resolveTransitively )
        throws CoreException
    {
        /* get maven project from maven embedder */
        IMavenProject mavenProject;
        try
        {
            mavenProject = MavenManager.getMaven().getMavenProject( project, resolveTransitively );

            /* Add information we want to be persisted with the IProject */
            setPersistentProperties( project, mavenProject );
        }
        catch ( CoreException e )
        {
            /* error building project, let's catch the error to avoid building until fixed */
            MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( project, e );
            mavenProject = null;
        }

        /* Create cache object */
        MavenProjectCachedInfo cachedProject =
            MavenProjectCachedInfo.newMavenProjectCachedInfo( mavenProject, resolveTransitively, getModificationStamp( project ) );

        /* Add to cache */
        addCachedInfo( project, cachedProject );

        return cachedProject;
    }

    private void setPersistentProperties( IProject project, IMavenProject mavenProject ) throws CoreException
    {
        project.setPersistentProperty( IMavenProject.GROUP_ID, mavenProject.getGroupId() );
        project.setPersistentProperty( IMavenProject.ARTIFACT_ID, mavenProject.getArtifactId() );
        project.setPersistentProperty( IMavenProject.VERSION, mavenProject.getVersion() );
    }

    /**
     * Retrieves the IMavenProject for the given Artifact from the cache or the maven embedder following a set of rules.
     * 
     * @param artifact
     *            the maven artifact to look for.
     * @param resolveTransitively
     *            <code>true</code> if the artifact must be resolved transitively.
     * @return The maven project, or <code>null</code> if not found on the workspace.
     * @throws CoreException
     */
    public IMavenProject getMavenProject( Artifact artifact, boolean resolveTransitively ) throws CoreException
    {
        String groupId = artifact.getGroupId();
        String artifactId = artifact.getArtifactId();
        String version = artifact.getVersion();
        return getMavenProject( groupId, artifactId, version, resolveTransitively );
    }

    /**
     * Retrieves the IMavenProject for the artifact with the given groupId, artifactId and version from the cache or the
     * maven embedder following a set of rules.
     * 
     * @param groupId
     *            the project's group Id
     * @param artifactId
     *            the project's artifact Id
     * @param version
     *            the project's version
     * @param resolveTransitively
     *            <code>true</code> if the artifact must be resolved transitively.
     * @return The maven project, or <code>null</code> if not found on the workspace.
     * @throws CoreException
     */
    public IMavenProject getMavenProject( String groupId, String artifactId, String version, boolean resolveTransitively )
        throws CoreException
    {
        Map.Entry<IProject, MavenProjectCachedInfo> entry = getEntry( groupId, artifactId, version );
        if ( entry != null )
        {
            return getMavenProject( entry.getKey(), resolveTransitively );
        }

        return null;
    }

    /**
     * Retrieve the IProject for the given triplet
     * 
     * @param groupId
     * @param artifactId
     * @param version
     * @return the workspace project
     */
    public IProject getWorkspaceProject( String groupId, String artifactId, String version )
    {
        Map.Entry<IProject, MavenProjectCachedInfo> info = getEntry( groupId, artifactId, version );
        if ( info != null )
        {
            return info.getKey();
        }

        return null;
    }

    /**
     * Retrieve all the IProject's in the cache
     * 
     * @return all the cached workspace project
     */
    public IProject[] getWorkspaceProjects()
    {
        return mavenProjects.keySet().toArray( new IProject[mavenProjects.size()] );
    }

    private void addCachedInfo( IProject project, MavenProjectCachedInfo cachedProject )
    {
        mavenProjects.put( project, cachedProject );
    }

    private Map.Entry<IProject, MavenProjectCachedInfo> getEntry( String groupId, String artifactId, String version )
    {
        for ( Map.Entry<IProject, MavenProjectCachedInfo> entry : mavenProjects.entrySet() )
        {
            String _groupId = "", _artifactId = "", _version = "";

            MavenProjectCachedInfo info = entry.getValue();
            if ( info != null && info != NULL_CACHED_INFO && info.getMavenProject() != null )
            {
                // This entry has a cached IMavenProject
                IMavenProject mavenProject = info.getMavenProject();

                _groupId = mavenProject.getGroupId();
                _artifactId = mavenProject.getArtifactId();
                _version = mavenProject.getVersion();
            }
            else
            {
                // No cached IMavenProject, we need to retrieve the triplet from the
                // persistent properties of the IProject
                IProject project = entry.getKey();

                /* check that project was not deleted or closed in the meantime */
                if ( !project.exists() || !project.isOpen() )
                {
                    continue;
                }

                try
                {
                    _groupId = project.getPersistentProperty( IMavenProject.GROUP_ID );
                    _artifactId = project.getPersistentProperty( IMavenProject.ARTIFACT_ID );
                    _version = project.getPersistentProperty( IMavenProject.VERSION );
                }
                catch ( CoreException e )
                {
                    MavenCoreActivator.getLogger().log(
                                                        "Can't read triplet from project's '" +
                                                            entry.getKey().getName() + "' persistent properties", e );
                    continue;
                }
            }

            // The artifact resolver executes this loop before the project has been created, so ALWAYS skip maven
            // projects without persistent properties.
            if ( _groupId != null && _groupId.equals( groupId ) && _artifactId.equals( artifactId ) &&
                _version.equals( version ) )
            {
                return entry;
            }
        }

        return null;
    }

    private static class MavenProjectCachedInfo
    {
        private IMavenProject mavenProject;

        private boolean resolvedTransitively = false;

        private boolean modified = false;
        
        private long modificationStamp;

        private long lastBuildStamp;

        public static MavenProjectCachedInfo newMavenProjectCachedInfo( IMavenProject mavenProject,
                                                                        boolean resolvedTransitively, long modificationStamp )
        {
            return new MavenProjectCachedInfo( mavenProject, resolvedTransitively, modificationStamp, false );
        }

        private MavenProjectCachedInfo( IMavenProject project, boolean resolvedTransitively, long modificationStamp,
                                        boolean modified )
        {
            this.mavenProject = project;
            this.resolvedTransitively = resolvedTransitively;
            this.modified = modified;
            this.modificationStamp = modificationStamp;
            this.lastBuildStamp = new Date().getTime();
        }

        public IMavenProject getMavenProject()
        {
            return mavenProject;
        }

        public void setMavenProject( IMavenProject mavenProject )
        {
            this.mavenProject = mavenProject;
        }

        public boolean isResolvedTransitively()
        {
            return resolvedTransitively;
        }

        public void setResolvedTransitively( boolean resolvedTransitively )
        {
            this.resolvedTransitively = resolvedTransitively;
        }

        public boolean isModified()
        {
            return modified;
        }

        public void setModified( boolean modified )
        {
            this.modified = modified;
        }

        public long getModificationStamp()
        {
            return modificationStamp;
        }

        public void setModificationStamp( long modificationStamp )
        {
            this.modificationStamp = modificationStamp;
        }

        public long getLastBuildStamp()
        {
            return lastBuildStamp;
        }

        public void setLastBuildStamp( long lastBuildStamp )
        {
            this.lastBuildStamp = lastBuildStamp;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder( this ).append( "mavenProject", mavenProject ).append( "resolvedTransitively",
                                                                                              resolvedTransitively ).append(
                                                                                                                             "modified",
                                                                                                                             modified ).toString();
        }
    }
}