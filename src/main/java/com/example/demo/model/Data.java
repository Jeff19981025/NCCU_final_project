package com.example.demo.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "data")
public class Data {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DATA_JSON")
	private String data_json;

	@Column(name = "CREATION_DATE")
	private Date creation_date = new Date(System.currentTimeMillis());
	
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="BELONGS")
    private User user;
	
	public Data() {}
	public Data(String data_json) {
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

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
