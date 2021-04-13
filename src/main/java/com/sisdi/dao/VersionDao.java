
package com.sisdi.dao;

import com.sisdi.model.Version;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Vale
 */
@Repository
public interface VersionDao extends CrudRepository <Version, Integer> {
    
}
