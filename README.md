# 流程:
- 浏览-支付-发送账号数据
- 页面待优化;
- 页面支付跳转优化;
- 发送邮件入口,传参信息等;
- 页面邮件发送提示;
- 发送数据存储; 订单创建数据存储; 
- 账号登录功能-信息购买;
- 支付宝账号开通;
- 营业执照注册;
- 账号批量导入到数据库表zhong
- https://b.alipay.com/page/ar-center/merchant-sign/form?productCode=I1011000100000000005
- https://ect.scjgj.beijing.gov.cn/ect/notice.html?code=7dba8f0d-6c1b-3701-8f18-507e0f6d3a18&state=I20

# 上线:
- 数据库-以及脚本创建-账号导入到数据库当中
- Java包部署-配置生产环境秘钥等信息
- web包部署-配置Nginx域名转向
- 域名校验是否正确

# Maven模板工程

## 环境:
- jdk8
- springboot2.7.3
- 依赖:
  - web
  - Jackson
  - Http
  - log