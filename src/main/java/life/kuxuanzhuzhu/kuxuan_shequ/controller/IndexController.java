package life.kuxuanzhuzhu.kuxuan_shequ.controller;

import life.kuxuanzhuzhu.kuxuan_shequ.dto.PageDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.Result;
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


    @GetMapping("index")
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "5") Integer size,
                        @RequestParam(value = "search", required = false) String search) {
        Result result = questionService.select(page, size,search);
        PageDTO pageDTO = (PageDTO) result.getData();
        if (-1 == pageDTO.getTotalPage()) {
            model.addAttribute("error", "暂无问题");
        }
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("search", search);

        return "index";
    }

}
