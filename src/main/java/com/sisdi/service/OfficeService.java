package com.sisdi.service;

import com.sisdi.model.Office;
import java.util.Date;
import java.util.List;

public interface OfficeService {
    List<Office> listarOficios();
     List<Office> listarOficiosExp(int indx);
    Office addOffice(Office office);
    Office searchOffice(String offnumber);
    List<Office> listOfficeByEmisor(String emisor);
    List<Office> listOfficeByReceptor(String receptor);
    List<Office> listOfficeByUser(String user);
    List<Office> listByName(List <Office> list, String name);
    List<Office> listByDate(List<Office> list, Date date);
    List<Office> listByReason(List<Office> list, String reason);
    void deleteOfficesExp(int INDX);
    List<Office> listOfficeByYear(int y);
    List<Office> listOfficeByDepartment(String departamento);
    List<Office> listOfficeByState3();
}
