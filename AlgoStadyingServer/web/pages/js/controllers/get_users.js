
var request = null;
function createGetUsers() 
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

function getUsers()
{
    createGetUsers()
    var url = "/web/get_users";
    request.onreadystatechange = handleGetUsers;
    request.open("POST", url, true);
    request.send(null);
}

function handleGetUsers() {
    if (request.readyState==4 && request.status==200)
    { 
        var table = document.getElementById("users_table");
        table.innerHTML=""
         var row =  table.insertRow(i + 1);
            var cell = row.insertCell(0);
            cell.innerHTML="Index"
            var cell = row.insertCell(1);
            cell.innerHTML="Login"
            var cell = row.insertCell(2);
            cell.innerHTML="Name"
            var cell = row.insertCell(3);
            cell.innerHTML=""
        var det=eval("("+request.responseText+")");
        for(var i=0; i<det.length; i++) {
            var row =  table.insertRow(i + 1);
            var cell = row.insertCell(0);
            cell.innerHTML="<a href=\"./users_details.html?id=" + det[i].login + "\">"+ i +"</a>"
            var cell = row.insertCell(1);
            cell.innerHTML=det[i].login
            var cell = row.insertCell(2);
            cell.innerHTML=det[i].name
            var cell = row.insertCell(3);
            cell.innerHTML="<button onclick=\"removeUser('" + det[i].login +"')\"\>Remove\</button\>"
        }
    }
}