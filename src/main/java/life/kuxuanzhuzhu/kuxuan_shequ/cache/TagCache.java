package life.kuxuanzhuzhu.kuxuan_shequ.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomErrorCode;
import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;


/**
 * @author 邓鑫鑫
 * @date 2019年08月10日 15:20:54
 * @Description
 */
@Component
public class TagCache {

    public JSONObject getTag() throws IOException {
        String tagFile = this.getClass().getClassLoader().getResource("tag.json").getPath();
        File file = new File(tagFile);
        String file1 = FileUtils.readFileToString(file, "UTF-8");
        JSONObject jsonobject = JSON.parseObject(file1);
        if(null == jsonobject){
            throw new CustomException(CustomErrorCode.QUESTION_NOT_FOUND);
        }
        return jsonobject;
    }
}
