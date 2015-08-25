package it.qube.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.util.DesktopCleanup;

public class DesktopListener implements DesktopCleanup {

	private static final Logger logger = LoggerFactory.getLogger(DesktopListener.class);

	@Override
	public void cleanup(Desktop desktop) throws Exception {
		logger.info("cleanup for" + desktop);
	}

}
