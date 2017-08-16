package com.spy.service;

import com.spy.model.Gamer;
import com.spy.model.Vote;
import com.spy.model.dao.GamerDao;
import com.spy.model.dao.VoteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by bingwang on 8/15/17.
 */
@Service
public class VoteService {
    VoteDao voteDao;
    GamerDao gamerDao;

    @Autowired
    public VoteService(VoteDao voteDao, GamerDao gamerDao) {
        this.voteDao = voteDao;
        this.gamerDao = gamerDao;
    }


    public boolean vote(String voter, String voted) {
        Gamer gamerVoter = gamerDao.findOneByGamer(voter);
        Gamer gamerBeenVoted = gamerDao.findOneByGamer(voted);

        if (gamerVoter == null | gamerBeenVoted == null) {
            return false;
        }
        Vote vote = voteDao.findOneByPlayer(gamerBeenVoted.getGamer());
        if (vote == null) {
            Vote newVote = new Vote();
            newVote.setPlayer(gamerBeenVoted.getGamer());
            newVote.setVoteNumber(1);
            voteDao.save(newVote);
        } else {
            Integer number = vote.getVoteNumber() + 1;
            vote.setVoteNumber(number);
            voteDao.save(vote);
        }
        gamerVoter.setVoted(true);
        gamerDao.save(gamerVoter);
        return true;
    }
}
