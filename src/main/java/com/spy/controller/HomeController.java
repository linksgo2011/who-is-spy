package com.spy.controller;

import com.spy.model.dao.GamerDao;
import com.spy.model.dao.RoomDao;
import com.spy.model.dao.VoteDao;
import com.spy.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    GamerDao gamerDao;
    RoomDao roomDao;
    VoteDao voteDao;
    GameService gameService;

    @Autowired
    public HomeController(GamerDao gamerDao, RoomDao roomDao, VoteDao voteDao, GameService gameService) {
        this.gamerDao = gamerDao;
        this.roomDao = roomDao;
        this.voteDao = voteDao;
        this.gameService = gameService;
        gameService.initWords();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goHome() {

        return "index";
    }
}
