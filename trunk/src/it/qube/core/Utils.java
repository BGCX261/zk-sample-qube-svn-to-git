package it.qube.core;

import it.mengoni.exception.LogicException;
import it.qube.persistence.DaoFactory;
import it.qube.persistence.dao.ResetLoginDao;
import it.qube.persistence.dao.UsersDao;
import it.qube.persistence.dto.ResetLogin;
import it.qube.persistence.dto.Users;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.codec.binary.Base64;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;


public class Utils {

	private static final String JDBC_ZK_TEST = "jdbc/zkTest";

//	private static final Logger logger = LoggerFactory.getLogger(Utils.class);


	private static Utils instance = new Utils();

	public static String encodeBase64(byte[] data) {
		return new String(Base64.encodeBase64(data));
	}

	public static byte[] decodeBase64(String data) {
		return Base64.decodeBase64(data.getBytes());
	}

	/**
	 * in zk.xml aggiungere <system-config>
	 * <disable-event-thread>false</disable-event-thread> </system-config>
	 * altrimenti non funziona!
	 *
	 * @param message
	 * @return true se è stato premuto SI
	 */
	public static boolean confirm(String message) {
		int result = Messagebox.NO;
		try {
			result = Messagebox.show(message, "Conferma", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION);
		} catch (InterruptedException e) {
		}
		return result == Messagebox.YES;
	}

	public static Utils getInstance() {
		return instance;
	}

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	private MailConfig mailConfig;

	private Utils() {
		super();
	}


	public String dateFormat(Date date) {
		if (date == null)
			return "";
		return dateFormat.format(date);
	}

	private DataSource dataSource = null;

	public DataSource getDataSource() {
		if (dataSource == null) {
			try {
				Context initContext = new InitialContext();
				dataSource = (DataSource)initContext.lookup("java:/comp/env/"+JDBC_ZK_TEST);
			} catch (NamingException e) {
				throw new ApplicationError("impossibile ottenere il datasource " + JDBC_ZK_TEST + ": " + e.getMessage(), e);
			}
		}
		return dataSource;
	}

	public Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

	public void login(String username, String password) {
		if (Executions.getCurrent() == null)
			throw new ApplicationError("questo metodo va usato solo durante l'esecuzione di una request");
		try {
			UsersDao dao = DaoFactory.getUsersDao();
			Users user = dao.getByPrimaryKey(username);
			if (user == null || !user.getLogin().match(password))
				throw new IllegalArgumentException("Login failed");
			Executions.getCurrent().getSession().setAttribute(AuthenticationFilter.LOGGED_USER, user);
		} catch (LogicException e) {
			throw new ApplicationError("Login error", e);
		}
	}

	public boolean checkLogin(String username, String password) {
		if (Executions.getCurrent() == null)
			throw new ApplicationError("questo metodo va usato solo durante l'esecuzione di una request");
		try {
			UsersDao dao = DaoFactory.getUsersDao();
			Users user = dao.getByPrimaryKey(username);
			return !(user == null || !user.getLogin().match(password));
		} catch (LogicException e) {
			throw new ApplicationError("Login error", e);
		}
	}

	public void changeLogin(String username, String password) {
		if (Executions.getCurrent() == null)
			throw new ApplicationError("questo metodo va usato solo durante l'esecuzione di una request");
		try {
			UsersDao dao = DaoFactory.getUsersDao();
			Users user = dao.getByPrimaryKey(username);
			if (user == null)
				throw new ApplicationError("Utente '" + username + "' non trovato");
			user.getLogin().setPassword(password);
			dao.update(user);
		} catch (LogicException e) {
			throw new ApplicationError("Login error", e);
		}
	}

	public void logout() {
		if (Executions.getCurrent() == null)
			throw new ApplicationError("questo metodo va usato solo durante l'esecuzione di una request");
		Executions.getCurrent().getSession().removeAttribute(AuthenticationFilter.LOGGED_USER);
	}

	public Users getLoggedUser() {
		if (Executions.getCurrent() == null)
			throw new ApplicationError("questo metodo va usato solo durante l'esecuzione di una request");
		return (Users) Executions.getCurrent().getSession().getAttribute(AuthenticationFilter.LOGGED_USER);
	}

	public Users canResetLogin(String id){
		ResetLoginDao dao = DaoFactory.getResetLoginDao();
		try{
			ResetLogin reset = dao.getByPrimaryKey(id);
			if (reset!=null && reset.isExpired()){
				UsersDao udao = DaoFactory.getUsersDao();
				return udao.getByPrimaryKey(reset.getUsername());
			}
			return null;
		} catch (LogicException e) {
			throw new ApplicationError("Login error", e);
		}
	}

	public MailConfig getMailConfig() {
		return mailConfig;
	}

	public void setMailConfig(MailConfig mailConfig) {
		this.mailConfig = mailConfig;
	}


}