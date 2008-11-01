/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.preferences;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Set;

import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.eclipse.jface.preference.IPreferenceStore;

public class MavenPreferenceManager
{
    public static final String ARCHETYPE_PLUGIN_GROUPID = MavenCoreActivator.PLUGIN_ID + ".archetypePluginGroupId";

    public static final String ARCHETYPE_PLUGIN_ARTIFACTID =
        MavenCoreActivator.PLUGIN_ID + ".archetypePluginArtifactId";

    public static final String ARCHETYPE_PLUGIN_VERSION = MavenCoreActivator.PLUGIN_ID + ".archetypePluginVersion";

    public static final String OFFLINE = MavenCoreActivator.PLUGIN_ID + ".offline";

    public static final String DOWNLOAD_SOURCES = MavenCoreActivator.PLUGIN_ID + ".downloadSources";

    public static final String DOWNLOAD_JAVADOC = MavenCoreActivator.PLUGIN_ID + ".downloadJavadoc";

    public static final String ARCHETYPE_PAGE_CONN_TIMEOUT = MavenCoreActivator.PLUGIN_ID + ".archetypeConnTimeout";

    public static final String RECURSIVE_EXECUTION = MavenCoreActivator.PLUGIN_ID + ".recursive";

    public static final String USER_SETTINGS_XML_FILENAME = MavenCoreActivator.PLUGIN_ID + ".userSettingsXml";

    public static final String GLOBAL_SETTINGS_XML_FILENAME = MavenCoreActivator.PLUGIN_ID + ".globalSettingsXml";

    public static final String EVENTS_VIEW_SIZE = MavenCoreActivator.PLUGIN_ID + ".eventsViewSize";

    public static final String PROFILES_KEY = MavenCoreActivator.PLUGIN_ID + ".profilesKey";

    private IPreferenceStore preferenceStore;

    public MavenPreferenceManager( IPreferenceStore prefStore )
    {
        this.preferenceStore = prefStore;
    }

    public boolean isDownloadSources()
    {
        return preferenceStore.getBoolean( DOWNLOAD_SOURCES );
    }

    /**
     * @deprecated use {@link #isDownloadSources()}
     */
    @Deprecated
    public boolean downloadSources()
    {
        return isDownloadSources();
    }

    public void setDownloadSources( boolean downloadSources )
    {
        preferenceStore.setValue( DOWNLOAD_SOURCES, downloadSources );
    }

    public boolean isDownloadJavadoc()
    {
        return preferenceStore.getBoolean( DOWNLOAD_JAVADOC );
    }

    public void setDownloadJavadoc( boolean downloadJavadoc )
    {
        preferenceStore.setValue( DOWNLOAD_JAVADOC, downloadJavadoc );
    }

    public int getArchetypeConnectionTimeout()
    {
        return preferenceStore.getInt( ARCHETYPE_PAGE_CONN_TIMEOUT );
    }

    public void setArchetypeConnectionTimeout( int timeout )
    {
        preferenceStore.setValue( ARCHETYPE_PAGE_CONN_TIMEOUT, timeout );
    }

    public boolean isRecursive()
    {
        return preferenceStore.getBoolean( RECURSIVE_EXECUTION );
    }

    public void setRecursive( boolean value )
    {
        preferenceStore.setValue( RECURSIVE_EXECUTION, value );
    }

    public String getGlobalSettingsXmlFilename()
    {
        return preferenceStore.getString( GLOBAL_SETTINGS_XML_FILENAME );
    }

    public String getUserSettingsXmlFilename()
    {
        return preferenceStore.getString( USER_SETTINGS_XML_FILENAME );
    }

    public void setUserSettingsXmlFilename( String userSettingsXml )
    {
        preferenceStore.setValue( USER_SETTINGS_XML_FILENAME, userSettingsXml );
    }

    public IPreferenceStore getPreferenceStore()
    {
        return preferenceStore;
    }

    public void setPreferenceStore( IPreferenceStore preferenceStore )
    {
        this.preferenceStore = preferenceStore;
    }

    public boolean isOffline()
    {
        return preferenceStore.getBoolean( OFFLINE );
    }

    private void setOffline( boolean offline )
    {
        preferenceStore.setValue( OFFLINE, offline );
    }

    /**
     * Gets set list of profile names enabled by default
     * 
     * @return
     */
    public Set<String> getDefaultProfiles()
    {
        String rawValue = preferenceStore.getString( PROFILES_KEY );
        // We can NOT use mementos since this needs to be accessed outside the ui
        if ( null == rawValue || "".equals( rawValue ) )
        {
            return Collections.emptySet();
        }
        InputStream is;
        try
        {
            is = new ByteArrayInputStream( rawValue.getBytes( "UTF-8" ) );
        }
        catch ( UnsupportedEncodingException e1 )
        {
            // Use default encoding
            is = new ByteArrayInputStream( rawValue.getBytes() );
        }
        XMLDecoder d = new XMLDecoder( is );
        try
        {
            Set<String> result = (Set<String>) d.readObject();
            d.close();
            return result;
        }
        catch ( ArrayIndexOutOfBoundsException e )
        {
            // Nothing saved
            return Collections.emptySet();
        }
    }

    /**
     * Sets the set of profile nemes enabled by default
     * 
     * @param profilesSourceList
     */
    public void setDefaultProfiles( Set<String> profilesSourceList )
    {
        if ( null == profilesSourceList )
        {
            preferenceStore.setValue( PROFILES_KEY, null );
        }
        // We can NOT use mementos since this needs to be accessed outside the ui
        ByteArrayOutputStream os = new ByteArrayOutputStream( 256 );
        XMLEncoder e = new XMLEncoder( os );
        e.writeObject( profilesSourceList );
        e.close();
        String rawValue;
        try
        {
            rawValue = os.toString( "UTF-8" );
        }
        catch ( UnsupportedEncodingException e1 )
        {
            // Use default encoding
            rawValue = os.toString();
        }
        preferenceStore.setValue( PROFILES_KEY, rawValue );
    }

    /* ******************************* Archetypes ******************************* */

    public String getArchetypePluginGroupId()
    {
        return preferenceStore.getString( ARCHETYPE_PLUGIN_GROUPID );
    }

    public void setArchetypePluginGroupId( String groupId )
    {
        preferenceStore.setValue( ARCHETYPE_PLUGIN_GROUPID, groupId );
    }

    public String getArchetypePluginArtifactId()
    {
        return preferenceStore.getString( ARCHETYPE_PLUGIN_ARTIFACTID );
    }

    public void setArchetypePluginArtifactId( String artifactId )
    {
        preferenceStore.setValue( ARCHETYPE_PLUGIN_ARTIFACTID, artifactId );
    }

    public String getArchetypePluginVersion()
    {
        return preferenceStore.getString( ARCHETYPE_PLUGIN_VERSION );
    }

    public void setArchetypePluginVersion( String version )
    {
        preferenceStore.setValue( ARCHETYPE_PLUGIN_VERSION, version );
    }

    /* ******************************* Events ******************************* */

    public int getEventsViewSize()
    {
        return preferenceStore.getInt( EVENTS_VIEW_SIZE );
    }

    public void setEventsViewSize( int size )
    {
        preferenceStore.setValue( EVENTS_VIEW_SIZE, size );
    }

}
