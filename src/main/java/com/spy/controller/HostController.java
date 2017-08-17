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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createRoom() {
        return "host/create";
    }

    @RequestMapping(value = "/back", method = RequestMethod.GET)
    public ModelAndView backToHost(@RequestParam String roomToken) {
        return returnHostRoomInfo(roomToken);
    }


    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createRoom(
            @RequestParam String name,
            HttpSession httpSession,
            HttpServletRequest httpServletRequest
    ) {
        Room room = roomDao.findOneByRoomOwner(name);
        if (room != null) {
            return "redirect:/dashboard?roomToken="+room.getRoomToken();
        }

        Room newRoom = new Room();
        // TODO room token should be a UUID in database
        String roomToken = RandomStringUtils.random(8, "0123456789abcdefghijklmnopqrstuvwxyz");
        String roomLink = getRoomLink(httpServletRequest, roomToken);
        newRoom.setRoomLink(roomLink);
        newRoom.setRoomOwner(name);
        newRoom.setRoomToken(roomToken);
        newRoom.setHostSession(httpSession.getId());
        newRoom.setStatus(Status.WAITING);
        roomDao.save(newRoom);

        return "redirect:/dashboard?roomToken="+roomToken;
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard(@RequestParam String roomToken) {
        return returnHostRoomInfo(roomToken);
    }

    @RequestMapping(value = "/stopVoting", method = RequestMethod.GET)
    public ModelAndView stopVoting(
            @RequestParam String token,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        Room room = roomDao.findOneByRoomToken(token);
        String referer = request.getHeader("Referer");

        if (room == null) {
            redirectAttributes.addFlashAttribute("flashSuccessMsg","Room can't be found!");
            return new ModelAndView("redirect:"+ referer);
        }

        room.setStatus(Status.STARTED);
        room.setShowVote(true);
        roomDao.save(room);
        return new ModelAndView("redirect:"+ referer);
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public ModelAndView startGame(
            @RequestParam String token,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        Room room = roomDao.findOneByRoomToken(token);
        String referer = request.getHeader("Referer");

        if (room == null) {
            redirectAttributes.addFlashAttribute("flashSuccessMsg","Room can't be found!");
            return new ModelAndView("redirect:"+ referer);
        }

        boolean result = gameService.asignWords(token);
        if (!result) {
            redirectAttributes.addFlashAttribute("flashSuccessMsg","Assign word failed!");
            return new ModelAndView("redirect:"+ referer);
        }
        room.setStatus(Status.STARTED);
        roomDao.save(room);

        return new ModelAndView("redirect:"+ referer);
    }

    @RequestMapping(value = "/startVoting", method = RequestMethod.GET)
    public ModelAndView startVoting(
            @RequestParam String token,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        Room room = roomDao.findOneByRoomToken(token);
        String referer = request.getHeader("Referer");

        if (room == null) {
            redirectAttributes.addFlashAttribute("flashSuccessMsg","Room can't be found!");
            return new ModelAndView("redirect:"+ referer);
        }

        room.setStatus(Status.VOTING);
        room.setShowVote(true);
        roomDao.save(room);
        return new ModelAndView("redirect:"+ referer);
    }

    /**
     * @param httpServletRequest
     * @param roomToken
     * @return
     */
    private String getRoomLink(HttpServletRequest httpServletRequest, String roomToken) {
        StringBuffer url = httpServletRequest.getRequestURL();
        String uri = httpServletRequest.getRequestURI();
        String host = url.substring(0, url.indexOf(uri));

        return host + "/room?roomToken=" + roomToken;
    }

    private ModelAndView returnHostRoomInfo(String roomToken) {
        Room room = roomDao.findOneByRoomToken(roomToken);
        List<Gamer> gamers = gamerDao.findByRoom(roomToken);
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();

        modelMap.addAttribute("gamers", gamers);
        modelMap.addAttribute("room", room);
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("host/dashboard");
        return modelAndView;
    }
}