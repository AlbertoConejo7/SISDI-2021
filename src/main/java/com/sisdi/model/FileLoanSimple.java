package com.sisdi.model;
import java.util.Date;
import lombok.Data;

@Data
public class FileLoanSimple {
    private int id;
    private int fileId;
    private String fileName;
    private String observations;
    private String nameRequest;
    private String departmentRequest;
    private String departmentOther;
    private String year;  
    private Date dateCreate;
    private int ofiicesAmount;
}
