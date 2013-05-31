package com.gzeport.app.gps.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_NSCARINFO", schema = "PORTAL")
public class NsCarInfo implements java.io.Serializable {

	private static final long serialVersionUID = -6822364632733757253L;
	private Long seqid;
	private String plate;
	private String inareano;
	private String status; 
	private Date starttime;
	private Date stoptime; 

	// Constructors

	/** default constructor */
	public NsCarInfo() {
	}

	/** minimal constructor */
	public NsCarInfo(Long seqid, String plate, String inareano,
			String status) {
		this.seqid = seqid;
		this.plate = plate;
		this.inareano = inareano;
		this.status = status;
	}

	/** full constructor */
	public NsCarInfo(Long seqid, String plate, String inareano,
			String status, Date starttime, Date stoptime) {
		this.seqid = seqid;
		this.plate = plate;
		this.inareano = inareano;
		this.status = status;
		this.starttime = starttime;
		this.stoptime = stoptime;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seqid_nscarinfo")
	@SequenceGenerator(name="seqid_nscarinfo",sequenceName="PORTAL.NSCARINFO_SEQ",allocationSize=1)
	@Column(name = "SEQID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getSeqid() {
		return this.seqid;
	}

	public void setSeqid(Long seqid) {
		this.seqid = seqid;
	}

	@Column(name = "PLATE", nullable = false, length = 10)
	public String getPlate() {
		return this.plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	@Column(name = "INAREANO", nullable = false, length = 15)
	public String getInareano() {
		return this.inareano;
	}

	public void setInareano(String inareano) {
		this.inareano = inareano;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTTIME", length = 18)
	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STOPTIME", length = 18)
	public Date getStoptime() {
		return this.stoptime;
	}

	public void setStoptime(Date stoptime) {
		this.stoptime = stoptime;
	}

}