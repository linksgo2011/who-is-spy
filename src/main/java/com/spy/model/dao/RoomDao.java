package com.spy.model.dao;

import com.spy.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bingwang on 8/5/17.
 */
@Repository
public interface RoomDao extends CrudRepository<Room, Integer> {
    Room findOneByRoomToken(String roomToken);
    Room findOneByRoomOwner(String roomOwner);
    Room findOneByHostSession(String hostSession);
}
