package it.qube.controller;

import it.mengoni.exception.LogicException;
import it.qube.core.ApplicationError;
import it.qube.core.Utils;
import it.qube.persistence.DaoFactory;
import it.qube.persistence.dao.UsersDao;
import it.qube.persistence.dto.Users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;


public class ChangeLoginComposer extends GenericForwardComposer {

	private static final Logger logger = LoggerFactory.getLogger(ChangeLoginComposer.class);

	private static final long serialVersionUID = 1L;

	//auto-wired component
	private Button btnChangelogin;
	private Textbox password;
	private Textbox newPassword1;
	private Textbox newPassword2;


	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	public void onChange$newPassword1(){
		check();
	}

	public void onChange$newPassword2(){
		check();
	}

	public void onChange$password(){
		check();
	}

	private void check(){
		try{
			Users user = Utils.getInstance().getLoggedUser();
			btnChangelogin.setDisabled(!(user.getLogin().match(password.getText())));
		}catch (Exception e) {
			btnChangelogin.setDisabled(true);
		}
	}

	public void onClick$btnChangelogin() throws WrongValueException {
		try {
			Users user = Utils.getInstance().getLoggedUser();
			if (user.getLogin().match(password.getText())){
				if (newPassword1.getText().equals(newPassword2.getText())){
					String username = user.getUsername();
					UsersDao dao = DaoFactory.getUsersDao();
					dao.updateLogin(username,newPassword1.getText());
					alert("Login modificato");
					self.detach();
				} else {
					alert("Le password non coincidono");
					return;
				}
			} else {
				alert("La password attuali non coincidono");
				return;
			}
		} catch (ApplicationError e) {
			alert(e.getMessage());
		} catch (LogicException e) {
			logger.error("Error", e);
			alert(""+ (e.getMessage()==null?e:e.getMessage()));
		}
	}

	public void onOK(){
		onClick$btnChangelogin();
	}

}