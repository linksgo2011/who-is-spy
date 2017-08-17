<script src="https://cdn.bootcss.com/jquery/1.8.2/jquery.min.js"></script>

<script>
    $(function () {
        function refresh() {
            $.get(window.location.href,function (data) {
                var $targetElement = $("#container");
                if($targetElement.length && $targetElement.hasAttribute("enableRefresh")){
                    $("#container").replaceWith($.parseHTML(data)[13]);
                }
            })
        }
        setInterval(refresh,3000);
    })
</script>
</body>
</html>