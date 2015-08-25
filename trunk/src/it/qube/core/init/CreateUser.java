package it.qube.core.init;

import it.mengoni.exception.LogicException;
import it.mengoni.exception.SystemError;
import it.mengoni.persistence.dao.JdbcFactory;
import it.qube.persistence.DaoFactory;
import it.qube.persistence.dao.UsersDao;
import it.qube.persistence.dto.Users;

import java.beans.PropertyVetoException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class CreateUser {

	public static void main(String[] args) throws SystemError, LogicException, PropertyVetoException {
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
		UsersDao dao = DaoFactory.getUsersDao();
		Users u = dao.newIstance();
		u.setUsername("lorenzo");
		u.setLastname("Mengoni");
		u.setFirstname("Lorenzo");
		u.setEmail("zernolo@gmail.com");
		u.setRole("A");
		u.getLogin().setPassword("xxxx");
		dao.insert(u);
		System.out.println(u);
		System.out.println(u.isNew());
	}

}
