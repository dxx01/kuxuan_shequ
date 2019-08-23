package life.kuxuanzhuzhu.kuxuan_shequ.controller;

import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomErrorCode;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.PageDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.Result;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.NotificationMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.model.User;
import life.kuxuanzhuzhu.kuxuan_shequ.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 邓鑫鑫
 * @date 2019年07月22日 11:16:04
 * @Description
 */
@Controller
public class IndexController {


    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationMapper notificationMapper;

    @GetMapping("index")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "2") Integer size) {
        User user = (User) request.getSession().getAttribute("user");
        Result result = questionService.select(page,size);
        PageDTO pageDTO = (PageDTO) result.getData();
        if(-1 == pageDTO.getTotalPage()){
            model.addAttribute("error", "暂无问题");
        }
        Integer inform = null;
        if(null != user){
            inform = notificationMapper.selectCountByReceiver(user.getId());
            model.addAttribute("inform",inform);
        }
        model.addAttribute("pageDTO",pageDTO);

        return "index";
    }
}