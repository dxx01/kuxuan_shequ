package life.kuxuanzhuzhu.kuxuan_shequ.controller;

import life.kuxuanzhuzhu.kuxuan_shequ.dto.Result;
import life.kuxuanzhuzhu.kuxuan_shequ.model.DianZan;
import life.kuxuanzhuzhu.kuxuan_shequ.service.DianZanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 邓鑫鑫
 * @date 2019年08月21日 22:34:33
 * @Description
 */
@Controller
public class DianZanController {

    @Autowired
    private DianZanService dianZanService;


    @ResponseBody
    @RequestMapping(value = "dianZanInsert", method = RequestMethod.POST)
    public Result insert(@RequestBody DianZan dianZan) {
        dianZanService.insertAndUpdate(dianZan);
        return Result.ok();
    }
}
