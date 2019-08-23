package life.kuxuanzhuzhu.kuxuan_shequ.Exception;

import com.alibaba.fastjson.JSON;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.Result;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 邓鑫鑫
 * @date 2019年07月27日 20:35:24
 * @Description 异常处理类
 */
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model, HttpServletResponse response) {
        String contentType = request.getContentType();

        if("application/json".equals(contentType) || "application/json;charset=UTF-8".equals(contentType)){
            //post请求
            Result result;
            if (ex instanceof CustomException) {
                result =  Result.error((CustomException) ex);
            } else {
                result = Result.error(CustomErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(result));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else{
            //get请求
            if (ex instanceof CustomException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", "服务异常,请稍后再试试！！！");
            }
            return new ModelAndView("error");
        }
    }

}
