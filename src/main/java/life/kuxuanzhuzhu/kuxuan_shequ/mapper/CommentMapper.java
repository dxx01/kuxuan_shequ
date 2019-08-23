package life.kuxuanzhuzhu.kuxuan_shequ.mapper;

import life.kuxuanzhuzhu.kuxuan_shequ.dto.CommentDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 邓鑫鑫
 * @date 2019年07月29日 09:55:00
 * @Description
 */
@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id,type,commentator,gmt_create,gmt_modified,like_count,content,comment_count,to_id) values(#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content},#{commentCount},#{toId})")
    Integer insert(Comment comment);


    /**
     * 根据编号去查询评论信息
     *
     * @param Id 评论编号
     * @return
     */
    @Select("select * from comment where id = #{id}")
    Comment selectById(Long Id);


    /**
     * 获取该问题或评论的评论的所有信息和对应的用户信息
     *
     * @param parentId 父级id
     * @param type       评论类型
     * @return
     */
    List<CommentDTO> selectByIdAndParentIdAndType(@Param("parentId") Long parentId, @Param("type") Integer type);


    /**
     * 删除评论
     *
     * @param id 评论编号
     * @return
     */
    Integer deleteById(Long id);


    /**
     * 增加/减少评论数
     *
     * @return
     */
    Integer updateCommentByIdUpOrDown(@Param("id") Long id, @Param("commentCount") Integer commentCount, @Param("up0rDown") String up0rDown);

    /**
     * 增加/减少点赞数
     * @param id  评论编号
     * @param likeCount  点赞数
     * @param type up：+   down：-
     * @return
     */
    Integer updateLikeCountByIdUpOrDown(@Param("id") Long id, @Param("likeCount") int likeCount, @Param("type") String type);
}
