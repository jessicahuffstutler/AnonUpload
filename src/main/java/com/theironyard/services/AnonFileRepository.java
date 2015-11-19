package com.theironyard.services;

import com.theironyard.entities.AnonFile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by jessicahuffstutler on 11/18/15.
 */
public interface AnonFileRepository extends CrudRepository<AnonFile, Integer> {
    //custom query for finding all files that are permanent, could add "OrderByIdAsc" but it's not required
    List<AnonFile> findAllByIsPermanent(boolean isPermanent);
}
