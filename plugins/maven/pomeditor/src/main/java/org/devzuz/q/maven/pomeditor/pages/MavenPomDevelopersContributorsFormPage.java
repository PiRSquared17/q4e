/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import java.util.List;

import org.devzuz.q.maven.pom.Contributor;
import org.devzuz.q.maven.pom.Developer;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditContributorDeveloperDialog;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomDevelopersContributorsFormPage extends FormPage
{
    private ScrolledForm form;

    private Model pomModel;

    private Table developersTable;
    
    private Table contributorsTable;

    private Button addContributorButton;

    private Button editContributorButton;

    private Button removeContributorButton;

    private Button addDeveloperButton;

    private Button removeDeveloperButton;

    private Button editDeveloperButton;

    private int selectedContributorIndex;
    
    private int selectedDeveloperIndex;
    
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;
    
    public MavenPomDevelopersContributorsFormPage( String id, String title )
    {
        super( id, title );
    }

    @SuppressWarnings("unchecked")
    public MavenPomDevelopersContributorsFormPage( FormEditor editor, String id, String title, Model modelPOM, EditingDomain domain, DataBindingContext bindingContext )
    {
        super( editor, id, title );
        this.pomModel = modelPOM;
        this.domain = domain;
        this.bindingContext = bindingContext;
    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        
        form.getBody().setLayout( new GridLayout( 1 , false ) );        
        
        Section developerTable =
            toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        developerTable.setDescription( "Information about the committers on this project." );
        developerTable.setText( Messages.MavenPomEditor_MavenPomEditor_Developers );
        developerTable.setLayoutData( createSectionLayoutData() );
        developerTable.setClient( createDeveloperTableControls( developerTable, toolkit ) );
        
        Section contributorTable = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        contributorTable.setDescription( "Information about people who have contributed to the project, but who do not have commit privileges" );
        contributorTable.setText( Messages.MavenPomEditor_MavenPomEditor_Contributors );
        contributorTable.setLayoutData( createSectionLayoutData() );
        contributorTable.setClient( createContributorTableControls( contributorTable, toolkit ) );
    }

    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        return layoutData;
    }

    private Control createDeveloperTableControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent );
        container.setLayout( new GridLayout( 2, false ) );
        
        developersTable = toolKit.createTable( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE | SWT.H_SCROLL );
        developersTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        developersTable.setLinesVisible( true );
        developersTable.setHeaderVisible( true );
    
        DevelopersTableListener tableListener = new DevelopersTableListener();
    
        developersTable.addSelectionListener( tableListener );
        
        TableColumn idColumn = new TableColumn( developersTable, SWT.BEGINNING, 0 );
        idColumn.setWidth( 50 );
        idColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        
        TableColumn nameColumn = new TableColumn( developersTable, SWT.BEGINNING, 1 );
        nameColumn.setWidth( 75 );
        nameColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        TableColumn emailColumn = new TableColumn( developersTable, SWT.BEGINNING, 2 );
        emailColumn.setWidth( 75 );
        emailColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Email );
        
        TableColumn urlColumn = new TableColumn( developersTable, SWT.BEGINNING, 3 );
        urlColumn.setWidth( 90 );
        urlColumn.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        TableColumn orgColumn = new TableColumn( developersTable, SWT.BEGINNING, 4 );
        orgColumn.setWidth( 90 );
        orgColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Organization );
        
        TableColumn orgUrlColumn = new TableColumn( developersTable, SWT.BEGINNING, 5 );
        orgUrlColumn.setWidth( 100 );
        orgUrlColumn.setText( Messages.MavenPomEditor_MavenPomEditor_OrganizationUrl );
        
        TableColumn timezoneColumn = new TableColumn( developersTable, SWT.BEGINNING, 6 );
        timezoneColumn.setWidth( 75 );
        timezoneColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Timezone );
        

        ModelUtil.bindTable(
        		pomModel, 
        		new EStructuralFeature[] { PomPackage.Literals.MODEL__DEVELOPERS, PomPackage.Literals.DEVELOPERS_TYPE__DEVELOPER }, 
        		new EStructuralFeature[] { PomPackage.Literals.DEVELOPER__ID, PomPackage.Literals.DEVELOPER__NAME, PomPackage.Literals.DEVELOPER__EMAIL, PomPackage.Literals.DEVELOPER__URL, PomPackage.Literals.DEVELOPER__ORGANIZATION, PomPackage.Literals.DEVELOPER__ORGANIZATION_URL, PomPackage.Literals.DEVELOPER__TIMEZONE }, 
        		developersTable, 
        		domain );
        
        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );
        
        addDeveloperButton = toolKit.createButton( container2, 
               Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
    
        AddDeveloperButtonListener addButtonListener = new AddDeveloperButtonListener();
        addDeveloperButton.addSelectionListener( addButtonListener );
        
        editDeveloperButton = toolKit.createButton( container2, 
               Messages.MavenPomEditor_MavenPomEditor_EditButton, SWT.PUSH | SWT.CENTER );
        
        EditDeveloperButtonListener editButtonListener = new EditDeveloperButtonListener();
        editDeveloperButton.addSelectionListener( editButtonListener );
        editDeveloperButton.setEnabled( false );
        
        removeDeveloperButton = toolKit.createButton( container2, 
               Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH | SWT.CENTER );
        
        RemoveDeveloperButtonListener removeButtonListener = new RemoveDeveloperButtonListener();
        removeDeveloperButton.addSelectionListener( removeButtonListener );
        removeDeveloperButton.setEnabled( false );

        return container;
    
    }
    
    private Control createContributorTableControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent );
        container.setLayout( new GridLayout( 2, false ) );
        
        contributorsTable = toolKit.createTable( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE | SWT.H_SCROLL );
        contributorsTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        contributorsTable.setLinesVisible( true );
        contributorsTable.setHeaderVisible( true );
        
        ContributorsTableListener tableListener = new ContributorsTableListener();
        contributorsTable.addSelectionListener( tableListener );
        
        TableColumn nameColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 0 );
        nameColumn.setWidth( 75 );
        nameColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        TableColumn emailColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 1 );
        emailColumn.setWidth( 75 );
        emailColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Email );
        
        TableColumn urlColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 2 );
        urlColumn.setWidth( 90 );
        urlColumn.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        TableColumn orgColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 3 );
        orgColumn.setWidth( 90 );
        orgColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Organization );
        
        TableColumn orgUrlColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 4 );
        orgUrlColumn.setWidth( 100 );
        orgUrlColumn.setText( Messages.MavenPomEditor_MavenPomEditor_OrganizationUrl );
        
        
        TableColumn timezoneColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 5 );
        timezoneColumn.setWidth( 75 );
        timezoneColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Timezone );
        
        
        ModelUtil.bindTable(
        		pomModel, 
        		new EStructuralFeature[] { PomPackage.Literals.MODEL__CONTRIBUTORS, PomPackage.Literals.CONTRIBUTORS_TYPE__CONTRIBUTOR }, 
        		new EStructuralFeature[] { PomPackage.Literals.CONTRIBUTOR__NAME, PomPackage.Literals.CONTRIBUTOR__EMAIL, PomPackage.Literals.CONTRIBUTOR__URL, PomPackage.Literals.CONTRIBUTOR__ORGANIZATION, PomPackage.Literals.CONTRIBUTOR__ORGANIZATION_URL, PomPackage.Literals.CONTRIBUTOR__TIMEZONE }, 
        		contributorsTable, 
        		domain );
        
        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );
        
        addContributorButton = toolKit.createButton( container2, 
                  Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
        
        AddContributorButtonListener addButtonListener = new AddContributorButtonListener();
        addContributorButton.addSelectionListener( addButtonListener );
        
        editContributorButton = toolKit.createButton( container2, 
                  Messages.MavenPomEditor_MavenPomEditor_EditButton, SWT.PUSH | SWT.CENTER );
        
        EditContributorButtonListener editButtonListener = new EditContributorButtonListener();
        editContributorButton.addSelectionListener( editButtonListener );
        editContributorButton.setEnabled( false );
        
        removeContributorButton = toolKit.createButton( container2, 
                  Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH | SWT.CENTER );
        RemoveContributorButtonListener removeButtonListener = new RemoveContributorButtonListener();
        removeContributorButton.addSelectionListener( removeButtonListener );
        removeContributorButton.setEnabled( false );
        
        return container;
    }

    private class DevelopersTableListener extends SelectionAdapter
    {

        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = developersTable.getSelection();

            if ( ( items != null ) && ( items.length > 0 ) )
            {
                addDeveloperButton.setEnabled( true );
                editDeveloperButton.setEnabled( true );
                removeDeveloperButton.setEnabled( true );
                
                if( developersTable.getSelectionIndex() >= 0 )
                {
                    selectedDeveloperIndex = developersTable.getSelectionIndex();
                }
            }
        }

    }
    
    private class ContributorsTableListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = contributorsTable.getSelection();
            
            if ( ( items != null ) && ( items.length > 0 ) )
            {
                addContributorButton.setEnabled( true );
                editContributorButton.setEnabled( true );
                removeContributorButton.setEnabled( true );
                
                if ( contributorsTable.getSelectionIndex() >= 0 )
                {
                    selectedContributorIndex = contributorsTable.getSelectionIndex();
                }
            }
        }
    }    
    
    private class AddDeveloperButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            AddEditContributorDeveloperDialog addDialog = 
                AddEditContributorDeveloperDialog.newAddEditContributorDialog
                ( Messages.MavenPomEditor_MavenPomEditor_Developers );
            
            if ( addDialog.open() == Window.OK )
            {
                if ( !developerAlreadyExist( addDialog.getId() ) )
                {
                    Developer developer = PomFactory.eINSTANCE.createDeveloper();
                    
                    developer.setId( addDialog.getId() );
                    developer.setName( addDialog.getName() );
                    developer.setEmail( addDialog.getEmail() );
                    developer.setUrl( nullIfBlank( addDialog.getUrl() ) );
                    developer.setOrganization( nullIfBlank( addDialog.getOrganization() ) );
                    developer.setOrganizationUrl( nullIfBlank( addDialog.getOrganizationUrl() ) );
                    
                    if ( ( addDialog.getRoles() != null ) && ( addDialog.getRoles() != "" ) )
                    {
                        String roles = addDialog.getRoles();
                        
                        String[] roleArray = roles.split( "," );
                        List<String> roleList = (List<String>) ModelUtil.getValue( developer, new EStructuralFeature[]{ PomPackage.Literals.DEVELOPER__ROLES, PomPackage.Literals.ROLES_TYPE1__ROLE } , domain, true );
                        for ( String role : roleArray )
                        {
                        	roleList.add( role.trim() );
                        }
                    }
                    else
                    {
                        developer.setRoles( null );
                    }
                    
                    developer.setTimezone( nullIfBlank( addDialog.getTimezone() ) );
                    //TODO: developer.setProperties( nullIfSizeZero( addDialog.getProperties() ) );
                    
                    List<Developer> developersList = (List<Developer>) ModelUtil.getValue( pomModel, new EStructuralFeature[]{ PomPackage.Literals.MODEL__DEVELOPERS, PomPackage.Literals.DEVELOPERS_TYPE__DEVELOPER } , domain, true );
                    developersList.add( developer );
                }
            }
        }
    }
    
    private class EditDeveloperButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            List<Developer> developersList = (List<Developer>) ModelUtil.getValue( pomModel, new EStructuralFeature[]{ PomPackage.Literals.MODEL__DEVELOPERS, PomPackage.Literals.DEVELOPERS_TYPE__DEVELOPER } , domain, true );
            Developer selectedDeveloper = developersList.get( selectedDeveloperIndex );
            
            List<String> roleList = (List<String>) ModelUtil.getValue( selectedDeveloper, new EStructuralFeature[]{ PomPackage.Literals.DEVELOPER__ROLES, PomPackage.Literals.ROLES_TYPE1__ROLE } , domain, true );
            String oldRoles = roleList == null ? null : convertRolesListToString( roleList );
            AddEditContributorDeveloperDialog editDialog = 
                AddEditContributorDeveloperDialog.newAddEditContributorDialog( Messages.MavenPomEditor_MavenPomEditor_Developers );
                        
            if (editDialog.openWithItem( selectedDeveloper.getId(), selectedDeveloper.getName(), 
                                         selectedDeveloper.getEmail(), 
                                         blankIfNull( selectedDeveloper.getUrl() ), 
                                         blankIfNull( selectedDeveloper.getOrganization() ), 
                                         blankIfNull( selectedDeveloper.getOrganizationUrl() ), 
                                         blankIfNull( oldRoles ), 
                                         blankIfNull( selectedDeveloper.getTimezone() ),
                                         /*TODO: selectedDeveloper.getProperties() */ null ) == Window.OK )
            {             
                    
                Developer newDeveloper = PomFactory.eINSTANCE.createDeveloper();
                    
                newDeveloper.setId( editDialog.getId() );
                newDeveloper.setName( editDialog.getName() );
                newDeveloper.setEmail( editDialog.getEmail() );
                newDeveloper.setUrl( nullIfBlank( editDialog.getUrl() ) );
                newDeveloper.setOrganization( nullIfBlank( editDialog.getOrganization() ) );
                newDeveloper.setOrganizationUrl( nullIfBlank( editDialog.getOrganizationUrl() ) );
                newDeveloper.setTimezone( nullIfBlank( editDialog.getTimezone() ) );
                    
                if ( ( editDialog.getRoles() != null ) && ( editDialog.getRoles() != "" ) )
                {
                    String roles = editDialog.getRoles();
                        
                    String[] roleArray = roles.split( "," );
                    List<String> newRoleList = (List<String>) ModelUtil.getValue( newDeveloper, new EStructuralFeature[]{ PomPackage.Literals.DEVELOPER__ROLES, PomPackage.Literals.ROLES_TYPE1__ROLE } , domain, true );
                    for ( String role : roleArray )
                    {
                    	newRoleList.add( role.trim() );
                    }
                }
                else
                {
                    newDeveloper.setRoles( null );
                }
                
                //TODO: newDeveloper.setProperties( nullIfSizeZero( editDialog.getProperties() ) );
                
                //if ( ( developerAlreadyExist( newDeveloper.getId() ) ) ||
                //      ( !selectedDeveloper.getId().equalsIgnoreCase( newDeveloper.getId() ) ) )
                if ( developerAlreadyExist( newDeveloper.getId() ) )
                {
                    // old ID == new ID --> any field except ID was modified
                    if ( selectedDeveloper.getId().equalsIgnoreCase( newDeveloper.getId() ) )
                    {
                        developersList.remove( selectedDeveloper );
                        
                        developersList.add( newDeveloper );
                    }
                    // this means user put in a duplicate ID
                    else
                    {
                        MessageBox mesgBox = new MessageBox( form.getShell(), SWT.ICON_ERROR | SWT.OK  );
                        mesgBox.setMessage( "Developer ID already exists." );
                        mesgBox.setText( "Saving Developer Error" );
                        mesgBox.open( );
                    }
                }
                else // id was modified
                {
                    developersList.remove( selectedDeveloper );
                    
                    developersList.add( newDeveloper );
                } 
            }
        }
    }
    
    private class RemoveDeveloperButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
        	List<Developer> developersList = (List<Developer>) ModelUtil.getValue( pomModel, new EStructuralFeature[]{ PomPackage.Literals.MODEL__DEVELOPERS, PomPackage.Literals.DEVELOPERS_TYPE__DEVELOPER } , domain, true );
            for ( int index = 0; index < developersList.size(); index++ )
            {
                if ( index == developersTable.getSelectionIndex() )
                {
                    Developer developer = (Developer) developersList.get( index );
                    developersList.remove( developer );
                    removeDeveloperButton.setEnabled( false );
                    editDeveloperButton.setEnabled( false );
                }
            }            
        }
    }
    
    private class AddContributorButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            AddEditContributorDeveloperDialog addDialog = 
                AddEditContributorDeveloperDialog.newAddEditContributorDialog
                ( Messages.MavenPomEditor_MavenPomEditor_Contributors );
            
            if ( addDialog.open() == Window.OK )
            {
                if ( !contributorAlreadyExist( addDialog.getName(), addDialog.getEmail() ) )
                {
                
                    Contributor contributor = PomFactory.eINSTANCE.createContributor();
                
                    contributor.setName( addDialog.getName() );
                    contributor.setEmail( addDialog.getEmail() );
                    contributor.setUrl( nullIfBlank( addDialog.getUrl() ) );
                    contributor.setOrganization( nullIfBlank( addDialog.getOrganization() ) );
                    contributor.setOrganizationUrl( nullIfBlank( addDialog.getOrganizationUrl()) );
                
                    if ( ( addDialog.getRoles() != null ) && ( addDialog.getRoles() != "" ) )
                    {
                        String roles = addDialog.getRoles();
                    
                        String[] roleArray = roles.split( "," );
                        List<String> roleList = (List<String>) ModelUtil.getValue( contributor, new EStructuralFeature[]{ PomPackage.Literals.CONTRIBUTOR__ROLES, PomPackage.Literals.ROLES_TYPE__ROLE } , domain, true );
                        for ( String role : roleArray )
                        {
                        	roleList.add( role.trim() );
                        }
                    }
                    else
                    {
                        contributor.setRoles( null );
                    }
                
                    contributor.setTimezone( nullIfBlank( addDialog.getTimezone() ) );
                    //TODO: contributor.setProperties( nullIfSizeZero( addDialog.getProperties() ) );
                    
                    List<Contributor> contributorList = (List<Contributor>) ModelUtil.getValue( pomModel, new EStructuralFeature[]{ PomPackage.Literals.MODEL__CONTRIBUTORS, PomPackage.Literals.CONTRIBUTORS_TYPE__CONTRIBUTOR } , domain, true );
                    contributorList.add( contributor );
                }
            }
        }        
    }
    
    private class EditContributorButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
        	List<Contributor> contributorList = (List<Contributor>) ModelUtil.getValue( pomModel, new EStructuralFeature[]{ PomPackage.Literals.MODEL__CONTRIBUTORS, PomPackage.Literals.CONTRIBUTORS_TYPE__CONTRIBUTOR } , domain, true );
        	Contributor selectedContributor = contributorList.get( selectedContributorIndex );
        	
            List<String> roleList = (List<String>) ModelUtil.getValue( selectedContributor, new EStructuralFeature[]{ PomPackage.Literals.CONTRIBUTOR__ROLES, PomPackage.Literals.ROLES_TYPE__ROLE } , domain, true );
            String oldRoles = roleList == null ? null : convertRolesListToString( roleList );
            AddEditContributorDeveloperDialog editDialog = 
                AddEditContributorDeveloperDialog.newAddEditContributorDialog( Messages.MavenPomEditor_MavenPomEditor_Contributors );
                        
            if (editDialog.openWithItem( null, selectedContributor.getName(), 
                                         selectedContributor.getEmail(), 
                                         blankIfNull( selectedContributor.getUrl() ), 
                                         blankIfNull( selectedContributor.getOrganization() ), 
                                         blankIfNull( selectedContributor.getOrganizationUrl() ), 
                                         blankIfNull( oldRoles ), 
                                         blankIfNull( selectedContributor.getTimezone() ),
                                         /* TODO: selectedContributor.getProperties() */ null ) == Window.OK )
            {                    
                Contributor newContributor = PomFactory.eINSTANCE.createContributor();                    
                
                newContributor.setName( editDialog.getName() );
                newContributor.setEmail( editDialog.getEmail() );
                newContributor.setUrl( nullIfBlank( editDialog.getUrl() ) );
                newContributor.setOrganization( nullIfBlank( editDialog.getOrganization() ) );
                newContributor.setOrganizationUrl( nullIfBlank( editDialog.getOrganizationUrl() ) );
                newContributor.setTimezone( nullIfBlank( editDialog.getTimezone() ) );
                    
                if ( ( editDialog.getRoles() != null ) && ( editDialog.getRoles() != "" ) )
                {
                    String roles = editDialog.getRoles();
                        
                    String[] roleArray = roles.split( "," );
                    List<String> newRoleList = (List<String>) ModelUtil.getValue( selectedContributor, new EStructuralFeature[]{ PomPackage.Literals.CONTRIBUTOR__ROLES, PomPackage.Literals.ROLES_TYPE__ROLE } , domain, true );
                    for ( String role : newRoleList )
                    {
                    	newRoleList.add( role.trim() );
                    }
                }
                else
                {
                    newContributor.setRoles( null );
                }
                
                //TODO: newContributor.setProperties( nullIfSizeZero( editDialog.getProperties() ) );
                
                if ( contributorAlreadyExist( newContributor.getName(), newContributor.getEmail() ) )
                {
                    // this means that name and email were not modified
                    // check other fields
                    if ( ( !( blankIfNull( selectedContributor.getUrl() ).equals( blankIfNull(newContributor.getUrl() ) ) ) ) ||
                         ( !( blankIfNull( selectedContributor.getOrganization() ).equals( blankIfNull( newContributor.getOrganization() ) ) ) ) ||
                         ( !( blankIfNull( selectedContributor.getOrganizationUrl() ).equals( blankIfNull( newContributor.getOrganizationUrl() ) ) ) ) ||
                         ( ! (blankIfNull( oldRoles).equals( blankIfNull( editDialog.getRoles() ) ) ) ) ||
                         ( !( blankIfNull( selectedContributor.getTimezone() ).equals( blankIfNull( newContributor.getTimezone() ) ) ) ) 
                       )
                    {
                        contributorList.remove( selectedContributor );
                    
                        contributorList.add( newContributor );
                    }
                    else
                    {
                        MessageBox mesgBox = new MessageBox( form.getShell(), SWT.ICON_ERROR | SWT.OK  );
                        mesgBox.setMessage( "Contributor already exists." );
                        mesgBox.setText( "Saving Contributor Error" );
                        mesgBox.open( );
                    }
                }                
                else
                {
                    contributorList.remove( selectedContributor );
                    
                    contributorList.add( newContributor );
                }                
            }
        }
    }
    
    private class RemoveContributorButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
        	List<Contributor> contributorList = (List<Contributor>) ModelUtil.getValue( pomModel, new EStructuralFeature[]{ PomPackage.Literals.MODEL__CONTRIBUTORS, PomPackage.Literals.CONTRIBUTORS_TYPE__CONTRIBUTOR } , domain, true );
            for ( int index = 0; index < contributorList.size(); index++ )
            {
                if ( index == contributorsTable.getSelectionIndex() )
                {
                    Contributor contributor = ( Contributor ) contributorList.get( index );
                    contributorList.remove( contributor );
                    removeContributorButton.setEnabled( false );
                    editContributorButton.setEnabled( false );
                }
            }
        }
    }

    public boolean contributorAlreadyExist( String name, String email )
    {
    	List<Contributor> contributorList = (List<Contributor>) ModelUtil.getValue( pomModel, new EStructuralFeature[]{ PomPackage.Literals.MODEL__CONTRIBUTORS, PomPackage.Literals.CONTRIBUTORS_TYPE__CONTRIBUTOR } , domain, true );
        for ( Contributor contributor : contributorList )
        {
            if ( ( contributor.getName().equals( name ) ) &&
                 ( contributor.getEmail().equals( email ) ) )
            {
                return true;
            }
        }
        
        return false;
    }

    private boolean developerAlreadyExist( String id )
    {
    	List<Developer> developersList = (List<Developer>) ModelUtil.getValue( pomModel, new EStructuralFeature[]{ PomPackage.Literals.MODEL__DEVELOPERS, PomPackage.Literals.DEVELOPERS_TYPE__DEVELOPER } , domain, true );
        for ( Developer developer : developersList )
        {
            if ( developer.getId().equals( id ) )
            {
                return true;
            }
        }
        return false;
    }

    
    private String convertRolesListToString( List<String> rolesList )
    {   
        String roleString = "";
        int length = 0;
        
        for ( String role : rolesList )
        {
            roleString = roleString + role;
            length++;
            if ( length < rolesList.size() )
            {
                roleString = roleString + ", ";
            }
        }
        
        return roleString;
    }
    
    private String nullIfBlank(String str) 
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }
    
    private String blankIfNull( String str )
    {
        return str != null ? str : "";
    }

}