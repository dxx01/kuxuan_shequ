package life.kuxuanzhuzhu.kuxuan_shequ.dto;

import lombok.Data;

/**
 * @author 邓鑫鑫
 * @date 2019年07月22日 17:41:52
 * @Description Githunb用户信息类
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;

}
