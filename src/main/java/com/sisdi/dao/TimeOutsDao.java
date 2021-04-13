package com.sisdi.dao;

import com.sisdi.model.TimeOuts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



/**
 *
 * @author alber
 */
@Repository
public interface TimeOutsDao extends CrudRepository<TimeOuts, Integer> {
    
}
