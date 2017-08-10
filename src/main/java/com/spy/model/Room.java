package com.spy.model;

import javax.persistence.*;

/**
 * Created by bingwang on 8/4/17.
 */
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "room_owner")
    private String roomOwner;

    @Column(name="room_link")
    private String roomLink;

    @Column(name="room_token")
    private String roomToken;

    @Column(name = "status")
    private String status;

    @Column(name="host_session")
    private String hostSession;

    @Column(name = "showVote")
    private boolean showVote = false;






    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(String roomOwner) {
        this.roomOwner = roomOwner;
    }

    public String getRoomLink() {
        return roomLink;
    }

    public void setRoomLink(String roomLink) {
        this.roomLink = roomLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoomToken() {
        return roomToken;
    }

    public void setRoomToken(String roomToken) {
        this.roomToken = roomToken;
    }

    public String getHostSession() {
        return hostSession;
    }

    public void setHostSession(String hostSession) {
        this.hostSession = hostSession;
    }

    public boolean isShowVote() {
        return showVote;
    }

    public void setShowVote(boolean showVote) {
        this.showVote = showVote;
    }
}
