<div class="row">
    <div class="row" style="background-color: rgba(172,118,222,0.21); margin-top: 10px;">
        <div class="col-12">
            <form>
                <div class="form-group">
                    <p>
                        GetAllUsers:
                    </p>
                    <div id="users"></div>
                </div>
                <div class="form-group">
                    <nav aria-label="Page navigation example" id="paginationButtons"></nav>
                </div>
            </form>
        </div>
    </div>
    <div id="createNewUser" class="row" style="background-color: rgba(154,222,118,0.21); margin-top: 10px;">
        <div class="col-12">
            <form>
                <div class="form-group">
                    <p>
                        CreateNewUser:
                    </p>
                </div>
                <div class="form-group">
                    ID:
                    <input name="userId" id="newUserId" type="text" size="20">
                    Name:
                    <input name="userName" id="newUserName" type="text" size="20">
                    Phone:
                    <input name="userPhone" id="newUserPhone" type="text" size="20">
                    <input type="button" id="selectRoleButton" class="btn btn-secondary btn-dark dropdown-toggle" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false" value="ROLE NAME">
                    <div class="dropdown-menu dropdown-menu-right" id="rolesName"></div>
                </div>
                <div class="form-group">
                    Password:
                    <input name="userPassword" id="newUserPassword" type="text" size="20">
                    First Name:
                    <input name="userFirstName" id="newUserFirstName" type="text" size="20">
                    Second Name:
                    <input name="userSecondName" id="newUserSecondName" type="text" size="20">
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-secondary btn-sm" onclick="eventCreateNewUser()">CREATE USER
                    </button>
                    <button type="button" class="btn btn-secondary btn-sm" onclick="eventUpdateUser()">UPDATE USER
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="row">
    <div id="deleteUser" class="row" style="background-color: rgba(118,222,189,0.21); margin-top: 10px;">
        <div class="col-12">
            <form>
                <div class="form-group">
                    <label for="deleteUserId">Delete user form</label>
                    <input type="number" name="userId" class="form-control" id="deleteUserId"
                           placeholder="Enter user id">
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-secondary btn-sm" onclick="eventDeleteUserById()">DELETE USER
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

