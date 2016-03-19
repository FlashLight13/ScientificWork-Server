
var request = null;
function createGetUserRequest() 
{
    try {
        request = new XMLHttpRequest();
    } catch (trymicrosoft) {
        try {
            request = new ActiveXObject("MsXML2.XMLHTTP");
        } catch (othermicrosoft) {
            try {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (failed) {
                request = null;
            }
        }
    }

    if (request == null)
        alert("Error creating request object!");
}

function getUser()
{
    createGetUserRequest()
    var query = window.location.search.substring(1);
    var url = "/web/get_user?" + query;
    request.onreadystatechange = handleGetUserResponse;
    request.open("POST", url, true);
    request.send(null);
}

function handleGetUserResponse() {
    if (request.readyState==4 && request.status==200)
    { 
        var det=eval("("+request.responseText+")");
        document.getElementById("login").innerHTML = det.login;
        document.getElementById("name").innerHTML = det.name;
        document.getElementById("type").innerHTML = det.type;
        document.getElementById("pass").innerHTML = det.pass;
    }
}