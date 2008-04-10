package org.devzuz.q.maven.pomeditor.pages;

import java.util.Collections;
import java.util.List;

import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Resource;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.IncludeExcludeComponent;
import org.devzuz.q.maven.pomeditor.components.ResourceComponent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomBuildTestResourcesPage extends FormPage 
{
	private Build build;

	private ScrolledForm form;

	private List<Resource> resourcesList;

	private boolean isPageModified;

	private ResourceComponent testResourceComponent;

	private IncludeExcludeComponent includeComponent;

	private IncludeExcludeComponent excludeComponent;

	protected Resource selectedResource;

	@SuppressWarnings ("unchecked")
	public MavenPomBuildTestResourcesPage( FormEditor editor, String id,
			String title, Model pomModel )
	{
		super( editor, id, title );
        build = pomModel.getBuild();
        if ( build == null )
        {
            build = new Build();
            pomModel.setBuild( build );
        }

        resourcesList = build.getResources();
	}
	
	public MavenPomBuildTestResourcesPage( String id, String title )
	{
		super( id, title );
	}
	
	protected void createFormContent( IManagedForm managedForm )
    {
        form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();

        form.getBody().setLayout( new GridLayout( 2, false ) );

        GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );

        Section resourceTable = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.DESCRIPTION );
        resourceTable.setDescription( "This element describes all of the classpath resources associated with a project or unit tests." );
        resourceTable.setText( Messages.MavenPomEditor_MavenPomEditor_TestResource );
        resourceTable.setLayoutData( layoutData );
        resourceTable.setClient( createResourceTableControls( resourceTable, toolkit ) );

        Composite container = toolkit.createComposite( form.getBody() );
        container.setLayoutData( layoutData );
        createIncludeExcludeTables( container, toolkit );
    }

    @SuppressWarnings( "unchecked" )
    private Control createResourceTableControls( Composite parent, FormToolkit toolKit )
    {
        SelectionAdapter tableListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                selectedResource = testResourceComponent.getSelectedResource();
                synchronizedSelectedResourceToDetails( selectedResource.getIncludes() , 
                                                       selectedResource.getExcludes() ,
                                                       true );
            }
        };

        SelectionAdapter buttonListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                if ( testResourceComponent.isModified() == true )
                {
                    pageModified();
                }
            }
        };
        
        SelectionAdapter removebuttonListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                if ( testResourceComponent.isModified() == true )
                {
                    synchronizedSelectedResourceToDetails( Collections.EMPTY_LIST , 
                                                           Collections.EMPTY_LIST ,
                                                           false );
                    pageModified();
                }
            }
        };

        Composite container = toolKit.createComposite( parent, SWT.None );
        container.setLayout( new GridLayout( 1, false ) );

        testResourceComponent = new ResourceComponent( container, SWT.NONE, resourcesList );
        testResourceComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );
        testResourceComponent.addResourcesTableListener( tableListener );
        testResourceComponent.addAddButtonListener( buttonListener );
        testResourceComponent.addEditButtonListener( buttonListener );
        testResourceComponent.addRemoveButtonListener( removebuttonListener );

        return container;
    }
    
    private void synchronizedSelectedResourceToDetails( List<String> includes , List<String> excludes  , 
                                                        boolean enableAdd )
    {
        includeComponent.updateTable( includes );
        excludeComponent.updateTable( excludes );
        includeComponent.setAddButtonEnabled( enableAdd );
        excludeComponent.setAddButtonEnabled( enableAdd );
    }

    private Control createIncludeExcludeTables( Composite container, FormToolkit toolkit )
    {
        container.setLayout( new FillLayout( SWT.VERTICAL ) );

        Section includeTable =
            toolkit.createSection( container, Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED );
        includeTable.setDescription( "A set of files patterns which specify the files to include as resources under that specified directory, using * as a wildcard." );
        includeTable.setText( Messages.MavenPomEditor_MavenPomEditor_Resource_Includes );
        includeTable.setClient( createIncludeTableControls( includeTable, toolkit ) );

        Section excludeTable =
            toolkit.createSection( container, Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED );
        excludeTable.setDescription( "A set of files patterns which specify the files to exclude as resources under that specified directory, using * as a wildcard."
                        + "	The same structure as includes , but specifies which files to ignore. In conflicts between include  and exclude , exclude  wins." );
        excludeTable.setText( Messages.MavenPomEditor_MavenPomEditor_Resource_Excludes );
        excludeTable.setClient( createExcludeTableControls( excludeTable, toolkit ) );

        return container;

    }

    private Control createExcludeTableControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent, SWT.None );
        container.setLayout( new GridLayout( 1, false ) );

        excludeComponent = new IncludeExcludeComponent( container, SWT.NONE );
        excludeComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );

        ExcludeComponentButtonListener buttonListener = new ExcludeComponentButtonListener();
        excludeComponent.addAddButtonListener( buttonListener );
        excludeComponent.addEditButtonListener( buttonListener );
        excludeComponent.addRemoveButtonListener( buttonListener );

        return container;
    }

    private Control createIncludeTableControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent, SWT.None );
        container.setLayout( new GridLayout( 1, false ) );

        includeComponent = new IncludeExcludeComponent( container, SWT.NONE );
        includeComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );

        IncludeComponentButtonListener buttonListener = new IncludeComponentButtonListener();
        includeComponent.addAddButtonListener( buttonListener );
        includeComponent.addEditButtonListener( buttonListener );
        includeComponent.addRemoveButtonListener( buttonListener );

        return container;
    }

    @SuppressWarnings( "unchecked" )
    private class IncludeComponentButtonListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            pageModified();
        }
    }

    @SuppressWarnings( "unchecked" )
    private class ExcludeComponentButtonListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            pageModified();
        }
    }

    protected void pageModified()
    {
        isPageModified = true;
        this.getEditor().editorDirtyStateChanged();
    }

    public boolean isDirty()
    {
        return isPageModified;
    }

    public boolean isPageModified()
    {
        return isPageModified;
    }

    public void setPageModified( boolean isPageModified )
    {
        this.isPageModified = isPageModified;
    }
}
