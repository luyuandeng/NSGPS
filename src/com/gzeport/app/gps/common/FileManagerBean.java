package com.gzeport.app.gps.common;

import com.gzeport.app.gps.util.StringUtil;

public class FileManagerBean {

	public String getBeanNames() {
		return beanNames;
	}

	public void setBeanNames(String beanNames) {
		this.beanNames = beanNames;
	}

	public FileManagerBean() {
		port = 0;
		remoteBool = false;
		dalyTime = 1800L;
		localbool = true;
		sendByEdi = "false";
		docType = "NEW";
		downDosType = "ALL";
		receiverCode = "ALL";
		docTypes = "";
		localbool = true;
		remoteBool = false;
		dalyTime = 1800L;
		localUploadErrorDir = "error";
		localDowndErrorDir = "error";
		sendByEdi = "false";
	}

	public String getUserName() {
		return StringUtil.trimNull(userName);
	}

	public void setUserName(String userName) {
		this.userName = StringUtil.trimNull(userName);
	}

	public String getUserPwd() {
		return StringUtil.trimNull(userPwd);
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = StringUtil.trimNull(userPwd);
	}

	public String getHostIp() {
		return StringUtil.trimNull(hostIp);
	}

	public void setHostIp(String hostIp) {
		this.hostIp = StringUtil.trimNull(hostIp);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getLocalUploadDir() {
		return StringUtil.trimNull(localUploadDir);
	}

	public void setLocalUploadDir(String localUploadDir) {
		this.localUploadDir = StringUtil.trimNull(localUploadDir);
	}

	public String getLocalDownDir() {
		return StringUtil.trimNull(localDownDir);
	}

	public void setLocalDownDir(String localDownDir) {
		this.localDownDir = StringUtil.trimNull(localDownDir);
	}

	public String getRemoteUploadDir() {
		return StringUtil.trimNull(remoteUploadDir);
	}

	public void setRemoteUploadDir(String remoteUploadDir) {
		this.remoteUploadDir = StringUtil.trimNull(remoteUploadDir);
	}

	public String getRemoteDownDir() {
		return StringUtil.trimNull(remoteDownDir);
	}

	public void setRemoteDownDir(String remoteDownDir) {
		this.remoteDownDir = StringUtil.trimNull(remoteDownDir);
	}

	public String getRemoteBackupDir() {
		return StringUtil.trimNull(remoteBackupDir);
	}

	public void setRemoteBackupDir(String remoteBackupDir) {
		this.remoteBackupDir = StringUtil.trimNull(remoteBackupDir);
	}

	public String getRemoteError() {
		return StringUtil.trimNull(remoteError);
	}

	public void setRemoteError(String remoteError) {
		this.remoteError = StringUtil.trimNull(remoteError);
	}

	public String getLocalBackupDir() {
		return StringUtil.trimNull(localBackupDir);
	}

	public void setLocalBackupDir(String localBackupDir) {
		this.localBackupDir = StringUtil.trimNull(localBackupDir);
	}

	public boolean isRemoteBool() {
		return remoteBool;
	}

	public void setRemoteBool(boolean remoteBool) {
		this.remoteBool = remoteBool;
	}

	public boolean isLocalbool() {
		return localbool;
	}

	public void setLocalbool(boolean localbool) {
		this.localbool = localbool;
	}

	public String getRemoteBaseDir() {
		return StringUtil.trimNull(remoteBaseDir);
	}

	public void setRemoteBaseDir(String remoteBaseDir) {
		this.remoteBaseDir = StringUtil.trimNull(remoteBaseDir);
	}

	public String getLocalBaseDir() {
		return StringUtil.trimNull(localBaseDir);
	}

	public void setLocalBaseDir(String localBaseDir) {
		this.localBaseDir = StringUtil.trimNull(localBaseDir);
	}

	public long getDalyTime() {
		return dalyTime;
	}

	public void setDalyTime(long dalyTime) {
		this.dalyTime = dalyTime;
	}

	public String getLocalUploadErrorDir() {
		return localUploadErrorDir;
	}

	public void setLocalUploadErrorDir(String localUploadErrorDir) {
		this.localUploadErrorDir = localUploadErrorDir;
	}

	public String getLocalDowndErrorDir() {
		return localDowndErrorDir;
	}

	public void setLocalDowndErrorDir(String localDowndErrorDir) {
		this.localDowndErrorDir = localDowndErrorDir;
	}

	public boolean hasisSendByEdi() {
		return !"false".equalsIgnoreCase(getSendByEdi());
	}

	public String getSendByEdi() {
		return sendByEdi;
	}

	public void setSendByEdi(String sendByEdi) {
		this.sendByEdi = sendByEdi;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDownDosType() {
		return downDosType;
	}

	public void setDownDosType(String downDosType) {
		this.downDosType = downDosType;
	}

	public String getReceiverCode() {
		return receiverCode;
	}

	public void setReceiverCode(String receiverCode) {
		this.receiverCode = receiverCode;
	}

	public String getDocTypes() {
		return docTypes;
	}

	public void setDocTypes(String docTypes) {
		this.docTypes = docTypes;
	}

	String userName;
	String userPwd;
	String hostIp;
	int port;
	String remoteBaseDir;
	String remoteUploadDir;
	String remoteDownDir;
	String localUploadDir;
	String localDownDir;
	String remoteBackupDir;
	String remoteError;
	boolean remoteBool;
	String localBaseDir;
	String localBackupDir;
	String localUploadErrorDir;
	String localDowndErrorDir;
	long dalyTime;
	boolean localbool;
	String sendByEdi;
	String docType;
	String downDosType;
	String receiverCode;
	String beanNames;
	String docTypes;
}
