package com.bej.usertrackservice.repository;

import com.bej.usertrackservice.domain.TrackLibrary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackLibraryRepository extends MongoRepository<TrackLibrary,String> {

}
