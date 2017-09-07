<script src="https://cdn.bootcss.com/jquery/1.8.2/jquery.min.js"></script>

<script>
    $(function () {
        var $targetElement = $("#container");

        function refresh() {
            $.get(window.location.href, function (data) {
                console.log($("#container",$($.parseHTML(data))));

                $("#container").replaceWith($("#container",$($.parseHTML(data))));
            })
        }

        if ($targetElement.length && $targetElement.attr("enableRefresh")) {
                setInterval(refresh, 3000);
        }
    })
</script>
</body>
</html>