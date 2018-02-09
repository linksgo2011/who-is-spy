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


    public boolean vote(Integer voterId, Integer votedId,String roomToken) {
        Gamer gamerVoter = gamerDao.findOne(voterId);
        Gamer gamerBeenVoted = gamerDao.findOne(votedId);

        if (gamerVoter.isVoted() == true | gamerVoter == null | gamerBeenVoted == null) {
            return false;
        }
        gamerVoter.setVoted(true);
        Vote vote = voteDao.findOneByPlayer(votedId);
        try {
            if (vote == null) {
                Vote newVote = new Vote();
                newVote.setPlayer(votedId);
                newVote.setVoteNumber(1);
                newVote.setRoom(roomToken);
                newVote.setVoters(gamerVoter.getGamer());
                voteDao.save(newVote);
            } else {
                Integer number = vote.getVoteNumber() + 1;
                vote.setVoteNumber(number);
                String voters = vote.getVoters();
                voters += (", "+gamerVoter.getGamer());
                vote.setVoters(voters);
                voteDao.save(vote);
            }
        } catch (Exception e) {
            gamerVoter.setVoted(false);
            e.printStackTrace();
        }

        gamerDao.save(gamerVoter);
        return true;
    }
}
