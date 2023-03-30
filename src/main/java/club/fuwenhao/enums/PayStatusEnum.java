package club.fuwenhao.enums;

/**
 * 支付状态枚举
 *
 * @author fuwenhao
 * @createDate 2023/3/30 16:58
 * @descripton
 */
public enum PayStatusEnum {
    created(0, "创建"),
    success(1, "支付成功"),
    failure(2, "支付失败"),
    email_succeeded(3, "邮件发送成功"),
    email_failed(4, "邮件发送失败");

    private Integer code;
    private String des;

    PayStatusEnum(Integer code, String des) {
        this.code = code;
        this.des = des;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
