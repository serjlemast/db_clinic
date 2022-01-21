getAllRolesFromServer();

function getAllRolesFromServer() {
    fetch(baseUrl+"/roles", {method: 'GET'})
        .then(response => response.json())
        .then(jsonResponse => createHtml(jsonResponse))
}

function createHtml(roleInJsonFormat) {
    createHtmlList(roleInJsonFormat)
    createRoleNameList(roleInJsonFormat)
}

function createHtmlList(roleInJsonFormat) {
    let divElement = document.getElementById("roles");
    let rootElement = document.createElement("ui");
    rootElement.setAttribute("id", "role_list")
    roleInJsonFormat.forEach(function (item) {
        let childElement = document.createElement("li");
        childElement.setAttribute("id", "li_id_" + item.id)
        let text = document.createTextNode("id: " + item.id + ", name: " + item.name);
        childElement.appendChild(text);
        rootElement.appendChild(childElement)
    })
    divElement.appendChild(rootElement);
}

function createRoleNameList(roleInJsonFormat) {
    let selectRoleButton = document.getElementById("selectRoleButton");
    let divElement2 = document.getElementById("rolesName");
    let rootElement2 = document.createElement("ui");
    roleInJsonFormat.forEach(function (item) {
        let childElement2 = document.createElement("button");
        childElement2.setAttribute("type", "button")
        childElement2.setAttribute("class", "dropdown-item")
        childElement2.setAttribute("id", "role_name_list" + item.id)
        let text2 = document.createTextNode(item.name);
        childElement2.appendChild(text2);
        rootElement2.appendChild(childElement2)
    })
    divElement2.appendChild(rootElement2);
    divElement2.addEventListener('click', (event) => {
        const isButton = event.target.nodeName === 'BUTTON';
        if (!isButton) {
            return;
        }
        selectRoleButton.value = event.target.innerHTML;
    })
}

function eventCreateNewRole() {
    let newRoleNameValue = document.getElementById("newRoleName").value;
    if (newRoleNameValue === "") {
        alert("Please fill role name!!!")
        return;
    }
    let role = {
        name: newRoleNameValue
    };
    fetch(roleUrl,
        {
            method: "POST",
            body: JSON.stringify(role)
        })
        .then(response => response.json())
        .then(response => {
            let id = response.id
            let rootElement = document.getElementById("role_list");
            let childElement = document.createElement("li");
            childElement.setAttribute("id", "li_id_" + id)
            let text = document.createTextNode("id: " + id + ", name: " + newRoleNameValue);
            childElement.appendChild(text);
            rootElement.appendChild(childElement)
            clearField("newRoleName")
        })
}

function eventUpdateRole() {
    let updateRoleNameValue = document.getElementById("updateRoleName").value
    let updateRoleIdValue = document.getElementById("updateRoleId").value;
    if (updateRoleNameValue === "") {
        alert("Please fill role name!!!")
        return;
    }
    if (updateRoleIdValue === "") {
        alert("Please fill role id!!!")
        return;
    }
    let role = {
        id: updateRoleIdValue,
        name: updateRoleNameValue
    };
    idValidate(updateRoleIdValue)
    fetch(roleUrl,
        {
            method: "PUT",
            body: JSON.stringify(role)
        })
        .then(response => {
            let liElement = document.getElementById("li_id_" + updateRoleIdValue)
            liElement.innerHTML = "id: " + updateRoleIdValue + ", name: " + updateRoleNameValue;
            clearField("updateRoleName")
            clearField("updateRoleId")
        })
}

function eventDeleteRoleById() {
    let roleIdElement = document.getElementById("deleteRoleId").value
    if (roleIdElement === "") {
        alert("Please fill id!!!")
        return;
    }
    idValidate(roleIdElement)
    fetch(roleUrl + "/" + roleIdElement, {method: 'DELETE'})
        .then(response => {
            console.log(response)
            document.getElementById("li_id_" + roleIdElement).remove()
            clearField("deleteRoleId")
        })
}

function idValidate(id) {
    let res = id.replace(/[0-9]/gm, "")
    if (res !== "" || id < 0) {
        alert("Please enter correct id")
    }
}

function clearField(elementId) {
    document.getElementById(elementId).value = ""
}