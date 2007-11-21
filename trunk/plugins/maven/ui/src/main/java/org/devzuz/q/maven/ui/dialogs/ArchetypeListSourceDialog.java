/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/

package org.devzuz.q.maven.ui.dialogs;

import java.net.MalformedURLException;
import java.net.URL;

import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.core.archetypeprovider.IArchetypeListProvider;
import org.devzuz.q.maven.ui.core.archetypeprovider.MavenArchetypeProviderManager;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class ArchetypeListSourceDialog extends AbstractResizableDialog
{
    private static ArchetypeListSourceDialog archetypeListSourceDialog = null;

    public static synchronized ArchetypeListSourceDialog getArchetypeListSourceDialog()
    {
        if ( archetypeListSourceDialog == null )
        {
            archetypeListSourceDialog =
                new ArchetypeListSourceDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
        }

        return archetypeListSourceDialog;
    }

    public ArchetypeListSourceDialog( IShellProvider parentShell )
    {
        super( parentShell );
    }

    public ArchetypeListSourceDialog( Shell parentShell )
    {
        super( parentShell );
    }

    private Text archetypeListSourceText;

    private Combo typeText;

    private String archetypeListSource = "";

    private String type = "";

    public String getArchetypeListSource()
    {
        return archetypeListSource;
    }

    public void setArchetypeListSource( String archetypeListSource )
    {
        this.archetypeListSource = archetypeListSource;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    @Override
    protected Control internalCreateDialogArea( Composite container )
    {
        ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };

        container.setLayout( new GridLayout( 2, false ) );

        // Key and type Label and Text
        Label label = new Label( container, SWT.NULL );
        label.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label.setText( Messages.MavenArchetypePreferencePage_sourceurl );

        archetypeListSourceText = new Text( container, SWT.BORDER | SWT.SINGLE );
        archetypeListSourceText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        archetypeListSourceText.addModifyListener( modifyingListener );

        Label label2 = new Label( container, SWT.NULL );
        label2.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label2.setText( Messages.MavenArchetypePreferencePage_type );

        typeText = new Combo( container, SWT.READ_ONLY );
        typeText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        // typeText.addModifyListener( modifyingListener );

        IArchetypeListProvider[] providers = MavenArchetypeProviderManager.getArchetypeListProviders();

        for ( IArchetypeListProvider provider : providers )
        {
            typeText.add( provider.getProviderName() );
        }

        return container;
    }

    @Override
    public void onWindowActivate()
    {
        archetypeListSourceText.setText( archetypeListSource );
        if ( !type.trim().equals( "" ) )
            typeText.setText( type );
        else
            typeText.select( 0 );
        validate();
    }

    @Override
    protected void okPressed()
    {
        archetypeListSource = archetypeListSourceText.getText().trim();
        type = typeText.getText();

        super.okPressed();
    }

    public int openWithEntry( String archetypeListSource, String type )
    {
        setArchetypeListSource( archetypeListSource );
        setType( type );

        return super.open();
    }

    private static boolean isValidURL( String strURL )
    {
        try
        {
            URL url = new URL( strURL );
            String protocol = url.getProtocol();
            return "http".equals( protocol ) || "https".equals( protocol ) || "ftp".equals( protocol ) || "file".equals( protocol );
        }
        catch ( MalformedURLException e )
        {
            return false;
        }
    }

    public void validate()
    {
        getButton( IDialogConstants.OK_ID ).setEnabled( didValidate() );
    }

    private boolean didValidate()
    {
        return ( ( archetypeListSourceText.getText().trim().length() > 0 && isValidURL( archetypeListSourceText.getText() ) ) && ( typeText.getText().length() > 0 ) );
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return MavenUiActivator.getDefault().getPluginPreferences();
    }
}
