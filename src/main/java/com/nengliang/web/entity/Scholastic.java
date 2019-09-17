package com.nengliang.web.entity;

import java.util.Date;

public class Scholastic {

	private Integer id;

    private String username;

    private Integer userage;

    private String mailbox;

    private String usergender;

    private String headportrait;
    
    private Date creationtime;

    private Date modifytime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getUserage() {
		return userage;
	}

	public void setUserage(Integer userage) {
		this.userage = userage;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public String getUsergender() {
		return usergender;
	}

	public void setUsergender(String usergender) {
		this.usergender = usergender;
	}

	public String getHeadportrait() {
		return headportrait;
	}

	public void setHeadportrait(String headportrait) {
		this.headportrait = headportrait;
	}

	public Date getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Date creationtime) {
		this.creationtime = creationtime;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	@Override
	public String toString() {
		return "Scholastic [id=" + id + ", username=" + username + ", userage=" + userage + ", mailbox=" + mailbox
				+ ", usergender=" + usergender + ", headportrait=" + headportrait + ", creationtime=" + creationtime
				+ ", modifytime=" + modifytime + "]";
	}
	
	
}
