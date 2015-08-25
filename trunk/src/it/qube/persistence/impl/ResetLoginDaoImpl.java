package it.qube.persistence.impl;

import it.mengoni.exception.LogicException;
import it.mengoni.persistence.dao.AbstractRelationDao;
import it.mengoni.persistence.dao.Field;
import it.mengoni.persistence.dao.FieldImpl;
import it.mengoni.persistence.dao.PkFieldImpl;
import it.qube.persistence.dao.ResetLoginDao;
import it.qube.persistence.dto.ResetLogin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.javatuples.Tuple;
import org.javatuples.Unit;

public class ResetLoginDaoImpl extends AbstractRelationDao<ResetLogin> implements ResetLoginDao {
	private static final List<Field<ResetLogin>> fields = new ArrayList<Field<ResetLogin>>();
	static {
		fields.add(new PkFieldImpl<ResetLogin>("ID", 40, Types.VARCHAR) {
			@Override
			public void checkValue(ResetLogin bean) {
				checkValue(bean.getId());
			}

			@Override
			public void setValue(ResultSet rs, ResetLogin bean) throws SQLException {
				bean.setId((getStringValue(rs)));
			}

			@Override
			protected Object getValue(ResetLogin bean) {
				return bean.getId();
			}
		});
		fields.add(new FieldImpl<ResetLogin>("USERNAME", false, false, 30, Types.VARCHAR) {
			@Override
			public void checkValue(ResetLogin bean) {
				checkValue(bean.getUsername());
			}

			@Override
			public void setValue(ResultSet rs, ResetLogin bean) throws SQLException {
				bean.setUsername((getStringValue(rs)));
			}

			@Override
			protected Object getValue(ResetLogin bean) {
				return bean.getUsername();
			}
		});
		fields.add(new FieldImpl<ResetLogin>("EXPIRE", false, false, 19, Types.TIMESTAMP) {
			@Override
			public void checkValue(ResetLogin bean) {
				checkValue(bean.getExpire());
			}

			@Override
			public void setValue(ResultSet rs, ResetLogin bean) throws SQLException {
				bean.setExpire((rs.getTimestamp(getName())));
			}

			@Override
			protected Object getValue(ResetLogin bean) {
				return bean.getExpire();
			}
		});
	}

	public ResetLoginDaoImpl() {
		super("RESET_LOGIN", fields);
	}

	@Override
	public ResetLogin newIstance() {
		return new ResetLoginImpl();
	}

	protected Tuple newKey(String id) {
		return new Unit<String>(id);
	}

	public ResetLogin getByPrimaryKey(String id) throws LogicException {
		return get(new Unit<String>(id));
	}
}
