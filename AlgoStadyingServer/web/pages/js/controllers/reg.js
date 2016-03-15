
var request = null;
function createRegRequest() 
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

function registerUser()
{
    createRegRequest()

    var login = document.getElementById("login").value;
    var name = document.getElementById("name").value;
    var pass = document.getElementById("pass").value;
    var type = document.getElementById("type").value;

    var url = "/register?"
    + "login=" + login + "&"
    + "type=" + type + "&"
    + "pass=" + pass + "&"
    + "name=" + name + "&";

    console.log(url);

    request.onreadystatechange = handleRegResponse;
    request.open("POST", url, true);
    request.send(null);
}

function handleRegResponse() {
    if (request.readyState==4)
    { 
        if (request.status==200) {
            alert("User created!")
            window.history.back();
        } else {
            alert("Failed to create the user")
        }        
    }
}