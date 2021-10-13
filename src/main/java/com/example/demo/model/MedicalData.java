package com.example.demo.model;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "medicaldata")
public class MedicalData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DATA_JSON")
	private String data_json;

	@Column(name = "CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
    private Calendar utilCalendar=Calendar.getInstance(Locale.TAIWAN);
	
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="BELONGS")
    private User user;
	
	public MedicalData() {}
	public MedicalData(String data_json) {
		super();
		this.data_json = data_json;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getData_json() {
		return data_json;
	}

	public void setData_json(String data_json) {
		this.data_json = data_json;
	}

	public Calendar getUtilCalendar() {
		return utilCalendar;
	}
	public void setUtilCalendar(Calendar utilCalendar) {
		this.utilCalendar = utilCalendar;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
