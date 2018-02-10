package ecjtu.husen.service;

import ecjtu.husen.pojo.vo.UserVO;

/**
 * @author 11785
 */
public interface UserService {
    /**
     * 用户注册
     * @param userVO
     * @return
     * @throws Exception
     */
    public boolean userRegist(UserVO userVO) throws Exception;

    /**
     * 检查手机号是否已被注册
     * @param mobilePhone
     * @return
     * @throws Exception
     */
    public boolean ifUserRegisted(String mobilePhone) throws Exception;
}
