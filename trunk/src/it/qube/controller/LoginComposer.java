package it.qube.controller;

import it.mengoni.exception.LogicException;
import it.qube.core.ApplicationError;
import it.qube.core.AuthenticationFilter;
import it.qube.core.SendMailMessage;
import it.qube.core.Utils;
import it.qube.persistence.DaoFactory;
import it.qube.persistence.dao.ResetLoginDao;
import it.qube.persistence.dao.UsersDao;
import it.qube.persistence.dto.ResetLogin;
import it.qube.persistence.dto.Users;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;

public class LoginComposer extends GenericForwardComposer {

	private static final Logger logger = LoggerFactory.getLogger(LoginComposer.class);
	private static final long serialVersionUID = 1L;

	private Textbox username;
	private Textbox password;
	private Textbox sendToEmail;
	private Div divForgotPassword;
	private Div sendDiv;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		HttpServletRequest request = (HttpServletRequest) execution.getNativeRequest();
		String afterLoginPage = (String) request.getAttribute(AuthenticationFilter.AFTER_LOGIN_PAGE);
		execution.getDesktop().setAttribute(AuthenticationFilter.AFTER_LOGIN_PAGE, afterLoginPage);
		divForgotPassword.setVisible(Utils.getInstance().getMailConfig() != null);
		sendDiv.setVisible(false);
	}

	public void onClick$loginButton() {
		try {
			Utils.getInstance().login(username.getText(), password.getText());
			String afterLoginPage = (String) execution.getDesktop().getAttribute(AuthenticationFilter.AFTER_LOGIN_PAGE);
			if (afterLoginPage != null)
				execution.sendRedirect(afterLoginPage);
			else
				execution.sendRedirect("/");
		} catch (ApplicationError e) {
			alert(e.getMessage());
		} catch (Exception e) {
			logger.error("onClick$loginButton", e);
			alert("" + (e.getMessage() == null ? e : e.getMessage()));
		}
	}

	public void onOK() {
		onClick$loginButton();
	}

	public void onClick$btnSendPassword() {
		try {
			String email = getEmail();
			ResetLogin rl = createResetLogin(email);
			String subject = "Il link sottostante per la modifica del login sarà attivo fino alle " + rl.getExpire();
			String message = "Fate click sul link o copiatelo sulla barra degli indirizzi \n" + getUrl() + rl.getId();
			sendEmail(subject, message, email);
		} catch (ApplicationError e) {
			alert(e.getMessage());
		} catch (LogicException e) {
			logger.error("onClick$btnSendPassword", e);
			alert("Errore in recupero dati per invio email" + e.getMessage());
		}
	}

	public void onClick$btnSendUsername() {
		try {
			String email = getEmail();
			Users user = getUser(email);
			String subject = "Il vostro username per l'accesso";
			String message = "Questo è il vostro username:" + user.getUsername() + "\n";
			sendEmail(subject, message, email);
		} catch (ApplicationError e) {
			alert(e.getMessage());
		} catch (LogicException e) {
			logger.error("onClick$btnSendUsername", e);
			alert("Errore in recupero dati per invio email" + e.getMessage());
		}
	}

	private ResetLogin createResetLogin(String email) throws LogicException {
		Users user = getUser(email);
		ResetLoginDao rdao = DaoFactory.getResetLoginDao();
		ResetLogin result = rdao.newIstance();
		result.setUsername(user.getUsername());
		return result;
				//createNew(user.getUsername());
	}

	private String getEmail() {
		String email = sendToEmail.getValue();
		if (email == null || email.isEmpty()) {
			throw new ApplicationError("La email non è corretta");
		}
		return email;
	}

	private Users getUser(String email) throws LogicException {
		UsersDao dao = DaoFactory.getUsersDao();
		Users user = dao.getUserByEmail(email);
		if (user == null) {
			throw new ApplicationError("Impossibile inviare la email");
		}
		return user;
	}

	private void sendEmail(String subject, String message, String email) throws LogicException {
		SendMailMessage sm = new SendMailMessage(Utils.getInstance().getMailConfig());
		List<String> to = new ArrayList<String>();
		List<String> reply = new ArrayList<String>();
		to.add(email);
		reply.add("noreply@nodomanin_noway_nothing.com");
		sm.sendMail(Utils.getInstance().getMailConfig().getFrom(), to, null, null, reply, subject, message, null);
		alert("Email inviata a " + email);

	}

	private String getUrl() {
		HttpServletRequest request = (HttpServletRequest) execution.getNativeRequest();
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/loginreset.zul?id=";
	}

}