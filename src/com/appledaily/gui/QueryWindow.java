package com.appledaily.gui;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.appledaily.model.NewsBean;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import jdbc.mysql.MySQLConnector;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class QueryWindow {

	private JFrame frmDbQuery;
	private JTextField mysqlHost;
	private JTextField mysqlAccount;
	private JLabel lblMysql_2;
	private JLabel lblDatabase;
	private JLabel lblTable;
	private JLabel label;
	private JLabel label_1;
	private JTextField databaseName;
	private JTextField tableName;
	private JTextField searchKeywords;
	private JSpinner fontSize;
	private JButton button;
	private JLabel label_2;
	private JLabel resultCount;
	private DefaultListModel<String> resultListModel;
	private JTextPane showContent;
	private JPasswordField mysqlPassword;
	private JList<String> resultList;
	private JScrollPane resultListScrollPane;

	private Runnable dbQueryTask;
	private ArrayList<NewsBean> resultBeanList;
	private MySQLConnector dbConnector;
	private JScrollPane showContentScrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(AppWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(AppWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(AppWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(AppWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QueryWindow window = new QueryWindow();
					window.frmDbQuery.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public QueryWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDbQuery = new JFrame();
		frmDbQuery.setTitle("蘋果日報新聞爬蟲-資料庫查詢");
		frmDbQuery.setBounds(100, 100, 800, 600);
		frmDbQuery.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDbQuery.getContentPane()
				.setLayout(new FormLayout(
						new ColumnSpec[] { ColumnSpec.decode("10px"), ColumnSpec.decode("right:110px"),
								ColumnSpec.decode("left:150px"), ColumnSpec.decode("10px"),
								ColumnSpec.decode("450px:grow"), ColumnSpec.decode("10px"), },
						new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								RowSpec.decode("default:grow"), FormSpecs.RELATED_GAP_ROWSPEC, }));

		JLabel lblMysql = new JLabel("MySQL 伺服器：");
		frmDbQuery.getContentPane().add(lblMysql, "2, 2");

		mysqlHost = new JTextField();
		frmDbQuery.getContentPane().add(mysqlHost, "3, 2, fill, default");
		mysqlHost.setColumns(10);

		showContentScrollPane = new JScrollPane();
		frmDbQuery.getContentPane().add(showContentScrollPane, "5, 2, 1, 19, fill, fill");

		showContent = new JTextPane();
		showContentScrollPane.setViewportView(showContent);
		showContent.setContentType("text/html");
		showContent.setEditable(false);

		JLabel lblMysql_1 = new JLabel("MySQL 帳號：");
		frmDbQuery.getContentPane().add(lblMysql_1, "2, 4");

		mysqlAccount = new JTextField();
		frmDbQuery.getContentPane().add(mysqlAccount, "3, 4, fill, default");
		mysqlAccount.setColumns(10);

		lblMysql_2 = new JLabel("MySQL 密碼：");
		frmDbQuery.getContentPane().add(lblMysql_2, "2, 6");

		mysqlPassword = new JPasswordField();
		frmDbQuery.getContentPane().add(mysqlPassword, "3, 6, fill, default");

		lblDatabase = new JLabel("Database 名稱：");
		frmDbQuery.getContentPane().add(lblDatabase, "2, 8");

		databaseName = new JTextField();
		frmDbQuery.getContentPane().add(databaseName, "3, 8, fill, default");
		databaseName.setColumns(10);

		lblTable = new JLabel("Table 名稱：");
		frmDbQuery.getContentPane().add(lblTable, "2, 10");

		tableName = new JTextField();
		frmDbQuery.getContentPane().add(tableName, "3, 10, fill, default");
		tableName.setColumns(10);

		label = new JLabel("搜尋關鍵字：");
		frmDbQuery.getContentPane().add(label, "2, 12");

		searchKeywords = new JTextField();
		frmDbQuery.getContentPane().add(searchKeywords, "3, 12, fill, default");
		searchKeywords.setColumns(10);

		label_1 = new JLabel("字型大小：");
		frmDbQuery.getContentPane().add(label_1, "2, 14");

		fontSize = new JSpinner();
		fontSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				doPaintShowContent();
			}
		});
		frmDbQuery.getContentPane().add(fontSize, "3, 14");
		fontSize.setModel(new SpinnerNumberModel(14, 1, 100, 1));

		button = new JButton("查詢");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (button.isEnabled()) {
					button.setEnabled(false);
					resultListModel = new DefaultListModel<String>();
					new Thread(dbQueryTask).start();
				}
			}
		});
		frmDbQuery.getContentPane().add(button, "3, 16, left, center");

		label_2 = new JLabel("查詢結果：");
		frmDbQuery.getContentPane().add(label_2, "2, 18, right, default");

		resultCount = new JLabel("共 0 筆");
		frmDbQuery.getContentPane().add(resultCount, "3, 18, center, default");

		resultList = new JList<String>();
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					doPaintShowContent();
				}
			}
		});

		resultListScrollPane = new JScrollPane(resultList);
		frmDbQuery.getContentPane().add(resultListScrollPane, "2, 20, 2, 1, fill, fill");

		this.resultBeanList = new ArrayList<NewsBean>();

		this.dbQueryTask = () -> {
			String query = new String();
			StringBuilder sb = new StringBuilder();
			this.dbConnector = new MySQLConnector(this.mysqlHost.getText(), this.mysqlAccount.getText(),
					String.valueOf(this.mysqlPassword.getPassword()), this.databaseName.getText());

			// construct sql query
			if (!this.tableName.getText().isEmpty()) {
				sb.append(String.format("SELECT * FROM `%s` WHERE", this.tableName.getText()));
				Arrays.asList(searchKeywords.getText().split("\\s")).forEach(keyword -> sb.append(
						String.format(" `title` LIKE '%%%s%%' OR `content` LIKE '%%%s%%' OR", keyword, keyword)));
				query = sb.toString().trim().replaceAll(" OR$", ";");
			}

			if (!query.isEmpty()) {
				try {
					this.resultBeanList = new ArrayList<NewsBean>();
					Statement stmt = this.dbConnector.getConnection().createStatement();
					ResultSet rs = stmt.executeQuery(query);

					while (rs.next()) {
						this.resultBeanList.add(new NewsBean(rs.getInt("id"), rs.getString("url_guid"),
								rs.getString("date"), rs.getString("title"), rs.getString("content")));
					}

					rs.close();
					stmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			this.resultCount.setText(String.format("共 %d 筆", this.resultBeanList.size()));

			Collections.sort(this.resultBeanList);
			this.resultBeanList.forEach(bean -> {
				this.resultListModel.addElement(String.format("%s %s", bean.getDate(), bean.getTitle()));
			});
			this.resultList.setModel(resultListModel);

			this.button.setEnabled(true);
		};
	}

	public void doPaintShowContent() {
		if (resultList.getSelectedIndex() >= 0 && resultList.getSelectedIndex() < resultBeanList.size()) {
			NewsBean current = resultBeanList.get(resultList.getSelectedIndex());
			StringBuilder sb = new StringBuilder();

			sb.append(String.format("<html><head><style>p{font-size:%dpx;}</style></head>", fontSize.getValue()));
			sb.append(String.format("<body><h1>%s</h1><p>%s</p></body>", current.getTitle(), current.getContent()));
			sb.append("</html>");
			showContent.setText(sb.toString().trim());
		}
	}
}
