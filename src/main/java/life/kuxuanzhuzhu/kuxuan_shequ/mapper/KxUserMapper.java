package life.kuxuanzhuzhu.kuxuan_shequ.mapper;

import life.kuxuanzhuzhu.kuxuan_shequ.dto.UserAndKxUser;
import life.kuxuanzhuzhu.kuxuan_shequ.model.KxUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 邓鑫鑫
 * @date 2019年09月27日 16:00:57
 * @Description
 */
@Mapper
public interface KxUserMapper {


    Integer insert(KxUser kxUser);

    KxUser selectByEmail(String email);


    KxUser selectByToken(String token);

    KxUser login(@Param("email") String email, @Param("pass") String pass);


    KxUser selectById(String id);
}
