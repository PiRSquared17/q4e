package org.devzuz.q.maven.ui.archetype.provider.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.devzuz.q.maven.embedder.QCoreException;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.archetype.provider.AbstractArchetypeProvider;
import org.devzuz.q.maven.ui.archetype.provider.Archetype;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Archetype2CatalogProvider extends AbstractArchetypeProvider
{
    private static final String FILENAME_MEMENTO_NAME = "filename";
    
    private String catalogFilename;
    
    public String getCatalogFilename()
    {
        return catalogFilename;
    }

    public void setCatalogFilename( String catalogFilename )
    {
        this.catalogFilename = catalogFilename;
    }

    public Collection<Archetype> getArchetypes() throws QCoreException
    {
        return getArchetypesFromCatalog( catalogFilename );
    }

    public IMemento exportState( String rootType )
    {
        XMLMemento root = XMLMemento.createWriteRoot( rootType );
        root.putString( FILENAME_MEMENTO_NAME, catalogFilename );
        return root;
    }
    
    public void importState( IMemento customProperties )
    {
        String filename = customProperties.getString( FILENAME_MEMENTO_NAME );
        if ( ( filename != null ) && ( filename.length() > 0 ) )
        {
            setCatalogFilename( filename );
        }
    }
    
    private Collection<Archetype> getArchetypesFromCatalog( String filename ) throws QCoreException
    {
        Collection<Archetype> archetypes = new ArrayList<Archetype>();
        File file = new File( filename );
        if ( file.exists() )
        {
            try
            {
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                Document doc = docBuilder.parse( file );

                NodeList archetypesNodeList = doc.getElementsByTagName( "archetype" );
                for ( int i = 0; i < archetypesNodeList.getLength(); i++ )
                {
                    Node node = archetypesNodeList.item( i );
                    if ( node.getNodeType() == Node.ELEMENT_NODE )
                    {
                        Archetype archetype = getArchetypeFromNode( node );
                        if ( archetype != null )
                        {
                            archetypes.add( archetype );
                        }
                        else
                        {
                            logError( "Error in reading one of the archetypes, node didn't pass validation." );
                        }
                    }
                }

            }
            catch ( IOException e )
            {
                StringBuffer meaningfulErrorMsg = new StringBuffer();
                meaningfulErrorMsg.append( "Error accessing archetype catalog. Accessing file " );
                meaningfulErrorMsg.append( filename );
                meaningfulErrorMsg.append( " produces IO errors" );
                
                logError( meaningfulErrorMsg.toString() );
                throw new QCoreException( new Status( IStatus.ERROR, MavenUiActivator.PLUGIN_ID,
                                                      meaningfulErrorMsg.toString() , e  ) );
            }
            catch( Exception e )
            {
                logError( e.getMessage() );
                throw new QCoreException( new Status( IStatus.ERROR, MavenUiActivator.PLUGIN_ID,
                                                      e.getMessage() , e  ) );
            }
        }
        else
        {
            StringBuffer meaningfulErrorMsg = new StringBuffer();
            meaningfulErrorMsg.append( "Error accessing archetype catalog. File " );
            meaningfulErrorMsg.append( filename );
            meaningfulErrorMsg.append( " doesn't exist" );
            
            logError( meaningfulErrorMsg.toString() );
            throw new QCoreException( new Status( IStatus.ERROR, MavenUiActivator.PLUGIN_ID,
                                                  meaningfulErrorMsg.toString() , 
                                                  new FileNotFoundException( meaningfulErrorMsg.toString()  ) ) );
        }
        
        return archetypes;
    }

    private Archetype getArchetypeFromNode( Node node )
    {
        Archetype archetype = new Archetype();
        
        Element archetypeElement = (Element) node;
        
        archetype.setGroupId( getElementString( archetypeElement , "groupId" ) );
        archetype.setArtifactId( getElementString( archetypeElement , "artifactId" ) );
        archetype.setVersion( getElementString( archetypeElement , "version" ) );
        archetype.setRemoteRepositories( getElementString( archetypeElement , "repository" ) );
        archetype.setDescription( getElementString( archetypeElement , "description" ) );
        
        if( !validateArchetype( archetype ) )
        {
            return null;
        }
        
        return archetype;
    }

    private String getElementString( Element archetypeElement , String id )
    {
        NodeList nodelist = archetypeElement.getElementsByTagName( id );
        if ( nodelist != null && nodelist.getLength() > 0 )
        {
            Node node = (Node) nodelist.item( 0 );
            if( node != null )
            {
                String textContent = node.getTextContent();
                if( textContent != null )
                {
                    return textContent.trim();
                }
            }
        }
        
        return null;
    }
    
    private boolean validateArchetype( Archetype archetype )
    {
        if( archetype.getGroupId() == null || archetype.getGroupId().length() <= 0 ||
            archetype.getArtifactId() == null || archetype.getArtifactId().length() <= 0 || 
            archetype.getVersion() == null || archetype.getVersion().length() <= 0 )
        {
            return false;
        }
        
        return true;
    }
    
    protected void logError( String message )
    {
        MavenUiActivator.getLogger().error( message );
    }
}
