package org.devzuz.q.maven.pomeditor.components;

import org.apache.maven.model.PluginExecution;
import org.devzuz.q.maven.pomeditor.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PluginExecutionComponent extends AbstractComponent 
{
	private Text idText;
	
	private Text phaseText;
	
	private Button inheritedRadioButton;
	
	private IComponentModificationListener componentListener;
	
	private boolean isModifiedFlag;

    private PluginExecution pluginExecution;

	public PluginExecutionComponent ( Composite parent, int style,
	                                  IComponentModificationListener componentListener)
	{
		super( parent, style );
		
		this.componentListener = componentListener;
		
		setLayout( new GridLayout( 2, false ) );
		
		GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false );
		labelData.widthHint = 50;
		GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false );
		controlData.horizontalIndent = 10;
		
		Label idLabel = new Label( this, SWT.NULL );
		idLabel.setLayoutData( labelData );
		idLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
		
		idText = new Text( this, SWT.BORDER | SWT.SINGLE );
		idText.setLayoutData( controlData );
		
		Label phaseLabel = new Label( this, SWT.NULL );
		phaseLabel.setLayoutData( labelData );
		phaseLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Phase );
		
		phaseText = new Text( this, SWT.BORDER | SWT.SINGLE );
		phaseText.setLayoutData( controlData );
		
		Label inheritedLabel = new Label( this, SWT.NULL );
        inheritedLabel.setLayoutData(labelData );
        inheritedLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Inherited );
        
        inheritedRadioButton = new Button( this, SWT.CHECK );
        inheritedRadioButton.setLayoutData( controlData );
        
        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                if ( isModifiedFlag() )
                {
                    pluginExecution.setId( nullIfBlank( getId() ) );
                    pluginExecution.setPhase( nullIfBlank( getPhase() ) );
                    
                    notifyListeners( e.widget );
                }
            }
        };
        
        idText.addModifyListener( listener );
        phaseText.addModifyListener( listener );
        
        SelectionListener selectionListener = new SelectionListener()
        {
            public void widgetSelected( SelectionEvent arg0 )
            {
                if ( isModifiedFlag() )
                {
                    if ( isInherited() == true )
                    {
                        pluginExecution.setInherited( "true" );
                    }
                    else
                    {
                        pluginExecution.setInherited( "false" );
                    }
                    
                    notifyListeners( arg0.widget );
                }
            }
            
            public void widgetDefaultSelected( SelectionEvent arg0 )
            {
                widgetSelected( arg0 );
            }
        };
        
        inheritedRadioButton.addSelectionListener( selectionListener );
	}
	
	public void updateComponent( PluginExecution execution )
	{
	    this.pluginExecution = execution;
	    
	    setModifiedFlag( false );
	    
	    setId( blankIfNull( execution.getId() ) );
	    setPhase( blankIfNull( execution.getPhase() ) );
	    if ( ( execution.getInherited() != null ) &&
	         ( execution.getInherited().equalsIgnoreCase( "true" ) ) )
	    {
	        setInherited( true );
	    }
	    else
	    {
	        setInherited( false );
	    }
	    
	    addComponentModifyListener( componentListener );
	    
	    setModifiedFlag( true );
	}
	
	public String getId() {
		return idText.getText().trim();
	}

	public void setId(String id) {
	    idText.setText( blankIfNull( id ) );
	}

	public String getPhase() {
		return phaseText.getText().trim();
	}

	public void setPhase(String phase) {
		phaseText.setText( blankIfNull( phase ) );
	}

	public boolean isInherited() {
		return inheritedRadioButton.getSelection();
	}

	public void setInherited(boolean inherited) {
		inheritedRadioButton.setSelection( inherited );
	}
	
	public boolean isModifiedFlag()
    {
        return isModifiedFlag;
    }

    public void setModifiedFlag( boolean isModifiedFlag )
    {
        this.isModifiedFlag = isModifiedFlag;
    }

    private String blankIfNull( String str )
    {
        return str != null ? str : "";
    }
    
    private String nullIfBlank(String str) 
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }
}