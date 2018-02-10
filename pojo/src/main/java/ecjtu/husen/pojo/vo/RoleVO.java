package ecjtu.husen.pojo.vo;

import ecjtu.husen.pojo.po.PermissionPO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 11785
 */
public class RoleVO implements Serializable {
    private Integer roleId;
    private String roleName;
    private String description;
    private List<PermissionPO> permissionPOS;
    public RoleVO(){
        permissionPOS = new ArrayList<>();
    }
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PermissionPO> getPermissionPOS() {
        return permissionPOS;
    }

    public void setPermissionPOS(List<PermissionPO> permissionPOS) {
        this.permissionPOS = permissionPOS;
    }

    @Override
    public String toString() {
        return "RoleVO{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
