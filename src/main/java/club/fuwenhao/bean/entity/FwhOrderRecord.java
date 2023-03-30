package club.fuwenhao.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.io.Serializable;

/**
 * 支付订单记录表(FwhOrderRecord)实体类
 *
 * @author makejava
 * @since 2023-03-30 12:00:44
 */
@Data
@Accessors(chain = true)
public class FwhOrderRecord implements Serializable {
    private static final long serialVersionUID = -23973952716298301L;
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 订单号
     */
    private Long orderNo;
    /**
     * 支付宝交易订单号
     */
    private String outTradeNo;
    /**
     * 收件人邮箱
     */
    private String recipientAccount;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态 0-创建 1-支付成功 2-支付失败
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifiedTime;

}

