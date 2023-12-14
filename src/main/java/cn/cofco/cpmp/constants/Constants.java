package cn.cofco.cpmp.constants;

import java.util.List;

/**
 * Created by Xujy on 2017/4/29.
 */
public class Constants {

	public static final boolean SUC_TRUE = true;
	public static final boolean SUC_FALSE = false;

	public static final String EFF_FLG_ON = "1";
	public static final String EFF_FLG_OFF = "0";

	public static final Integer PAGE_SIZE = 20;
	public static final Integer PAGE_SIZE_MAX = 100;

	/**
	 * 供应商登录token有效期（单位分钟）
	 */
	public static final Integer TOKEN_VALI_TIME = 30;

	/**
	 * 供应商账户类型：1-管理员；2-普通账户
	 */
	public static final String SPLR_ACNT_ADMIN = "01";
	public static final String SPLR_ACNT_USER = "02";

	/**
	 * 招标范围类型：0-非定向招标；1-定向招标
	 */
	public static final String BID_RNG_TYP_VECTORING = "1";
	public static final String BID_RNG_TYP_UNVERCTORING = "0";
	public static final String UNVERCTORING_MSG = "非定向招标";

	/**
	 * 报价次数类型：0-一次报价；1-多次报价（最多三次）；2-实时报价
	 */
	public static final String QOT_CNT_TYP_ONCE = "0";
	public static final String QOT_CNT_TYP_MANY_TIMS = "1";
	public static final String QOT_CNT_TYP_REAL_TIM = "2";

	/**
	 * 提交标识
	 */
	public static final String SUB_FLG_ON = "1";
	public static final String SUB_FLG_OFF = "0";

	/**
	 * 通用参数定义 - 綫上招標項目狀態定義
	 */
	public static final String COM_PARM_TYP_BID_PROJ_ON_STS = "BID_PROJ_ON_STS";
	public static final String BID_PROJ_ON_STS_EDTING = "00"; // 编辑中
	public static final String BID_PROJ_ON_STS_APP_ADTING = "10"; // 招标申请审批中
	public static final String BID_PROJ_ON_STS_APP_ADT_PASS = "11"; // 招标申请审批通过
	public static final String BID_PROJ_ON_STS_BIDDING = "20"; // 招标中
	public static final String BID_PROJ_ON_STS_BID_END = "21"; // 已截标
	public static final String BID_PROJ_ON_STS_OPENED = "22"; // 已开标
	public static final String BID_PROJ_ON_STS_GRADING = "23"; // 评标中
	public static final String BID_PROJ_ON_STS_GRADED = "24"; // 评标结束
	// public static final String BID_PROJ_ON_STS_EXPT_ADITING = "25"; //专家审批

	public static final String BID_PROJ_ON_STS_QOTING2 = "30"; // 二次报价中
	public static final String BID_PROJ_ON_STS_QOT2_END = "31"; // 二次报价结束
	public static final String BID_PROJ_ON_STS_QOT2_OPENED = "32"; // 二次开标
	public static final String BID_PROJ_ON_STS_QOT2_GRADING = "33"; // 二次评标中
	public static final String BID_PROJ_ON_STS_QOT2_GRADED = "34"; // 二次评标结束
	public static final String BID_PROJ_ON_STS_RPL = "-20"; // 已废标
	public static final String BID_PROJ_ON_STS_RPL_ADTING = "-21"; // 废标审批中
	public static final String BID_PROJ_ON_STS_AWDING = "40"; // 决标审批中
	public static final String BID_PROJ_ON_STS_AWD_ACCEPTED = "50"; // 决标审批通過

	/**
	 * 通用参数定义 - 綫下招標項目狀態定義
	 */
	public static final String COM_PARM_TYP_BID_PROJ_OFF_STS = "BID_PROJ_OFF_STS"; // 线下招标项目状态
	public static final String BID_PROJ_OFF_STS_EDTING = "00"; // 编辑中
	public static final String BID_PROJ_OFF_STS_APP_ADTING = "10"; // 招标申请审批中
	public static final String BID_PROJ_OFF_STS_APP_ADT_PASS = "11"; // 招标申请审批通过
	public static final String BID_PROJ_OFF_STS_APP_BIDDING = "20"; // 招标中
	public static final String BID_PROJ_OFF_STS_APP_BID_END = "21"; // 已截标
	public static final String BID_PROJ_OFF_STS_RPL = "-20"; // 已废标
	public static final String BID_PROJ_OFF_STS_RPL_ADTING = "-21"; // 废标审批中
	public static final String BID_PROJ_OFF_STS_AWDING = "40"; // 决标审批中
	public static final String BID_PROJ_OFF_STS_AWD_ACCEPTED = "50"; // 决标审批通過

	/**
	 * 通用参数定义 - 询价項目狀態定義
	 */
	public static final String COM_PARM_TYP_XJ_PROJ_STS = "XJ_PROJ_STS";
	public static final String XJ_PROJ_STS_EDTING = "00"; // 编辑中
	public static final String XJ_PROJ_STS_BIDDING = "20"; // 招标中
	public static final String XJ_PROJ_STS_BID_END = "21"; // 已截标
	public static final String XJ_PROJ_STS_OPENED = "22"; // 已开标
	public static final String XJ_PROJ_STS_RPL = "-20"; // 已废标
	public static final String XJ_PROJ_STS_RPL_ADTING = "-21"; // 废标审批中
	public static final String XJ_PROJ_STS_AWDING = "40"; // 决标审批中
	public static final String XJ_PROJ_STS_AWD_ACCEPTED = "50"; // 决标审批通過
	/**
	 * 供应商资质文件保存路径
	 */
	public static final String SPLR_APT_FILE_PATH = "apt";
	public static final String ATCH_FILE_PATH = "atch";

	/**
	 * 公共文件类型
	 */
	public static final String[] FILE_TYPE = { "atch", "img", "apt","chk" };
	/**
	 * 供应商风采图片保存路径
	 */
	public static final String SPLR_CHRM_FILE_PATH = "chrm";

	/**
	 * 供应商状态：
	 */
	public static final String SPLR_STS_REGISTER = "01";
	public static final String SPLR_STS_ADMITTANCE = "02";
	public static final String SPLR_STS_READY = "03";
	public static final String SPLR_STS_APLY = "04";
	public static final String SPLR_STS_QUALIFIED = "05";
	public static final String SPLR_STS_WEED_OUT = "06";
	public static final String SPLR_STS_BLACKLIST = "07";
	public static final String SPLR_STS_ADMITTANCE_FAIL = "08";
	public static final String SPLR_STS_APLY_FAIL = "09";
	public static final String SPLR_STS_MDM_FAIL = "10";
	public static final String SPLR_STS_APLY_SUCCESS = "11";
	public static final String SPLR_STS_MDM_APLY = "12";

	/**
	 * 供应商审核状态：
	 */
	public static final String SPLR_ADT_STS_APLY = "01";
	public static final String SPLR_ADT_STS_SUCCESS = "02";
	public static final String SPLR_ADT_STS_FAIL = "03";

	/**
	 * 供应商淘汰申请状态：01-建立申请；02-审批中；03-审批退回(申请不通过)；04-审批成功
	 */
	public static final String SPLR_WDOT_APLY_STS_BUILD = "01";
	public static final String SPLR_WDOT_APLY_STS_APLYING = "02";
	public static final String SPLR_WDOT_APLY_STS_BACK = "03";
	public static final String SPLR_WDOT_APLY_STS_SUCCESS = "04";

	/**
	 * 供应商重启用申请状态：01-建立申请；02-审批中；03-审批退回(申请不通过)；04-审批成功
	 */
	public static final String SPLR_RE_ACT_APLY_STS_BUILD = "01";
	public static final String SPLR_RE_ACT_APLY_STS_APLYING = "02";
	public static final String SPLR_RE_ACT_APLY_STS_BACK = "03";
	public static final String SPLR_RE_ACT_APLY_STS_SUCCESS = "04";

	/**
	 * 保证金状态：0-未缴纳；1-已缴纳
	 */
	public static final String DPST_STS_UNPAY = "0";
	public static final String DPST_STS_PAID = "1";

	/**
	 * 标书状态：00-已申请；01-已接受；02-已拒绝；03-未中标；04-已中标
	 */
	public static final String BID_DOC_STS_APPLIED = "00";
	public static final String BID_DOC_STS_ACCEPTED = "01";
	public static final String BID_DOC_STS_REJECTED = "02";
	public static final String BID_DOC_STS_UNAWD = "03";
	public static final String BID_DOC_STS_AWD = "04";

	/**
	 * 招标公告发布标识：0-待发布；1-已发布
	 */
	public static final String BID_NTC_PUB_FLG_OFF = "0";
	public static final String BID_NTC_PUB_FLG_ON = "1";

	/**
	 * 招标结果发布标识：0-未发布；1-已发布
	 */
	public static final String BID_RST_PUB_FLG_OFF = "0";
	public static final String BID_RST_PUB_FLG_ON = "1";

	/**
	 * 文章发布标识：0-未发布；1-已发布
	 */
	public static final String ARTCL_PUB_FLG_OFF = "0";
	public static final String ARTCL_PUB_FLG_ON = "1";

	/**
	 * 允许供应商查询的线上招标项目状态
	 */
	public static final String[] BID_PROJ_ON_STS_LIST_FOR_SPLR = { "20", "21", "22", "23", "24", "30", "31", "32", "33",
			"34", "40", "50" };

	/**
	 * 允许供应商查询的线下招标项目状态
	 */
	public static final String[] BID_PROJ_OFF_STS_LIST_FOR_SPLR = { "20", "21", "40", "50" };

	/**
	 * 允许供应商查询的询价项目状态
	 */
	public static final String[] XJ_PROJ_STS_LIST_FOR_SPLR = { "20", "21", "22",
			"40", "50" };
	/**
	 * 通用参数定义 - 物料單位名稱定義
	 */
	public static final String COM_PARM_TYP_MAT_UNT = "MAT_UNT";
	// 台
	// public static final String MAT_UNT_TAI = "000001";

	/**
	 * 招标次数类型：0-一次竞价；1-二次竞价
	 */
	public static final String BID_CNT_TYP_FST = "0";
	public static final String BID_CNT_TYP_SCD = "1";

	/**
	 * 项目评标状态：00-待评标；01-评标中；02-评标完成
	 */
	public static final String GRD_STS_WAITING = "00";
	public static final String GRD_STS_GRADDING = "01";
	public static final String GRD_STS_DONE = "02";

	/**
	 * 评标类别：0-一次评标；1-二次评标
	 */
	public static final String GRAD_TYP_ONCE = "0";
	public static final String GRAD_TYP_TWICE = "1";

	/**
	 * 专家初始密码
	 */
	public static final String EXPT_PSW_ORI = "666666";

	/**
	 * 供应商准入申请状态：01-建立申请；02-已提交物料表；03-已提交考核表；04-审批中；05-审批退回；06-审批成功
	 */
	public static final String SPLR_ADMT_APLY_STS_BUILD = "01";
	public static final String SPLR_ADMT_APLY_STS_MAT = "02";
	public static final String SPLR_ADMT_APLY_STS_CHK = "03";
	public static final String SPLR_ADMT_APLY_STS_APLYING = "04";
	public static final String SPLR_ADMT_APLY_STS_BACK = "05";
	public static final String SPLR_ADMT_APLY_STS_SUCCESS = "06";

	/**
	 * 投标标识：0-未投标；1-已投标
	 */
	public static final String BID_FLG_UNDO = "0";
	public static final String BID_FLG_DONE = "1";

	/**
	 * 投标标识：0-非定向；1-非定向
	 */
	public static final String SPLR_TYP_FLG_ADT = "1";
	public static final String SPLR_TYP_FLG_UNADT = "0";

	/**
	 * 是否发起二次竞价标识: 0-否；1-是
	 */
	public static final String BID_TWC_FLG_N = "0";
	public static final String BID_TWC_FLG_Y = "1";

	/**
	 * MDM 调用用户名和密码
	 */
	public static final String MDM_USER = "appuser5";
	public static final String MDM_USER_PASSWD = "a123456";

	/**
	 * BPM 调用用户名和密码
	 */
	public static final String PI_BPM_SYS_NAM = "MY-BPM";
	public static final String PI_SYS_NAM = "YZ-PJSRM";
	public static final String BPM_USER = "appuser_bpm";
	public static final String BPM_USER_PASSWD = "sap12345";
	// public static final String BPM_LOGINUSER = "pansh"; // "CG_TEST"; //
	// "IBM-DLM"

	/**
	 * 是否需要投标保证金：0-不需要；1-需要
	 */
	public static final String DPST_FLG_ON = "1";
	public static final String DPST_FLG_OFF = "0";

	/**
	 * 是否需要公开招标价格标识：0-否；1-是
	 */
	public static final String PUB_PRI_FLG_ON = "1";
	public static final String PUB_PRI_FLG_OFF = "0";

	/**
	 * 可以查看一次评标信息的项目状态
	 */
	public static String[] PROJ_STSES_OF_GET_GRD1_INF = { "23", // 评标中
			"24", // 评标结束
			"30", // 二次报价中
			"31", // 二次报价结束
			"32", // 二次开标
			"33", // 二次评标中
			"34", // 二次评标结束
			"40", // 决标审批中
			"50", // 决标审批通过
			"25" // 专家决标审批
	};

	/**
	 * 可以查看二次评标信息的项目状态
	 */
	public static String[] PROJ_STSES_OF_GET_GRD2_INF = { "33", // 二次评标中
			"34", // 二次评标结束
			"40", // 决标审批中
			"50" // 决标审批通过
	};

	public static String COMMA = ",";

	// 是否逆序排序
	public static String DESC_FLG_ON = "1";
	public static String DESC_FLG_OFF = "0";

	/**
	 * 通用参数定义 - 文章类别
	 */
	public static String COM_PARM_TYP_ARTCL_TYP = "ARTCL_TYP";
	public static String ARTCL_TYP_IMPORTANT_NOTICE = "000001"; // 文章类别 - 重要通知
	public static String ARTCL_TYP_LAWS_AND_REGULATIONS = "000002"; // 文章类别 -
	// 政策法规
	public static String ARTCL_TYP_GUIDELINES_FOR_PROCUREMENT = "000003"; // 文章类别
	// -
	// 采购指南
	public static String ARTCL_TYP_DOWNLOAD_CENTER = "000004"; // 文章类别 - 下载中心
	public static String ARTCL_TYP_FREQUENTLY_ASKED_QUESTIONS = "000005"; // 文章类别
	// -
	// 常见问题

	/**
	 * 通用参数定义 - 附件编码前缀
	 */
	public static String COM_PARM_TYP_ATCH_PFX = "ATCH_PFX";
	public static String ATCH_PFX_ARTCL = "000001"; // 附件编码前缀 - 文章附件
	public static String ATCH_PFX_GKBUD = "000002"; // 附件编码前缀 - 线下公开项目立项
	public static String ATCH_PFX_JJBUD = "000003"; // 附件编码前缀 - 网上竞价项目立项
	public static String ATCH_PFX_QOT = "000004"; // 附件编码前缀 - 报价
	public static String ATCH_PFX_GKAWD = "000005"; // 附件编码前缀 - 线下公开项目定标
	public static String ATCH_PFX_XJBUD = "000006"; // 附件编码前缀 - 询价项目定标
	public static String ATCH_PFX_XJQOT = "000007"; // 附件编码前缀 - 询价项目报价

	/**
	 * BPM回调鉴权
	 */
	public final static String UID_BPM = "BPM";
	public final static String SIG_BPM = "70EEF3C19CFB1BEE41C5335BC2C114E4";
	public final static Long BPM_SUC = 0L;

	/**
	 * 请求类型：1 - 立项审批；2 - 定标审批；3 -废标审批
	 */
	public final static String BPM_CAL_BAK_REQ_TYP_BUD = "1";
	public final static String BPM_CAL_BAK_REQ_TYP_AWD = "2";
	public final static String BPM_CAL_BAK_REQ_TYP_RPL = "3";

	/**
	 * 成功标识：0-审核不通过；1-审核通过
	 */
	public final static String BPM_CAL_BAK_SUC = "1";
	public final static String BPM_CAL_BAK_FAL = "0";

	/**
	 * 项目类型定义
	 */
	public final static String PROJ_TYP_JJ = "JJ";
	public final static String PROJ_TYP_GK = "GK";
	public final static String PROJ_TYP_XJ = "XJ";
	public final static String PROJ_TYP_ZR = "ZR";

	/**
	 * BPM回传单据定义
	 */
	public final static String BPM_APP_TYP_GK_BUD = "A010_ZB001";
	public final static String BPM_APP_TYP_GK_AWD = "A010_ZB002";
	public final static String BPM_APP_TYP_GK_RPL = "A010_ZB003";
	public final static String BPM_APP_TYP_JJ_BUD = "A010_JJ001";
	public final static String BPM_APP_TYP_JJ_AWD = "A010_JJ002";
	public final static String BPM_APP_TYP_JJ_RPL = "A010_JJ003";
	public final static String BPM_APP_TYP_GK_APY = "A010_GK001";
	public final static String BPM_APP_TYP_XJ_AWD = "XJ002_A010";
	public final static String BPM_APP_TYP_XJ_RPL = "A010_XJ003";

	/**
	 * BPM上送单据模板号后缀定义
	 */
	public final static String BPM_APP_TYP_GK_BUD_SUFFIX = "_ZB001";
	public final static String BPM_APP_TYP_GK_AWD_SUFFIX = "_ZB002";
	public final static String BPM_APP_TYP_GK_RPL_SUFFIX = "_ZB003";
	public final static String BPM_APP_TYP_JJ_BUD_SUFFIX = "_JJ001";
	public final static String BPM_APP_TYP_JJ_AWD_SUFFIX = "_JJ002";
	public final static String BPM_APP_TYP_JJ_RPL_SUFFIX = "_JJ003";
	public final static String BPM_APP_TYP_GK_APY_SUFFIX = "_GK001";
	public final static String BPM_APP_TYP_XJ_RPL_SUFFIX = "_XJ003";
	public final static String BPM_APP_TYP_XJ_AWD_SUFFIX = "XJ002_";
	/**
	 * 短信参数定义
	 */
	public final static String SYS_PARM_TYP_SMS = "SMS"; // 短信参数
	public final static String SYS_PARM_KEY_ACCESS_KEY_ID = "LTAIdO6edXAMolzp"; // 短信发送ACCESS_KEY_ID
	public final static String SYS_PARM_KEY_ACCESS_KEY_SECRET = "mkzPXPtBEtf8NlY6cGvawN9BauxQS2"; // 短信发送ACCESS_KEY_SECRET
	public final static String SYS_PARM_KEY_ACCESS_URL = "https://help.aliyun.com/document_detail/55288.html"; // 短信发送调用链接
	public final static String SYS_PARM_KEY_SIGN_NAME = "采购平台"; // 短信签名

	public final static String SYS_PARM_TYP_SMS_TMPL = "SMS_TMPL"; // 短信模板参数
	public final static String SYS_PARM_KEY_OPEN_BID = "BID_OPEN"; // 发送开标密钥短信模板
	public final static String SYS_PARM_KEY_GRD_BID = "BID_GRD"; // 发送评标密钥短信模板

	public final static String SYS_PARM_TYP_BPM = "BPM"; // BPM参数
	public final static String SYS_PARM_KEY_BPM_UPLOAD_URL = "UPLOAD_URL"; // BPM附件上传地址
	public final static String SYS_PARM_KEY_BPM_TOKEN_URL = "TOKEN_URL"; // BPM_TOKEN获取地址

	public final static String SYS_PARM_TYP_FILE_ADDR = "FILE_ADDR"; // 上传文件参数
	public final static String SYS_PARM_KEY_FILE_ADDR_UNIX = "FILE_ADDR_UNIX"; // 上传文件保存地址-UNIX系统
	public final static String SYS_PARM_KEY_FILE_ADDR_WINDOWS = "FILE_ADDR_WINDOWS"; // 上传文件保存地址-WINDOWS系统

	/**
	 * MDM语言代码
	 */
	public final static String LANGU_CHINESE = "ZH"; // 中文
	public final static String LANGU_ENGLISH = "EN"; // 英文

	public final static String BPM_RTN_TYP_SUC = "TRUE";
	public final static String BPM_RTN_TYP_FAIL = "FALSE";

	/**
	 * 供应商风采展示状态
	 */
	public final static String SPLR_CHRM_SHOW = "01";
	public final static String SPLR_CHRM_NO_SHOW = "02";

	/**
	 * 客商标识：20-供应商；30-客商
	 */
	public final static String CO_PARTNER_TYPE_SPLR = "20";
	public final static String CO_PARTNER_TYPE_PARTNER = "30";

	/**
	 * 客商标识：0-未删除；1-删除
	 */
	public final static String DEL_FLG_NODEL = "0";
	public final static String DEL_FLG_DEL = "1";

	/**
	 * 评标规则：0-最低单价评标; 1-专家评标; 2-最低总价评标
	 */
	public static final String GRD_RUL_0 = "0";
	public static final String GRD_RUL_1 = "1";
	public static final String GRD_RUL_2 = "2";

	/**
	 * 0:非招标中，1:招标中
	 */
	public static final String INZB_0 = "0";
	public static final String INZB_1 = "1";

	/*
	 * 专家审批结果：0-未审批; 1-同意; 2-不同意
	 */
	/*
	 * public static final String IS_AGREED_0 = "0"; public static final String
	 * IS_AGREED_1 = "1"; public static final String IS_AGREED_2 = "2";
	 */

	public static final String getGrdRulDesc(String grdRul) {
		if (GRD_RUL_0.equals(grdRul)) {
			return "最低单价评标";
		} else if (GRD_RUL_1.equals(grdRul)) {
			return "专家评标";
		} else {
			return "最低总价评标";
		}
	}
	/*
	 * public static final String getIsAgreed(String isAgreed) { if
	 * (IS_AGREED_0.equals(isAgreed)) { return "未审批"; } else
	 * if(IS_AGREED_1.equals(isAgreed)) { return "同意"; }else { return "不同意"; } }
	 */

	/**
	 * 项目评标状态：00-待评标；01-评标中；02-评标完成
	 */
	public static final String PROJ_GRD_STS_DONE = "02";

	/*采购员代号*/
	public static  String[] pchs = {"FC-C02","ZC-C02","YC-C02","ZCYC-C02","YX-C02"};
}
