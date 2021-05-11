package com.sisdi.service;

import com.sisdi.model.Expediente;
import java.util.Date;
import java.util.List;

public interface ExpedienteService {

    List<Expediente> listarExpedientes();
    
    List<Expediente> listarExpedientesByDepartment(String dep);
    
    List<Expediente> listarExpedientesByYear(int y);
    
    List<Expediente> listarExpedientesByState(int s);

    Expediente addExpediente(Expediente expediente);

    Expediente searchExpediente(String expnumber);

    List<Expediente> listExpedienteByUser(String user);

    List<Expediente> listExpedienteByEmisor(String emisor);

    List<Expediente> listExpedienteByReceptor(String receptor);

    Expediente getExpediente(String filename);
    
    Expediente getExpediente(int id);

    List<Expediente> listExpedienteBySameDepartment(String receiver, String sender);

    Expediente searchFile(String receiver, String sender, String year);
    
    Expediente searchFileRequest(String receiver, String sender, String year);

    Expediente createFile(String receiver, String sender);
    
    String getFileName(int indx);
    
    void deleteExpediente(int id);
    
}
