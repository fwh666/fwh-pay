package club.fuwenhao.bean.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fuwenhao
 * @createDate 2023/3/30 22:40
 * @Description
 **/
@Data
@Accessors(chain = true)
public class AccountDto {
    /**
     * 账号
     */
    private String account;
    private String password;
    /**
     * 账号登陆地址
     */
    private String loginUrl = "https://go.runba.cyou/auth/login";
}
