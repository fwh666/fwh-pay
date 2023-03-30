package club.fuwenhao.bean.dto;

import lombok.Data;
//import javax.validation.constraints.NotNull;

/**
 * @author fuwenhao
 * @createDate 2023/3/30 11:02
 * @descripton
 */
@Data
public class EmailDto {
    /**
     * 收件人邮箱
     */

    private String recipientAccount;
}
