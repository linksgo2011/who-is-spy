package com.spy.model.dao;

import com.spy.model.Gamer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bingwang on 8/3/17.
 */
@Repository
public interface GamerDao extends CrudRepository<Gamer, Integer> {
    List<Gamer> findBySession(String session);

    Gamer findOneBySession(String session);

    Gamer findOneByGamer(String gamer);

    List<Gamer> findByRoom(String room);
}
