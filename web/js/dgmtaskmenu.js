/**
 * Ask for refreshing the task list
 * @param {type} url
 * @returns {undefined}
 */
function refreshTaskList(url) {	
    var xhr=createXHR();
    xhr.open("GET", url, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status !== 404) {
                // Success
                var data = eval("(" + xhr.responseText + ")");
                tasklistDisplay( data );
            } else 
                tasklistFail();
        }
    };
    xhr.send(null);
}

/**
 * Click Event on task list (top right menu)
 */
$( "#dropdown-tasks" ).click(function() {
    $("#lutasklist").empty();
    WaitForTaskRefresh();
    // ask for resfresh / rest call
    refreshTaskList('./rest/taskslist/5');
});

/*
 * Format JSON / data variable 
 * [
 *      {"duration":0,"name":"wf_Full_Delta_SC","start":"2016-34-05 08:34:44","end":"2016-34-05 08:34:44","id":1,"message":"java.io.IOException: Cannot run program \"No\": CreateProcess error=2, Le fichier spécifié est introuvable","object":"infawf","status":"Success"}, 
 *      {"duration":0,"name":"wf_Full_Delta_SC","start":"2016-35-05 08:35:01","end":"2016-35-05 08:35:01","id":2,"message":"java.io.IOException: Cannot run program \"No\": CreateProcess error=2, Le fichier spécifié est introuvable","object":"infawf","status":"Success"}]
 * 
 */
function tasklistDisplay(data) {
    $("#lutasklist").empty();   // Empty the task list
    
    if (data.length == 0) {
        addOnlyOneTaskItem("No tasks !");
        addButtonTaskList();
        return;
    }
    
    for(var i=0; i < data.length; i++) {
        // add the task details
        addTaskItem(data[i].name + " - Started: " + data[i].start, data[i].status);
        
        // Add the divider (not for the last item)
        if (i != data.length -1) 
            addTaskDivider();
    }
    addButtonTaskList();
}

function addTaskDivider() {
    var lidivider = document.createElement("LI");
    lidivider.classList.add("divider");
    document.getElementById("lutasklist").appendChild(lidivider);
}

function addTaskItem(taskName, status) {
    var li = document.createElement("LI");
    li.classList.add("litaskitem");
    var liDiv = document.createElement("DIV");
    liDiv.classList.add("divTask");
    var liDivImg = document.createElement("DIV");
    
    var i = document.createElement("I");
    i.classList.add("fa");
    i.classList.add("fa-3x");
    if (status == "Success") 
        i.classList.add("fa-check-circle-o");
    else if (status == "Failed") 
        i.classList.add("fa-times-circle-o");
    else 
        i.classList.add("fa-random");
    
    liDivImg.classList.add("divTaskItem");
    liDivImg.appendChild(i);
    var liDivTxt = document.createElement("DIV");
    liDivTxt.classList.add("divTaskDesc");
    
    var content = document.createTextNode(taskName);
    liDivTxt.appendChild(content);
    liDiv.appendChild(liDivImg);
    liDiv.appendChild(liDivTxt);
    
    li.appendChild(liDiv);
    document.getElementById("lutasklist").appendChild(li);
}

function addOnlyOneTaskItem(task) {
    var li = document.createElement("LI");
    var liDiv = document.createElement("DIV");
    liDiv.classList.add("divNoTask");
    var content = document.createTextNode(task);
    liDiv.appendChild(content);
    li.classList.add("litaskitem");
    li.appendChild(liDiv);
    document.getElementById("lutasklist").appendChild(li);
}

function addButtonTaskList() {
    var li = document.createElement("LI");
    li.classList.add("divider");
    document.getElementById("lutasklist").appendChild(li);
    
    var li = document.createElement("LI");
    li.innerHTML = "<A href='?object=tasks' class='text-center'><strong>Go to Task list</Strong></a>";
    document.getElementById("lutasklist").appendChild(li);
}

/**
 * Wait message while refreshing the task list
 * @returns {undefined}
 */
function WaitForTaskRefresh() {
    $("#lutasklist").empty();   // Empty the task list
    var li = document.createElement("LI");
    li.classList.add("litaskitem");
    var liDiv = document.createElement("DIV");
    liDiv.classList.add("divNoTask");
    var liDivImg = document.createElement("DIV");
    liDivImg.classList.add("divTaskImage");
    var liDivTxt = document.createElement("DIV");
    liDivTxt.classList.add("divTaskDesc");
    
    var content = document.createTextNode("Please wait ");
    liDivTxt.appendChild(content);
    liDiv.appendChild(liDivImg);
    liDiv.appendChild(liDivTxt);
    
    li.appendChild(liDiv);
    document.getElementById("lutasklist").appendChild(li);
}

function tasklistFail() {
    $("#lutasklist").empty();   // Empty the task list
    addOnlyOneTaskItem("Refresh failed");
}