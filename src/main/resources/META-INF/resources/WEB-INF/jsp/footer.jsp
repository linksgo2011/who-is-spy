<script src="https://cdn.bootcss.com/jquery/1.8.2/jquery.min.js"></script>

<script>
    $(function () {
        var $targetElement = $("#container");

        function refresh() {
            $.get(window.location.href,function (data) {
                $("#container").replaceWith($.parseHTML(data)[13]);
            })
        }

        if($targetElement.length && $targetElement.attr("enableRefresh")){
            setInterval(refresh,3000);
        }
    })
</script>
</body>
</html>