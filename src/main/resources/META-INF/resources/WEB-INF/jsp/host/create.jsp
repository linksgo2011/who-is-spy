<%@include file="../header.jsp"%>

<div class="container dir-ltr">
    <div class="main-content">
        <div class="alert">${flashSuccessMsg}</div>

        <form action="/new" method="POST">
            <div class="row">
                <div class="six columns">
                    <label for="roomName">New room</label>
                    <input class="u-full-width" type="text" name="name" placeholder="Please type your name" id="roomName">
                </div>
            </div>
            <button class="button-primary">create</button>
        </form>
    </div>
</div>

<%@include file="../footer.jsp"%>
