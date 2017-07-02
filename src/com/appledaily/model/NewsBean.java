package com.appledaily.model;

import java.io.Serializable;

public class NewsBean implements Serializable, Comparable<NewsBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8933649353338407346L;

	private int dbId;
	private String guid;
	private String date;
	private String title;
	private String content;

	/**
	 * @param dbId
	 * @param guid
	 * @param date
	 * @param title
	 * @param content
	 */
	public NewsBean(int dbId, String guid, String date, String title, String content) {
		super();
		this.dbId = dbId;
		this.guid = guid;
		this.date = date;
		this.title = title;
		this.content = content;
	}

	/**
	 * @return the dbId
	 */
	public int getDbId() {
		return dbId;
	}

	/**
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	@Override
	public int compareTo(NewsBean o) {
		int o1Date = Integer.parseInt(this.date);
		int o2Date = Integer.parseInt(o.getDate());

		return o1Date == o2Date ? 0 : o1Date < o2Date ? -1 : 1;
	}
}
