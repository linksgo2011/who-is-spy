<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Who is spy</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gbk">
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <title>Who is the spy!</title>
    <style type="text/css">
        body {
            padding: 50px 10px;
            text-align: center;
        }

        input {
            width: 100%;
            height: 30px;
            border: 1px solid #ccc;
        }

        button, .btn {
            width: 100%;
            line-height: 20px;
            padding: 10px;
            border-radius: 2px;
            text-decoration: none;
            margin-top: 20px;
            color: #333;
            font-size: 18px;
            background: #f60;
            color: #fff;
            font-weight: lighter;
            border: 1px solid #f60;
        }

        td {
            word-break: break-all;
            word-wrap: break-word;
        }

        table {
            width: 100%;
            line-height: 30px;
            margin-top: 10px;
        }

        .alert {
            color: #f60;
            text-align: center;
            padding: 20px 0px;
            font-size: 16px;
        }
    </style>
</head>

<body>
<div>
    <span>Status:${status}</span>

    <h3>player list</h3>
    <div>
        <span>name</span>
    </div>
    <ul>
        <c:forEach items="${gamers}" var="gamer">
            <li>
                <span>${gamer.gamer}</span>
                <c:if test="${status=='voting'}">
                    <c:if test="${!player.voted}">
                    <button onclick="vote()">vote</button>
                    <script type="application/javascript">
                        var vote = function () {
                            var url = "http://10.206.20.20:8087/vote?voter=" + "${player}" + "&voted=" + "${gamer.gamer}"+"&roomToken="+"${player.room}";
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
<script type="application/javascript">
    setTimeout("self.location.reload();", 3000);
</script>
</body>
</html>