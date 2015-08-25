package it.qube.persistence.impl;

import it.mengoni.exception.LogicException;
import it.mengoni.persistence.dao.AbstractRelationDao;
import it.mengoni.persistence.dao.Field;
import it.mengoni.persistence.dao.FieldImpl;
import it.mengoni.persistence.dao.PkFieldImpl;
import it.qube.persistence.dao.TagliaDao;
import it.qube.persistence.dto.Taglia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.javatuples.Tuple;
import org.javatuples.Unit;

public class TagliaDaoImpl extends AbstractRelationDao<Taglia> implements TagliaDao {
	private static final List<Field<Taglia>> fields = new ArrayList<Field<Taglia>>();
	static {
		fields.add(new PkFieldImpl<Taglia>("ID_TAGLIA", 10, Types.INTEGER) {
			@Override
			public void checkValue(Taglia bean) {
				checkValue(bean.getIdTaglia());
			}

			@Override
			public void setValue(ResultSet rs, Taglia bean) throws SQLException {
				bean.setIdTaglia((rs.getInt(getName())));
			}

			@Override
			protected Object getValue(Taglia bean) {
				return bean.getIdTaglia();
			}
		});
		fields.add(new FieldImpl<Taglia>("DESRIZIONE", false, false, 20, Types.VARCHAR) {
			@Override
			public void checkValue(Taglia bean) {
				checkValue(bean.getDescrizione());
			}

			@Override
			public void setValue(ResultSet rs, Taglia bean) throws SQLException {
				bean.setDescrizione((getStringValue(rs)));
			}

			@Override
			protected Object getValue(Taglia bean) {
				return bean.getDescrizione();
			}
		});
		fields.add(new FieldImpl<Taglia>("PREZZO", false, false, 15, Types.DOUBLE) {
			@Override
			public void checkValue(Taglia bean) {
				checkValue(bean.getPrezzo());
			}

			@Override
			public void setValue(ResultSet rs, Taglia bean) throws SQLException {
				bean.setPrezzo((rs.getDouble(getName())));
			}

			@Override
			protected Object getValue(Taglia bean) {
				return bean.getPrezzo();
			}
		});
	}

	public TagliaDaoImpl() {
		super("TAGLIA", fields);
		setKeyGenerator(new FirebirdGenerator<Taglia>("id_taglia") {
			@Override
			protected void setInteger(Taglia bean, Integer x) {
				bean.setIdTaglia(x);
			}
		});
	}

	@Override
	public Taglia newIstance() {
		return new TagliaImpl();
	}

	protected Tuple newKey(Integer idTaglia) {
		return new Unit<Integer>(idTaglia);
	}

	public Taglia getByPrimaryKey(Integer idTaglia) throws LogicException {
		return get(new Unit<Integer>(idTaglia));
	}
}
