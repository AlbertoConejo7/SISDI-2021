package com.sisdi.dao;

import com.sisdi.model.Expediente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpedienteDao extends CrudRepository<Expediente, Integer>{
    
}
