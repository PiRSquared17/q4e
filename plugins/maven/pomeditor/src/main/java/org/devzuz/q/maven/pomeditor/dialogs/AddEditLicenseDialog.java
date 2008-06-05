/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.dialogs;

import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.dialogs.AbstractResizableDialog;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class AddEditLicenseDialog extends AbstractResizableDialog
{
    public static AddEditLicenseDialog newAddEditLicenseDialog()
    {
        return new AddEditLicenseDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
    }

    private String name;

    private String distribution;

    private String url;

    private String comment;

    private Text nameText;

    private Text distributionText;

    private Text urlText;

    private Text commentText;

    public AddEditLicenseDialog( Shell shell )
    {
        super( shell );
    }

    protected Control internalCreateDialogArea( Composite container )
    {
        ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };

        container.setLayout( new GridLayout( 3, false ) );

        Label label = new Label( container, SWT.NULL );
        label.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label.setText( Messages.MavenPomEditor_MavenPomEditor_Name );

        nameText = new Text( container, SWT.BORDER | SWT.SINGLE );
        nameText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
 
        Label label3 = new Label( container, SWT.NULL );
        label3.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label3.setText( Messages.MavenPomEditor_MavenPomEditor_URL );

        urlText = new Text( container, SWT.BORDER | SWT.SINGLE );
        urlText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
 
        Label label2 = new Label( container, SWT.NULL );
        label2.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label2.setText( Messages.MavenPomEditor_MavenPomEditor_Distribution );

        distributionText = new Text( container, SWT.BORDER | SWT.SINGLE );
        distributionText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        
        Label label4 = new Label( container, SWT.NULL );
        label4.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label4.setText( Messages.MavenPomEditor_MavenPomEditor_Comment );

        commentText = new Text( container, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL );
        GridData grid = new GridData( GridData.FILL_HORIZONTAL );
        grid.heightHint = 40;
        commentText.setLayoutData( grid );
        
        syncModelToControls();
        
        urlText.addModifyListener( modifyingListener );
        nameText.addModifyListener( modifyingListener );
        commentText.addModifyListener( modifyingListener );
        distributionText.addModifyListener( modifyingListener );
        
        return container;
    }
    
    @Override
    protected Control createButtonBar( Composite parent )
    {
        Control bar = super.createButtonBar( parent );
        validate();
        return bar; 
    }
    
    public int openWithLicense( String name , String distribution , String url , String comment )
    {
        this.name = name;
        this.distribution = distribution;
        this.url = url;
        this.comment = comment;
        return open();
    }
    
    private void syncModelToControls()
    {
        nameText.setText( blankIfNull( name ) );
        distributionText.setText( blankIfNull( distribution ) );
        urlText.setText( blankIfNull( url ) );
        commentText.setText( blankIfNull( comment ) );
    }

    public void onWindowActivate()
    {
        validate();
    }

    public void validate()
    {
        if ( didValidate() )
        {
            getButton( IDialogConstants.OK_ID ).setEnabled( true );
        }
        else
        {
            getButton( IDialogConstants.OK_ID ).setEnabled( false );
        }
    }

    protected void okPressed()
    {
        name = nameText.getText().trim();
        distribution = distributionText.getText().trim();
        url = urlText.getText().trim();
        comment = commentText.getText().trim();

        super.okPressed();
    }

    public boolean didValidate()
    {
        if ( ( isNotNullOrWhiteSpace( nameText.getText() ) ) )
        {
            return false;
        }
        return true;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getDistribution()
    {
        return distribution;
    }

    public void setDistribution( String distribution )
    {
        this.distribution = distribution;
    }

    public String getURL()
    {
        return url;
    }

    public void setURL( String url )
    {
        this.url = url;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment( String comment )
    {
        this.comment = comment;
    }

    private boolean isNotNullOrWhiteSpace( String str )
    {
        return ( str == null || str.trim().length() == 0 );
    }
    
    private String blankIfNull( String str )
    {
        return str == null ? "" : str;
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return MavenUiActivator.getDefault().getPluginPreferences();
    }

}