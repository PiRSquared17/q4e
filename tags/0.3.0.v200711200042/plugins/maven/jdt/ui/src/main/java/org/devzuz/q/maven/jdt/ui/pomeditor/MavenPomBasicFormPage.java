/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.pomeditor;

import org.devzuz.q.maven.jdt.ui.Messages;
import org.apache.maven.model.Model;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import javax.swing.event.HyperlinkEvent;

public class MavenPomBasicFormPage extends FormPage
{
    private ScrolledForm form;
    
    private String groupID;
    
    private String artifactID;
    
    private String version;
    
    private String packaging;
    
    private String classifier;
    
    private String name;
    
    private String description;
    
    private String url;
    
    private String inceptionYear;
    
    private String parenPOMgroupID;
    
    private String parentPOMArtifactID;
    
    private String parentPOMVersion;
    
    private String parentPOMRelPath;
    
    private Model modelPOM;

    public MavenPomBasicFormPage( FormEditor editor, String id, String title, Model modelPOM )
    {
        super( editor, id, title );
        this.modelPOM = modelPOM;
        
        setPOMEditorProjectInfomation();
        
    }
    
    public MavenPomBasicFormPage( String id, String title )
    {
        super( id, title );
    }       

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        ExpansionAdapter expansionAdapter = new ExpansionAdapter() 
        {
            public void expansionStateChanged(ExpansionEvent e) 
            {
                form.reflow( true );
            }
        };
        
        form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        
        form.getBody().setLayout( new GridLayout( 2 , false ) );
        
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        
        Section basicCoordinateControls = toolkit.createSection( form.getBody() , Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        basicCoordinateControls.setDescription( "These basic informations act like a coordinate system for Maven projects." );
        basicCoordinateControls.setText( Messages.MavenPomEditor_MavenPomEditor_BasicInformation );
        basicCoordinateControls.setLayoutData( layoutData );
        basicCoordinateControls.setClient( createBasicCoordinateControls( basicCoordinateControls , toolkit ) );
        
        Section linkControls = toolkit.createSection( form.getBody() , Section.TITLE_BAR | Section.EXPANDED );
        linkControls.setText( Messages.MavenPomEditor_MavenPomEditor_Links ); 
        linkControls.setLayoutData( layoutData );
        linkControls.setClient( createLinkControls( linkControls , toolkit ) );
        
        Section moreProjectInfoControls = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION );
        moreProjectInfoControls.setDescription( "Add more project information to this POM." );
        moreProjectInfoControls.setText( Messages.MavenPomEditor_MavenPomEditor_MoreProjInfo );
        moreProjectInfoControls.setLayoutData( layoutData );
        moreProjectInfoControls.setClient( createMoreProjectInfoControls( moreProjectInfoControls , toolkit ) );
        
        Section parentProjectControls = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION );
        parentProjectControls.setDescription( "Add a parent POM whose elements are inherited this POM." );
        parentProjectControls.setText( Messages.MavenPomEditor_MavenPomEditor_ParentPOM );
        parentProjectControls.setLayoutData( layoutData );
        parentProjectControls.setClient( createParentProjectControls( parentProjectControls , toolkit ) );
        
        parentProjectControls.addExpansionListener( expansionAdapter );
        moreProjectInfoControls.addExpansionListener( expansionAdapter );        
    }
    
    public Control createBasicCoordinateControls( Composite form , FormToolkit toolKit )
    {
          
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2 , false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 50;
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        
            
        Label groupIdLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_GroupId , SWT.NONE ); 
        groupIdLabel.setLayoutData( labelData );
        
        Text groupIdText = toolKit.createText( parent, "" ); 
        this.createTextDisplay( groupIdText, controlData );
        groupIdText.setText( getGroupID() );    
   
        Label artifactIdLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_ArtifactId, SWT.NONE ); 
        artifactIdLabel.setLayoutData( labelData );
        
        Text artifactIdText = toolKit.createText( parent, "" ); 
        this.createTextDisplay( artifactIdText, controlData );
        artifactIdText.setText( getArtifactID());      

        Label versionLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Version, SWT.NONE ); 
        versionLabel.setLayoutData( labelData );

        Text versionText = toolKit.createText( parent, "" ); 
        this.createTextDisplay( versionText, controlData );
        versionText.setText( getVersion());           

        Label packagingLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Packaging, SWT.NONE ); 
        packagingLabel.setLayoutData( labelData );
       
        Text packagingText = toolKit.createText( parent, ""  ); 
        this.createTextDisplay( packagingText, controlData );
        packagingText.setText( getPackaging() );
        
        Label classifierLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Classifier, SWT.NONE ); 
        classifierLabel.setLayoutData( labelData );       
        
        Text classifierText = toolKit.createText( parent, ""  ); 
        this.createTextDisplay( classifierText, controlData );
        classifierText.setText( getClassifier() ); 
        
        toolKit.paintBordersFor(parent);
        
        return parent;
    }
    
    public Control createLinkControls( Composite form , FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new RowLayout( SWT.VERTICAL ) );
        
        Hyperlink dependencieslink = toolKit.createHyperlink( parent, "Add/Modify/Remove Dependencies", SWT.WRAP );
        dependencieslink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                System.out.println( "Link activated!" );
            }
        } );
        
        dependencieslink.setText( "Add/Modify/Remove Dependencies" );
        
        Hyperlink licensesLink = toolKit.createHyperlink( parent, "Manage Licenses", SWT.WRAP );
        licensesLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                System.out.println( "Link activated!" );
            }
        } );
        
        licensesLink.setText( "Manage Licenses" );
        
        Hyperlink developersLink = toolKit.createHyperlink( parent, "Manage Developers Information", SWT.WRAP );
        developersLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                System.out.println( "Link activated!" );
            }
        } );
        
        developersLink.setText( "Manage Developers Information" );
        
        Hyperlink contributorsLink = toolKit.createHyperlink( parent, "Manage Contributors Information", SWT.WRAP );
        contributorsLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                System.out.println( "Link activated!" );
            }
        } );
        
        contributorsLink.setText( "Manage Contributors Information" );
        
        return parent;
    }
    
    public Control createMoreProjectInfoControls( Composite form , FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2 , false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 70;
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        
        Label nameLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Name , SWT.NONE ); 
        nameLabel.setLayoutData( labelData );
        
        Text nameText = toolKit.createText( parent, "Name" ); 
        this.createTextDisplay( nameText, controlData );
        nameText.setText(getName());
        
        Label descriptionLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Description, SWT.NONE ); 
        descriptionLabel.setLayoutData( labelData );
        
        Text descriptionText = toolKit.createText( parent, "Description" ); 
        this.createTextDisplay( descriptionText, controlData );
        descriptionText.setText(getDescription());
        
        Label urlLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NONE ); 
        urlLabel.setLayoutData( labelData );
        
        Text urlText = toolKit.createText( parent, "URL" ); 
        this.createTextDisplay( urlText, controlData );
        urlText.setText(getUrl());
        
        Label inceptionYearLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_InceptionYear, SWT.NONE ); 
        inceptionYearLabel.setLayoutData( labelData );
        
        
        Text inceptionYearText = toolKit.createText( parent, "Inception Year" ); 
        this.createTextDisplay( inceptionYearText, controlData );
        inceptionYearText.setText(getInceptionYear());
        
        toolKit.paintBordersFor(parent);
        
        return parent;
        
    }
    
    public Control createParentProjectControls( Composite form , FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2 , false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 70;
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        
        Label groupIdLabel = toolKit.createLabel( parent, "Group Id" , SWT.NONE ); 
        groupIdLabel.setLayoutData( labelData );
        
        Text groupIdText = toolKit.createText( parent, "groupId" ); 
        this.createTextDisplay( groupIdText, controlData );      
        groupIdText.setText(getParenPOMgroupID());
        
        Label artifactIdLabel = toolKit.createLabel( parent, "Artifact Id", SWT.NONE ); 
        artifactIdLabel.setLayoutData( labelData );
        
        Text artifactIdText = toolKit.createText( parent, "artifactId" ); 
        this.createTextDisplay( artifactIdText, controlData );
        artifactIdText.setText(getParentPOMArtifactID());
        
        Label versionLabel = toolKit.createLabel( parent, "Version", SWT.NONE ); 
        versionLabel.setLayoutData( labelData );
        
        Text versionText = toolKit.createText( parent, "Version" ); 
        this.createTextDisplay( versionText, controlData );
        versionText.setText(getParentPOMVersion());
        
        
        Label relativePathLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_RelativePath, SWT.NONE ); 
        relativePathLabel.setLayoutData( labelData );
        
        Text relativePathText = toolKit.createText( parent, "Relative Path" ); 
        this.createTextDisplay( relativePathText, controlData );
        relativePathText.setText(getParentPOMRelPath());
        
        toolKit.paintBordersFor(parent);
        
        return  parent;
    }
    
    private void createTextDisplay(Text text, GridData controlData)
    {
        if(text != null)
        {
            text.setLayoutData( controlData );
            text.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        }
    }
    
    private void setPOMEditorProjectInfomation()
    {
        //sets data to be used in createBasicCoordinateControls
        this.setGroupID(modelPOM.getGroupId());
        this.setArtifactID(modelPOM.getArtifactId());
        this.setVersion(modelPOM.getVersion());
        this.setPackaging(modelPOM.getPackaging());
        this.setClassifier("");
        
        //sets data to be used in createMoreProjectInfoControls
        this.setName(checkStringIfNull(modelPOM.getName()));        
        this.setDescription(checkStringIfNull(modelPOM.getDescription()));
        this.setUrl(checkStringIfNull(modelPOM.getUrl()));
        this.setInceptionYear(checkStringIfNull(modelPOM.getInceptionYear()));    
        
        //sets data to be used in createParentProjectControls
        if( modelPOM.getParent() != null)
        {
            this.setParenPOMgroupID(checkStringIfNull(modelPOM.getParent().getGroupId()));
            this.setParentPOMArtifactID(checkStringIfNull(modelPOM.getParent().getArtifactId()));
            this.setParentPOMVersion(checkStringIfNull(modelPOM.getParent().getVersion()));
            this.setParentPOMRelPath(checkStringIfNull(modelPOM.getParent().getRelativePath()));
        }
        else
        {
            this.setParenPOMgroupID("");
            this.setParentPOMArtifactID("");
            this.setParentPOMVersion("");
            this.setParentPOMRelPath("");
        }
    }
    
    private String checkStringIfNull(String strTemp)
    {
    	if(null != strTemp )
    	{
    		return strTemp;
    	}
    	else
    	{
    		return "";
    	}
    }

    public String getGroupID()
    {
        return groupID;
    }

    private void setGroupID( String groupID )
    {
        this.groupID = groupID;
    }

    public String getArtifactID()
    {
        return artifactID;
    }

    private void setArtifactID( String artifactID )
    {
        this.artifactID = artifactID;
    }
    
    public String getVersion()
    {
        return version;
    }

    private void setVersion( String version )
    {
        this.version = version;
    }
    
    public String getPackaging()
    {
        return packaging;
    }

    private void setPackaging( String packaging )
    {
        this.packaging = packaging;
    }
    
    public String getClassifier()
    {
        return classifier;
    }

    public void setClassifier( String classifier )
    {
        this.classifier = classifier;
    }

	public String getName() {
		return name;
	}

	private void setName(String name) 
	{
		this.name = name;
	}

	public String getDescription() 
	{
		return description;
	}

	private void setDescription(String description) 
	{
		this.description = description;
	}

	public String getUrl() 
	{
		return url;
	}

	private void setUrl(String url) 
	{
		this.url = url;
	}

	public String getInceptionYear() 
	{
		return inceptionYear;
	}

	private void setInceptionYear(String inceptionYear) 
	{
		this.inceptionYear = inceptionYear;
	}

	public String getParenPOMgroupID() 
	{
		return parenPOMgroupID;
	}

	private void setParenPOMgroupID(String parenPOMgroupID) 
	{
		this.parenPOMgroupID = parenPOMgroupID;
	}

	public String getParentPOMArtifactID() 
	{
		return parentPOMArtifactID;
	}

	private void setParentPOMArtifactID(String parentPOMArtifactID) 
	{
		this.parentPOMArtifactID = parentPOMArtifactID;
	}

	public String getParentPOMVersion() 
	{
		return parentPOMVersion;
	}

	private void setParentPOMVersion(String parentPOMVersion) 
	{
		this.parentPOMVersion = parentPOMVersion;
	}

	public String getParentPOMRelPath() 
	{
		return parentPOMRelPath;
	}

	private void setParentPOMRelPath(String parentPOMRelPath) 
	{
		this.parentPOMRelPath = parentPOMRelPath;
	}

    public Model getModelPOM()
    {
        return modelPOM;
    }
    
}
