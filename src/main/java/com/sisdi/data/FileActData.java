/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisdi.data;



import com.sisdi.model.Expediente;
import com.sisdi.model.FileAct;
import com.sisdi.model.FileActSimple;
import com.sisdi.service.ExpedienteServiceImp;
import com.sisdi.service.FileActServiceImp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileActData {
    @Autowired
    private FileActServiceImp fileActServiceImp;
    
    @Autowired
    private ExpedienteServiceImp expedienteServiceImp;
    
    public List<FileActSimple> listFileActSimples(){
        List<FileActSimple> list=new ArrayList();
        List<FileAct> file= fileActServiceImp.listarFileActs();
        for(FileAct f:file){
            list.add(this.fileActToFileActSimple(f));
        }
        return list;
    }
    
    public FileActSimple fileActToFileActSimple(FileAct file){
        FileActSimple aux=new FileActSimple();
       SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String create = format.format(file.getDATE_CREATE());
         String dfile = format.format(file.getDATE_FILE());
        
        aux.setId(file.getId());
        aux.setFileId(file.getFILE_ID());
        aux.setFileName(file.getFILENAME());
        aux.setObservations(file.getOBSERVATIONS());
        aux.setDateCreate(create);
         aux.setDateFile(dfile);
        aux.setNameRequest(file.getNAME_REQUEST());
        aux.setDepartmentRequest(file.getDEPARTMENT_REQUEST());
       
     
        return aux;
    }
     public FileActSimple SaveAct(Expediente file){
        FileActSimple aux=new FileActSimple();
        
        
        
        aux.setId(file.getINDX());
        aux.setFileId(file.getINDX());
        aux.setFileName(file.getFILENAME());
   
        aux.setDepartmentRequest(file.getOWNER_DEPARTMENT());
        aux.setNameRequest(file.getOWNER_ID());
        aux.setFileId(file.getINDX());
      
      
        
      
        return aux;
    }
    
    public FileAct fileActSimpleToFileAct(FileActSimple file) throws ParseException{
        FileAct aux=new FileAct();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date create=new SimpleDateFormat("dd/MM/yyyy").parse(file.getDateCreate());  
        Date dfile=new SimpleDateFormat("dd/MM/yyyy").parse(file.getDateCreate()); 
        aux.setId(file.getId());
        aux.setFILE_ID(file.getFileId());
        aux.setFILENAME(file.getFileName());
        aux.setOBSERVATIONS(file.getObservations());
        aux.setDATE_CREATE(create);
        aux.setDATE_FILE(dfile);
        aux.setNAME_REQUEST(file.getNameRequest());
        aux.setDEPARTMENT_REQUEST(file.getDepartmentRequest());
        return aux;
    }
    private String getYear(Date date){
        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        String year = getYearFormat.format(date);
        return year;
    }
    
}

