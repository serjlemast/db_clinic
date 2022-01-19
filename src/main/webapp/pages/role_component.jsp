<div class="row" style="margin-top: 10px" ;>
    <div class="col-lg">
        <div class="row" style="background-color: rgba(222,118,193,0.21);margin-right: 2px ">
            <div class="col-12">
                <p>
                    GetAllRoles:
                </p>
                <div id="roles"></div>
            </div>
        </div>
    </div>
    <div class="col-sm">
        <div class="row" style="background-color: rgba(172,118,222,0.21);margin-right: 2px;">
            <div class="col-12">
                <div id="createNewRole" class="Roles">
                    <p>
                        CreateNewRole:
                    </p>
                    <input name="roleName" id="newRoleName" type="text" size="20">
                    <button type="button" onclick="eventCreateNewRole()">CREATE ROLE</button>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm">
        <div class="row" style="background-color: rgba(118,147,222,0.21);margin-right: 2px;">
            <div class="col-12">
                <div id="deleteRole" class="Roles">
                    <div id="updateRole" class="Roles">
                        UpdateRole:
                    </div>
                    <div class="form-group">
                        Id:
                        <input id="updateRoleId" type="text" size="20"/>
                    </div>
                    <div class="form-group">
                        Name:
                        <input id="updateRoleName" type="text" size="20"/>
                        <button type="button" class="btn btn-primary" onclick="eventUpdateRole()">Create role
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm">
        <div class="row" style="background-color: rgba(172,118,222,0.21);">
            <div class="col-12">
                DeleteRole:
                <input name="roleId" id="deleteRoleId" type="text" size="40">
                <button type="button" onclick="eventDeleteRoleById()">DELETE ROLE</button>
            </div>
        </div>
    </div>
</div>



