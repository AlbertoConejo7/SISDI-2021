package com.sisdi.data;


import com.sisdi.model.TableSimple;
import com.sisdi.model.Usuario;
import com.sisdi.model.ConservationTable;
import com.sisdi.service.ConservationTableServiceImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConservationTableData {
    @Autowired
    private UserData userData;
    
    @Autowired
    private ConservationTableServiceImpl tableService;
    
    private Date fecha = new Date();
     
    public ConservationTable getTable(TableSimple table) throws ParseException{
        ConservationTable o=new ConservationTable();
         Usuario autor=userData.getUserByName(table.getAutor());
          Date first_date=new SimpleDateFormat("dd/MM/yyyy").parse(table.getFirst_date());
              o.setFirst_date(first_date);
       
         o.setIndx(table.getIndx());
         o.setFondo(table.getFondo());
         o.setSubfondo(table.getSubfondo());
          o.setSection(table.getSection());
           o.setAutor(table.getAutor());
            o.setSeriedocumental(table.getSeriedocumental());
             o.setIsoriginal(table.getIsoriginal());
              o.setCantcopies(table.getCantcopies());
               o.setContents(table.getContents());
                o.setSupport(table.getSupport());
                 o.setValidity(table.getValidity());
                  o.setCantmeters(table.getCantmeters());
                   o.setExtremedates(table.getExtremedates());
                    o.setObservations(table.getObservations());
                     o.setDepartment_request(table.getDepartment_request());
                      o.setFondo(table.getFondo());
                       o.setDate_create(fecha);
                        o.setFinaltime(table.getFinaltime());
                         o.setFondo(table.getFondo());
                          o.setLast_date(fecha);
                          o.setFirst_date(fecha);
                         return o;
                    
}
    public ConservationTable getTableEdit(TableSimple table) throws ParseException{
        ConservationTable o=new ConservationTable();
         Usuario autor=userData.getUserByName(table.getAutor());
          Date first_date=new SimpleDateFormat("dd/MM/yyyy").parse(table.getFirst_date());
              o.setFirst_date(first_date);
              
             
               
                Date date_create=new SimpleDateFormat("dd/MM/yyyy").parse(table.getDate_create());
               o.setDate_create(date_create);
               
       
         o.setIndx(table.getIndx());
         o.setFondo(table.getFondo());
         o.setSubfondo(table.getSubfondo());
          o.setSection(table.getSection());
           o.setAutor(table.getAutor());
            o.setSeriedocumental(table.getSeriedocumental());
             o.setIsoriginal(table.getIsoriginal());
              o.setCantcopies(table.getCantcopies());
               o.setContents(table.getContents());
                o.setSupport(table.getSupport());
                 o.setValidity(table.getValidity());
                  o.setCantmeters(table.getCantmeters());
                   o.setExtremedates(table.getExtremedates());
                    o.setObservations(table.getObservations());
                     o.setDepartment_request(table.getDepartment_request());
                      o.setFondo(table.getFondo());
                       o.setLast_date(fecha);
                        o.setFinaltime(table.getFinaltime());
                        
                          
                         return o;
                    
}
    
    
    public TableSimple getTableSimple(ConservationTable table){
       TableSimple o = new TableSimple();
        
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String incordate = format.format(table.getDate_create());
        String first = format.format(table.getFirst_date());
         String last = format.format(table.getLast_date());
         o.setIndx(table.getIndx());
         o.setFondo(table.getFondo());
         o.setSubfondo(table.getSubfondo());
          o.setSection(table.getSection());
           o.setAutor(table.getAutor());
            o.setSeriedocumental(table.getSeriedocumental());
             o.setIsoriginal(table.getIsoriginal());
              o.setCantcopies(table.getCantcopies());
               o.setContents(table.getContents());
                o.setSupport(table.getSupport());
                 o.setValidity(table.getValidity());
                  o.setCantmeters(table.getCantmeters());
                   o.setExtremedates(table.getExtremedates());
                    o.setObservations(table.getObservations());
                     o.setDepartment_request(table.getDepartment_request());
                      o.setFondo(table.getFondo());
                       o.setDate_create(incordate);
                        o.setFinaltime(table.getFinaltime());
                         o.setFondo(table.getFondo());
                          o.setLast_date(last);
                          o.setFirst_date(first);
        
        return o; 
    }
}
