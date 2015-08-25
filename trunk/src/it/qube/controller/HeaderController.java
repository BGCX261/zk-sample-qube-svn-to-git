package it.qube.controller;


import it.qube.core.Utils;
import it.qube.persistence.dto.Users;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Window;


public class HeaderController extends GenericForwardComposer {

	private static final long serialVersionUID = -5726463665358725571L;

	private Users utenteLoggato;
	private Menubar menubar;
	private Label userInfo;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		utenteLoggato = Utils.getInstance().getLoggedUser();
		if (utenteLoggato!=null){
			creaMenu();
			Calendar c= Calendar.getInstance();
			SimpleDateFormat format=new SimpleDateFormat("EE dd MMM yyyy");
			userInfo.setValue("User: "+ utenteLoggato.getFirstname() + " " +
			utenteLoggato.getLastname() +"                        " + "|" +
			"                   "+format.format(c.getTime()));
		}

	}

	public void creaMenu(){
		menubar.setAutodrop(true);

		Menuitem menuitem = new Menuitem("Banco");
		org.zkoss.zk.ui.metainfo.ZScript zscript = new org.zkoss.zk.ui.metainfo.ZScript(null, "java",
				"winHeader$composer.open(\""+  "banco/contenuto.zul" + "\",\""+ "1" +"\")", null);
		org.zkoss.zk.ui.metainfo.EventHandler eventHandler = new org.zkoss.zk.ui.metainfo.EventHandler( zscript, null);
		menuitem.addEventHandler(Events.ON_CLICK, eventHandler);
		menubar.appendChild(menuitem);


		Menuitem menuitem2 = new Menuitem("Cassa");
		org.zkoss.zk.ui.metainfo.ZScript zscript2 = new org.zkoss.zk.ui.metainfo.ZScript(null, "java",
				"winHeader$composer.open(\""+  "cassa/contenuto.zul" + "\",\""+ "1" +"\")", null);
		org.zkoss.zk.ui.metainfo.EventHandler eventHandler2 = new org.zkoss.zk.ui.metainfo.EventHandler( zscript2, null);
		menuitem2.addEventHandler(Events.ON_CLICK, eventHandler2);
		menubar.appendChild(menuitem2);

		Menuitem menuitem3 = new Menuitem("Statistiche");
		org.zkoss.zk.ui.metainfo.ZScript zscript3 = new org.zkoss.zk.ui.metainfo.ZScript(null, "java",
				"winHeader$composer.open(\""+  "statistiche/contenuto.zul" + "\",\""+ "3" +"\")", null);
		org.zkoss.zk.ui.metainfo.EventHandler eventHandler3 = new org.zkoss.zk.ui.metainfo.EventHandler( zscript3, null);
		menuitem3.addEventHandler(Events.ON_CLICK, eventHandler3);
		menubar.appendChild(menuitem3);
	}

	public void open(String url, String tipoApertura) throws SuspendNotAllowedException, InterruptedException{
		try{
			Integer apertura = Integer.parseInt(tipoApertura);

			switch (apertura) {
			case 1:
				Include center=(Include) Path.getComponent("//pageIndex/winIndex/center");
				center.setSrc(null);
				center.setSrc(url);

				break;
			case 2:
				execution.sendRedirect(url);
				break;
			case 3:
				Window window = (Window)Executions.getCurrent().createComponents(url,null,null );
				window.setClosable(true);
				window.setMaximizable(false);
				window.doModal();
				break;
			default:
				break;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}



}
