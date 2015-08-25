package it.qube.controller;

import it.mengoni.exception.LogicException;
import it.mengoni.exception.SystemError;
import it.qube.persistence.DaoFactory;
import it.qube.persistence.dao.GelatiDao;
import it.qube.persistence.dto.Gelati;
import it.qube.persistence.dto.Gusti;
import it.qube.persistence.dto.Taglia;
import it.qube.renderer.BancoGelatiRenderer;
import it.qube.renderer.ComboTagliaRenderer;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;
import org.zkoss.zul.api.Checkbox;
import org.zkoss.zul.api.Combobox;
import org.zkoss.zul.api.Intbox;

public class ContenutoBancoController extends GenericController {

	/**
	 *
	 */
	private static final long serialVersionUID = 611138954254029396L;
	private Grid bancoGelatiGrid;

	private Intbox numeroTavolo;
	//	private Combobox gustouno;
	//	private Combobox gustodue;
	//	private Combobox gustotre;
	private Combobox tagliaCono;
	private Checkbox panna;
	private Radio raDaFare;
	private Caption captionLista;
	private Radio raPagati;
	private Radio raDaPagare;
	private Radio raTutti;
	private Groupbox gustiSourceWin;
	private Groupbox gustiDestWin;
	private EventListener dropGustoListener  = new DropGustoListener();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		raTutti.setChecked(true);

		List<Gelati> gelati = DaoFactory.getGelatiDao().getAll();
		ListModel listModel = new ListModelList(gelati);
		bancoGelatiGrid.setRowRenderer(new BancoGelatiRenderer());
		bancoGelatiGrid.setModel(listModel);

		gustiSourceWin.addEventListener(Events.ON_DROP, dropGustoListener);
		gustiDestWin.addEventListener(Events.ON_DROP, dropGustoListener);
		Collection taglie = DaoFactory.getTagliaDao().getAll();
		ListModel tagliaModel = new ListModelList(taglie);
		tagliaCono.setItemRenderer(new ComboTagliaRenderer());
		tagliaCono.setModel(tagliaModel);

		numeroTavolo.addEventListener(Events.ON_OK, new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				onCheck$rgListaGelati();
			}
		});

		reloadGusti();
	}

	private void reloadGusti() throws LogicException, IOException{
		List<Gusti> gusti = DaoFactory.getGustiDao().getDisponibili();
		gustiDestWin.getChildren().clear();
		gustiSourceWin.getChildren().clear();
		for (Gusti gusto:gusti){
			GustoWindow win = new GustoWindow();
			win.setGusto(gusto);
			win.setStyle("display:inline-block; cursor:pointer;" + win.getStyle());
			win.setDroppable("true");
			win.setDraggable("true");
			win.setWidth("60px");
			win.setHeight("60px");
			win.setBorder("normal");
			win.addEventListener(Events.ON_DROP, dropGustoListener);
			gustiSourceWin.appendChild(win);
		}

	}

	private class DropGustoListener implements EventListener{

		@Override
		public void onEvent(Event evt) throws Exception {
			DropEvent event = (DropEvent) evt;
			Component win = event.getDragged();
			if (win instanceof GustoWindow){
				Component target = event.getTarget();
				if ((target==gustiDestWin && gustiDestWin.getChildren().size()<3) || target==gustiSourceWin){
					Component po = win.getParent();
					po.removeChild(win);
					win.setParent(target);
				}
			}
		}

	}

	public void onClick$btnResetTavolo() throws Exception {
		numeroTavolo.setValue(null);
		onCheck$rgListaGelati();
	}

	public void onClick$aggiungiGelato(Event event) throws Exception {
		if (gustiDestWin.getChildren().size()<=0)
			return;
		GelatiDao dao = DaoFactory.getGelatiDao();
		Gelati g1 = dao.newIstance();
		int c=0;
		for (int i = 0; i < gustiDestWin.getChildren().size(); i++) {
			Object x = gustiDestWin.getChildren().get(i);
			if (x instanceof GustoWindow){
				GustoWindow win = (GustoWindow) gustiDestWin.getChildren().get(i);
				switch (c) {
				case 0:
					g1.setToGustiUno(win.getGusto());
					break;
				case 1:
					g1.setToGustiDue(win.getGusto());
					break;
				case 2:
					g1.setToGustiTre(win.getGusto());
					break;
				default:
					break;
				}
				c++;
			}
		}
		org.zkoss.zul.api.Comboitem taglia = tagliaCono.getSelectedItemApi();
		Taglia t = (Taglia) taglia.getValue();
		g1.setNumeroTavolo(numeroTavolo.getValue());
		g1.setPanna(panna.isChecked());
		g1.setToTaglia(t);
		dao.insert(g1);
		onCheck$rgListaGelati();
		resetInput();
	}

	public void openTable(Integer numeroTavolo) throws SuspendNotAllowedException, InterruptedException, SystemError, LogicException {
		this.numeroTavolo.setValue(numeroTavolo);
		onCheck$rgListaGelati();
	}

	public void checkoutTable(Integer numeroTavolo) throws SuspendNotAllowedException, InterruptedException {
		Window window = (Window) Executions.createComponents("/application/cassa/popUpTotali.zul", null, null);
		window.setClosable(true);
		window.setMaximizable(false);
		org.zkoss.zk.ui.metainfo.ZScript zscript = new org.zkoss.zk.ui.metainfo.ZScript(null, "java", "popupTotali$composer.calcola(" + numeroTavolo + ")", null);
		org.zkoss.zk.ui.metainfo.EventHandler eventHandler = new org.zkoss.zk.ui.metainfo.EventHandler(zscript, null);
		window.addEventHandler(Events.ON_CREATE, eventHandler);
		window.doModal();
	}

	public void onClick$checkoutTable() throws WrongValueException, SuspendNotAllowedException, InterruptedException {
		checkoutTable(numeroTavolo.getValue());
	}

	private void resetInput() throws LogicException, IOException {
		reloadGusti();
		tagliaCono.setValue("");
		panna.setChecked(false);
	}

	public void onCheck$rgListaGelati() throws SystemError, LogicException {
		List<Gelati> gelati;
		Integer nt = numeroTavolo.getValue();
		String tavolo;
		if (nt != null)
			tavolo = " del tavolo:" + nt + " ";
		else
			tavolo = " ";
		if (raDaFare.isChecked()) {
			captionLista.setLabel("Gelati" + tavolo + raDaFare.getLabel());
			gelati = DaoFactory.getGelatiDao().getBy(nt, null, false);
		} else if (raDaPagare.isChecked()) {
			captionLista.setLabel("Gelati " + tavolo + raPagati.getLabel());
			gelati = DaoFactory.getGelatiDao().getBy(nt, false, null);
		} else if (raPagati.isChecked()) {
			captionLista.setLabel("Gelati " + tavolo + raPagati.getLabel());
			gelati = DaoFactory.getGelatiDao().getBy(nt, true, null);
		} else {
			captionLista.setLabel("Gelati " + tavolo);
			gelati = DaoFactory.getGelatiDao().getBy(nt, null, null);
		}
		ListModel listModel = new ListModelList(gelati);
		bancoGelatiGrid.setRowRenderer(new BancoGelatiRenderer());
		bancoGelatiGrid.setModel(listModel);
	}

}