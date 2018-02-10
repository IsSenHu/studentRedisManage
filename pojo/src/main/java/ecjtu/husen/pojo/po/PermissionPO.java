package ecjtu.husen.pojo.po;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author 11785
 */
@Entity(name = "t_permisson")
public class PermissionPO implements Serializable {

    /**
     * 权限ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer permissionId;

    /**
     * 资源url
     */
    @Column
    private String url;

    /**
     * 权限说明
     */
    @Column
    private String description;
    /*=======set,get=======*/

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    /*======toString=======*/

    @Override
    public String toString() {
        return "PermissionVO{" +
                "permissionId=" + permissionId +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
    /*======hash,equals=======*/

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PermissionPO that = (PermissionPO) o;
        return Objects.equals(permissionId, that.permissionId) &&
                Objects.equals(url, that.url) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissionId, url, description);
    }
}
