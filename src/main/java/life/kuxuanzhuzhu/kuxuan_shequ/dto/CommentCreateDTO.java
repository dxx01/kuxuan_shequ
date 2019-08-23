package life.kuxuanzhuzhu.kuxuan_shequ.dto;

import lombok.Data;

/**
 * @author 邓鑫鑫
 * @date 2019年07月29日 10:06:53
 * @Description
 */
@Data
public class CommentCreateDTO {
    private Long id; //被回复的评论编号
    private Long parentId; //问题编号
    private String content; // 评论内容
    private Integer type; //评论类型
    private Long toId; // @谁谁谁
}
