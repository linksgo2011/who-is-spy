var onCreate = function () {
    var name = document.getElementsByClassName("host")[0].value;
    if (name == "") {
        var element = document.getElementById("name");
        element.appendChild(document.createTextNode("please input your name"))
    } else {
        var url = "http://10.206.20.20:8087/new?name=" + name;
        window.open(url,"_self");
    }
}
