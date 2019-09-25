package life.kuxuanzhuzhu.kuxuan_shequ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class KuxuanShequApplication{// extends SpringBootServletInitializer {

    /*//tomcat启动方式
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(KuxuanShequApplication.class);
    }*/

    public static void main(String[] args) {
        SpringApplication.run(KuxuanShequApplication.class, args);
    }

}
