package com.sisdi.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="t_expediente")
public class Expediente implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int INDX;
    private String FILENAME;
    private String OBSERVATIONS;
    private String OWNER_ID;
    private String RECEIVER_ID;
    private int OFFICE_AMOUNT;
    private Date DATE_CREATE;
    private Date DATE_RETURN;
    private int STATE; // 0 para prestado 1 para devuelto
    private String OWNER_DEPARTMENT;
    private String RECEIVER_DEPARTMENT;

   
    
    public Expediente(){}
     
}
