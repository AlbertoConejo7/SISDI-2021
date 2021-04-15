package com.sisdi.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="t_transfer")
public class Transfer implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int INDX;
    private int INDX_FILE;
    private String OWNER_ID;
    private String RECEIVER_ID;
    private Date DATE_TRANSFER;
    private int STATE;
    private String OBSERVATIONS;
    private int OFFICE_AMOUNT;
    
    public Transfer(){};
}
