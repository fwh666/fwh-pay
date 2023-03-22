package club.fuwenhao;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fuwenhao
 * @createDate 2023/3/22 17:35
 * @descripton
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "teset";
    }
}
