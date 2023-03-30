package club.fuwenhao.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.io.Serializable;

/**
 * 账号表(FwhAccount)实体类
 *
 * @author makejava
 * @since 2023-03-30 22:51:20
 */
@Data
@Accessors(chain = true)
public class FwhAccount implements Serializable {
    private static final long serialVersionUID = -85337679380191962L;
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 邮箱账号
     */
    private String account;
    /**
     * 邮箱账号密码
     */
    private String password;
    /**
     * 出售状态
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

