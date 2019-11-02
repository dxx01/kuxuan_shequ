package life.kuxuanzhuzhu.kuxuan_shequ.dto;

import life.kuxuanzhuzhu.kuxuan_shequ.model.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 邓鑫鑫
 * @date 2019年07月24日 09:33:41
 * @Description
 */
@Data
public class QuestionDTO implements Serializable {
    private Long id;
    private String title;
    private String description; //描述
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private String creator;
    private Integer  viewCount;
    private Integer commentCount;
    private Integer lieCount;
    private UserAndKxUser userAndKxUser;
}
