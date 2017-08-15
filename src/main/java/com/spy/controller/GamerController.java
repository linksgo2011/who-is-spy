package com.spy.controller;

import com.spy.model.Gamer;
import com.spy.model.Room;
import com.spy.model.Status;
import com.spy.model.dao.GamerDao;
import com.spy.model.dao.RoomDao;
import com.spy.model.dao.VoteDao;
import com.spy.service.GameService;
import com.spy.service.PlayerService;
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

    @Autowired
    public GamerController(GamerDao gamerDao, PlayerService playerService, RoomDao roomDao, VoteDao voteDao, GameService gameService) {
        this.gamerDao = gamerDao;
        this.playerService = playerService;
        this.roomDao = roomDao;
        this.voteDao = voteDao;
        this.gameService = gameService;
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

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView createRoom(@RequestParam String name, HttpSession httpSession) {
        Room room = roomDao.findOneByRoomOwner(name);
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        if (room != null) {
            modelMap.addAttribute("roomlink", room.getRoomLink());
            List<Gamer> gamerList = gamerDao.findByRoom(room.getRoomToken());
            modelMap.addAttribute("gamers", gamerList);
            modelAndView.addAllObjects(modelMap);
            modelAndView.setViewName("hostroom");
            return modelAndView;
        }
        Room newRoom = new Room();
        String roomToken = RandomStringUtils.random(8, "0123456789abcdefghijklmnopqrstuvwxyz");
        String roomLink = "http://localhost:8087/room?roomToken=" + roomToken;
        newRoom.setRoomLink(roomLink);
        newRoom.setRoomOwner(name);
        newRoom.setRoomToken(roomToken);
        newRoom.setHostSession(httpSession.getId());
        newRoom.setStatus(Status.WAITING);
        roomDao.save(newRoom);
        modelMap.addAttribute("room", newRoom);
        List<Gamer> gamerList = gamerDao.findByRoom(roomToken);
        modelMap.addAttribute("gamers", gamerList);
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("hostroom");
        return modelAndView;
    }
}
