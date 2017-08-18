package com.spy.model;

import javax.persistence.*;

/**
 * Created by bingwang on 8/4/17.
 */
@Entity
@Table(name = "vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "player")
    private Integer player;

    @Column(name = "vote_number")
    private Integer voteNumber;

    @Column(name = "room")
    private String room;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player",insertable=false,updatable = false)
    private Gamer gamer;

    public Gamer getGamer() {
        return gamer;
    }

    public void setGamer(Gamer gamer) {
        this.gamer = gamer;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPlayer() {
        return player;
    }

    public void setPlayer(Integer player) {
        this.player = player;
    }

    public Integer getVoteNumber() {
        return voteNumber;
    }

    public void setVoteNumber(Integer voteNumber) {
        this.voteNumber = voteNumber;
    }
}
