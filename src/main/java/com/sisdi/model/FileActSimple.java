/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisdi.model;


import java.util.Date;
import lombok.Data;

@Data
public class FileActSimple {
    private int id;
    private int state;
    private int fileId;
    private String fileName;
    private String observations;
    private String nameRequest;
    private String departmentRequest;
    private String year;  
    private String dateCreate;
     private String dateFile;
    private int oficesAmount;
}
