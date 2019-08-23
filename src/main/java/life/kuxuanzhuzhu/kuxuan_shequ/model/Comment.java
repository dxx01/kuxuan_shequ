package life.kuxuanzhuzhu.kuxuan_shequ.model;

import lombok.Data;

/**
 * @author 邓鑫鑫
 * @date 2019年07月29日 09:51:23
 * @Description 评论类
 */
@Data
public class Comment {

    private Long id;
    private Long parentId; //父级id
    private Integer type;//类型
    private Long commentator;// 评论人
    private Long gmtCreate;//评论时间
    private Long gmtModified;//评论更新时间
    private Long likeCount; //点赞数
    private String content; //评论内容
    private Integer commentCount;//评论数量
    private Long toId; // @谁谁谁
}
