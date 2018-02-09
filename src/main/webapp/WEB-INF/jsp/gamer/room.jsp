<%@include file="../header.jsp"%>

<div class="container dir-ltr" id="container" enableRefresh="true">
    <div class="main-content">
        <div class="alert">${flashSuccessMsg}</div>

        <div class="welcome">Hello! ${player.gamer}</div>
        <h4 class="center">Status:${status}</h4>
        <c:if test="${gamerstatus =='out'}">
            <h6 style="color:red;">you are out!</h6>
        </c:if>
        <c:if test="${gamerstatus =='win'}">
            <h6 style="color:green;">you are winner!</h6>
        </c:if>
        <c:if test="${gamerstatus =='fail'}">
            <h6 style="color:red;">you are failure!</h6>
        </c:if>
        <c:if test="${status=='started'}">
            <hr>
            <h4 class="center">
                your word is: ${player.word}
            </h4>
        </c:if>
        <hr>
        <ol>
            <c:forEach items="${others}" var="item">
                <li>
                    <span>${item.gamer}</span>
                    <c:if test="${status=='voting' && player.id != item.id}">
                        <c:if test="${!player.voted && gamerstatus != 'out'}">
                            <a class="button" target="_self" href="/vote?roomToken=${room.roomToken}&voted=${item.id}">vote</a>
                        </c:if>
                    </c:if>
                </li>
            </c:forEach>
        </ol>
    </div>
</div>

<%@include file="../footer.jsp"%>