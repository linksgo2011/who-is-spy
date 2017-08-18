<%@include file="../header.jsp"%>

<div class="container dir-ltr" id="container" enableRefresh="true">
    <div class="main-content">
        <div class="alert">${flashSuccessMsg}</div>

        <div class="welcome">Hello! ${player.gamer}</div>
        <h4 class="center">Status:${status}</h4>
        <hr>
        <ol>
            <c:forEach items="${others}" var="item">
                <li>
                    <span>${item.gamer}</span>
                    <c:if test="${status=='voting'}">
                        <c:if test="${!player.voted}">
                            <a class="button" target="_self" href="/vote?roomToken=${room.roomToken}&voted=${item.id}">vote</a>
                        </c:if>
                    </c:if>
                </li>
            </c:forEach>
        </ol>
        <c:if test="${status=='started'}">
            <div>
                your word is: ${player.word}
            </div>
        </c:if>
    </div>
</div>

<%@include file="../footer.jsp"%>