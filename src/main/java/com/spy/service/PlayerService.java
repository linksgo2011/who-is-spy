package com.spy.service;

import com.spy.model.Gamer;
import com.spy.model.Room;
import com.spy.model.dao.GamerDao;
import com.spy.model.dao.RoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bingwang on 8/5/17.
 */
@Service
public class PlayerService {
    @Autowired
    GamerDao gamerDao;
    @Autowired
    RoomDao roomDao;

    public Gamer ifUserAlreadyLogin(String name) {
        return gamerDao.findOneByGamer(name);
    }

    public void init() {
        Room room = new Room();
        room.setRoomLink("http://10.206.20.20:8080/room?roomToken=123456");
        room.setRoomOwner("idolice");
        room.setStatus("waiting");
        room.setRoomToken("123456");
        roomDao.save(room);
    }

}
