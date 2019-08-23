package life.kuxuanzhuzhu.kuxuan_shequ.model;

import lombok.Data;

import java.util.List;

/**
 * @author 邓鑫鑫
 * @date 2019年08月10日 15:21:18
 * @Description
 */
@Data
public class Tag {
    private String categoryName;
    private List<String> tags;
}
