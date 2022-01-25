function eventLogin() {
    let loginUserName = document.getElementById("loginUserName").value;
    let loginUserPassword = document.getElementById("loginPassword").value;
    if (loginUserName === "") {
        alert("Please fill user name")
        return;
    }
    if (loginUserPassword === "") {
        alert("Please fill user password")
        return;
    }
    let user = {
        username: loginUserName,
        password: loginUserPassword
    }
    fetch("http://localhost:8080/test_db_project_war/login", {
        method: "POST",
        body: JSON.stringify(user)
    })
        .then(response => response.json())
}