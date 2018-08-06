package com.ccmp.sendmail.config.feign.pltform.model;

import java.io.Serializable;
import java.util.Map;

/**
 * @author yangtong
 * @date 2018/3/15 0015 16:39
 * @describe: 平台请求类
 */
public class TaskRequest implements Serializable {

	private static final long serialVersionUID = 6762438354041812616L;
	private boolean isTemplateFlag;
	private String ccmpSeq;
	private String accessSysCode;
	private String sendTo;
	private String subject;
	private String templateCodeOrContent;
	private Map<String, String> templateParams;
	private String bizNo;
	private Integer priority = 1;

	public TaskRequest() {
	}

	public TaskRequest(boolean isTemplateFlag, String ccmpSeq, String accessSysCode, String sendTo, String subject, String templateCodeOrContent, Map<String, String> templateParams, String bizNo) {
		this.isTemplateFlag = isTemplateFlag;
		this.ccmpSeq = ccmpSeq;
		this.accessSysCode = accessSysCode;
		this.sendTo = sendTo;
		this.subject = subject;
		this.templateCodeOrContent = templateCodeOrContent;
		this.templateParams = templateParams;
		this.bizNo = bizNo;
	}

	public boolean isTemplateFlag() {
		return isTemplateFlag;
	}
	public void setTemplateFlag(boolean isTemplateFlag) {
		this.isTemplateFlag = isTemplateFlag;
	}
	public String getCcmpSeq() {
		return ccmpSeq;
	}
	public void setCcmpSeq(String ccmpSeq) {
		this.ccmpSeq = ccmpSeq;
	}
	public String getAccessSysCode() {
		return accessSysCode;
	}
	public void setAccessSysCode(String accessSysCode) {
		this.accessSysCode = accessSysCode;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTemplateCodeOrContent() {
		return templateCodeOrContent;
	}
	public void setTemplateCodeOrContent(String templateCodeOrContent) {
		this.templateCodeOrContent = templateCodeOrContent;
	}
	public Map<String, String> getTemplateParams() {
		return templateParams;
	}
	public void setTemplateParams(Map<String, String> templateParams) {
		this.templateParams = templateParams;
	}
	public String getBizNo() {
		return bizNo;
	}
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "{isTemplateFlag:" + isTemplateFlag + ", ccmpSeq:" + ccmpSeq + ", accessSysCode:"
				+ accessSysCode + ", sendTo:" + sendTo + ", subject:" + subject + ", templateCodeOrContent:"
				+ templateCodeOrContent + ", templateParams:" + templateParams + ", bizNo:" + bizNo + ", priority:"
				+ priority + "}";
	}
	
}
