package club.fuwenhao.controller;

import club.fuwenhao.common.ResEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送邮件
 *
 * @author fuwenhao
 * @createDate 2023/3/29 22:52
 * @Description
 **/
@RestController
public class EmailController {

    @PostMapping("sendEmail")
    public ResEntity sendEmail() {
        /**
         * 1, 够惨
         * 2. 发送邮件
         * 3. 接受结果
         * 4. 配置发件人邮箱信息
         */
        return new ResEntity();
    }
}
