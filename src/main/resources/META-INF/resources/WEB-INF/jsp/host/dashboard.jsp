<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.jsp"%>

<div class="container dir-ltr" id="container" enableRefresh="true">
    <div class="main-content">
        <div class="alert">${flashSuccessMsg}</div>

        <div class="welcome">Hello! ${room.roomOwner}</div>
        <h4 class="center">
            <c:choose>
                <c:when test="${room.status == 'waiting'}">
                    Waiting For Players...
                </c:when>
                <c:when test="${room.status == 'started'}">
                    Game is in progress...
                </c:when>
                <c:when test="${room.status == 'voting'}">
                    Gamers are voting...
                </c:when>
            </c:choose>
        </h4>

        <div class="center access-link">
            <span>ROOM LINK: </span>
            <%--TODO one click copy--%>
            <input type="text" value="${room.roomLink}">
        </div>
        <hr>

        <%--<table class="u-full-width gamer-list">--%>
            <%--<thead>--%>
            <%--<tr>--%>
                <%--<th>Order</th>--%>
                <%--<th>Name</th>--%>
                <%--<th>Word</th>--%>
                <%--<th>Operation</th>--%>
            <%--</tr>--%>
            <%--</thead>--%>
            <%--<tbody>--%>

            <%--<c:forEach items="${gamers}" var="gamer">--%>
                <%--<tr>--%>
                    <%--<td></td>--%>
                    <%--<td>${gamer.gamer}</td>--%>
                    <%--<td>--%>
                        <%--<c:if test="${room.status=='started'||room.status=='voting'}">--%>
                            <%--<span class="word">${gamer.word}</span>--%>
                        <%--</c:if>--%>
                    <%--</td>--%>
                    <%--<td>TODO</td>--%>
                <%--</tr>--%>
            <%--</c:forEach>--%>
            <%--</tbody>--%>
        <%--</table>--%>

        <ol class="gamer-list">
            <c:forEach items="${gamers}" var="gamer">
                <li>
                    <span class="username">${gamer.gamer}</span>
                    <c:if test="${room.status=='started'||room.status=='voting'}">
                        <span class="word">${gamer.word}</span>
                    </c:if>
                </li>
            </c:forEach>
        </ol>
        <hr>
        <div class="center operation">
            <a class="button" target="_self" href="javascript:void(0)" onclick="confirm('are you sure start this game?')?window.location.href='/start':false;">start game</a>
            <c:if test="${room.status!='voting'}">
                <a class="button" target="_self" href="javascript:void(0)" onclick="confirm('are you sure start voting?')?window.location.href='/startVoting':false">start vote</a>
            </c:if>
            <c:if test="${room.status=='voting'}">
                <a class="button" target="_self" href="javascript:void(0)" onclick="confirm('are you sure stop voting and continue game?')?window.location.href='/stopVoting':false">next round</a>
            </c:if>
        </div>

        <c:if test="${room.status == 'voting'}">
            <hr>
            <div>
                <h4>Vote Result</h4>
                <ol>
                    <c:forEach items="${votes}" var="vote">
                        <li>
                                ${vote.gamer.gamer} got ${vote.voteNumber} votes
                        </li>
                    </c:forEach>
                </ol>
            </div>
        </c:if>
    </div>
</div>

<%@include file="../footer.jsp"%>
