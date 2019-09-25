package life.kuxuanzhuzhu.kuxuan_shequ.mapper;

import life.kuxuanzhuzhu.kuxuan_shequ.dto.QuestionDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 邓鑫鑫
 * @date 2019年07月23日 17:48:20
 * @Description
 */
@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    Integer insert(Question question);

    List<QuestionDTO> select(@Param("size") Integer size, @Param("offset") Integer offset, @Param("search") String search);

    Integer count(String search);

    @Select("select * from question where creator = #{userId} order by ID desc limit #{size} offset #{offset}")
    List<QuestionDTO> selectByUserId(@Param("userId") Long userId, @Param("size") Integer size, @Param("offset") Integer offset);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Long userId);

    @Select("select * from question where id = #{id}")
    QuestionDTO getById(@Param("id")Long id);

    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")
    Integer update(Question question);

    @Update("update question set view_count = view_count + #{viewCount} where id = #{id}")
    Integer updateViewCountById(QuestionDTO questionDTO);

    @Update("update question set comment_count = comment_count + #{commentCount} where id = #{id}")
    Integer updateContentCountByIdUp(QuestionDTO questionDTO);

    @Update("update question set comment_count = comment_count - #{commentCount} where id = #{id}")
    Integer updateContentCountByIdDown(QuestionDTO questionDTO);


    /**
     * 根据标签查询问题（相关问题）
     * @param id 问题编号
     * @param tag 标签
     * @return
     */
    List<QuestionDTO> selectByTag(@Param("id") Long id,@Param("tag") String tag);
}
