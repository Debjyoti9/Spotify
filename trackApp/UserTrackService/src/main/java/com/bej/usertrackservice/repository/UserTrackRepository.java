package com.bej.usertrackservice.repository;

import com.bej.usertrackservice.domain.Playlist;
import com.bej.usertrackservice.domain.Track;
import com.bej.usertrackservice.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserTrackRepository extends MongoRepository<User,String> {
    User findByEmail(String email);



//    Track findByTrackIdPlayListName(int trackId,String playListName);
//    Playlist findByPlayListName(String playListName);
}
