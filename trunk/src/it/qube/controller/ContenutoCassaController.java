package it.qube.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Window;


public class ContenutoCassaController extends GenericController {

	/**
	 *
	 */
	private static final long serialVersionUID = 611138954254029396L;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	public void openTable(Integer numeroTavolo) throws SuspendNotAllowedException, InterruptedException{
		System.out.println("tavolo->"+numeroTavolo);
		Window window = (Window)Executions.createComponents("cassa/popUpTotali.zul",null,null );
		window.setClosable(true);
		window.setMaximizable(false);
		org.zkoss.zk.ui.metainfo.ZScript zscript = new org.zkoss.zk.ui.metainfo.ZScript(null, "java",
				"popupTotali$composer.calcola(" + numeroTavolo + ")", null);
		org.zkoss.zk.ui.metainfo.EventHandler eventHandler = new org.zkoss.zk.ui.metainfo.EventHandler( zscript, null);
		window.addEventHandler(Events.ON_CREATE, eventHandler);
		window.doModal();

	}
}
