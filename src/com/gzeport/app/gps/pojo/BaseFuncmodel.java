package com.gzeport.app.gps.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "T_BASE_FUNCMODEL", schema = "PORTAL")
public class BaseFuncmodel implements  java.io.Serializable {
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private String funcId;
	private String funcCode;
	private String funcName;
	private String funcSysType;
	private String funcIsmenu;
	private String funcIslast;
	private String funcUrl;
	private String funcResume;
	private String funcUseType;

	/** default constructor */
	public BaseFuncmodel() {
	}

	/** minimal constructor */
	public BaseFuncmodel(String funcId) {
		this.funcId = funcId;
	}

	/** full constructor */
	public BaseFuncmodel(String funcId, BaseFuncmodel TBaseFuncmodel,
			String funcCode, String funcName, String funcSysType,
			String funcIsmenu, String funcIslast, String funcUrl,
			String funcResume, String funcUseType) {
		this.funcId = funcId;
		this.funcCode = funcCode;
		this.funcName = funcName;
		this.funcSysType = funcSysType;
		this.funcIsmenu = funcIsmenu;
		this.funcIslast = funcIslast;
		this.funcUrl = funcUrl;
		this.funcResume = funcResume;
		this.funcUseType = funcUseType;
	}

	// Property accessors
	@Id
	@Column(name = "FUNC_ID", unique = true, nullable = false, length = 12)
	public String getFuncId() {
		return this.funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	@Column(name = "FUNC_CODE", length = 2000)
	public String getFuncCode() {
		return this.funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	@Column(name = "FUNC_NAME", length = 100)
	public String getFuncName() {
		return this.funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	@Column(name = "FUNC_SYS_TYPE", length = 2)
	public String getFuncSysType() {
		return this.funcSysType;
	}

	public void setFuncSysType(String funcSysType) {
		this.funcSysType = funcSysType;
	}

	@Column(name = "FUNC_ISMENU", length = 1)
	public String getFuncIsmenu() {
		return this.funcIsmenu;
	}

	public void setFuncIsmenu(String funcIsmenu) {
		this.funcIsmenu = funcIsmenu;
	}

	@Column(name = "FUNC_ISLAST", length = 1)
	public String getFuncIslast() {
		return this.funcIslast;
	}

	public void setFuncIslast(String funcIslast) {
		this.funcIslast = funcIslast;
	}

	@Column(name = "FUNC_URL", length = 200)
	public String getFuncUrl() {
		return this.funcUrl;
	}

	public void setFuncUrl(String funcUrl) {
		this.funcUrl = funcUrl;
	}

	@Column(name = "FUNC_RESUME", length = 1500)
	public String getFuncResume() {
		return this.funcResume;
	}

	public void setFuncResume(String funcResume) {
		this.funcResume = funcResume;
	}

	@Column(name = "FUNC_USE_TYPE", length = 10)
	public String getFuncUseType() {
		return this.funcUseType;
	}

	public void setFuncUseType(String funcUseType) {
		this.funcUseType = funcUseType;
	}

}