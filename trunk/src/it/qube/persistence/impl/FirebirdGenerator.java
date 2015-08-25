package it.qube.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.javatuples.Tuple;

import it.mengoni.exception.LogicException;
import it.mengoni.exception.SystemError;
import it.mengoni.persistence.dao.JdbcFactory;
import it.mengoni.persistence.dao.KeyGenerator;
import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.util.JdbcHelper;

public abstract class FirebirdGenerator<T extends PersistentObject> implements KeyGenerator<T> {

	private static final String selectGen = "SELECT GEN_ID( %s, 1 ) FROM RDB$DATABASE;";
	private final String generatorName;
	private final String sql;

	public FirebirdGenerator(String generatorName) {
		super();
		this.generatorName = generatorName;
		this.sql = String.format(selectGen,generatorName);
	}

	public String getGeneratorName() {
		return generatorName;
	}

	@Override
	public Tuple newKey(T bean, String[] keyNames) throws SystemError, LogicException {
		JdbcHelper jdbcHelper = JdbcFactory.getInstance().getHelper();
		try {
			Connection conn = jdbcHelper.getConnection();
			PreparedStatement stm = conn.prepareStatement(sql);
			ResultSet res = stm.executeQuery();
			try{
				if (res.next()){
					Integer x = res.getInt(1);
					setInteger(bean, x);
				}
				return bean.getKey();
			}finally{
				res.close();
				stm.close();
				conn.close();
			}
		} catch (SQLException e) {
			throw new SystemError( "Errore in esecuzione query:" + e, GelatiDaoImpl.class.getSimpleName(), "newKey",  e);
		}
	}

	protected abstract void setInteger(T bean, Integer x);

}
