package ecjtu.husen.service;

import ecjtu.husen.dao.UserDao;
import ecjtu.husen.exception.UserHadRegistForbbidenException;
import ecjtu.husen.pojo.po.UserPO;
import ecjtu.husen.pojo.vo.Gender;
import ecjtu.husen.pojo.vo.UserStatu;
import ecjtu.husen.pojo.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 11785
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public boolean userRegist(UserVO userVO) throws Exception {
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(userVO, userPO);
        //先检查手机号是否已被注册
        UserPO userPO1 = userDao.findUserByUsername(userVO.getMobilePhone());
        if(userPO == null){
            throw new UserHadRegistForbbidenException("该用户已被注册");
        }
        /*
        * 1.默认禁用，需用admin启用
        * 2.默认手机号为用户名，后面可修改
        * 3.默认性别为男
        * */
        userPO.setUserStatu(UserStatu.disable);
        userPO.setUsername(userVO.getMobilePhone());
        userPO.setGender(Gender.male);
        try {
            userDao.userRegist(userPO);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean ifUserRegisted(String mobilePhone) throws Exception {
        return userDao.findUserByUsername(mobilePhone) == null ? false : true;
    }
}
