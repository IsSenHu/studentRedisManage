package ecjtu.husen.dao;

import ecjtu.husen.pojo.po.UserPO;

/**
 * @author 11785
 */
public interface UserDao {
    /**
     * 用户注册
     * @param userPO
     * @throws Exception
     */
    public void userRegist(UserPO userPO) throws Exception;

    /**
     * 检查用户是否已经注册
     * @param username
     * @return
     * @throws Exception
     */
    public UserPO findUserByUsername(String username) throws Exception;
}
