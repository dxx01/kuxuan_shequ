package life.kuxuanzhuzhu.kuxuan_shequ.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 邓鑫鑫
 * @date 2019年07月22日 11:16:04
 * @Description
 */
@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello() {
        return "index";
    }
}
