package club.fuwenhao.service.impl;

import club.fuwenhao.bean.entity.FwhOrderRecord;
import club.fuwenhao.dao.FwhOrderRecordDao;
import club.fuwenhao.service.FwhOrderRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 支付订单记录表(FwhOrderRecord)表服务实现类
 *
 * @author makejava
 * @since 2023-03-30 12:01:01
 */
@Service("fwhOrderRecordService")
public class FwhOrderRecordServiceImpl extends ServiceImpl<FwhOrderRecordDao, FwhOrderRecord> implements FwhOrderRecordService {

}
