package com.spy.service;

import com.spy.model.Gamer;
import com.spy.model.Word;
import com.spy.model.dao.GamerDao;
import com.spy.model.dao.RoomDao;
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

    @Autowired
    public GameService(RoomDao roomDao, WordDao wordDao, GamerDao gamerDao) {
        this.roomDao = roomDao;
        this.wordDao = wordDao;
        this.gamerDao = gamerDao;

    }

    public boolean asignWords(String roomToken,int wordId) {
//        List<Word> words = (List<Word>) wordDao.findAll();
//        if (words.size() == 0) {
//            return false;
//        }
//        int number = words.size();
//        Random random = new Random();
//        int index = random.nextInt(number) + 1;
//        Word word = wordDao.findOne(index);
        List<Gamer> gamers = gamerDao.findByRoom(roomToken);
        int playerNum = gamers.size();

        if (playerNum < 2) {
            return false;
        }
        Random random = new Random();
        Word word = wordDao.findOneById(wordId);
        int spy = random.nextInt(playerNum);
        Gamer gamer = gamers.get(spy);
        gamers.remove(spy);
        gamerDao.delete(gamer);
        gamer.setWord(word.getOption1());
        for (Gamer item : gamers) {
            gamerDao.delete(item);
            item.setWord(word.getOption2());
            gamerDao.save(item);
        }
        gamerDao.save(gamer);
        wordDao.delete(word);
        return true;
    }

    public void initWords() {
        Word word = new Word();
        word.setOption1("mother");
        word.setOption2("father");
        wordDao.save(word);
        Word word2 = new Word();
        word2.setOption1("sister");
        word2.setOption2("brother");
        wordDao.save(word2);
    }
}
