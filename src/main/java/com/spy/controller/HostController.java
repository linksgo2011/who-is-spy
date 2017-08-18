package com.spy.controller;

import com.spy.model.Gamer;
import com.spy.model.Room;
import com.spy.model.Status;
import com.spy.model.Vote;
import com.spy.model.dao.GamerDao;
import com.spy.model.dao.RoomDao;
import com.spy.model.dao.VoteDao;
import com.spy.service.GameService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by bingwang on 8/3/17.
 */
@Controller
public class HostController {

    GamerDao gamerDao;
    RoomDao roomDao;
    VoteDao voteDao;
    GameService gameService;

    private String SESSION_KEY = "ROOM";

    @Autowired
    public HostController(GamerDao gamerDao, RoomDao roomDao, VoteDao voteDao, GameService gameService) {
        this.gamerDao = gamerDao;
        this.roomDao = roomDao;
        this.voteDao = voteDao;
        this.gameService = gameService;
        gameService.initWords();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createRoom() {
        return "host/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String doCreateRoom(
            @RequestParam String name,
            HttpSession httpSession,
            HttpServletRequest httpServletRequest
    ) {
        Room room = roomDao.findOneByRoomOwner(name);
        if (room != null) {
            return "redirect:/dashboard";
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

        httpSession.setAttribute(SESSION_KEY,roomToken);

        return "redirect:/dashboard";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard(HttpSession httpSession) throws Exception {
        Room room = getRoomFromSession(httpSession);
        return returnHostRoomInfo(room);
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public ModelAndView startGame(
            @RequestParam String token,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            HttpSession httpSession
    ) throws Exception {
        Room room = getRoomFromSession(httpSession);
        String referer = request.getHeader("Referer");

        boolean result = gameService.asignWords(token);
        if (!result) {
            redirectAttributes.addFlashAttribute("flashSuccessMsg", "Assign word failed!");
            return new ModelAndView("redirect:" + referer);
        }

        // TODO before save room we should check previous state
        room.setStatus(Status.STARTED);
        roomDao.save(room);

        return new ModelAndView("redirect:" + referer);
    }

    @RequestMapping(value = "/startVoting", method = RequestMethod.GET)
    public ModelAndView startVoting(
            @RequestParam String roomToken,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            HttpSession httpSession
    ) throws Exception {
        // TODO stop use token for host

        Room room = getRoomFromSession(httpSession);
        String referer = request.getHeader("Referer");

        // TODO before save room we should check previous state
        String status = room.getStatus();
        if(status==Status.WAITING){
            redirectAttributes.addFlashAttribute("flashSuccessMsg", "please start game first!");
            return new ModelAndView("redirect:" + referer);
        }
        room.setStatus(Status.VOTING);
        roomDao.save(room);

        voteDao.deleteAllByRoom(room.getRoomToken());
        return new ModelAndView("redirect:" + referer);
    }


    @RequestMapping(value = "/stopVoting", method = RequestMethod.GET)
    public ModelAndView stopVoting(
            @RequestParam String token,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            HttpSession httpSession
    ) throws Exception {
        // TODO stop use token for host

        Room room = getRoomFromSession(httpSession);
        String referer = request.getHeader("Referer");

        // TODO before save room we should check previous state
        room.setStatus(Status.STARTED);
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

    private ModelAndView returnHostRoomInfo(Room room) throws Exception {
        List<Gamer> gamers = gamerDao.findByRoom(room.getRoomToken());
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();

        // TODO votes should add condition just limit to a room
        List<Vote> votes = (List<Vote>) voteDao.findAll();
        modelMap.addAttribute("votes", votes);
        modelMap.addAttribute("gamers", gamers);
        modelMap.addAttribute("room", room);
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("host/dashboard");
        return modelAndView;
    }

    private Room getRoomFromSession(HttpSession httpSession) throws Exception {
        String roomToken = (String) httpSession.getAttribute(SESSION_KEY);
        Room room = roomDao.findOneByRoomToken(roomToken);

        if(room == null){
            throw new Exception("token expired!");
        }
        return room;
    }
}