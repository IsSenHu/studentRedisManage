package ecjtu.husen.pojo.po;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 11785
 */
@Entity(name = "t_role")
public class RolePO implements Serializable{

    /**
     * 角色ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    /**
     * 角色名
     */
    @Column
    private String roleName;

    /**
     * 角色说明
     */
    @Column
    private String description;

    /**
     * 角色权限
     * 单边的一对多
     * fetch=FetchType.EAGER 立即加载，默认是延迟加载
     */
    @OneToMany(fetch = FetchType.EAGER, targetEntity = PermissionPO.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    private List<PermissionPO> permissionPOS = new ArrayList<>();

    /*=========get,set==========*/

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
    /*======toString=======*/

    @Override
    public String toString() {
        return "RolePO{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", permissionPOS=" + permissionPOS +
                '}';
    }
    /*=======hash,equals=======*/

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RolePO rolePO = (RolePO) o;
        return Objects.equals(roleId, rolePO.roleId) &&
                Objects.equals(roleName, rolePO.roleName) &&
                Objects.equals(description, rolePO.description) &&
                Objects.equals(permissionPOS, rolePO.permissionPOS);
    }
    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName, description, permissionPOS);
    }
}
