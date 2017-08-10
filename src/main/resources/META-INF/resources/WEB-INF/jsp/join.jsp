<%@ page language="java" import="java.util.*" pageEncoding="GB18030" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Who is spy</title>
</head>

<body>
<div>
    <span>Username</span>
    <input type="text" id="username">
    <script type="application/javascript">
        var join = function () {
            var name = document.getElementById("username").value;
            var url = "http://10.206.20.20:8087/login?name=" + name + "&roomToken=" +"${room.roomToken}";
            window.open(url, "_self");
        }
    </script>
    <input type="button" onclick="join()">join</input>
</div>

</body>
</html>