package com.ccmp.sendmail.config.feign.pltform.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangtong
 * @date 2018/3/15 0015 16:39
 * @describe: 平台响应类
 */
public class
PltResponse implements Serializable {

	//	成功
	public static final String RST_SUCC = "000000";
	//	系统错误
	public static final String SYS_ERROR = "520001";
	//	系统繁忙
	public static final String SYS_BUSY = "520002";
	//	并发超限
	public static final String CON_TFN = "520003";
	//	认证未通过
	public static final String AUTH_FAIL = "520004";
	//	操作未授权
	public static final String UNAUYH_OPT = "520005";
	//	授权过期
	public static final String AUTH_EXPIR = "520006";
	//	不支持该操作
	public static final String NOSOPT_OPT = "520007";
	//	请求格式不合法
	public static final String ILL_REQ = "520008";
	//	参数缺失
	public static final String MIS_PARAM = "520009";
	//	参数无效
	public static final String INV_PARAM = "520010";
	//	操作对象不存在
	public static final String OPTOBJ_NOEXT = "520011";
	//	操作对象已存在
	public static final String OPTOBJ_EXT = "520012";
	//	外部接口通信失败
	public static final String COMM_FAIL = "520013";
	//	外部接口通信超时
	public static final String COMM_TIMEOUT = "520014";
	//	部分数据成功，部分数据失败
	public static final String PART_FAIL = "520015";
	//	其他错误
	public static final String OTHER_ERR = "520999";
	private static final long serialVersionUID = -7049811306242064397L;

	private static Map<String, String> mapCode = new HashMap<String, String>();

	static{

		mapCode.put(RST_SUCC, "成功");
		mapCode.put(SYS_ERROR,"系统错误：[${spec_err}]");
		mapCode.put(SYS_BUSY,"系统繁忙:[]");
		mapCode.put(CON_TFN,"并发超限:[]");
		mapCode.put(AUTH_FAIL,"认证未通过:[]");
		mapCode.put(UNAUYH_OPT,"操作未授权:[]");
		mapCode.put(AUTH_EXPIR,"授权过期:[]");
		mapCode.put(NOSOPT_OPT,"不支持该操作:[]");
		mapCode.put(ILL_REQ,"请求格式不合法:[]");
		mapCode.put(MIS_PARAM,"参数缺失:[${param}不能为空]");
		mapCode.put(INV_PARAM,"参数无效:[${spec_err}]");
		mapCode.put(OPTOBJ_NOEXT,"操作对象不存在:[]");
		mapCode.put(OPTOBJ_EXT,"操作对象已存在:[]");
		mapCode.put(COMM_FAIL,"外部接口通信失败:[]");
		mapCode.put(COMM_TIMEOUT,"外部接口通信超时:[]");
		mapCode.put(PART_FAIL,"批量数据处理部分成功：[${spec_suc}]，部分地址错误:[${spec_err}]");
		mapCode.put(OTHER_ERR,"其他错误:[]");
	}

	private String respCode = RST_SUCC;
	private String respDesc = "成功";


	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
		String desc=mapCode.get(respCode);
		if (desc==null){
			this.respDesc = "";
		}else {
			this.respDesc = desc;
		}
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}

}
