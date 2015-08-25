package it.qube.controller;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Window;

public class GenericController extends GenericForwardComposer {

	private static final long serialVersionUID = -3535584445883330788L;	
	protected DecimalFormat twoDForm = new DecimalFormat("#.##");
	protected SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	protected static final String ELENCOGELATI = "elencogelati";
	
	protected String style="border: 0.5px solid red";
	
	

	public void detachEffect(Window win){
		Clients.evalJavaScript("jq('$" + win.getId() + "').show().fadeOut(1000)");
	}

}
