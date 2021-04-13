/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisdi.model;

import java.io.Serializable;
import java.sql.Time;
import javax.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name="t_timeouts")
/**
 *
 * @author alber
 */
public class TimeOuts implements Serializable {

    public TimeOuts() {
    }

    public TimeOuts(int ID, String cod, Date limitdate, int department, String type) {
        this.ID = ID;
        this.COD = cod;
        this.LIMITDATE = limitdate;
         this.DEPARTMENT = department;
        this.TYPE = type;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String COD;
    private Date LIMITDATE;
    private int DEPARTMENT;
    private String TYPE;
    
    
    
    
}
