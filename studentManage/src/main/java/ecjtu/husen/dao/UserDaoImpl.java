package ecjtu.husen.dao;

import ecjtu.husen.pojo.po.UserPO;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author 11785
 */
@Repository
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
    @Resource
    public void setSessionFactory2(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }
    @Override
    public void userRegist(UserPO userPO) throws Exception {
        this.getHibernateTemplate().save(userPO);
    }

    @Override
    public UserPO findUserByUsername(String username) throws Exception {
        List<UserPO> list = (List<UserPO>) getHibernateTemplate().find("from t_user where username = ?", username);
        if(list != null && list.size() > 0){
            return list.get(0);
        }else {
            return null;
        }
    }
}
