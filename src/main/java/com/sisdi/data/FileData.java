package com.sisdi.data;

import com.sisdi.model.Expediente;
import com.sisdi.model.FileSimple;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileData {

    private Date fecha = new Date();

    public Expediente getFile(FileSimple file) throws ParseException {
        Date create = new SimpleDateFormat("dd/MM/yyyy").parse(file.getDateCreateFile());
        Date returnF = new SimpleDateFormat("dd/MM/yyyy").parse(file.getDateReturn());
        Expediente fileO = new Expediente();
        fileO.setINDX(file.getIndx());
        fileO.setFILENAME(file.getFileName());
        fileO.setOWNER_ID(file.getOwner());
        fileO.setRECEIVER_ID(file.getReceiver());
        fileO.setDATE_CREATE(create);
        fileO.setOFFICE_AMOUNT(file.getOfficeAmount());
        fileO.setOBSERVATIONS(file.getObservations());
        fileO.setDATE_RETURN(returnF);
        fileO.setSTATE(1);
        return fileO;
    }

    public FileSimple getFileSimple(Expediente file) {
        FileSimple fileO = new FileSimple();
        fileO.setIndx(file.getINDX());
        fileO.setFileName(file.getFILENAME());
        fileO.setOwner(file.getOWNER_ID());
        fileO.setReceiver(file.getRECEIVER_ID());
        fileO.setDateCreateFile(file.getDATE_CREATE().toString());
        fileO.setOfficeAmount(file.getOFFICE_AMOUNT());
        fileO.setObservations(file.getOBSERVATIONS());

        return fileO;
    }

    public JSONArray expedientesVencidos(List<Expediente> expedientes) {
        JSONArray expedientesV = new JSONArray();
        JSONObject o = new JSONObject();
        o.put("Tipo", "Trasladar");
        expedientesV.put(o);
        for (Expediente e : expedientes) {
            LocalDate fHoy = LocalDate.now();
            LocalDate localDate = this.convertToLocalDate(e.getDATE_CREATE());
            long years = ChronoUnit.YEARS.between(localDate, fHoy);
            if (years >= 5.0) {
                JSONObject obj = new JSONObject();
                obj.put("Filename", e.getFILENAME());
                obj.put("Create", localDate);
                obj.put("Receptor", e.getRECEIVER_ID());
                expedientesV.put(obj);
            }
        }
        return expedientesV;
    }

    public JSONArray expedientesTrasladados(List<Expediente> expedientes) {
        String f=this.dateToString(fecha);
        JSONArray expedientesV = new JSONArray();
        JSONObject o = new JSONObject();
        o.put("Tipo", "Central");
        expedientesV.put(o);
        for (Expediente e : expedientes) {
            String eD=this.dateToString(e.getDATE_RETURN());
            if (f.equals(eD)) {
                JSONObject obj = new JSONObject();
                obj.put("Filename", e.getFILENAME());
                obj.put("Create", e.getDATE_CREATE());
                obj.put("Receptor", e.getRECEIVER_ID());
                expedientesV.put(obj);

            }
            log.info("Fecha de return: " + e.getDATE_RETURN().toString());
        }
        log.info(fecha.toString());
        return expedientesV;
    }

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public String dateToString(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(d);
        return strDate;
    }
}
