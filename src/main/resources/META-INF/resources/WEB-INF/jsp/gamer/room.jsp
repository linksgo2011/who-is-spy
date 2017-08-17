<%@include file="../header.jsp"%>

<div class="container dir-ltr">
    <div class="main-content">
        <div class="welcome">Hello! ${gamer.gamer}</div>
        <h4 class="center">Status:${status}</h4>
        <hr>
        <ul>
            <c:forEach items="${gamers}" var="gamer">
                <li>
                    <span>${gamer.gamer}</span>
                    <c:if test="${status=='voting'}">
                        <c:if test="${!player.voted}">
                            <button onclick="vote()">vote</button>
                            <script type="application/javascript">
                                var vote = function () {
                                    var url = "http://localhost:8087/vote?voter="+"${player.gamer}"+"&voted=" + "${gamer.gamer}";
                                    window.open(url, "_self");
                                }
                            </script>
                        </c:if>
                    </c:if>
                </li>
            </c:forEach>
        </ul>
        <c:if test="${showResult==true}">
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
