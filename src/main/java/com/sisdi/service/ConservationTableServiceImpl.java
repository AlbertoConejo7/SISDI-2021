/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisdi.service;

import com.sisdi.dao.ConservationTableDao;
import com.sisdi.model.ConservationTable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConservationTableServiceImpl implements ConservationTableService {

    @Autowired
    private ConservationTableDao conservationTableDao;

    @Override
    public List<ConservationTable> listConservationTable() {
        return (List<ConservationTable>) conservationTableDao.findAll();
    }

    @Override
    public ConservationTable addConservationTable(ConservationTable conservationtable) {
        return conservationTableDao.save(conservationtable);
    }

    @Override
    public ConservationTable searchTable(int tablenumber) {
        List<ConservationTable> list = this.listConservationTable();
        ConservationTable aux = null;
        for (ConservationTable o : list) {
            if (o.getIndx() == tablenumber) {
                aux = o;
            }
        }
        return aux;

    }

    @Override
    public void deleteTable(ConservationTable t) {
        conservationTableDao.delete(t);

    }
    @Override
    public long getTable(String dep, String type, String serie){
        List<ConservationTable> list = this.listConservationTable();
        long aux = (long) 5.0;
        for (ConservationTable o : list) {
            if (o.getSubfondo().equals(dep) && o.getSeriedocumental().equals(serie) && o.getValidity().equals(type)) {
                String a=o.getFinaltime();
                aux =  Long.parseLong(a);
            }
        }
        return aux;
    }

}
