<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="header.jsp"%>

<div class="container dir-ltr">
    <div class="main-content">
        <h4 class="center">
            Waiting For Players...
        </h4>
        <div class="center access-link">
            <span>ROOM LINK: </span>
            <input type="text" value="${room.roomLink}">
        </div>
        <hr>
        <ol>
            <c:forEach items="${gamers}" var="gamer">
                <li>
                    <span>${gamer.gamer}</span>
                    <c:if test="${room.status=='started'||room.status=='voting'}">
                        <span>${gamer.word}</span>
                    </c:if>
                </li>
            </c:forEach>
        </ol>
        <hr>
        <div class="center operation">
            <a class="button" target="_self" href="http://localhost:8087/start?token=${room.roomToken}">start game</a>

            <c:if test="${room.status!='voting'}">
                <a class="button" target="_self" id="votea" href="http://localhost:8087/startVoting?token=${room.roomToken}">start vote</a>
            </c:if>
            <c:if test="${room.status=='voting'}">
                <a class="button" target="_self" id="votea" href="http://localhost:8087/stopVoting?token=${room.roomToken}">stop vote</a>
            </c:if>
        </div>

        <c:if test="${showVote}">
            <hr>
            <div>
                <h3>Result</h3>
                <table>
                    <c:forEach items="${votes}" var="vote">
                        <div>
                            <span>${vote.player}</span>
                            <span>${vote.voteNumber}</span>
                        </div>
                    </c:forEach>
                </table>
            </div>
        </c:if>
    </div>
</div>

<%@include file="footer.jsp"%>
