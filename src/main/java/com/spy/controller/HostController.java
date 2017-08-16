package com.spy.controller;

import com.spy.model.Gamer;
import com.spy.model.Room;
import com.spy.model.Status;
import com.spy.model.Vote;
import com.spy.model.dao.GamerDao;
import com.spy.model.dao.RoomDao;
import com.spy.model.dao.VoteDao;
import com.spy.service.GameService;
import com.spy.service.PlayerService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by bingwang on 8/3/17.
 */
@Controller
public class HostController {

    GamerDao gamerDao;
    PlayerService playerService;
    RoomDao roomDao;
    VoteDao voteDao;
    GameService gameService;

    @Autowired
    public HostController(GamerDao gamerDao, PlayerService playerService, RoomDao roomDao, VoteDao voteDao, GameService gameService) {
        this.gamerDao = gamerDao;
        this.playerService = playerService;
        this.roomDao = roomDao;
        this.voteDao = voteDao;
        this.gameService = gameService;
        playerService.init();
        gameService.initWords();
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String goHome() {
        return "home";
    }


    @RequestMapping(value = "/back", method = RequestMethod.GET)
    public ModelAndView backToHost(@RequestParam String roomToken) {
        return returnHostRoomInfo(roomToken);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ModelAndView refreshPlayerList(@RequestParam String roomToken) {
        return returnHostRoomInfo(roomToken);
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public ModelAndView startGame(@RequestParam String token) {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        Room room = roomDao.findOneByRoomToken(token);
        if (room == null) {
            return new ModelAndView("error");
        }
        boolean result = gameService.asignWords(token);
        if (!result) {
            modelMap.addAttribute("room", room);
            modelAndView.addAllObjects(modelMap);
            modelAndView.setViewName("starterror");
            return modelAndView;
        }

        List<Gamer> gamers = gamerDao.findByRoom(room.getRoomToken());
        room.setStatus(Status.STARTED);
        roomDao.save(room);
        modelMap.addAttribute("gamers", gamers);
        modelMap.addAttribute("room", room);
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("hostroom");
        return modelAndView;
    }

    @RequestMapping(value = "/startVoting", method = RequestMethod.GET)
    public ModelAndView startVoting(@RequestParam String token) {
        Room room = roomDao.findOneByRoomToken(token);
        if (room == null) {
            return new ModelAndView("error");
        }
        roomDao.delete(room);
        room.setStatus(Status.VOTING);
        roomDao.save(room);
        List<Gamer> gamers = gamerDao.findByRoom(room.getRoomToken());
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("gamers", gamers);
        modelMap.addAttribute("room", room);
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("hostroom");
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
    @RequestMapping(value = "/stopVoting", method = RequestMethod.GET)
    public ModelAndView stopVoting(@RequestParam String token) {
        Room room = roomDao.findOneByRoomToken(token);
        if (room == null) {
            return new ModelAndView("error");
        }
        roomDao.delete(room);
        room.setStatus(Status.STARTED);
        room.setShowVote(true);
        roomDao.save(room);
        List<Vote> votes = (List<Vote>) voteDao.findAll();
        List<Gamer> gamers = gamerDao.findByRoom(room.getRoomToken());
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("gamers", gamers);
        modelMap.addAttribute("room", room);
        modelMap.addAttribute("votes", votes);
        modelMap.addAttribute("showVote", true);
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("hostroom");
        return modelAndView;
    }

    private ModelAndView returnHostRoomInfo(String roomToken) {
        Room room = roomDao.findOneByRoomToken(roomToken);
        List<Gamer> gamers = gamerDao.findByRoom(roomToken);
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();

        modelMap.addAttribute("gamers", gamers);
        modelMap.addAttribute("room", room);
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("hostroom");
        return modelAndView;
    }
}
