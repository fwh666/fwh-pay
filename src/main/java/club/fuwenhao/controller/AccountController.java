package club.fuwenhao.controller;

import club.fuwenhao.bean.entity.FwhAccount;
import club.fuwenhao.service.FwhAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class AccountController {
    /**
     * 1. 脚本读取
     * 2. 脚本入库
     * 3. 账号统计
     */

    @Resource
    private FwhAccountService accountService;

    @GetMapping("/insertBatch")
    private boolean insertBatch() {
        FwhAccount account = new FwhAccount();
        List<FwhAccount> accountList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            //todo-fwh-添加数据
            account.setAccount(String.valueOf(i)).setPassword("test").setCreateTime(new Date());
            accountList.add(account);
        }
        boolean b = accountService.saveBatch(accountList);
        return b;
    }
}
