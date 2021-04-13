package com.sisdi.service;

import com.sisdi.model.Expediente;
import java.util.Date;
import java.util.List;

public interface ExpedienteService {

    List<Expediente> listarExpedientes();

    Expediente addExpediente(Expediente expediente);

    Expediente searchExpediente(String expnumber);

    List<Expediente> listExpedienteByUser(String user);

    List<Expediente> listExpedienteByEmisor(String emisor);

    List<Expediente> listExpedienteByReceptor(String receptor);

    Expediente getExpediente(String filename);

    List<Expediente> listExpedienteBySameDepartment(String receiver, String sender);

    Expediente searchFile(String receiver, String sender, String year);

    Expediente createFile(String receiver, String sender);
}
