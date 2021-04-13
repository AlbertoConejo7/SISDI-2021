package com.sisdi.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Vale
 */

@Data
@Entity
@Table(name="T_VERSION")
public class Version implements Serializable {
   
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String office_id;
    private String version_id;
    private int version_number;
    private Date version_date;
    private String version_description;
    private String reason;
    private String observations;

public Version(int id, String office_id, String id_version, int version_number, Date version_date, String version_description, String reason, String observations) {
    this.id = id;
    this.version_id = id_version;
    this.office_id = office_id;
    this.version_number = version_number;
    this.version_date = version_date;
    this.version_description = version_description;
    this.reason = reason;
    this.observations = observations;
}

public Version (){
}
    
}
