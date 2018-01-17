<%@include file="../header.jsp" %>
<div>
    <form action="/addword" name="word" method="POST">
        <div class="row">
            <div class="six columns">
                <label for="word1">Create <a href=""></a> normal word</label>
                <input class="u-full-width" type="text"
                       name="word1" placeholder="Please input the word"
                       id="word1" required>
                <label for="word2">Create <a href=""></a> spy word</label>
                <input class="u-full-width" type="text"
                       name="word2" placeholder="Please input the word"
                       id="word2" required>
            </div>
        </div>
        <button class="button-primary">add</button>
    </form>
    <c:if test="${success=='true'}">
        <div class="row">successfully added the word!</div>
    </c:if>
    <c:if test="${showWord!='true'}">
        <a class="button" target="_self" href="javascript:void(0)" onclick="window.location.href='/findAllWords'">check
            all the words</a>
    </c:if>
    <c:if test="${showWord=='true'}">
        <c:forEach items="${words}" var="word">
            <li>
                    ${word.option1} vs ${word.option2}
            </li>
        </c:forEach>
    </c:if>

</div>
<%@include file="../footer.jsp" %>