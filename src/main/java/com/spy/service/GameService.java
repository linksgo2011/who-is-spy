package com.spy.service;

import com.spy.model.Gamer;
import com.spy.model.Vote;
import com.spy.model.Word;
import com.spy.model.dao.GamerDao;
import com.spy.model.dao.RoomDao;
import com.spy.model.dao.VoteDao;
import com.spy.model.dao.WordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Created by bingwang on 8/8/17.
 */
@Service
public class GameService {
    RoomDao roomDao;
    WordDao wordDao;
    GamerDao gamerDao;
    VoteDao voteDao;

    @Autowired
    public GameService(RoomDao roomDao, WordDao wordDao, GamerDao gamerDao, VoteDao voteDao) {
        this.roomDao = roomDao;
        this.wordDao = wordDao;
        this.gamerDao = gamerDao;
        this.voteDao = voteDao;

    }

    public boolean asignWords(String roomToken) {
        List<Gamer> gamers = gamerDao.findByRoom(roomToken);
        int playerNum = gamers.size();

        if (playerNum < 2) {
            return false;
        }
        Random random = new Random();
        List<Word> words = (List<Word>) wordDao.findAll();
        if (words.size() == 0) {
            return false;
        }
        int number = words.size();
        int index = random.nextInt(number) + 1;
        Word word = wordDao.findOne(index);
        int spy = random.nextInt(playerNum);
        Gamer gamer = gamers.get(spy);
        gamers.remove(spy);
        gamer.setWord(word.getOption1());
        for (Gamer item : gamers) {
            item.setWord(word.getOption2());
            gamerDao.save(item);
        }
        gamerDao.save(gamer);
        wordDao.delete(word);
        return true;
    }

    public void initWords() {
        saveWord("Narinder", "Sebastian");
        saveWord("wine", "beer");
        saveWord("potato", "tomato");
        saveWord("bus", "taxi");
        saveWord("button", "link");
        saveWord("mac", "ipad");
        saveWord("Turbo Drop", "roller coaster");
        saveWord("balloon", "bubble");
        saveWord("beautiful", "elegant");
        saveWord("air", "oxygen");
        saveWord("crab", "lobster");
    }

    private void saveWord(String option1, String option2) {
        Word word = new Word();
        word.setOption1(option1);
        word.setOption2(option2);
        wordDao.save(word);
    }

    public void oneGamerOut(String roomToken) {
        List<Vote> votes = voteDao.findAllByRoomOrderByVoteNumberDesc(roomToken);
        if (votes.size() > 1) {
            if (votes.get(0).getVoteNumber() != votes.get(1).getVoteNumber()) {
                Vote vote = votes.get(0);
                Gamer gamer = vote.getGamer();
                gamer.setStatus("out");
                gamerDao.save(gamer);
            }
        }
    }

    public void endGame(String roomToken) {
        List<Gamer> gamers = gamerDao.findByRoom(roomToken);
        for (Gamer gamer : gamers) {
            if(gamer.getStatus().equals("out")){
                gamer.setStatus("fail");
            }else {
                gamer.setStatus("win");
            }
        }
    }
}
