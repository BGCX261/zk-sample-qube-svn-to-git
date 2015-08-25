package it.qube.controller;

import it.mengoni.exception.LogicException;
import it.qube.core.Utils;
import it.qube.persistence.DaoFactory;
import it.qube.persistence.dao.UsersDao;
import it.qube.persistence.dto.Users;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;


public class WinEditUsersComposer extends GenericForwardComposer implements UiConst{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(WinEditUsersComposer.class);

	private Listbox lbUsers;
	private Textbox username;
	private Combobox cbCategoria;
	private Textbox password1;
	private Textbox password2;
	private Textbox firstname;
	private Textbox lastname;
	private Textbox email;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Users lg = Utils.getInstance().getLoggedUser();
		if (lg!=null && lg.isAdmin()){
			refreshList();
			ListitemRenderer renderer = new UserListitemRenderer();
			lbUsers.setItemRenderer(renderer);
		}
	}

	private void refreshList() throws LogicException{
		UsersDao dao = DaoFactory.getUsersDao();
		List<Users> users = dao.getAll();
		ListModel model = new BindingListModelList(users, true);
		lbUsers.setModel(model);
	}

	private class DelEventListener implements EventListener{

		private Users user;

		public DelEventListener(Users user) {
			super();
			this.user = user;
		}

		@Override
		public void onEvent(Event event) throws Exception {
			String msg = String.format("Eliminare l'utente:%s %s ?",
					user.getFirstname(), user.getLastname());
			if (Utils.confirm(msg)){
				UsersDao dao = DaoFactory.getUsersDao();
				dao.delete(user);
				refreshList();
				resetValues();
				lbUsers.invalidate();
			}
		}
	}

	private class UserListitemRenderer implements ListitemRenderer{

		@Override
		public void render(Listitem listItem, Object data) throws Exception {
			if (data instanceof Users){
				Users user = (Users)data;
				Listcell cell1 = new Listcell(String.format("%s %s (%s)",
						user.getFirstname(), user.getLastname(), user.getRole()));
				cell1.setParent(listItem);
				Listcell cell3 = new Listcell();
				Image image = new Image();
				image.setSrc(IMG_DELETE_PNG);
				image.setStyle(CURSOR_POINTER);
				image.addEventListener(Events.ON_CLICK, new DelEventListener(user));
				image.setTooltiptext("Elimina " + user.getUsername());
				cell3.appendChild(image);
				cell3.setParent(listItem);
				listItem.setValue(user);
			}
		}
	}

	public void onSelect$lbUsers(Event event){
		Listitem listItem = lbUsers.getSelectedItem();
		Users user = (Users)listItem.getValue();
		cbCategoria.setValue(user.getRole());
		if (!user.isNew()){
			cbCategoria.setConstraint("no empty");
			username.setConstraint("no empty");
			lastname.setConstraint("no empty");
			firstname.setConstraint("no empty");
			email.setConstraint("no empty");
		} else {
			cbCategoria.setConstraint("");
			username.setConstraint("");
			lastname.setConstraint("");
			firstname.setConstraint("");
			email.setConstraint("");
		}
		username.setValue(user.getUsername());
		lastname.setValue(user.getLastname());
		firstname.setValue(user.getFirstname());
		email.setValue(user.getEmail());
	}

	public void onClick$btnAddUser(Event event){
		UsersDao dao = DaoFactory.getUsersDao();
		Users user = dao.newIstance();
		user.setUsername("senza nome");
		BindingListModelList model = (BindingListModelList) lbUsers.getModel();
		model.add(user);
		lbUsers.invalidate();
	}

	public void onClick$btnSalva(){
		try {
			Listitem listItem = lbUsers.getSelectedItem();
			if (listItem==null)
				return;
			Users user = (Users)listItem.getValue();
			if (user==null)
				return;
			user.setUsername(username.getValue());
			user.setRole(cbCategoria.getValue());
			if (toChangeLogin())
				user.getLogin().setPassword(password1.getValue().trim());
			UsersDao dao = DaoFactory.getUsersDao();
			if (!user.isNew()){
				dao.update(user);
			}else{
				dao.insert(user);
			}
			refreshList();
			resetValues();
		} catch (LogicException e) {
			logger.error("Error", e);
			alert(e.getMessage());
		}
	}

	private void resetValues() {
		cbCategoria.setConstraint("");
		username.setConstraint("");
		lastname.setConstraint("");
		firstname.setConstraint("");
		email.setConstraint("");

		cbCategoria.setValue(null);
		password1.setValue("");
		password2.setValue("");

		username.setValue("");
		lastname.setValue("");
		firstname.setValue("");
		email.setValue("");
	}

	private boolean toChangeLogin(){
		if (password1.getText()==null || password1.getText().trim().isEmpty())
			return false;
		if (password2.getText()==null || password2.getText().trim().isEmpty())
			return false;
		return password1.getText().trim().equals(password2.getText().trim());
	}

	public void onClick$btnChiudi(){
		self.detach();
	}

}