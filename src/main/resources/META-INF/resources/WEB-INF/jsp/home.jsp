<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@include file="header.jsp"%>

<div class="container dir-ltr">
    <div class="main-content">
        <form action="/new" method="GET">
            <div class="row">
                <div class="six columns">
                    <label for="roomName">New room</label>
                    <input class="u-full-width" type="text" name="name" placeholder="Please type your room name" id="roomName">
                </div>
            </div>
            <button class="button-primary">create</button>
        </form>
    </div>
</div>

<%@include file="footer.jsp"%>
