
var request = null;
function createRemove() 
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

function removeUser(id)
{
    createRemove()
    var url = "/web/remove?"
    + "id=" + id;

    console.log(url);

    request.onreadystatechange = handleRemove;
    request.open("POST", url, true);
    request.send(null);
}

function handleRemove() {
    if (request.readyState==4)
    { 
        if (request.status==200) {
            getUsers();
        } else {
            alert("Failed to remove the user")
        }
    }
}