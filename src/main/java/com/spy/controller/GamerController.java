package com.spy.controller;

import com.spy.model.Gamer;
import com.spy.model.Room;
import com.spy.model.Status;
import com.spy.model.dao.GamerDao;
import com.spy.model.dao.RoomDao;
import com.spy.model.dao.VoteDao;
import com.spy.service.GameService;
import com.spy.service.PlayerService;
import com.spy.service.VoteService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class GamerController {

    GamerDao gamerDao;
    PlayerService playerService;
    RoomDao roomDao;
    VoteDao voteDao;
    GameService gameService;
    VoteService voteService;

    @Autowired
    public GamerController(GamerDao gamerDao, PlayerService playerService, RoomDao roomDao, VoteDao voteDao, GameService gameService, VoteService voteService) {
        this.gamerDao = gamerDao;
        this.playerService = playerService;
        this.roomDao = roomDao;
        this.voteDao = voteDao;
        this.gameService = gameService;
        this.voteService = voteService;
        playerService.init();
        gameService.initWords();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam String name, @RequestParam String roomToken, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        Gamer gamer = playerService.ifUserAlreadyLogin(name);

        Room room = roomDao.findOneByRoomToken(roomToken);

        if (gamer == null) {
            if (name == "") {
                name = "anonymous";
            }
            gamer = new Gamer(name, httpSession.getId());
            gamer.setRoom(roomToken);
            gamerDao.save(gamer);
        }
        List<Gamer> gamers = gamerDao.findByRoom(roomToken);
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", room.getStatus());
        modelMap.addAttribute("player", gamer);
        modelMap.addAttribute("gamers", gamers);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("gamerroom");
        return modelAndView;
    }

    @RequestMapping(value = "/room", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam String roomToken, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        List<Gamer> gamers = gamerDao.findBySession(httpSession.getId());
        ModelMap modelMap = new ModelMap();
        ModelAndView modelAndView = new ModelAndView();
        Gamer gamer = new Gamer();

        if (gamers.size() != 0) {
            gamer = gamers.get(0);
            if (roomToken.equals(gamer.getRoom())) {
                gamers = gamerDao.findByRoom(roomToken);
                modelMap.addAttribute("status", roomDao.findOneByRoomToken(roomToken).getStatus());
                modelMap.addAttribute("gamer", gamer);
                modelMap.addAttribute("gamers", gamers);
                modelAndView.addAllObjects(modelMap);
                modelAndView.setViewName("gamerroom");
                return modelAndView;
            }
        }

        Room room = roomDao.findOneByRoomToken(roomToken);
        modelMap.addAttribute("room", room);
        modelAndView.setViewName("join");
        modelAndView.addAllObjects(modelMap);
        return modelAndView;

    }

    @RequestMapping(value = "/vote", method = RequestMethod.GET)
    public ModelAndView vote(@RequestParam String voter, @RequestParam String voted) {

        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();

        voteService.vote(voter, voted);

        Gamer gamer = gamerDao.findOneByGamer(voter);
        Room room = roomDao.findOneByRoomToken(gamer.getRoom());

        List<Gamer> gamers = gamerDao.findByRoom(room.getRoomToken());
        modelMap.addAttribute("player", voter);
        modelMap.addAttribute("gamers", gamers);
        modelMap.addAttribute("status", room.getStatus());
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("gamerroom");
        return modelAndView;
    }
}
