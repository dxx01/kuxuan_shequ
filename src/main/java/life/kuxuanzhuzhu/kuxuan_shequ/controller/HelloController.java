package life.kuxuanzhuzhu.kuxuan_shequ.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 邓鑫鑫
 * @date 2019年07月22日 11:16:04
 * @Description
 */
@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(@RequestParam(name = "name") String name, Model model) {
        model.addAttribute("name",name);
        return "hello";
    }
}
