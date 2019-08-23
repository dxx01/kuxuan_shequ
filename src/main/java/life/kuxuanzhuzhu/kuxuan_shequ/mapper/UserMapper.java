package life.kuxuanzhuzhu.kuxuan_shequ.mapper;

import life.kuxuanzhuzhu.kuxuan_shequ.model.User;
import org.apache.ibatis.annotations.*;


/**
 * @author 邓鑫鑫
 * @date 2019年07月22日 20:48:04
 * @Description
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(Long creator);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(String accountId);

    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl} where id = #{id}")
    void update(User dbUser);
}
