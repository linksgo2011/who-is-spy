package com.spy.model.dao;

import com.spy.model.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bingwang on 8/5/17.
 */
@Repository
public interface VoteDao extends CrudRepository<Vote, Integer> {
    Vote findOneByPlayer(Integer player);

    Integer deleteAllByRoom(String roomToken);

    List<Vote> findAllByRoomOrderByVoteNumber(String roomToken);
}
