package it.qube.core;


import it.mengoni.persistence.dao.JdbcFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.WebAppInit;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SampleZkWebAppListener implements WebAppInit {

	private static final Logger logger = LoggerFactory.getLogger(SampleZkWebAppListener.class);

	private static final String filename = "appConfig.xml";

	private static Config config;

	public static Config getConfig() {
		return config;
	}

	@Override
	public void init(WebApp wapp) throws Exception {
		ServletContext context =  (ServletContext) wapp.getNativeContext();
		String path = context.getRealPath("WEB-INF/"+filename);
		config = loadConfig(path);
		if (config!=null)
			Utils.getInstance().setMailConfig(config.getMailConfig());
		JdbcFactory.getInstance().setDatasource(Utils.getInstance().getDataSource());
	}

	private Config loadConfig(String path) {
		try {
	        XStream xStream = getXStream();
	        InputStream input = new FileInputStream(new File(path)); //SampleZkWebAppListener.class.getClassLoader().getResourceAsStream(filename);
			return (Config) xStream.fromXML(input);
		} catch (Throwable e) {
			logger.error("Errore in lettura nel classpath della configurazione dal file:"+path+", errore:" + e, e);
			return null;
		}
	}

	private static XStream getXStream(){
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("config", Config.class);
		return xStream;
	}

	public static void main(String[] args) {
		Config config = new Config();
		XStream xStream = getXStream();
        System.out.println(xStream.toXML(config));
	}

}
