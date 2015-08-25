package it.qube.core;


import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.util.ExecutionInit;


public class ApplicationExecutionInit implements ExecutionInit {

	@Override
	public void init(Execution exec, Execution parent) throws Exception {
		if (AuthenticationFilter.toExclude(exec.getDesktop().getRequestPath()))
			return;
		if (Utils.getInstance().getLoggedUser()==null){
			exec.getDesktop().setAttribute(AuthenticationFilter.AFTER_LOGIN_PAGE, exec.getDesktop().getRequestPath());
			exec.sendRedirect(AuthenticationFilter.LOGIN_PAGE);
		}
	}

}
