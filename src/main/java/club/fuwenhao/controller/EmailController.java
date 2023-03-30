package club.fuwenhao.controller;

import club.fuwenhao.bean.dto.EmailDto;
import club.fuwenhao.bean.entity.FwhOrderRecord;
import club.fuwenhao.common.ResEntity;
import club.fuwenhao.service.FwhOrderRecordService;
import club.fuwenhao.utils.MailUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 发送邮件
 *
 * @author fuwenhao
 * @createDate 2023/3/29 22:52
 * @Description
 **/
@RestController
public class EmailController {

    @Resource
    private FwhOrderRecordService orderRecordService;

    @PostMapping("sendEmail")
    public ResEntity sendEmail(@RequestBody EmailDto emailDto) {
        /**
         * 1, 够惨
         * 2. 发送邮件
         * 3. 接受结果
         * 4. 配置发件人邮箱信息
         */
        MailUtil.sendEmail(emailDto.getRecipientAccount());

        FwhOrderRecord orderRecord = new FwhOrderRecord();
        orderRecord.setOutTradeNo("222").setRecipientAccount(emailDto.getRecipientAccount()).setStatus(1).setCreateTime(new Date()).setModifiedTime(new Date());
        boolean save = orderRecordService.save(orderRecord);
        return save ? new ResEntity().setCode("0").setMessage("成功") : new ResEntity().setCode("-1").setMessage("失败");
    }
}
