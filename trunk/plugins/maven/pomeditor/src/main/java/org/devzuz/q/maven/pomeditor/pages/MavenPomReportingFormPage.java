package org.devzuz.q.maven.pomeditor.pages;

import java.util.List;

import org.apache.maven.model.Model;
import org.apache.maven.model.ReportPlugin;
import org.apache.maven.model.Reporting;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditReportPluginDialog;
import org.devzuz.q.maven.pomeditor.dialogs.ConfigurationDialog;
import org.devzuz.q.maven.pomeditor.dialogs.ReportSetDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomReportingFormPage
    extends FormPage
{

    private boolean isPageModified;
    
    private ScrolledForm form;

    private Model pomModel;

    private Text outputDirectoryText;

    private Button excludeDefaultsRadioButton;

    private Button addPluginButton;

    private Button editPluginButton;

    private Button removePluginButton;

    private Button addReportSetButton;

    private Button editReportSetButton;

    private Button removeReportSetButton;

    private Button pluginConfigurationButton;

    private Button reportSetButton;
    
    private ReportPlugin selectedReportPlugin;

    private Table reportPluginTable;
    
    private List<ReportPlugin> reportPluginList;

    @SuppressWarnings("unchecked")
    public MavenPomReportingFormPage( FormEditor editor, String id, String title, Model model )
    {
        super( editor, id, title );
        this.pomModel = model;         
        
        if ( pomModel.getReporting() == null )
        {
            Reporting reporting = new Reporting();
            pomModel.setReporting( reporting );            
        }
        
        reportPluginList = pomModel.getReporting().getPlugins();
    }
    
    public MavenPomReportingFormPage( String id, String title )
    {
        super( id, title );
    }
    
    protected void createFormContent( IManagedForm managedForm )
    {
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();

        form.getBody().setLayout( new GridLayout( 2, false ) );
        
        Section reportingSection = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        reportingSection.setLayoutData( createSectionLayoutData( true ) );
        reportingSection.setText( Messages.MavenPomEditor_MavenPomEditor_Reporting );
        reportingSection.setDescription( "Section for management of reports and their configuration." );
        reportingSection.setClient( createReportingControls( reportingSection, toolkit ) );
    }
    
    private Control createReportingControls( Composite parent, FormToolkit toolkit )
    {
        SelectionListener selectionListener = new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent arg0 )
            {
                syncControlsToModel();
                pageModified();
            }
        };
        
        Composite container = toolkit.createComposite( parent );
        container.setLayout( new GridLayout( 2, false ) );

        Label excludeDefaultsLabel = toolkit.createLabel( container, Messages.MavenPomEditor_MavenPomEditor_ExcludeDefaults, SWT.None );
        excludeDefaultsLabel.setLayoutData( createLabelLayoutData() );
        
        excludeDefaultsRadioButton = toolkit.createButton( container, "", SWT.CHECK );
        excludeDefaultsRadioButton.addSelectionListener( selectionListener );
        
        Label outputDirectoryLabel = toolkit.createLabel( container, Messages.MavenPomEditor_MavenPomEditor_OutputDirectory, SWT.None );
        outputDirectoryLabel.setLayoutData( createLabelLayoutData() );        
        
        outputDirectoryText = toolkit.createText( container, "", SWT.SINGLE | SWT.BORDER );
        createTextDisplay( outputDirectoryText, createControlLayoutData(), null );        
    
        createPluginGroupTable( container, toolkit );
        
        return container;
    }
    
    protected void syncControlsToModel()
    { 
        pomModel.getReporting().setOutputDirectory( nullIfBlank( outputDirectoryText.getText().trim() ) );
        pomModel.getReporting().setExcludeDefaults( excludeDefaultsRadioButton.getSelection() );
        
    }

    private void createPluginGroupTable ( Composite container, FormToolkit toolkit )
    {
        Group pluginGroup = new Group( container, SWT.None );
        pluginGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Plugins );
        pluginGroup.setLayoutData( new GridData( SWT.FILL, SWT.CENTER , true, false, 2, 1 ) );
        pluginGroup.setLayout( new GridLayout( 2, false ) );
        
        container.setLayout( new GridLayout( 2, false ) );
        
        reportPluginTable = toolkit.createTable( pluginGroup, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );        
        reportPluginTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        reportPluginTable.setLinesVisible( true );
        reportPluginTable.setHeaderVisible( true );        

        ReportPluginTableListener tableListener = new ReportPluginTableListener();
        reportPluginTable.addSelectionListener( tableListener );        
        
        TableColumn groupIdColumn = new TableColumn( reportPluginTable, SWT.BEGINNING, 0 );
        groupIdColumn.setWidth( 100 );
        groupIdColumn.setText( Messages.MavenPomEditor_MavenPomEditor_GroupId );
        
        TableColumn artifactIdColumn = new TableColumn( reportPluginTable, SWT.BEGINNING, 1 );
        artifactIdColumn.setWidth( 100 );
        artifactIdColumn.setText( Messages.MavenPomEditor_MavenPomEditor_ArtifactId );
        
        TableColumn versionColumn = new TableColumn( reportPluginTable, SWT.BEGINNING, 2 );
        versionColumn.setWidth( 50 );
        versionColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Version );
        
        TableColumn inheritedColumn = new TableColumn( reportPluginTable, SWT.BEGINNING, 3 );
        inheritedColumn.setWidth( 60 );
        inheritedColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Inherited );
        
        Composite pluginButtonContainer = toolkit.createComposite( pluginGroup, SWT.NULL );        
        pluginButtonContainer.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        pluginButtonContainer.setLayout( layout );
        
        addPluginButton = new Button( pluginButtonContainer, SWT.PUSH | SWT.CENTER );
        addPluginButton.setText( Messages.MavenPomEditor_MavenPomEditor_AddButton );
        AddPluginButtonListener addButtonListener = new AddPluginButtonListener();
        addPluginButton.addSelectionListener( addButtonListener );
        addPluginButton.setEnabled( true );
        
        editPluginButton = new Button( pluginButtonContainer, SWT.PUSH | SWT.CENTER );
        editPluginButton.setText( Messages.MavenPomEditor_MavenPomEditor_EditButton );
        //EditButtonListener editButtonListener = new EditButtonListener();
        //editButton.addSelectionListener( editButtonListener );
        editPluginButton.setEnabled( false );

        removePluginButton = new Button( pluginButtonContainer, SWT.PUSH | SWT.CENTER );
        removePluginButton.setText( Messages.MavenPomEditor_MavenPomEditor_RemoveButton );
        //RemoveButtonListener removeButtonListener = new RemoveButtonListener();
        //removeButton.addSelectionListener( removeButtonListener );
        removePluginButton.setEnabled( false );
        
        pluginConfigurationButton = new Button( pluginButtonContainer, SWT.PUSH | SWT.CENTER );
        pluginConfigurationButton.setText( Messages.MavenPomEditor_MavenPomEditor_Configuration );
        pluginConfigurationButton.setEnabled( false );
        
        PluginConfigurationButtonListener configListener = new PluginConfigurationButtonListener();
        pluginConfigurationButton.addSelectionListener( configListener );
        
        
        reportSetButton = new Button( pluginButtonContainer, SWT.PUSH | SWT.CENTER );
        reportSetButton.setText( Messages.MavenPomEditor_MavenPomEditor_ReportSet );
        reportSetButton.setEnabled( false );
        
        ReportSetButtonListener reportSetListener = new ReportSetButtonListener();
        reportSetButton.addSelectionListener( reportSetListener );
        
        populateReportPluginTable();
    }

    private void createTextDisplay( Text text, GridData controlData, String data )
    {
        if ( text != null )
        {
            ModifyListener modifyingListener = new ModifyListener()
            {
                public void modifyText( ModifyEvent e )
                {
                    syncControlsToModel();
                    pageModified();
                }
            };
            
            text.setLayoutData( controlData );
            text.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );
            text.setText( blankIfNull( data ) );
            text.addModifyListener( modifyingListener );
        }
        
    }
    
    private class ReportPluginTableListener extends SelectionAdapter
    {
        private int selectedIndex;

        public void defaultWidgetSelected ( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] item = reportPluginTable.getSelection();
            
            if ( ( item != null ) && ( item.length > 0 ) )
            {
                editPluginButton.setEnabled( true );
                removePluginButton.setEnabled( true );
                pluginConfigurationButton.setEnabled( true );
                reportSetButton.setEnabled( true );
                
                if ( reportPluginTable.getSelectionIndex() >= 0 )
                {
                    selectedIndex = reportPluginTable.getSelectionIndex();
                    selectedReportPlugin = reportPluginList.get( selectedIndex );
                    
                    System.out.println("ReportPluginTableListener " + selectedReportPlugin.getArtifactId());
                }
            }
        }
    }

    private class AddPluginButtonListener extends SelectionAdapter
    {
        public void defaultWidgetSelected ( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
            AddEditReportPluginDialog addDialog = AddEditReportPluginDialog.newAddEditPluginReportDialog();
            
            if ( addDialog.open() == Window.OK )
            {    
                ReportPlugin reportPlugin = new ReportPlugin();
                
                reportPlugin.setGroupId( addDialog.getGroupId() );
                reportPlugin.setArtifactId( addDialog.getArtifactId() );
                reportPlugin.setVersion( addDialog.getVersion() );
                if ( addDialog.isInherited() == true )
                {
                    reportPlugin.setInherited( "true" );
                }
                else
                {
                    reportPlugin.setInherited( "false" );
                }
                
                pomModel.getReporting().addPlugin( reportPlugin );
                
                populateReportPluginTable();
                
                pageModified();
                
            }
        }
    }
    
    private class ReportSetButtonListener extends SelectionAdapter
    {
        public void defaultWidgetSelected ( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
            ReportSetDialog reportSetDialog = ReportSetDialog.newReportSetDialog();
            
            if ( reportSetDialog.open() == Window.OK )
            {
                System.out.println("moogle testing #1 kupo");
            }
        }
    }
    
    private class PluginConfigurationButtonListener extends SelectionAdapter
    {
        public void defaultWidgetSelected ( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
            ConfigurationDialog configDialog = ConfigurationDialog.newConfigurationDialog();
            
            System.out.println(selectedReportPlugin.getArtifactId());
            
            Xpp3Dom dom = ( Xpp3Dom )selectedReportPlugin.getConfiguration();            
            
            if ( configDialog.openWithConfiguration( dom ) == Window.OK )
            {
                System.out.println("moogle testing #2 kupo");
            }
            
        }
    }
    
    private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 100;
        return labelData;
    }
    
    public void populateReportPluginTable()
    {
        reportPluginTable.removeAll();
        
        if ( reportPluginList != null )
        {
            for ( ReportPlugin reportPlugin : reportPluginList )
            {
                TableItem item = new TableItem( reportPluginTable, SWT.BEGINNING );
                String inherited = new Boolean( reportPlugin.getInherited()).toString();
                item.setText( new String[] { reportPlugin.getGroupId(), reportPlugin.getArtifactId(), 
                    reportPlugin.getVersion(), inherited } );
            }
        }
        
    }

    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        //controlData.horizontalIndent = 10;
        return controlData;
    }

    private GridData createSectionLayoutData(boolean fill)
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, fill );
        return layoutData;
    }
    
    private String blankIfNull( String str )
    {
        return str == null ? "" : str;
    }
    
    private String nullIfBlank( String str )
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }
    
    public boolean isDirty()
    {
        return isPageModified;
    }

    public void setPageModified( boolean isModified )
    {
        this.isPageModified = isModified;
    }

    protected void pageModified()
    {
        isPageModified = true;
        this.getEditor().editorDirtyStateChanged();

    }

}
