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
                            <a class="button" target="_self" href="/vote?voter=${player.gamer}&voted=${item.gamer}">vote</a>
                        </c:if>
                    </c:if>
                </li>
            </c:forEach>
        </ol>
        <c:if test="${showResult==true}">
            <hr>
            <div>
                <ul>
                    <c:forEach items="${vote}" var="item">
                        <li>
                            <span>vote.player</span>
                            <span>vote.voteNumber</span>
                        </li>
                    </c:forEach>

                </ul>
            </div>
        </c:if>
        <c:if test="${status=='started'}">
            <div>
                your word is: ${player.word}
            </div>
        </c:if>
    </div>
</div>

<%@include file="../footer.jsp"%>