package it.qube.controller;

import it.mengoni.exception.LogicException;
import it.qube.core.ApplicationError;
import it.qube.core.Utils;
import it.qube.persistence.DaoFactory;
import it.qube.persistence.dao.ResetLoginDao;
import it.qube.persistence.dao.UsersDao;
import it.qube.persistence.dto.Users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;


public class ResetLoginComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ResetLoginComposer.class);

	private Label usernameLabel;
	private Button btnChangelogin;
	private Textbox newPassword1;
	private Textbox newPassword2;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		String id = execution.getParameter("id");
		Users user = Utils.getInstance().canResetLogin(id);
		boolean canReset = user!=null;
		if (canReset){
			usernameLabel.setValue(user.getUsername());
			desktop.setAttribute("resetUser", user);
			desktop.setAttribute("resetId", id);
		} else{
			desktop.removeAttribute("resetUser");
			desktop.removeAttribute("resetId");
		}
		btnChangelogin.setDisabled(!canReset);
	}

	public void onClick$btnChangelogin() {
		try {
			Users user = (Users) desktop.getAttribute("resetUser");
			if (user!=null){
				if (newPassword1.getText()!=null
						&& !newPassword1.getText().trim().isEmpty()
						&& newPassword1.getText().equals(newPassword2.getText())){
					String username = user.getUsername();
					UsersDao dao = DaoFactory.getUsersDao();
					dao.updateLogin(username, newPassword1.getText());
					removeLogin();
					alert("Login modificato");
					self.detach();
					execution.sendRedirect("/");
				} else {
					alert("Le password non coincidono");
					return;
				}
			} else {
				alert("La modifica non è autorizzata");
				self.detach();
				return;
			}
		} catch (ApplicationError e) {
			alert(e.getMessage());
		} catch (LogicException e) {
			alert(e.getMessage());
		}
	}

	private void removeLogin() {
		try {
			ResetLoginDao rdao = DaoFactory.getResetLoginDao();
			String id = (String) desktop.getAttribute("resetId");
			rdao.delete(rdao.getByPrimaryKey(id));
		} catch (LogicException e) {
			logger.error("Error", e);
		}
	}

}