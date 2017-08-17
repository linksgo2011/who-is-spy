<%@include file="../header.jsp"%>

<div class="container dir-ltr">
    <div class="main-content">
        <form action="/login" method="POST">
            <div class="row">
                <div class="six columns">
                    <label for="roomName">Join a room</label>
                    <input type="hidden" name="roomToken" value="${roomToken}">
                    <input class="u-full-width" type="text" name="name" placeholder="Please type your name" id="roomName">
                </div>
            </div>
            <button class="button-primary">Join</button>
        </form>
    </div>
</div>

<%@include file="../footer.jsp"%>
