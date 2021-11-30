package com.laison.erp.model.sys;

import com.laison.erp.common.utils.StringUtils;
import com.laison.erp.common.validatgroup.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Config implements Serializable {
//	public enum AlarmType{
//		
//		TYPE_WATER_BALANCE(1),
//		TYPE_LOW_VOLTAGE(2);
//		public Integer type;
//		
//		private AlarmType( int type) {
//	    	 this.type=type;
//	    }
//		
//	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull(groups = { UpdateGroup.class }, message = "{dept.id.null}")
	private String deptId="0";
	@NotNull(groups = { UpdateGroup.class }, message = "currencySymbol can not be null")
	private String currencySymbol = "$";
	private boolean showAllMeterType=false;//是否显示所有表计类型
	private int customerIdGenType =1;//1=通用的12 开头 长度至少是6位   2=泰安 13开头 长度至少3位  
	private String quickEntry = "/account/center|/biz/customer";
	private String volumeSymbol = "m³";
	private Integer freezenDay = 1;//冻结日
	private Integer monthDataComputeMode=2;//月冻结计算方式 1=日冻结累计 (优势实时，但数据不准确) 2=冻结日当天数据上传时减去上一个冻结日的total （数据准确，但有可能缺失）
	private String timeZone = "Africa/Johannesburg";
	private String alarmPhone = "";
	private String alarmEmail = "";
	private String logoImg = "default";
	private Boolean randomPassword=false;//是否随机密码通过邮件发送
	private String mailConfigString="smtp.exmail.qq.com|25|software@laisontech.com|software@laisontech.com|Laison4u123";//host|port|发送方|username|password
	private Integer changePassWordTime=-1;//修改密码的周期  时间天 -1代表不起作用
	private String sgc = "000120";//newsgc
	private String oldsgc = "000120";//用于硬加密 oldsgc
    private Integer oldregnum=0;//用于硬加密
    private Integer newregnum=0;//用于硬加密
	private String rootKey = "8305470922B44253D577405242AB9F5A";//newRootkey  oldkey是常量
	private String oldRootKey = "8305470922B44253D577405242AB9F5A";//newRootkey  oldkey是常量
	private Integer tidBaseYear=1993;
	private int modalLayoutType =1;//0=上下排列（阿拉伯语标题靠右） 1=左右排列
	private Boolean enableThird = true;//是否显示第三方
	private Boolean enableAgency = true;//是否显示代理
	private Boolean showTiered = true;//购买提示是否显示阶梯
	private Boolean createRefundTrade = true;//退购是否产生交易记录
	private String paypalConfig="sandbox|2cw9wfqxkkz8hby4|24kpkqwrrrhht6my|a1a7e21293c24c32c5794ada0f41a110";//底部怎么显示
	private String footType="laison";//底部怎么显示
	private String meterNumnerLenght="11@13";//多种长度用@分割
	private String socketNumberPreFix="01";//socketNumber的前缀
	private String meterPatten="";//多种长度用@分割
	private String meterRange="";//表计范围规则 竖线隔开->表计号取值范围1(取值范围-隔开起止值)|表计号取值范围2
	private BigDecimal  minPurchaseAmount= new BigDecimal("0.1");//单次购买最小金额
	private BigDecimal  minPurchaseVolume= new BigDecimal("0.1");//单次购买最小量
	private BigDecimal  maxPurchaseAmount= new BigDecimal("65535");//单次购买最大金额
	private BigDecimal  maxPurchaseVolume= new BigDecimal("9999999");//单次购买最大量
	private BigDecimal  monthMinPurchaseVolume= new BigDecimal("0");//没有最少购买量
	private Integer period=1;
	private BigDecimal  excessive_fees= new BigDecimal("0");//本周期内如果超过限量需要支付多少超额费
	private BigDecimal  excessiveVolume= new BigDecimal("0");//超过这个量就要交超额费
	private BigDecimal  additionFeeAmountPrecision = new BigDecimal("2");

	//启用哪些告警
	private int alarmShowType=0b11111111;//第一位 WALKBY_TYPE 第二位HOUR24_HIGH_USE_TYPE 第三位LOW_USE_TYPE 第四位 COMMUNICATION_ERROR_TYPE  第5位LOW_VOLTAGE_TYPE  第六位CLOCK_ERROR_TYPE 第7位 FLASH_ERROR_TYPE 第8位 VALVE_ERROR_TYPE
	private Boolean showRechargeState  = false;//是否显示充值状态
	private Boolean enableAccurateToken  = false;//启用高精度token  开启后表计类型里添加特殊标记
	private int alarmSendType=0b00000000;//水平衡告警类型  告警类型 1长时间未用水报警，2用水异常报警，4水表断网报警，8电池低电，16用水不平衡报警
	private int availableMeterType=0b00000000;//有哪些表计类型可用使用
	private int calculateFeeMode=0;//计费模式
	private int paidMode=0;//预付费类型0   后付费1  2是让前端选择
	private int stepMode=1;//单阶梯1   多阶梯0  2是让前端选择
	private int stepLevel=16;//阶梯最多可以有多少
	private int maxStepVolume=65535;//最大阶梯量
	private int maxPrice=65535;//最大单价
	private int dayForCopyingData =50;//补抄几天的数据
	private int  loraReportingInterval=30;//rola表的上报间隔  单位秒 

	private int saveScale =0;//精度保留方式  0为四舍五入 1为超出表计精度部分进行保留供下次累加使用

	private int cardExpiryDate=9999;//卡片有效期
	private int cardEffectiveDate=0;//卡片几天后生效
	private Boolean  enableAdditionalFee=true;
	private Boolean  enableDebt=true;
	private Boolean  enableMinConsumed=true;
	private Boolean  checkAngencyBalance=false;
	private Boolean  singletonMode=false;//是否单段模式
	private Boolean  enableCompID=true;//是否开启特定的公司id表计  
	private Integer  compIDGenMode=1;//CompID产生方式 ，0=不需要产生  1=前端输入  2=1级2级部门自增,3级使用2级父亲的CompID
	private String language = "en_US";
	private List<String> languageList = Arrays.asList("en_US", "zh_CN", "ar_EG");//"[\"en_US\",\"zh_CN\",\"ar_EG\"]"
	private int orderShowTypeList=0b00001111;//第一位是安装工单  第二位是维修工单 第三位是换表工单 第四位是销户工单
	private int orderShowSourceList=0b00000001;//第一位是营业厅投诉录入  第二位=系统自动生成  第三位=邮件转工单
	//private List<String> orderTypesList = Arrays.asList("安装工单", "维修工单", "换表工单", "销户工单");
	//private List<String> orderSourceList = Arrays.asList("营业厅报修", "维修工单", "换表工单", "销户工单");
	private String invoiceType = "default";//发票模板
	private Boolean invoiceHisCustomer = true;//发票客户信息是否用addtionInfo里的客户信息
	private String  pretemp="default";//预计算模板
	private String posInvoiceType = "default";//pos机小票模板
	private String tradeTypeList="";//交易类型下拉框
	private String debtTypeList="";//债务类型下拉框
	private String functionTokenTypeList="";//功能token类型下拉框
	private String additionalFeeDeductionModeList="";//附加费扣费方式
	private String meterDescriptionFieldsList="";//表记管理-新增-表类型-表详情描述展示字段控制
	private BigDecimal  longitude= new BigDecimal("120.10940183613585");//地图的中心
	private BigDecimal  latitude= new BigDecimal("30.266417609677376");
	private BigDecimal  hourHighVolume= new BigDecimal("30.0");//居民表小时高流量告警值
	private BigDecimal  dayLowVolume= new BigDecimal("1.0");//居民表小时高流量告警值
	private BigDecimal  lowDays= new BigDecimal("10.0");//超过几天低于dayLowVolume告警
	private Integer oneCustomerMeters=1;//一个客户可以有几个表
	private String remotedriveEncryUrl="";//远端token生成  通常是硬加密服务的地址
	private Integer deptLevel=5;//部门最大深度
	private Integer regionLevel=5;//区域最大深度
	private Integer meterLevel=5;//表计最大深度
	private Integer priceLevel=16;//价格方案最多可以有多少阶梯
	private Integer holidayYearNumber = 1;
	private Integer holidayDayNumber = 25;
	private Integer weekendOrWrkDays = 0;//0-选节假日 1-选工作日 默认为0
	private Integer meterTypeListFiled = 0;//0表示显示1表示不显示
	private Integer meterTypeAddFiled = 0;
	private List<String> meterTypeList = new ArrayList<>();
	private Integer friendlyPeriodSchemeListFiled = 0;//0表示显示1表示不显示
	private Integer friendlyPeriodSchemeAddFiled = 0;
	private Integer holidaySchemeListFiled = 0;//0表示显示1表示不显示
	private Integer holidaySchemeAddFiled = 0;
	private Integer suezFeeListFiled = 0;//0表示显示1表示不显示


	private Integer suezFeeAddFiled = 0;
	private Integer priceSchemeListFiled = 0;//0表示显示1表示不显示
	private Integer priceSchemeAddFiled = 0; 
	private Integer meterInfoListFiled = 0;//0表示显示1表示不显示 
	private Integer meterInfoAddFiled = 0;
	private Integer customerTypeListFiled = 0;//0表示显示1表示不显示
	private Integer customerTypeAddFiled = 0;
	private Integer additionalFeeSchemeListFiled = 0;//0表示显示1表示不显示
	private Integer additionalFeeSchemeAddFiled = 0;
	private Integer additionalFeeDetailListFiled = 0;//0表示显示1表示不显示
	private Integer additionalFeeDetailAddFiled = 0;
	private Integer customerInfoListFiled = 0;//0表示显示1表示不显示 
	private Integer customerInfoAddFiled = 0;
	private Integer amiMeterTaskListFiled = 0;//0表示显示1表示不显示
	private Integer amiMeterTaskAddFiled = 0;
	private Integer dcuInfoListFiled = 0;
	private Integer dcuInfoAddFiled = 0;

	private Integer meterStateViewListFiled = 0;//0表示显示1表示不显示
	private Integer meterStateViewAddFiled = 0;

	private Integer sysAlramListFiled = 0;//0表示显示1表示不显示
	private Integer sysAlramAddFiled = 0;
	private Integer tradeViewListFiled = 0;//0表示显示1表示不显示
	private Integer meterMonthlyViewListFiled=0;//0表示显示1表示不显示
	private Integer operateLogListFiled = 0;//0表示显示1表示不显示
	
	private Integer meterBindRecordListFiled = 0;//0表示显示1表示不显示
	
	private Integer feeRecordViewListFiled = 0;//0表示显示1表示不显示

	private String bridgeToolVersion = "1.0.0.4";
	public boolean needSendAlarm(Integer alarmType) {
		return !((this.alarmSendType&alarmType)==0)&&!StringUtils.isBlank(alarmPhone);
	}
}