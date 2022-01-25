getDefaultCountOfUsersFromServer();
createButtonsForPagination();

function getDefaultCountOfUsersFromServer() {
    fetch(baseUrl + "users", {method: 'GET'})
        .then(response => response.json())
        .then(jsonResponse => createUserList(jsonResponse))
}

function getUsersFromServerByPageNumber(page_number) {
    fetch(baseUrl + "users?page_number=" + page_number, {method: 'GET'})
        .then(response => response.json())
        .then(jsonResponse => createUserList(jsonResponse))
}

function createButtonsForPagination() {
    fetch(baseUrl + "users/count", {method: 'GET'})
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
            + ", role name:" + item.roleName + ", birthday:" + item.birthday
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
    let newUserNameValue = document.getElementById("newUserName").value;
    let newUserPasswordValue = document.getElementById("newUserPassword").value;
    let newUserPhoneValue = document.getElementById("newUserPhone").value;
    let newUserFirstNameValue = document.getElementById("newUserFirstName").value;
    let newUserSecondNameValue = document.getElementById("newUserSecondName").value;
    let newUserIdValue = document.getElementById("newUserId").value;
    let newUserRoleName = document.getElementById("selectRoleButton").value;
    let newUserBirthday = document.getElementById("newUserBirthday").value;
    let user = {
        username: newUserNameValue,
        password: newUserPasswordValue,
        phone: newUserPhoneValue,
        firstName: newUserFirstNameValue,
        secondName: newUserSecondNameValue,
        roleName: newUserRoleName,
        birthday: newUserBirthday
    };
    // validateUser({newUserSecondNameValue,newUserNameValue,newUserRoleName});
    if (newUserIdValue !== "") {
        alert("id should fill just for update!!!")
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
    if (newUserRoleName === "ROLE NAME" || newUserRoleName === "") {
        alert("Please fill user name !!!")
        return;
    }
    fetch("http://localhost:8080/test_db_project_war/users",
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
                + ", role name:" + newUserRoleName, ", birthday:" + newUserBirthday);
            childElement.appendChild(text);
            rootElement.appendChild(childElement)
        })
}

function eventUpdateUser() {
    let newUserNameValue = document.getElementById("newUserName").value;
    let newUserPasswordValue = document.getElementById("newUserPassword").value;
    let newUserPhoneValue = document.getElementById("newUserPhone").value;
    let newUserFirstNameValue = document.getElementById("newUserFirstName").value;
    let newUserSecondNameValue = document.getElementById("newUserSecondName").value;
    let newUserIdValue = document.getElementById("newUserId").value;
    let newUserRoleName = document.getElementById("selectRoleButton").value;
    let newUserBirthday = document.getElementById("newUserBirthday").value;
    let user = {
        username: newUserNameValue,
        password: newUserPasswordValue,
        phone: newUserPhoneValue,
        firstName: newUserFirstNameValue,
        secondName: newUserSecondNameValue,
        roleName: newUserRoleName,
        id: newUserIdValue,
        birthday: newUserBirthday
    };
    if (newUserSecondNameValue === "") {
        alert("Please fill second name !!!")
        return;
    }
    if (newUserNameValue === "") {
        alert("Please fill user name !!!")
        return;
    }
    if (newUserRoleName === "ROLE NAME" || newUserRoleName === "") {
        alert("Please fill user name !!!")
        return;
    }
    fetch(baseUrl + "users",
        {
            method: "PUT",
            body: JSON.stringify(user)
        })
        .then(response => {
            let liElement = document.getElementById("li_id_" + newUserIdValue)
            liElement.innerHTML = "id: " + newUserIdValue + ", user name: " + newUserNameValue
                + ", password:" + newUserPasswordValue + ", phone: " + newUserPhoneValue
                + ", first name:" + newUserFirstNameValue + ", second name:" + newUserSecondNameValue
                + ", role name:" + newUserRoleName + ", birthday:" + newUserBirthday;
        })
}

function eventDeleteUserById() {
    let userIdElement = document.getElementById("deleteUserId").value
    if (userIdElement === "") {
        alert("Please fill id!!!")
        return;
    }
    fetch(baseUrl + "users/" + userIdElement, {method: 'DELETE'})
        .then(response => {
            console.log(response)
            document.getElementById("li_id_" + userIdElement).remove()
        })
}

function validateUser(arr) {
    arr.forEach(function (item) {
        if (item === "") {
            alert("Please fill" + item + "!!!");
            return;
        }
    });
}
