package life.kuxuanzhuzhu.kuxuan_shequ.dto;

import lombok.Data;

/**
 * @author 邓鑫鑫
 * @date 2019年08月12日 19:35:38
 * @Description
 */
@Data
public class NotificationDTO {
    private Long id;  //通知编号
    private String notifier;  //发送人id
    private String receiver;  //接收人id
    private Long outerId;  //评论id或问题id或点赞id
    private Integer type;
    private Long gmtCreate;  //通知创建时间
    private Long gmtCheck;  //通知阅读时间
    private String status;  //状态
    private Long questionId;//问题id


    private String notifierName;//发送人名字
    private String questionTitle; //问题标题
    private String content;//评论内容
    private String typeName;//通知类型名称
}
