package ecjtu.husen.pojo.vo;

import java.io.Serializable;

/**
 * @author 11785
 */

public enum UserStatu implements Serializable {

    /**
     * 表示用户的状态
     */
    enable(1, "启用"),
    disable(2, "禁用");

    public final static String DOC = "1:enable;2:disable";

    private final Integer value;
    private final String description;

    UserStatu(Integer value, String description){
        this.description = description;
        this.value = value;
    }

    public Integer getValue(){
        return this.value;
    }
    public String getDescription(){
        return this.description;
    }

    @Override
    public String toString() {
        return "UserStatu{" +
                "value=" + value +
                ", description='" + description + '\'' +
                '}';
    }
}
