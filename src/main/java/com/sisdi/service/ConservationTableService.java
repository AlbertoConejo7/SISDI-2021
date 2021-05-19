/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisdi.service;


import com.sisdi.model.ConservationTable;
import java.util.List;


public interface ConservationTableService {
    List<ConservationTable> listConservationTable();
    ConservationTable addConservationTable(ConservationTable conservationtable);
    ConservationTable  searchTable(int tablenumber);
    void deleteTable(ConservationTable t);
    long getTable(String dep, String type, String serie);
}
