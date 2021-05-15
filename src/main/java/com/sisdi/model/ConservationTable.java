/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisdi.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="T_CONSERVATIONTABLE")
public class ConservationTable implements Serializable{

    public ConservationTable(int indx, String fondo, String subfondo, String section, String autor, String seriedocumental, String isoriginal, String cantcopies, String contents, String support, String validity, String cantmeters, String extremedates, String observations, String department_request, Date date_create, String finaltime, Date first_date, Date last_date) {
        this.indx = indx;
        this.fondo = fondo;
        this.subfondo = subfondo;
        this.section = section;
        this.autor = autor;
        this.seriedocumental = seriedocumental;
        this.isoriginal = isoriginal;
        this.cantcopies = cantcopies;
        this.contents = contents;
        this.support = support;
        this.validity = validity;
        this.cantmeters = cantmeters;
        this.extremedates = extremedates;
        this.observations = observations;
        this.department_request = department_request;
        this.date_create = date_create;
        this.finaltime = finaltime;
        this.first_date = first_date;
        this.last_date = last_date;
    }



    public ConservationTable() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int indx;
    private String fondo;
    private String subfondo;
    private String section;
    private String autor;
    private String seriedocumental;
    private String isoriginal;
    private String cantcopies;
    private String contents;
    private String support;
    private String validity;
    private String cantmeters;
    private String extremedates;
    private String observations;
    private String department_request;
    private Date date_create;
     private String finaltime;
  private Date first_date;
   private Date last_date;
    
    
    
    
     
}
