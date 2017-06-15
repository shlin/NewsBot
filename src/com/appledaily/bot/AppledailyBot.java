package com.appledaily.bot;

import java.sql.Connection;
import java.util.Calendar;

public class AppledailyBot {
	private Connection dbConn;
	private String strQuerystr;
	private Calendar calSDate;
	private Calendar calEDate;

	/**
	 * @param dbConn
	 * @param strQuerystr
	 * @param calSDate
	 * @param calEDate
	 */
	public AppledailyBot(Connection dbConn, String strQuerystr, Calendar calSDate, Calendar calEDate) {
		this.dbConn = dbConn;
		this.strQuerystr = strQuerystr;
		this.calSDate = calSDate;
		this.calEDate = calEDate;
	}
}
