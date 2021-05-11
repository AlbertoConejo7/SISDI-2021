/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisdi.data;

import com.sisdi.model.Department;
import com.sisdi.model.Expediente;
import com.sisdi.model.FileLoan;
import com.sisdi.model.FileLoanSimple;
import com.sisdi.service.ExpedienteServiceImp;
import com.sisdi.service.FileLoanServiceImp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class FileLoanData {

    @Autowired
    private FileLoanServiceImp fileLoanServiceImp;

    @Autowired
    private ExpedienteServiceImp expedienteServiceImp;
    
    @Autowired
    private DepartmentData depData;
    
    private Date fecha = new Date();

    public List<FileLoanSimple> listFileLoanSimples() {
        List<FileLoanSimple> list = new ArrayList();
        List<FileLoan> file = fileLoanServiceImp.listarFileLoans();
        for (FileLoan f : file) {
            list.add(this.fileLoanToFileLoanSimple(f));
        }
        return list;
    }

    public FileLoanSimple fileLoanToFileLoanSimple(FileLoan file) {
        FileLoanSimple aux = new FileLoanSimple();
        Expediente exp = expedienteServiceImp.searchExpediente(file.getFILENAME());
        aux.setId(file.getId());
        aux.setFileId(file.getFILE_ID());
        aux.setFileName(file.getFILENAME());
        aux.setObservations(file.getOBSERVATIONS());
        aux.setDateCreate(this.dateToString(file.getDATE_CREATE()));
        aux.setNameRequest(file.getNAME_REQUEST());
        aux.setDepartmentRequest(file.getDEPARTMENT_REQUEST());
        if (file.getDEPARTMENT_REQUEST().equals(exp.getOWNER_DEPARTMENT())) {
            aux.setDepartmentOther(exp.getRECEIVER_DEPARTMENT());
        } else {
            aux.setDepartmentOther(exp.getOWNER_DEPARTMENT());
        }
        aux.setYear(this.getYear(exp.getDATE_CREATE()));
        aux.setOfiicesAmount(exp.getOFFICE_AMOUNT());
        return aux;
    }

    public String dateToString(Date fecha) {
        SimpleDateFormat limFormat = new SimpleDateFormat("dd/MM/yyyy");
        String limit = limFormat.format(fecha);
        return limit;
    }

    public Date stringToDate(String fecha) throws ParseException {
        Date dateLimit = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
        return dateLimit;
    }

    public FileLoan fileLoanSimpleToFileLoan(FileLoanSimple file) throws ParseException {
        FileLoan aux = new FileLoan();

        aux.setId(file.getId());
        aux.setFILE_ID(file.getFileId());
        aux.setFILENAME(file.getFileName());
        aux.setOBSERVATIONS(file.getObservations());
        aux.setDATE_CREATE(this.stringToDate(file.getDateCreate()));
        aux.setNAME_REQUEST(file.getNameRequest());
        aux.setDEPARTMENT_REQUEST(file.getDepartmentRequest());
        return aux;
    }

    private String getYear(Date date) {
        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        String year = getYearFormat.format(date);
        return year;
    }
    
    public JSONArray listFileLoanByDepartment(){
        JSONArray departamentos = new JSONArray();
        List<Department> list=depData.listDepartments();
        for(Department d:list){
            List<FileLoan> aux=fileLoanServiceImp.listFileLoanByDepartment(d.getName());
            if(!aux.isEmpty()){
                JSONObject obj = new JSONObject();
                obj.put("name", d.getName());
                obj.put("y", aux.size());
                departamentos.put(obj);
            }
        }
        return departamentos;
    }
    public JSONArray listFileLoanByYear(){
        JSONArray years = new JSONArray();
        String actual=new SimpleDateFormat("yyyy").format(this.fecha);
        int year = Integer.parseInt(actual);
        for(int y=2000;y<=year;y++){
            List<FileLoan> aux=fileLoanServiceImp.listFileLoanByYear(y);
            if(!aux.isEmpty()){
                JSONObject obj = new JSONObject();
                obj.put("name", y);
                obj.put("y", aux.size());
                years.put(obj);
            }
        }
        return years;
    }

}
