<%@ page language="java" import="java.util.*" pageEncoding="GB18030" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Who is spy</title>
    <script type="text/javascript" src="js/room.js"></script>
</head>

<body>
<div>
    <div>
        <span>room link: </span>
        <input type="text" value="${room.roomLink}">
    </div>
    <div>
        <h3>Gamer list</h3>
        <input type="button" value="refresh" onclick="refresh()">
        <script type="application/javascript">
            var refresh = function () {
                var url = "http://localhost:8087/refresh?roomToken="+"${room.roomToken}";
                window.open(url,"_self");
            }

        </script>
        <table>
            <c:forEach items="${gamers}" var="gamer">
                <div>
                    <span>${gamer.gamer}</span>
                    <c:if test="${room.status=='started'}">
                        <span>${gamer.word}</span>
                    </c:if>
                </div>
            </c:forEach>
        </table>
    </div>
    <div>
        <a target="_self" href="http://localhost:8087/start?token=${room.roomToken}">start</a>
        <c:if test="${room.status!='voting'}">
            <a target="_self" id="votea" href="http://localhost:8087/startVoting?token=${room.roomToken}">vote</a>
        </c:if>
        <c:if test="${room.status=='voting'}">
            <a target="_self" id="votea" href="http://localhost:8087/stopVoting?token=${room.roomToken}">stop
                vote</a>
        </c:if>

    </div>
    <c:if test="${showVote}">
        <div>
            <h3>Result</h3>
            <table>
                <c:forEach items="${votes}" var="vote">
                    <div>
                        <span>${vote.name}</span>
                        <span>${vote.vote}</span>
                    </div>
                </c:forEach>
            </table>
        </div>
    </c:if>
</div>


</body>
</html>