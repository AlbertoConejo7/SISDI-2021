/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisdi.service;

import com.sisdi.dao.ExpedienteDao;
import com.sisdi.model.Expediente;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpedienteServiceImp implements ExpedienteService {

    @Autowired
    private ExpedienteDao expedienteDao;



 @Override
    public List<Expediente> listarExpedientes() {
        return (List<Expediente>) expedienteDao.findAll();
    }
    
    @Override
    public Expediente addExpediente(Expediente expediente) {
        return expedienteDao.save(expediente);
    }

@Override
    public Expediente searchExpediente(String expnumber) {
        List<Expediente> list = this.listarExpedientes();
        Expediente aux = null;
        for (Expediente o : list) {
            if (o.getFILENAME().equals(expnumber)) {
                aux = o;
            }
        }
        return aux;
    }
    
   
    @Override
    public List<Expediente> listExpedienteByEmisor(String emisor) {
        List<Expediente> list = this.listarExpedientes();
        List<Expediente> aux = new ArrayList();
        for (Expediente o : list) {
            if (o.getOWNER_ID().equals(emisor)) {
                aux.add(o);
            }
        }

        return aux;
    }
    @Override
    public List<Expediente> listExpedienteByReceptor(String receptor) {
        List<Expediente> list = this.listarExpedientes();
        List<Expediente> aux = new ArrayList();
        for (Expediente o : list) {
            if (o.getRECEIVER_ID().equals(receptor)) {
                aux.add(o);
            }
        }

        return aux;
    }
    @Override
    public List<Expediente> listExpedienteByUser(String user) {
        List<Expediente> aux = new ArrayList();
        List<Expediente> emisor = this.listExpedienteByEmisor(user);
        List<Expediente> receptor = this.listExpedienteByReceptor(user);
        aux.addAll(emisor);
        aux.addAll(receptor);
        return aux;
    }

    @Override
    public Expediente getExpediente(String filename) {
        List<Expediente> list = this.listarExpedientes();
        Expediente aux = null;
        for (Expediente o : list) {
            if (o.getFILENAME().equals(filename)) {
                aux=o;
            }
        }
        return aux;
    }
    
     @Override
    public Expediente getExpediente(int id) {
        List<Expediente> list = this.listarExpedientes();
        Expediente aux = null;
        for (Expediente o : list) {
            if (o.getINDX() == id) {
                aux = o;
            }
        }
        return aux;
    }
    
     //lista los expedientes que tengan mismos departamentos en receptor o emisor
    @Override
    public List<Expediente> listExpedienteBySameDepartment(String receiver, String sender) {
        List<Expediente> list = this.listarExpedientes();
        List<Expediente> sameDepartments = new ArrayList();
        for (Expediente f : list) {
            if ((f.getOWNER_DEPARTMENT().equals(sender)) && (f.getRECEIVER_DEPARTMENT().equals(receiver))
                    || (f.getOWNER_DEPARTMENT().equals(receiver)) && (f.getRECEIVER_DEPARTMENT().equals(sender))) {
                sameDepartments.add(f);
            }
        }

        return sameDepartments;
    }

    @Override
    public Expediente searchFile(String receiver, String sender, String year) {
        
        List<Expediente> list = this.listExpedienteBySameDepartment(receiver, sender);
        Expediente sameYearFile = new Expediente();
        if (!list.isEmpty()) {
            for (Expediente f : list) {
                String a = new SimpleDateFormat("yyyy").format(f.getDATE_CREATE());
                if ((a.contains(year))) {
                    sameYearFile = f;
                }
            }
        } else {
            sameYearFile = this.createFile(receiver, sender);
        }
        return sameYearFile;
    }

    @Override
    public Expediente createFile(String receiver, String sender) {
        Expediente e = new Expediente();
        e.setOWNER_DEPARTMENT(sender);
        e.setRECEIVER_DEPARTMENT(receiver);
        int cantidad =e.getOFFICE_AMOUNT()+1;
        e.setOFFICE_AMOUNT(cantidad);
        return e;
    }
    
    @Override
    public String getFileName(int indx){
        Expediente e=this.getExpediente(indx);
        return e.getFILENAME();
    }
}
