package com.sisdi.data;

import com.sisdi.model.FileSimple;
import com.sisdi.model.Transfer;
import com.sisdi.model.TransferSimple;
import com.sisdi.service.ExpedienteServiceImp;
import com.sisdi.service.TransferServiceImp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransferData {

    private Date fecha = new Date();

    @Autowired
    private ExpedienteServiceImp expedienteServiceImp;

    @Autowired
    private TransferServiceImp transferServiceImp;

    public Transfer getTransfer(FileSimple file, String email) {
        Transfer t = new Transfer();
        t.setINDX_FILE(file.getIndx());
        t.setOFFICE_AMOUNT(file.getOfficeAmount());
        t.setOBSERVATIONS(file.getObservations());
        t.setSTATE(0);
        t.setDATE_TRANSFER(fecha);
        t.setOWNER_ID(email);
        t.setRECEIVER_ID("archivocentral@sanpablo.go.cr");

        return t;
    }

    public Transfer getTransfer(TransferSimple t) {
        Transfer aux = new Transfer();
        aux.setINDX(t.getId());
        aux.setINDX_FILE(t.getNumeroExpediente());
        aux.setOWNER_ID(t.getEmisor());
        aux.setRECEIVER_ID(t.getReceptor());
        aux.setDATE_TRANSFER(t.getFechaTraslado());
        aux.setSTATE(t.getEstado());
        aux.setOFFICE_AMOUNT(t.getOficios());
        aux.setOBSERVATIONS(t.getObservaciones());
        return aux;
    }

    public TransferSimple getTransfer(Transfer t) {
        TransferSimple aux = new TransferSimple();
        String nameFile = expedienteServiceImp.getFileName(t.getINDX());
        aux.setId(t.getINDX());
        aux.setNumeroExpediente(t.getINDX_FILE());
        aux.setNombreExpediente(nameFile);
        aux.setEmisor(t.getOWNER_ID());
        aux.setReceptor(t.getRECEIVER_ID());
        aux.setFechaTraslado(t.getDATE_TRANSFER());
        aux.setEstado(t.getSTATE());
        aux.setOficios(t.getOFFICE_AMOUNT());
        aux.setObservaciones(t.getOBSERVATIONS());
        return aux;
    }

    public List<TransferSimple> listTransfers() {
        List<Transfer> list = transferServiceImp.listTransfers();
        List<TransferSimple> lT = new ArrayList();
        for (Transfer t : list) {
            TransferSimple aux = this.getTransfer(t);
            if(!aux.getNombreExpediente().equals("")){
                lT.add(aux);
            }
            
        }
        return lT;
    }

    public JSONArray listTransfersState() {
        JSONArray expedientesV = new JSONArray();
        JSONObject o = new JSONObject();
        o.put("Tipo", "Central");
        expedientesV.put(o);
        List<Transfer> list = transferServiceImp.listTransfersState(0);
        for (Transfer t : list) {
            String nameFile = expedienteServiceImp.getFileName(t.getINDX());
            JSONObject obj = new JSONObject();
            obj.put("Filename", nameFile);
            obj.put("Create", t.getDATE_TRANSFER());
            obj.put("Emisor", t.getOWNER_ID());
            expedientesV.put(obj);
        }
        return expedientesV;
    }
    
    public List<Transfer> listExpiredTransfers(List<Transfer> transfers) {
        List<Transfer> exp = new ArrayList();
        for (Transfer e : transfers) {
            LocalDate fHoy = LocalDate.now();
            LocalDate localDate = this.convertToLocalDate(e.getDATE_TRANSFER());
            long years = ChronoUnit.YEARS.between(localDate, fHoy);
            if (years >= 5.0) {
               exp.add(e);
            }
        }
        return exp;
    }
    
        public LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
