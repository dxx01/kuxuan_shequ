package life.kuxuanzhuzhu.kuxuan_shequ.dto;

/**
 * @author 邓鑫鑫
 * @date 2019年07月22日 17:41:52
 * @Description Githunb用户信息类
 */
public class GithubUser {
    private String name;
    private long id;
    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
