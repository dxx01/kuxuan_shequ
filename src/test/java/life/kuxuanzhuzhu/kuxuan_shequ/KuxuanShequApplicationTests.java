package life.kuxuanzhuzhu.kuxuan_shequ;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KuxuanShequApplicationTests {

    @Test
    public void contextLoads() {
        Thread t = new Thread();
        t.interrupt();
    }

}
