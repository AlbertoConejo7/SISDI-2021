package com.sisdi.data;

import com.sisdi.model.Department;
import com.sisdi.model.Expediente;
import com.sisdi.model.FileSimple;
import com.sisdi.service.ConservationTableService;
import com.sisdi.service.ExpedienteServiceImp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class FileData {

    private Date fecha = new Date();
    
    @Autowired
    private DepartmentData depData;
    
    @Autowired
    private ExpedienteServiceImp expedienteServiceImp;
    
    @Autowired
    private ConservationTableService tablasPlazoServiceImp;

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
        
         List<Expediente> exp = this.listExpVencidos(expedientes);
        for (Expediente e : exp) {
            LocalDate localDate = this.convertToLocalDate(e.getDATE_CREATE());
                JSONObject obj = new JSONObject();
                obj.put("Filename", e.getFILENAME());
                obj.put("Create", localDate);
                obj.put("Receptor", e.getRECEIVER_ID());
                expedientesV.put(obj);
        }
        return expedientesV;
    }

    public List<Expediente> listExpVencidos(List<Expediente> expedientes) {
        List<Expediente> exp = new ArrayList();
        for (Expediente e : expedientes) {
            long time= tablasPlazoServiceImp.getTable(e.getOWNER_DEPARTMENT(), "Oficina", "Expedientes");
            LocalDate fHoy = LocalDate.now();
            LocalDate localDate = this.convertToLocalDate(e.getDATE_CREATE());
            long years = ChronoUnit.YEARS.between(localDate, fHoy);
            if (years >= time) {
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
    public JSONArray listFileByDepartment(){
        JSONArray departamentos = new JSONArray();
        List<Department> list=depData.listDepartments();
        for(Department d:list){
            List<Expediente> aux=expedienteServiceImp.listarExpedientesByDepartment(d.getName());
            if(!aux.isEmpty()){
                JSONObject obj = new JSONObject();
                obj.put("name", d.getName());
                obj.put("y", aux.size());
                departamentos.put(obj);
            }
        }
        return departamentos;
    }
    public JSONArray listFileByYear(){
        JSONArray years = new JSONArray();
        String actual=new SimpleDateFormat("yyyy").format(this.fecha);
        int year = Integer.parseInt(actual);
        for(int y=2000;y<=year;y++){
            List<Expediente> aux=expedienteServiceImp.listarExpedientesByYear(y);
            if(!aux.isEmpty()){
                JSONObject obj = new JSONObject();
                obj.put("name", y);
                obj.put("y", aux.size());
                years.put(obj);
            }
        }
        return years;
    }

    public String dateToString(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(d);
        return strDate;
    }
}
