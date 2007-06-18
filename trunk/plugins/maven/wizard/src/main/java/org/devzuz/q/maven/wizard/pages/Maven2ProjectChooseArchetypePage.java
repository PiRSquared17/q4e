/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.pages;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.devzuz.q.maven.wizard.Messages;
import org.devzuz.q.maven.wizard.core.Archetype;
import org.devzuz.q.maven.wizard.core.Maven2ArchetypeManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

public class Maven2ProjectChooseArchetypePage
    extends Maven2ValidatingWizardPage
{
    private static final String DEFAULT_ARCHETYPE = "maven-archetype-quickstart";
    private static final String URL_REG_EX = "((http|https|ftp)://)?" +                             // for the optional http:// or https:// 
                                             "(localhost|" +                                        // for localhost
                                             "[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}|" + // or IP
                                             "([\\w]+\\.)*[\\w]+\\.[\\w]+)" +                       // or hostname 
                                             "(:[0-9]{1,5})?" +                                     // for the option port to use
                                             "(/[\\w.?%&=]*)*";                                     // for the optional path
    
    private List archetypeList;

    Map<String, Archetype> archetypeMap;

    private Label archetypeDescriptionLabel;
    
    private Button publishedArchetypesButton;
    
    private Group publishedArchetypesGroup;
    
    private Button customArchetypesButton;

    private Group customArchetypeGroup;
    
    private Text groupIDText;

    private Text artifactIDText;

    private Text versionText;
    
    private Text remoteRepositoryText;
    
    public Maven2ProjectChooseArchetypePage()
    {
        super( Messages.wizard_project_chooseArchetype_name );
        setTitle( Messages.wizard_project_chooseArchetype_title );
        setDescription( Messages.wizard_project_chooseArchetype_desc );
        setPageComplete( true );
    }

    public void createControl( Composite parent )
    {
        SelectionAdapter buttonListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                boolean publishedArchetypesEnabled = publishedArchetypesButton.getSelection();
                
                publishedArchetypesGroup.setEnabled( publishedArchetypesEnabled );
                setEnableChildren( publishedArchetypesGroup , publishedArchetypesEnabled );
                
                customArchetypeGroup.setEnabled( !publishedArchetypesEnabled );
                setEnableChildren( customArchetypeGroup , !publishedArchetypesEnabled );
                
                validate();
            }
        };
        
        ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };
        
        Composite container = new Composite( parent, SWT.NULL );
        container.setLayout( new GridLayout( 1, false ) );

        GridData gridData = new GridData();
        gridData.horizontalSpan = 1;
        
        publishedArchetypesButton = new Button( container, SWT.RADIO );
        publishedArchetypesButton.setText( Messages.wizard_project_chooseArchetype_group_label );
        publishedArchetypesButton.setLayoutData( new GridData( GridData.BEGINNING , GridData.CENTER , false , false , 3 , 1 ) );
        publishedArchetypesButton.addSelectionListener( buttonListener );
        
        publishedArchetypesGroup = new Group( container, SWT.NULL );
        publishedArchetypesGroup.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );

        FillLayout fillLayout = new FillLayout( SWT.HORIZONTAL );
        fillLayout.spacing = 10;
        publishedArchetypesGroup.setLayout( fillLayout );
        publishedArchetypesGroup.setText( "" );

        archetypeList = new List( publishedArchetypesGroup, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL );
        archetypeList.addSelectionListener( new SelectionListener()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetEvent( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                widgetEvent( e );
            }

        } );
        archetypeDescriptionLabel = new Label( publishedArchetypesGroup, SWT.WRAP );

        customArchetypesButton = new Button( container, SWT.RADIO );
        customArchetypesButton.setText( Messages.wizard_project_chooseArchetype_custom_label );
        customArchetypesButton.setLayoutData( new GridData( GridData.BEGINNING , GridData.CENTER , false , false , 3 , 1 ) );
        //customArchetypesButton.addSelectionListener( buttonListener );
        
        customArchetypeGroup = new Group( container, SWT.NULL );
        customArchetypeGroup.setLayout( new GridLayout( 2, false ) );
        customArchetypeGroup.setLayoutData( new GridData( GridData.FILL, SWT.BOTTOM , true, false ) );
        
        Label groupIDLabel = new Label( customArchetypeGroup, SWT.NULL );
        groupIDLabel.setText( Messages.wizard_project_archetypeInfo_arch_groupid_label );
        groupIDText = new Text( customArchetypeGroup, SWT.BORDER | SWT.SINGLE );
        groupIDText.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, false ) );
        groupIDText.addModifyListener( modifyingListener );

        Label artifactIDLabel = new Label( customArchetypeGroup, SWT.NULL );
        artifactIDLabel.setText( Messages.wizard_project_archetypeInfo_arch_artifactid_label );
        artifactIDText = new Text( customArchetypeGroup, SWT.BORDER | SWT.SINGLE );
        artifactIDText.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, false ) );
        artifactIDText.addModifyListener( modifyingListener );
        
        Label versionLabel = new Label( customArchetypeGroup, SWT.NULL );
        versionLabel.setText( Messages.wizard_project_archetypeInfo_arch_version_label );
        versionText = new Text( customArchetypeGroup, SWT.BORDER | SWT.SINGLE );
        versionText.setText( "" );
        versionText.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, false ) );
        
        Label remoteRepositoryLabel = new Label( customArchetypeGroup, SWT.NULL );
        remoteRepositoryLabel.setText( Messages.wizard_project_archetypeInfo_remoteRepo_label );
        remoteRepositoryText = new Text( customArchetypeGroup, SWT.BORDER | SWT.SINGLE );
        remoteRepositoryText.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, false ) );
        remoteRepositoryText.addModifyListener( modifyingListener );
        
        initialize();

        setControl( container );
    }

    @Override
    protected boolean isPageValid()
    {
        if( !publishedArchetypesButton.getSelection() )
        {
            if( artifactIDText.getText().trim().length() > 0 )
            {
                if( groupIDText.getText().trim().length() > 0 )
                {
                    if( validateUrl( remoteRepositoryText.getText().trim() ) )
                    {
                        return true;
                    }
                    else
                    {
                        setError( Messages.wizard_project_archetypeInfo_error_remote_url );
                    }
                }
                else
                {
                    setError( Messages.wizard_project_archetypeInfo_error_groupId );
                }
            }
            else
            {
                setError( Messages.wizard_project_archetypeInfo_error_artifactId );
            }
        }
        
        return false;
    }
    
    public Archetype getArchetype()
    {
        if( publishedArchetypesButton.getSelection() )
        {
            return archetypeMap.get( archetypeList.getSelection()[0] );
        }
        else
        {
            return new Archetype( artifactIDText.getText().trim(),
                                  groupIDText.getText().trim(), versionText.getText().trim(),
                                  remoteRepositoryText.getText().trim(),"" );
        }
    }

    private void initialize()
    {
        archetypeMap = Maven2ArchetypeManager.getArchetypes();
        for ( String key : archetypeMap.keySet() )
        {
            archetypeList.add( key );
        }

        archetypeList.select( archetypeList.indexOf( DEFAULT_ARCHETYPE ) );
        
        publishedArchetypesButton.setSelection(  true );
        setEnableChildren( publishedArchetypesGroup , true );
        setEnableChildren( customArchetypeGroup , false );
    }

    private void widgetEvent( SelectionEvent e )
    {
        archetypeDescriptionLabel.setText( archetypeMap.get( archetypeList.getSelection()[0] ).getDescription() );
        setPageComplete( true );
    }
    
    static private void setEnableChildren( Composite parent , boolean flag )
    {
        for( Control control : parent.getChildren() )
        {
            control.setEnabled( flag );
        }
    }
    
    static private boolean validateUrl( String urlStr )
    {
        // TODO : Im currently using a home-brewed URL RegEx pattern which myt have bugs,
        //        Any suggestions on how to properly validate a URL will be appreciated.
        Pattern urlPattern = Pattern.compile( URL_REG_EX );
        Matcher match = urlPattern.matcher( urlStr );
        return match.matches();
    }
}
