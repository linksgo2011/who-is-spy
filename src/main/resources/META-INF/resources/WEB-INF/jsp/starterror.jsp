<%@ page language="java" import="java.util.*" pageEncoding="GB18030" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Who is spy</title>
</head>

<body>
<div>
    game can't start!
    <input type="button" value="return" onclick="back()"/>
    <script type="application/javascript">
        var back = function () {
            var url = "http://10.206.20.20:8087/back?roomToken=" + "${room.roomToken}";
            window.open(url, "_self");
        }
    </script>
</div>

</body>
</html>