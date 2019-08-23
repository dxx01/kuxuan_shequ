package life.kuxuanzhuzhu.kuxuan_shequ.model;

import lombok.Data;

/**
 * @author 邓鑫鑫
 * @date 2019年08月12日 17:22:04
 * @Description
 */
@Data
public class Notification {
    private Long id;
    private Long notifier;
    private Long receiver;
    private Long outerId;
    private Integer type;
    private Long gmtCreate;
    private Long gmtCheck;
    private Integer status;
    private Long questionId;
}
