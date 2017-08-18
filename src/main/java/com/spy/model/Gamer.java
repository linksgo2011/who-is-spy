package com.spy.model;

import javax.persistence.*;

/**
 * Created by bingwang on 8/3/17.
 */
@Entity
public class Gamer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    public String getGamer() {
        return gamer;
    }

    public void setGamer(String gamer) {
        this.gamer = gamer;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Column(name = "gamer")
    private String gamer;

    @Column(name = "session")
    private String session;

    @Column(name = "word")
    private String word;

    @Column(name = "room")
    private String room;

    @Column(name = "voted")
    private boolean voted = false;

    public Gamer(String name, String httpSessionId) {
        this.gamer = name;
        this.session = httpSessionId;
    }

    public Gamer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }
}
