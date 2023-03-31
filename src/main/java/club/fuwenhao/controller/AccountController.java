package club.fuwenhao.controller;

import club.fuwenhao.bean.entity.FwhAccount;
import club.fuwenhao.service.FwhAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.*;
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
        List<FwhAccount> accountList = new ArrayList<>();

        String fileName = "/export/applogs/schedule-vpn/account.txt";
        File file = new File(fileName);
        try {
            //考虑到编码格式
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader bu = new BufferedReader(read);
            String lineText = null;
            while ((lineText = bu.readLine()) != null) {
                FwhAccount account = new FwhAccount();
                String emailParam = lineText.trim();
                account.setAccount(emailParam).setPassword("PaI3QXq26obxVri").setCreateTime(new Date());
                accountList.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountService.saveBatch(accountList);
    }
}
