package ecjtu.husen.pojo.converter;


import ecjtu.husen.pojo.vo.Gender;
import javax.persistence.AttributeConverter;

/**
 * @author 11785
 */
public class GenderConverter implements AttributeConverter<Gender, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Gender gender) {
        return gender.getValue();
    }

    @Override
    public Gender convertToEntityAttribute(Integer integer) {
        Gender[] genders = Gender.values();
        for(Gender gender : genders){
            if(gender.getValue().equals(integer)){
                return gender;
            }
        }
        return null;
    }
}
