package life.kuxuanzhuzhu.kuxuan_shequ.model;

import lombok.Data;

/**
 * @author 邓鑫鑫
 * @date 2019年08月12日 17:22:04
 * @Description 通知类
 */
@Data
public class Notification {
    private Long id;        //编号
    private String notifier;  //发出通知的人的id
    private String receiver;  //接受通知的人的id
    private Long outerId;  //评论的id
    private Integer type;  //类型（1：问题；2评论；3：点赞）
    private Long gmtCreate;  //通知时间
    private Long gmtCheck;  //查看时间
    private String status;  //状态（0：未查看；1：查看）
    private Long questionId;  //问题编号
}
