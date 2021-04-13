package com.sisdi.dao;

import com.sisdi.model.Office;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeDao extends CrudRepository<Office, Integer>{
//    @Modifying
//    @Query("UPDATE T_OFFICE u SET u.STATE=:state WHERE u.OFFNUMBER=:offnumber")
//    void updateState(@Param(value = "offnumber") String offnumber, @Param(value = "STATE") int state);

}
