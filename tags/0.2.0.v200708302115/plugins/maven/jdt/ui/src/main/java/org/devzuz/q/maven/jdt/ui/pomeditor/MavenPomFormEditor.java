/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.pomeditor;

import java.io.File;

import org.devzuz.q.maven.jdt.ui.pomeditor.pomreader.MavenPOMSearcher;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;


public class MavenPomFormEditor extends FormEditor
{
    private String strProjectSelected; 
    
    private IPath pomIPath;
    
    public MavenPomFormEditor()
    {
        
    }
    
    @Override
    protected void addPages()
    {
        try
        {
            setSelectedLocationOfPOMs();
            
            startPOMSearch();
            
            addPage( new MavenPomBasicFormPage( this , "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomBasicFormPage;",
                                                       "Project Information" , getPOMFilePath()));
            addPage( new MavenPomDependenciesFormPage( this , "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomDependenciesFormPage;",
                                                       "Dependencies" ) );
            addPage( new MavenPomPropertiesModuleFormPage( this , "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomPropertiesModuleFormPage;",
                                                       "Properties/Module" ) );

           
        }
        catch ( PartInitException pie )
        {
            // TODO: handle exception
            pie.printStackTrace();
        }
    }

    @Override
    public void doSave( IProgressMonitor monitor )
    {
        
    }

    @Override
    public void doSaveAs()
    {
        
    }

    @Override
    public boolean isSaveAsAllowed()
    {
        return false;
    }
    
    private void setSelectedLocationOfPOMs()
    {
        IEditorInput  iei= this.getEditorInput();
        strProjectSelected = iei.toString().substring( iei.toString().indexOf( "(" )+1,iei.toString().indexOf( ")" ));        
    }
    
    public String getSelectedLocationOfPOM()
    {
        return this.strProjectSelected;
    }
    
    public void startPOMSearch()
    {
        MavenPOMSearcher mps = new MavenPOMSearcher(getSelectedLocationOfPOM());
        setPOMIPath(mps.getProjectPOMFilePath());
    }
    
    private void setPOMIPath(IPath ip)
    {
        this.pomIPath = ip;
    }
    
    public File getPOMFilePath()
    {
        return pomIPath.toFile();
    }
    
    
}
