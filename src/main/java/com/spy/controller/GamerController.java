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
import javassist.NotFoundException;
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

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public ModelAndView join(@RequestParam(value="roomToken",required = false) String roomToken, HttpServletRequest httpServletRequest) throws NotFoundException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roomToken",roomToken);
        modelAndView.setViewName("gamer/join");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView doLogin(@RequestParam String name, @RequestParam String roomToken, HttpServletRequest httpServletRequest) throws NotFoundException {
        Room room = roomDao.findOneByRoomToken(roomToken);
        if(room == null){
            throw new NotFoundException("can't find room");
        }

        HttpSession httpSession = httpServletRequest.getSession();
        Gamer gamer = playerService.ifUserAlreadyLogin(name);

        if (gamer == null) {
            if (name == "") {
                name = "anonymous";
            }
            gamer = new Gamer(name, httpSession.getId());
            gamer.setRoom(roomToken);
            gamerDao.save(gamer);
        }

        return new ModelAndView("redirect:/room?roomToken="+roomToken);
    }


    @RequestMapping(value = "/room", method = RequestMethod.GET)
    public ModelAndView room(@RequestParam String roomToken, HttpServletRequest httpServletRequest) throws Exception {
        HttpSession httpSession = httpServletRequest.getSession();
        List<Gamer> gamers = gamerDao.findBySession(httpSession.getId());
        ModelMap modelMap = new ModelMap();
        ModelAndView modelAndView = new ModelAndView();
        Gamer gamer = new Gamer();

        if (gamers.size() == 0) {
            return new ModelAndView("redirect:/join?roomToken="+roomToken);
        }

        gamer = gamers.get(0);
        if (!roomToken.equals(gamer.getRoom())) {
            throw new Exception("room token is not equal");
        }

        gamers = gamerDao.findByRoom(roomToken);
        modelMap.addAttribute("status", roomDao.findOneByRoomToken(roomToken).getStatus());
        modelMap.addAttribute("gamer", gamer);
        modelMap.addAttribute("others", gamers);
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("gamer/room");
        return modelAndView;
    }

    @RequestMapping(value = "/vote", method = RequestMethod.GET)
    public ModelAndView vote(@RequestParam String voter, @RequestParam String voted) {
        // TODO check room state and prevent double commit
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
