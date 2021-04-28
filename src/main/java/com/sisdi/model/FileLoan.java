/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisdi.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name="t_fileloan")
public class FileLoan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int FILE_ID;
    private String FILENAME;
    private String OBSERVATIONS;
    private String NAME_REQUEST;
    private String DEPARTMENT_REQUEST;
    private Date DATE_CREATE;
    private Date DATE_RETURN;
    private int STATE;
    
    public FileLoan(){}

}
