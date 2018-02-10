package ecjtu.husen.pojo.converter;

import ecjtu.husen.pojo.vo.UserStatu;

import javax.persistence.AttributeConverter;

/**
 * @author 11785
 */
public class UserStatuConverter implements AttributeConverter<UserStatu, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserStatu userStatu) {
        return userStatu.getValue();
    }

    @Override
    public UserStatu convertToEntityAttribute(Integer value) {
        UserStatu[] userStatus = UserStatu.values();
        for (UserStatu userStatu : userStatus){
            if(userStatu.getValue().equals(value)){
                return userStatu;
            }
        }
        return null;
    }
}
