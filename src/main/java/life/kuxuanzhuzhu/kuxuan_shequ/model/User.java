package life.kuxuanzhuzhu.kuxuan_shequ.model;

import lombok.Data;

/**
 * @author 邓鑫鑫
 * @date 2019年07月22日 20:56:14
 * @Description
 */
@Data
public class User {

    private Long id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
