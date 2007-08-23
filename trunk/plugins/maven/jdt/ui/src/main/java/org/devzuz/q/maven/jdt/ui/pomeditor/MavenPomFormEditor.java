/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.pomeditor;

import org.devzuz.q.maven.jdt.ui.pomeditor.pomreader.POMSearcher;
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
            setSelectedLocationOfPOM();
            
            startPOMSearch();
            
            addPage( new MavenPomBasicFormPage( this , "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomBasicFormPage;",
                                                       "Project Information" , getPOMFilePath()));
            addPage( new MavenPomDependenciesFormPage( this , "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomDependenciesFormPage;",
                                                       "Dependencies" ) );
            addPage( new MavenPomPropertiesModuleFormPage( this , "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomPropertiesModuleFormPage;",
                                                       "Properties/Module" ) );

           
        }
        catch ( PartInitException e )
        {
            // TODO: handle exception
            e.printStackTrace();
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
    
    private void setSelectedLocationOfPOM()
    {
        IEditorInput  ie= this.getEditorInput();
        strProjectSelected = ie.toString().substring( ie.toString().indexOf( "(" )+1,ie.toString().indexOf( ")" ));        
    }
    
    public String getSelectedLocationOfPOM()
    {
        return this.strProjectSelected;
    }
    
    public void startPOMSearch()
    {
        POMSearcher ps = new POMSearcher(getSelectedLocationOfPOM());
        setPOMIPath(ps.getProjectPOMFilePath());
    }
    
    private void setPOMIPath(IPath ip)
    {
        this.pomIPath = ip;
    }
    
    public String getPOMFilePath()
    {
        return pomIPath.toOSString();
    }
    
    
}
