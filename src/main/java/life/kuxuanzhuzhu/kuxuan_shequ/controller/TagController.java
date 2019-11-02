package life.kuxuanzhuzhu.kuxuan_shequ.controller;

import com.alibaba.fastjson.JSONObject;
import life.kuxuanzhuzhu.kuxuan_shequ.cache.TagCache;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * @author 邓鑫鑫
 * @date 2019年08月10日 17:47:11
 * @Description
 */
@Controller
public class TagController {

    @Autowired
    private TagCache tagCache;

    @ResponseBody
    @RequestMapping(value = "getTag", method = RequestMethod.GET)
    /*public JSONObject getTag() throws IOException {
        JSONObject jsonObject = tagCache.getTag();
        return jsonObject;
    }*/
    public List<TagDTO> getTag(){

        return TagCache.get();
    }
}
