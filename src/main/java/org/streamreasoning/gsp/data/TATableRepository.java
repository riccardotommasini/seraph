package org.streamreasoning.gsp.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TATableRepository
        extends
            JpaRepository<TATable, Long>,
            JpaSpecificationExecutor<TATable> {

}
