package life.kuxuanzhuzhu.kuxuan_shequ.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 邓鑫鑫
 * @date 2019年09月25日 16:27:21
 * @Description
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
