/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import java.util.List;

import org.apache.maven.model.License;
import org.apache.maven.model.Model;
import org.apache.maven.model.Organization;
import org.apache.maven.model.Scm;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditLicenseDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * @author gbaal
 * 
 */
public class MavenPomLicensesScmOrgFormPage extends FormPage
{
    private ScrolledForm form;

    private Table propertiesTable;

    private Button newPropertyButton;

    private Button removePropertyButton;

    private Button editPropertyButton;

    private Model pomModel;

    private List<License> licenseList;
    
    private License selectedLicense;

    private Scm scm;
    
    private Organization organization;
    
    private boolean isPageModified;

    private Text connectionText;

    private Text developerConnectionText;

    private Text tagText;

    private Text urlText;
    
    private Text nameText;

    private Text organizationUrlText;

    public MavenPomLicensesScmOrgFormPage( String id, String title )
    {
        super( id, title );
    }

    @SuppressWarnings( "unchecked" )
    public MavenPomLicensesScmOrgFormPage( FormEditor editor, String id, String title, Model modelPOM )
    {
        super( editor, id, title );
        this.pomModel = modelPOM;
        this.licenseList = modelPOM.getLicenses();
        this.organization = modelPOM.getOrganization();
        this.scm = modelPOM.getScm();
    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();

        form.getBody().setLayout( new GridLayout( 2, false ) );

        GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );

        Section licenseTable =
            toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        licenseTable.setDescription( "Set the License of this POM." );
        licenseTable.setText( "License" );
        licenseTable.setLayoutData( layoutData );
        licenseTable.setClient( createLicenseTableControls( licenseTable, toolkit ) );

        Composite container = toolkit.createComposite( form.getBody() );
        container.setLayoutData( layoutData );
        createScmSectionControls( container, toolkit );
        
        populateLicenseDatatable();
        syncModelToControls();
    }

    private Control createLicenseTableControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent );

        container.setLayout( new GridLayout( 2, false ) );

        propertiesTable = toolKit.createTable( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        propertiesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        propertiesTable.setLinesVisible( true );
        propertiesTable.setHeaderVisible( true );
        PropertiesTableListener tableListener = new PropertiesTableListener();
        propertiesTable.addSelectionListener( tableListener );

        TableColumn column = new TableColumn( propertiesTable, SWT.BEGINNING, 0 );
        column.setWidth( 220 );
        column.setText( "License" );
        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );
        
        ButtonListener buttonListener = new ButtonListener();

        newPropertyButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
        newPropertyButton.addSelectionListener( buttonListener );
        
        editPropertyButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_EditButton, SWT.PUSH | SWT.CENTER );
        editPropertyButton.setEnabled( false );
        editPropertyButton.addSelectionListener( buttonListener );
        
        removePropertyButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH | SWT.CENTER );
        removePropertyButton.setEnabled( false );
        removePropertyButton.addSelectionListener( buttonListener );
        
        return container;
    }
    
    private Control createScmSectionControls( Composite container, FormToolkit toolkit )
    {
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section scmControls =
            toolkit.createSection( container, Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        scmControls.setDescription( "This section contains informations required to the SCM (Source Control Management) of the project." );
        scmControls.setText( "SCM (Source Control Management)" );
        scmControls.setClient( createScmControls( scmControls, toolkit ) );

        Section organizationControls =
            toolkit.createSection( container, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED |
                            Section.DESCRIPTION );
        organizationControls.setDescription( "This section specifies the organization that produces this project." );
        organizationControls.setText( "Organization" );
        organizationControls.setClient( createOrganizationControls( organizationControls, toolkit ) );

        return container;
    }
    
    public Control createScmControls( Composite form, FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2, false ) );

        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 120;
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;

        Label connectionLabel =
            toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Connection, SWT.NONE );
        connectionLabel.setLayoutData( labelData );

        connectionText = toolKit.createText( parent, "" );
        createTextDisplay( connectionText, controlData );
        
        Label developerConnectionLabel =
            toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_DeveloperConnection, SWT.NONE );
        developerConnectionLabel.setLayoutData( labelData );

        developerConnectionText = toolKit.createText( parent, "" );
        createTextDisplay( developerConnectionText, controlData );
        
        Label tagLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Tag, SWT.NONE );
        tagLabel.setLayoutData( labelData );

        tagText = toolKit.createText( parent, "" );
        createTextDisplay( tagText, controlData );
        
        Label urlLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NONE );
        urlLabel.setLayoutData( labelData );

        urlText = toolKit.createText( parent, "" );
        createTextDisplay( urlText, controlData );
        
        toolKit.paintBordersFor( parent );

        return parent;
    }

    public Control createOrganizationControls( Composite form, FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2, false ) );

        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 30;
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;

        Label nameLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Name, SWT.NONE );
        nameLabel.setLayoutData( labelData );

        nameText = toolKit.createText( parent, "" );
        createTextDisplay( nameText, controlData );
        
        Label organizationUrlLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NONE );
        organizationUrlLabel.setLayoutData( labelData );

        organizationUrlText = toolKit.createText( parent, "" );
        createTextDisplay( organizationUrlText, controlData );
        
        toolKit.paintBordersFor( parent );

        return parent;
    }
    
    private void createTextDisplay( final Text text, GridData controlData )
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
            text.addModifyListener( modifyingListener );
        }
    }
    
    private void populateLicenseDatatable()
    {
        propertiesTable.removeAll();
        for ( License license : licenseList )
        {
            TableItem item = new TableItem( propertiesTable, SWT.BEGINNING );
            item.setText( new String[] { license.getName() } );
        }
    }
    
    private void syncModelToControls()
    {
        if ( scm != null )
        {
            connectionText.setText( blankIfNull( scm.getConnection() ) );
            developerConnectionText.setText( blankIfNull( scm.getDeveloperConnection() ) );
            tagText.setText( blankIfNull( scm.getTag() ) );
            urlText.setText( blankIfNull( scm.getUrl() ) );
        }

        if ( organization != null )
        {
            nameText.setText( blankIfNull( organization.getName() ) );
            organizationUrlText.setText( blankIfNull( organization.getUrl() ) );
        }
    }

    private void syncControlsToModel()
    {
        if ( connectionText.getText().trim().equals( "" ) && 
             developerConnectionText.getText().trim().equals( "" ) &&
             tagText.getText().trim().equals( "" ) && 
             urlText.getText().trim().equals( "" ) )
        {
            scm = null;
            pomModel.setScm( null );
        }
        else
        {
            if ( scm == null )
            {
                scm = new Scm();
                pomModel.setScm( scm );
            }
            
            scm.setConnection( nullIfBlank( connectionText.getText().trim() ) );
            scm.setDeveloperConnection( nullIfBlank( developerConnectionText.getText().trim() ) );
            scm.setTag( nullIfBlank( tagText.getText().trim() ) );
            scm.setUrl( nullIfBlank( urlText.getText().trim() ) );
        }

        if ( nameText.getText().trim().equals( "" ) && 
             organizationUrlText.getText().trim().equals( "" ) )
        {
            organization = null;
            pomModel.setOrganization( null );
        }
        else
        {
            if ( organization == null )
            {
                organization = new Organization();
                pomModel.setOrganization( organization );
            }
            organization.setName( nullIfBlank( nameText.getText().trim() ) );
            organization.setUrl( nullIfBlank( organizationUrlText.getText().trim() ) );
        }
    }

    private String nullIfBlank( String str )
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }
    
    private String blankIfNull( String str )
    {
        return str == null ? "" : str;
    }

    private class PropertiesTableListener extends SelectionAdapter
    {
        public int selection;

        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = propertiesTable.getSelection();

            if ( ( items != null ) && ( items.length > 0 ) )
            {
                removePropertyButton.setEnabled( true );
                editPropertyButton.setEnabled( true );
                int selectedIndex = propertiesTable.getSelectionIndex(); 
                if ( selectedIndex >= 0 )
                {
                    selectedLicense = licenseList.get( selectedIndex );
                }
            }
        }

    }

    private class ButtonListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent event )
        {
            widgetSelected( event );
        }

        public void widgetSelected( SelectionEvent event )
        {
            Widget widget = event.widget;
            
            if ( widget.equals( removePropertyButton ) )
            {
                licenseList.remove( selectedLicense );
                resetControlsState();
            }
            else if(  widget.equals( editPropertyButton ) )
            {
                AddEditLicenseDialog editDialog = AddEditLicenseDialog.newAddEditLicenseDialog();
                if( editDialog.openWithLicense( selectedLicense.getName(), 
                                                selectedLicense.getDistribution(), 
                                                selectedLicense.getUrl(), 
                                                selectedLicense.getComments() ) == Window.OK )
                {
                    selectedLicense.setName( editDialog.getName() );
                    selectedLicense.setUrl( editDialog.getURL() );
                    selectedLicense.setDistribution( editDialog.getDistribution() );
                    selectedLicense.setComments( editDialog.getComment() );
                    
                    resetControlsState();
                }
            }
            else if ( widget.equals( newPropertyButton ) )
            {
                License license = new License();
                AddEditLicenseDialog addDialog = AddEditLicenseDialog.newAddEditLicenseDialog();

                if ( addDialog.open() == Window.OK )
                {
                    license.setName( addDialog.getName() );
                    license.setUrl( addDialog.getURL() );
                    license.setDistribution( addDialog.getDistribution() );
                    license.setComments( addDialog.getComment() );
                    if ( isValidLicense( license ) && !duplicateLicense( license ) )
                    {
                        licenseList.add( license );
                    }

                    resetControlsState();
                }
            }
        }

        private void resetControlsState()
        {
            clear();
            populateLicenseDatatable();
            pageModified();
        }
    }
    
    private boolean isValidLicense( License license )
    {
        boolean flag = true;

        if ( license == null || 
             license.getName() == null ||
             license.getName().trim().length() <= 0 )
        {
            flag = false;
        }
        
        return flag;
    }
    
    private boolean duplicateLicense( License l )
    {
        boolean flag = false;
        if ( licenseList.contains( l ) )
        {
            flag = true;
        }
        else
        {
            for ( License license : licenseList )
            {
                flag = license.getName().equalsIgnoreCase( l.getName() );
                if ( flag )
                {
                    break;
                }
            }
        }
        if ( flag )
        {
            flag =
                !MessageDialog.openConfirm( form.getShell(), "License Error",
                                            Messages.MavenPomEditor_MavenPomEditor_DuplicateLicense );
        }
        return flag;
    }
    
    public void clear()
    {
        propertiesTable.deselect( propertiesTable.getSelectionIndex() );
        removePropertyButton.setEnabled( false );
        editPropertyButton.setEnabled( false );
    }
    
    /**
     * @return the isDirty
     */
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
