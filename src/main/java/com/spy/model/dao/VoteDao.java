package com.spy.model.dao;

import com.spy.model.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bingwang on 8/5/17.
 */
@Repository
public interface VoteDao extends CrudRepository<Vote, Integer> {
    Vote findOneByPlayer(Integer player);

    Integer deleteAllByRoom(String roomToken);
}
