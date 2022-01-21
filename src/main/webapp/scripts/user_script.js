getDefaultCountOfUsersFromServer();
createButtonsForPagination();

function getDefaultCountOfUsersFromServer() {
    fetch(baseUrl + "/users", {method: 'GET'})
        .then(response => response.json())
        .then(jsonResponse => createUserList(jsonResponse))
}

function getUsersFromServerByPageNumber(page_number) {
    fetch(baseUrl + "/users?page_number=" + page_number, {method: 'GET'})
        .then(response => response.json())
        .then(jsonResponse => createUserList(jsonResponse))
}

function createButtonsForPagination() {
    fetch(baseUrl + "/users/count", {method: 'GET'})
        .then(response => response.json())
        .then(jsonResponse => createPaginationButtons(jsonResponse))
}

function createUserList(userInJsonFormat) {
    let divElement = document.getElementById("users");
    if (document.getElementById("user_list") != null) {
        document.getElementById("user_list").remove()
    }
    let rootElement = document.createElement("ui");
    rootElement.setAttribute("id", "user_list")
    userInJsonFormat.forEach(function (item) {
        let stroke = "id: " + item.id + ", user name: " + item.username
            + ", password: " + item.password + ", phone: " + item.phone
            + ", first name:" + item.firstName + ", second name:" + item.secondName
            + ", role name:" + item.roleName
        let childElement = document.createElement("li");
        childElement.setAttribute("id", "li_id_" + item.id)

        let text = document.createTextNode(stroke);
        childElement.appendChild(text);
        rootElement.appendChild(childElement)

    })
    divElement.appendChild(rootElement);
}

function createPaginationButtons(countOfUser) {
    let divElement2 = document.getElementById("paginationButtons");
    let rootElement2 = document.createElement("ui");
    rootElement2.setAttribute("class", "pagination")
    for (let i = 1; i < countOfUser.total / countOfUser.limit + 1; i++) {
        let childElement2 = document.createElement("button");
        childElement2.setAttribute("type", "button")
        childElement2.setAttribute("class", "btn btn-secondary btn-sm btn-outline-dark")
        childElement2.setAttribute("id", "buttons_" + i)
        let text2 = document.createTextNode(i);
        childElement2.appendChild(text2);
        rootElement2.appendChild(childElement2)
    }
    divElement2.appendChild(rootElement2);
    divElement2.addEventListener('click', (event) => {
        const isButton = event.target.nodeName === 'BUTTON';
        if (!isButton) {
            return;
        }
        getUsersFromServerByPageNumber(event.target.id.replace("buttons_", ""));
    })
}

function eventCreateNewUser() {
    validateUserCreate()
    fetch(baseUrl + "/users",
        {
            method: "POST",
            body: JSON.stringify(user)
        })
        .then(response => response.json())
        .then(response => {
            let id = response.id
            let rootElement = document.getElementById("user_list");
            let childElement = document.createElement("li");
            childElement.setAttribute("id", "li_id_" + id)
            let text = document.createTextNode("id: " + id + ", user name: " + newUserNameValue
                + ", password:" + newUserPasswordValue + ", phone: " + newUserPhoneValue
                + ", first name:" + newUserFirstNameValue + ", second name:" + newUserSecondNameValue
                + ", role name:" + newUserRoleName);
            childElement.appendChild(text);
            rootElement.appendChild(childElement)
        })
}

function eventUpdateUser() {
    validateUserUpdate()
    fetch(baseUrl + "/users",
        {
            method: "PUT",
            body: JSON.stringify(user)
        })
        .then(response => {
            let liElement = document.getElementById("li_id_" + newUserIdValue)
            liElement.innerHTML = "id: " + newUserIdValue + ", user name: " + newUserNameValue
                + ", password:" + newUserPasswordValue + ", phone: " + newUserPhoneValue
                + ", first name:" + newUserFirstNameValue + ", second name:" + newUserSecondNameValue
                + ", role name:" + newUserRoleName;
        })
}

function eventDeleteUserById() {
    let userIdElement = document.getElementById("deleteUserId").value
    if (userIdElement === "") {
        alert("Please fill id!!!")
        return;
    }
    fetch(BaseUrl + "/" + userIdElement, {method: 'DELETE'})
        .then(response => {
            console.log(response)
            document.getElementById("li_id_" + userIdElement).remove()
        })
}

function validateUserCreate() {
    if (newUserNameValue === "") {
        alert("Please fill user name!!!")
        return;
    }
    if (newUserPasswordValue === "") {
        alert("Please fill user password!!!")
        return;
    }
    if (newUserRoleName === "") {
        alert("Please fill role name!!")
        return;
    }
    if (newUserIdValue !== "") {
        alert("id should fill just for update!!!")
        return;
    }
}

function validateUserUpdate() {
    if (newUserIdValue === "") {
        alert("Please fill role id!!!")
        return;
    }
    if (newUserSecondNameValue === "") {
        alert("Please fill second name !!!")
        return;
    }
    if (newUserNameValue === "") {
        alert("Please fill user name !!!")
        return;
    }
}