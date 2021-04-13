package com.sisdi.data;

import com.sisdi.model.Office;
import com.sisdi.model.OfficeSimple;
import com.sisdi.model.Usuario;
import com.sisdi.model.searchOffice;
import com.sisdi.service.OfficeServiceImp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OfficeData {
    @Autowired
    private UserData userData;
    
    @Autowired
    private OfficeServiceImp officeService;
    
    private Date fecha = new Date();
     
    public Office getOffice(OfficeSimple office, int state) throws ParseException{
        Office o=new Office();
         Usuario emisor=userData.getUserByName(office.getEmisor());
         Usuario receptor=userData.getUserByName(office.getReceptor());
         if(office.getDateLimit()!=""){
              Date dateLimit=new SimpleDateFormat("dd/MM/yyyy").parse(office.getDateLimit());
              o.setDEADLINE(dateLimit);
         }
         o.setOFFNUMBER(office.getOffnumber());
         o.setREASON(office.getReason());
         o.setINCORDATE(fecha);
         o.setUSER_ID(emisor.getTempUser().getEmail());
         o.setRECEIVER_ID(receptor.getTempUser().getEmail());
         o.setTYPE_ID(office.getType_id());
         o.setSTATE(state);
         o.setOBSERVATIONS(office.getObservations());
         o.setTIMEOUTS_ID(1);
        return o;
    }
    public OfficeSimple getOfficeSimple(Office office){
       OfficeSimple o = new OfficeSimple();
        Usuario emisor=userData.getUser(office.getUSER_ID());
        Usuario receptor=userData.getUser(office.getRECEIVER_ID());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String incordate = format.format(office.getINCORDATE());
        o.setId(office.getINDX());
        o.setOffnumber(office.getOFFNUMBER());
        o.setDateCreate(incordate);
        o.setEmisor(emisor.getTempUser().getName());
        o.setEmisorDep(emisor.getDepartment().getName());
        o.setReceptor(receptor.getTempUser().getName());
        o.setReceptorDep(receptor.getDepartment().getName());
        o.setReason(office.getREASON());
        o.setType_id(office.getTYPE_ID());
        o.setObservations(office.getOBSERVATIONS());
        if(office.getDEADLINE()!= null){
             SimpleDateFormat limFormat = new SimpleDateFormat("dd/MM/yyyy");
             String limit = limFormat.format(office.getDEADLINE());
            o.setDateLimit(limit);
        }
        return o; 
    }
    public List<Office> searchOffice(searchOffice search, String user) throws ParseException{
        List<Office> list=officeService.listOfficeByUser(user);
        log.info(list.toString());
       if(!search.getOffnumber().equals("")){
           list=officeService.listByName(list, search.getOffnumber());
       }
       log.info(list.toString());
       if(search.getDateCreate()!=""){
            Date dateLimit=new SimpleDateFormat("dd/MM/yyyy").parse(search.getDateCreate());
            list=officeService.listByDate(list, dateLimit);
            log.info(dateLimit.toString());
         }
       log.info(list.toString());

       if(!search.getReason().equals("")){
           list=officeService.listByReason(list, search.getReason());
           
       }
       log.info(list.toString());

        return list;
    }
}
