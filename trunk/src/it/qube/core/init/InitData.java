package it.qube.core.init;

import it.mengoni.exception.LogicException;
import it.mengoni.exception.SystemError;
import it.mengoni.persistence.dao.JdbcFactory;
import it.qube.persistence.DaoFactory;
import it.qube.persistence.dao.GustiDao;
import it.qube.persistence.dao.TagliaDao;
import it.qube.persistence.dto.Gusti;
import it.qube.persistence.dto.Taglia;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class InitData {


	private static TagliaDao dao;
	private static GustiDao daoG;

	public static void main(String[] args) throws Exception {
		String url = "jdbc:firebirdsql:localhost/3050:/lorenzo/zktest.fdb";
		String user = "sysdba";
		String password = "masterkey";
		ComboPooledDataSource ds = new com.mchange.v2.c3p0.ComboPooledDataSource();
		String driverClass = "org.firebirdsql.jdbc.FBDriver";
		ds.setDriverClass(driverClass);
		ds.setJdbcUrl(url);
		ds.setUser(user);
		ds.setPassword(password);
		JdbcFactory.getInstance().setDatasource(ds);
		dao = DaoFactory.getTagliaDao();

		//		create("Vaschetta piccola", new BigDecimal(4));
		//		create("Vaschetta media", new BigDecimal(6));
		//		create("Vaschetta grande", new BigDecimal(8));
		//
		//		create("Coppa piccola", new BigDecimal(1.5));
		//		create("Coppa media", new BigDecimal(2));
		//		create("Coppa grande", new BigDecimal(3.5));
		//
		//		create("Cono piccolo", new BigDecimal(1));
		//		create("Cono medio", new BigDecimal(1.5));
		//		create("Cono grande", new BigDecimal(2.5));

		daoG = DaoFactory.getGustiDao();

		String gr = "gelati/";

//		crateGusto("pera", "Pera sopraffina", true);
//		crateGusto("arancio", "Tarocco superbo", true);
//		crateGusto("limone", "Limone di Sorrento", true);
//		crateGusto("pesca", "Pesca Biologica", true);
//		crateGusto("mirtillo", "Mirtillo delle Alpi", true);
//		crateGusto("lampone", "Lampone", true);
//		crateGusto("fragola", "Fragola di sottobosco", true);
//		crateGusto("stracciatella", "Latte biologico e fondente Equador", true);
//		crateGusto("cioccolato", "fondente Equador", true);
//		crateGusto("gianduia", "Piemonteis :)", true);
//		crateGusto("pistacchio", "pistacchio di Bronte", true);
//		crateGusto("ciliegia", "Ciliegina ambilissima", true);
//		crateGusto("fichi", "", true);
//		crateGusto("susina", "", true);
//		crateGusto("mela", "", true);

//		caricaImmagine(getGusto("arancio"), gr + "arancia.jpg      ");
//		caricaImmagine(getGusto("cioccolato"), gr + "chocolate.png    ");
//		caricaImmagine(getGusto("ciliegia"), gr + "ciliegio.jpg     ");
//		caricaImmagine(getGusto("fichi"), gr + "fico.jpg         ");
//		caricaImmagine(getGusto("fragola       "), gr + "fragola.jpg      ");
//		caricaImmagine(getGusto("gianduia      "), gr + "gianduia.jpg     ");
//		caricaImmagine(getGusto("images        "), gr + "images.jpg       ");
//		caricaImmagine(getGusto("lampone       "), gr + "lampone.jpg      ");
//		caricaImmagine(getGusto("limone        "), gr + "limone.jpg       ");
//		caricaImmagine(getGusto("mela          "), gr + "melo.jpg         ");
//		caricaImmagine(getGusto("mirtillo      "), gr + "mirtillo.jpg     ");
//		caricaImmagine(getGusto("pera          "), gr + "pero.jpg         ");
//		caricaImmagine(getGusto("pesca         "), gr + "pesco.jpg        ");
//		caricaImmagine(getGusto("stracciatella "), gr + "stracciatella.jpg");
//		caricaImmagine(getGusto("susina        "), gr + "susino.jpg       ");
		caricaImmagine(getGusto("pistacchio    "), gr + "pistacchio.jpg         ");
	}

	private static void caricaImmagine(Gusti gusto, String imagepath) throws IOException, SystemError, LogicException {
		if (gusto==null)
			return;
		byte[] imageData = getBytesFromFile(new File(imagepath.trim()));
		gusto.setImage(imageData);
		daoG.update(gusto);

	}

	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int)length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "+file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}
	private static Gusti getGusto(String string) throws SystemError, LogicException {
		List<Gusti> list = daoG.getByWhere("Gusto = ? ", string.trim());
		if (list.size()>0)
			return list.get(0);
		return null;
	}

	public static void crateGusto(String string, String descrizione, boolean disponibile) throws SystemError, LogicException {
		List<Gusti> x = daoG.getByWhere("GUSTO = ? ", string);
		Gusti gusto;
		if (x.size()>0)
			gusto = x.get(0);
		else
		    gusto = daoG.newIstance();
		gusto.setGusto(string);
		gusto.setDisponibile(disponibile);
		gusto.setDescrizione(descrizione);
		if (gusto.isNew())
			daoG.insert(gusto);
		else
			daoG.update(gusto);

	}

	public static void create(String string, BigDecimal bigDecimal) throws SystemError, LogicException {
		Taglia vp = dao.newIstance();
		vp.setDescrizione(string);
		vp.setPrezzo(bigDecimal.doubleValue());
		dao.insert(vp);
	}

}
