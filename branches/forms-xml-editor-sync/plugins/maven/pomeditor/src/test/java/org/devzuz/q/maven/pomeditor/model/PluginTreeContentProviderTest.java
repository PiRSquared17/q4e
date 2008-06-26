package org.devzuz.q.maven.pomeditor.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import junit.framework.TestCase;

import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.pomeditor.pages.internal.DeleteItemAction;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class PluginTreeContentProviderTest extends TestCase
    
{
    Model pomModel;
    PluginTreeContentProvider provider;
    List<Plugin> plugins = null;
    Build build;
    
    @SuppressWarnings ("unchecked")
    public void setUp() throws Exception
    {
        try
        {
            //this.pomModel = new MavenXpp3Reader().read( new FileReader( "../resources/pom.xml" ) );
            URL url = getClass().getResource("pom.xml");
            this.pomModel = new MavenXpp3Reader().read( new FileReader( url.getFile() ) );
            
            build = pomModel.getBuild();
            plugins = build.getPlugins();
            
            assertTrue( plugins != null );
            
            provider = new PluginTreeContentProvider( build );
        }
        catch ( FileNotFoundException e )
        {
            System.out.println("FileNotFoundException");
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        catch ( XmlPullParserException e )
        {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings ("unchecked")
    public void testSamplePom()
    {
        Object[] root = provider.getChildren( build );
        assertTrue( root.length == 1 );
        
        Object[] childrenPlugins = provider.getChildren( plugins );
        assertTrue( childrenPlugins.length == 2 );
        
        assertTrue( childrenPlugins[0] instanceof Plugin );
        
        Plugin plugin1 = ( Plugin ) childrenPlugins[0];
        
        Object[] plugin1Children = provider.getChildren( plugin1 );
        
        assertTrue( plugin1Children.length == 1 );
        
        assertTrue( ((Plugin) childrenPlugins[0]).getConfiguration() instanceof Xpp3Dom );
        
        Xpp3Dom dom = (Xpp3Dom) ((Plugin) childrenPlugins[0]).getConfiguration();
        
        System.out.println("children count = " + dom.getChildCount() );
        
        
        assertTrue( childrenPlugins[1] instanceof Plugin );
        
        Plugin plugin2 = (Plugin) childrenPlugins[1];
        
        assertTrue( plugin2.getExecutions().size() == 1 );
        
        assertTrue( plugin2.getConfiguration() == null );
        
        assertTrue( plugin2.getDependencies().size() == 0 );
        
        Object[] plugin2Children = provider.getChildren( plugin2 );
        
        System.out.println("Count = " + plugin2Children.length );
        
        assertTrue( plugin2Children[0] instanceof List );
        
        PluginExecution pluginExecution = ( (List<PluginExecution>) plugin2Children[0] ).get( 0 );
        
        Object[] executionChildren = provider.getChildren( pluginExecution );
        
        assertTrue( executionChildren.length == 2 );
    }
    
    public void testParentChild()
    {
        checkNode( plugins );
    }
    
    public void checkNode( Object parent )
    {
        Object[] childrenPlugins = provider.getChildren( parent );
        if ( childrenPlugins != null )
        {
            for ( Object child : childrenPlugins )
            {
                assertTrue( "Checking " + child.toString(), provider.getParent( child ) == parent );
                checkNode( child );
            }
        }
    }
    
    public void testLabelProvider()
    {
        showName( plugins , new PluginTreeLabelProvider() );
    }
    
    public void testXpp3Dom()
    {
        assertTrue ( "plugins != null ",plugins != null );
        Xpp3Dom configDom = ( Xpp3Dom ) ( (PluginExecution) plugins.get( 1 ).getExecutions().get( 0 ) ).getConfiguration();
        assertTrue ( "configDom != null", configDom != null );
        assertTrue ( "configDom.getParent() == null" , configDom.getParent() == null );
        printXpp3DomRecursive( configDom , 0 );
    }
    
    private void printXpp3DomRecursive( Xpp3Dom dom , int gen )
    {
        String value = dom.getValue() == null ? "null" : dom.getValue();
        System.out.printf( repeat( '\t', gen ) + "{ " + dom.getName() + " , " + value + " }\n" );
        if ( dom.getChildCount() > 0 )
        {
            for ( Xpp3Dom child : dom.getChildren() )
            {
                printXpp3DomRecursive( child, gen + 1 );
            }
        }
    }
    
    private String repeat( char ch , int times )
    {
        StringBuffer buffer = new StringBuffer("");
        for( int i = 0 ; i < times; i++ )
        {
            buffer.append( ch );
        }
        
        return buffer.toString();
    }
    
    public void showName( Object parent , PluginTreeLabelProvider labelProvider )
    {
        System.out.println("Label = " + labelProvider.getText( parent ) );
        Object[] childrenPlugins = provider.getChildren( parent );
        if ( childrenPlugins != null )
        {
            for ( Object child : childrenPlugins )
            {
                showName( child , labelProvider );
            }
        }
    }
    
    @SuppressWarnings ("unchecked")
    public void testDeleteItemAction()
    {
        Build build = pomModel.getBuild();
        List<Object> objectList = build.getPlugins();
        Plugin plugin = ( Plugin ) objectList.get( 1 );
        
        assertTrue(objectList.contains( plugin ) );
        
        DeleteItemActionMock action = new DeleteItemActionMock( "Plugin", "Plugin" , new PluginTreeContentProvider( build ) );
        action.doAction( plugin );
        
        assertTrue( !(objectList.contains( plugin )) );
    }
    
    public void tearDown() throws Exception
    {
    }
    
    private class DeleteItemActionMock extends DeleteItemAction
    {
        public DeleteItemActionMock( String commandString, String objectClass, ITreeContentProvider contentProvider )
        {
            super( commandString , objectClass , contentProvider );
        }
        
        protected boolean showWarningAndPrompt()
        {
            return true;
        }
    }
}
