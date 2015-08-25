package it.qube.controller;

import it.qube.model.dto.UtenteDto;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Form;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Textbox;

public class LoginController extends GenericController {


	private static final long serialVersionUID = 6373634212850347853L;
	private static Logger LOG = Logger.getLogger(LoginController.class);
	private Textbox userTb;
	private Textbox pwdTb;
	private Form f;
	protected String style="border: 0.5px solid red";


	public void doAfterCompose(Component component) throws Exception{
		super.doAfterCompose(component);
		
		sessionScope.remove("user");
		
		/***************************************/
		userTb.setValue("admin");
		pwdTb.setValue("admin123");
//		login();
		/***************************************/
	}

	public void onClick$qubeHome(){
		Executions.sendRedirect("http://qube-srl.com/");
	}

	public void onOK(Event event){
		login();
	}

	public void onClick$entra(Event event){
		login();
	}

	public void onClick$userTb(){
		userTb.setStyle("border: medium none #FFFFFF");
	}

	public void onClick$pwdTb(){
		pwdTb.setStyle("border: medium none #FFFFFF");
	}

	private void login() {

		try{
			//Controllo Textbox
			if(userTb.getText().isEmpty() && pwdTb.getText().isEmpty())
				throw new Exception("ALL");			
			else if(userTb.getText().isEmpty())
				throw new Exception("USER");
			else if(pwdTb.getText().isEmpty())
				throw new Exception("PASS");


			if(!userTb.getText().equals("admin")){
				throw new Exception("USER");
			}else if  (!pwdTb.getText().equals("admin123")){
				throw new Exception("PASS");
			}else if(userTb.getText().equals("admin") && pwdTb.getText().equals("admin123")){
				UtenteDto utente = new UtenteDto();
				utente.setPassword(pwdTb.getText());
				utente.setUsername(userTb.getText());
				
				utente.setCognome("Mastro");
				utente.setNome("Gelataio");
				
				
				sessionScope.put("user",utente);

				Executions.sendRedirect("/application/index.zul");
			}else{
				throw new Exception("ALL");
			}

		}catch(Exception e){
			if(e.getMessage().equals("ALL")){
				userTb.setStyle(style);
				pwdTb.setValue("");
				pwdTb.setStyle(style);
			}else if(e.getMessage().equals("USER")){
				userTb.setStyle(style);
			}else if(e.getMessage().equals("PASS")){
				pwdTb.setValue("");
				pwdTb.setStyle(style);
			}else
				e.printStackTrace();
		}

	}

	public Textbox getUserTb() {
		return userTb;
	}

	public void setUserTb(Textbox userTb) {
		this.userTb = userTb;
	}

	public Textbox getPwdTb() {
		return pwdTb;
	}

	public void setPwdTb(Textbox pwdTb) {
		this.pwdTb = pwdTb;
	}
}
