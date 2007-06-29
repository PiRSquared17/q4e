package org.devzuz.q.maven.jdt.ui.pomeditor;

import org.devzuz.q.maven.jdt.ui.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomDependenciesFormPage extends FormPage
{
    private ScrolledForm form;
    
    public MavenPomDependenciesFormPage( String id, String title )
    {
        super( id, title );
    }

    public MavenPomDependenciesFormPage( FormEditor editor, String id, String title )
    {
        super( editor, id, title );
    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();
        
        form.getBody().setLayout( new GridLayout( 2 , false ) );
        
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        
        Section dependencyTable = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        dependencyTable.setDescription( "Set the Dependencies of this POM." );
        dependencyTable.setText( "Dependencies" ); 
        dependencyTable.setLayoutData( layoutData );
        dependencyTable.setClient( createDependencyTableControls( dependencyTable , toolkit ) );
        
        /*
        Section dependencyDetail = toolkit.createSection(form.getBody(), Section.NO_TITLE );
        dependencyDetail.setDescription( "Set the Dependencies of this POM." );
        dependencyDetail.setText( "Dependencies" ); 
        dependencyDetail.setLayoutData( layoutData );
        dependencyDetail.setClient( createDependencyDetailControls( dependencyDetail , toolkit ) ); */
        
        Composite container = toolkit.createComposite( form.getBody() );
        container.setLayoutData( layoutData );
        createDependencyDetailControls( container , toolkit );
    }
    
    private Control createDependencyTableControls( Composite parent , FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent );
        
        container.setLayout( new GridLayout( 2, false ) );
        
        Table propertiesTable = toolKit.createTable( container , SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        propertiesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        propertiesTable.setLinesVisible( true );
        propertiesTable.setHeaderVisible( true );
        
        /*
        TableColumn column = new TableColumn( propertiesTable, SWT.CENTER, 0 );
        column.setText( Messages.getString("MavenPomEditor.MavenPomEditor_Key") );
        column.setWidth( 100 );

        column = new TableColumn( propertiesTable, SWT.CENTER, 1 );
        column.setText( Messages.getString("MavenPomEditor.MavenPomEditor_Value") );
        column.setWidth( 100 ); */

        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        Button addPropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor.MavenPomEditor_AddButton"), SWT.PUSH | SWT.CENTER );
        //addPropertyButton.addSelectionListener( buttonListener );

        Button removePropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor.MavenPomEditor_RemoveButton"), SWT.PUSH | SWT.CENTER );
        //removePropertyButton.addSelectionListener( buttonListener );
        removePropertyButton.setEnabled( false );
        
        toolKit.paintBordersFor( parent );
        
        return container;
    }
    
    private Control createDependencyDetailControls( Composite container , FormToolkit toolKit )
    {
        //Composite container = toolKit.createComposite( parent );
        
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section dependencyInfo = toolKit.createSection( container , Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        dependencyInfo.setDescription( "Modify the details for the selected dependency." );
        dependencyInfo.setText( "Dependency Information" ); 
        dependencyInfo.setClient( createDependencyInfoControls( dependencyInfo , toolKit ) );
        
        Section dependencyExclusions = toolKit.createSection( container , Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        dependencyExclusions.setDescription( "Add exclusions for the selected dependency." );
        dependencyExclusions.setText( "Dependency Exclusions" ); 
        dependencyExclusions.setClient( createDependencyExclusionControls( dependencyExclusions , toolKit ) );
        
        return container;
    }
    
    private Control createDependencyInfoControls( Composite form , FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        
        parent.setLayout( new GridLayout( 2 , false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 60;
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        
        Label groupIdLabel = toolKit.createLabel( parent, Messages.getString("MavenPomEditor.MavenPomEditor_GroupId") , SWT.NONE ); 
        groupIdLabel.setLayoutData( labelData );
        
        Text groupIdText = toolKit.createText( parent, "groupId" /*, SWT.BORDER | SWT.SINGLE*/ ); 
        groupIdText.setLayoutData( controlData );
        groupIdText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label artifactIdLabel = toolKit.createLabel( parent, Messages.getString("MavenPomEditor.MavenPomEditor_ArtifactId"), SWT.NONE ); 
        artifactIdLabel.setLayoutData( labelData );
        
        Text artifactIdText = toolKit.createText( parent, "artifactId" /*, SWT.BORDER | SWT.SINGLE*/ ); 
        artifactIdText.setLayoutData( controlData );
        artifactIdText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label versionLabel = toolKit.createLabel( parent, Messages.getString("MavenPomEditor.MavenPomEditor_Version"), SWT.NONE ); 
        versionLabel.setLayoutData( labelData );
        
        Text versionText = toolKit.createText( parent, "Version" /*, SWT.BORDER | SWT.SINGLE*/ ); 
        versionText.setLayoutData( controlData );
        versionText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label typeLabel = toolKit.createLabel( parent, Messages.getString("MavenPomEditor.MavenPomEditor_Type"), SWT.NONE ); 
        typeLabel.setLayoutData( labelData );
        
        Text typeText = toolKit.createText( parent, "Type" /*, SWT.BORDER | SWT.SINGLE*/ ); 
        typeText.setLayoutData( controlData );
        typeText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label scopeLabel = toolKit.createLabel( parent, Messages.getString("MavenPomEditor.MavenPomEditor_Scope"), SWT.NONE ); 
        scopeLabel.setLayoutData( labelData );
        
        Text scopeText = toolKit.createText( parent, "Scope" /*, SWT.BORDER | SWT.SINGLE*/ ); 
        scopeText.setLayoutData( controlData );
        scopeText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label systemPathLabel = toolKit.createLabel( parent, Messages.getString("MavenPomEditor.MavenPomEditor_SystemPath"), SWT.NONE ); 
        systemPathLabel.setLayoutData( labelData );
        systemPathLabel.setEnabled( false );
        
        Text systemPathText = toolKit.createText( parent, "SystemPath" /*, SWT.BORDER | SWT.SINGLE*/ ); 
        systemPathText.setLayoutData( controlData );
        systemPathText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        systemPathText.setEnabled( false );
        
        Label optionalLabel = toolKit.createLabel( parent, Messages.getString("MavenPomEditor.MavenPomEditor_Optional"), SWT.NONE ); 
        optionalLabel.setLayoutData( labelData );
        
        Button optionalRadioButton = toolKit.createButton( parent , "", SWT.CHECK);
        optionalRadioButton.setLayoutData( controlData );
        
        return parent;
    }
    
    private Control createDependencyExclusionControls( Composite form , FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( form );
        
        container.setLayout( new GridLayout( 2, false ) );
        
        Table dependencyExclusionTable = toolKit.createTable( container , SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        dependencyExclusionTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        dependencyExclusionTable.setLinesVisible( true );
        dependencyExclusionTable.setHeaderVisible( true );
        
        /*
        TableColumn column = new TableColumn( propertiesTable, SWT.CENTER, 0 );
        column.setText( Messages.getString("MavenPomEditor.MavenPomEditor_Key") );
        column.setWidth( 100 );

        column = new TableColumn( propertiesTable, SWT.CENTER, 1 );
        column.setText( Messages.getString("MavenPomEditor.MavenPomEditor_Value") );
        column.setWidth( 100 ); */

        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        Button addPropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor.MavenPomEditor_AddButton"), SWT.PUSH | SWT.CENTER );
        //addPropertyButton.addSelectionListener( buttonListener );

        Button removePropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor.MavenPomEditor_RemoveButton"), SWT.PUSH | SWT.CENTER );
        //removePropertyButton.addSelectionListener( buttonListener );
        removePropertyButton.setEnabled( false );
        
        toolKit.paintBordersFor( container );
        
        return container;
    }
}
