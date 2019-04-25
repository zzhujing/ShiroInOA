import com.bdth.oa.utils.GeneratorPicUtil;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author hj
 * @create 2019-04-22 13:52
 */
public class MainTest {
    public static void main(String[] args) throws IOException {
        String result = Base64Utils.encodeToString(Arrays.copyOf("oa".getBytes(), 16));
        System.out.println(result);
    }
}
