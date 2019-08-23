package life.kuxuanzhuzhu.kuxuan_shequ.model;

import lombok.Data;

/**
 * @author 邓鑫鑫
 * @date 2019年08月21日 12:01:15
 * @Description
 */
@Data
public class DianZan {
    private Long id;
    private Long userId;
    private Long commentId;
    private Long questionId;
    private String status;
}
