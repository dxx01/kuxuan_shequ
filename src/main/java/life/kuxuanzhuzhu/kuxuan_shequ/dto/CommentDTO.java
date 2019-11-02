package life.kuxuanzhuzhu.kuxuan_shequ.dto;

import life.kuxuanzhuzhu.kuxuan_shequ.model.User;
import lombok.Data;

/**
 * @author 邓鑫鑫
 * @date 2019年07月31日 13:37:24
 * @Description
 */
@Data
public class CommentDTO {
    private User user;

    /*
     评论
     */
    private Long id;
    private Long parentId; //父级id
    private Integer type;//类型
    private String commentator;// 评论人
    private Long gmtCreate;//评论时间
    private Long gmtModified;//评论更新时间
    private Long likeCount; //点赞数
    private String content; //评论内容
    private Integer commentCount;//评论数量
    private String toName; // @谁谁谁的名字

    /*
    用户
     */
    private String uName; //用户名称
    private String avatarUrl;//用户头像
    /*private String accountId;
    private String token;
    private Long uGmtCreate;//创建时间
    private Long uGmtModified;//更新时间*/

    /*
    点赞
    */
    private String dStatus;//点赞状态
}
