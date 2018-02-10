package ecjtu.husen.pojo.vo;

import java.io.Serializable;

/**
 * @author 11785
 */
public class PermissionVO implements Serializable {
    private Integer permissionId;
    private String url;
    private String description;

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

    @Override
    public String toString() {
        return "PermissionVO{" +
                "permissionId=" + permissionId +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
