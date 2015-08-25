package it.qube.persistence.impl;

import it.mengoni.exception.LogicException;
import it.mengoni.persistence.dao.AbstractRelationDao;
import it.mengoni.persistence.dao.Field;
import it.mengoni.persistence.dao.FieldImpl;
import it.mengoni.persistence.dao.PkFieldImpl;
import it.qube.persistence.dao.GustiDao;
import it.qube.persistence.dto.Gusti;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.javatuples.Tuple;
import org.javatuples.Unit;

public class GustiDaoImpl extends AbstractRelationDao<Gusti> implements GustiDao {
	private static final List<Field<Gusti>> fields = new ArrayList<Field<Gusti>>();
	static {
		fields.add(new PkFieldImpl<Gusti>("ID_GUSTO", 10, Types.INTEGER) {
			@Override
			public void checkValue(Gusti bean) {
				checkValue(bean.getIdGusto());
			}

			@Override
			public void setValue(ResultSet rs, Gusti bean) throws SQLException {
				bean.setIdGusto((rs.getInt(getName())));
			}

			@Override
			protected Object getValue(Gusti bean) {
				return bean.getIdGusto();
			}
		});
		fields.add(new FieldImpl<Gusti>("GUSTO", false, false, 40, Types.VARCHAR) {
			@Override
			public void checkValue(Gusti bean) {
				checkValue(bean.getGusto());
			}

			@Override
			public void setValue(ResultSet rs, Gusti bean) throws SQLException {
				bean.setGusto((getStringValue(rs)));
			}

			@Override
			protected Object getValue(Gusti bean) {
				return bean.getGusto();
			}
		});
		fields.add(new FieldImpl<Gusti>("DESCRIZIONE", false, true, 80, Types.VARCHAR) {
			@Override
			public void checkValue(Gusti bean) {
				checkValue(bean.getDescrizione());
			}

			@Override
			public void setValue(ResultSet rs, Gusti bean) throws SQLException {
				bean.setDescrizione((getStringValue(rs)));
			}

			@Override
			protected Object getValue(Gusti bean) {
				return bean.getDescrizione();
			}
		});
		fields.add(new FieldImpl<Gusti>("DISPONIBILE", false, true, 1, Types.CHAR) {
			@Override
			public void checkValue(Gusti bean) {
				checkValue(bean.getDisponibile()?"T":"F");
			}

			@Override
			public void setValue(ResultSet rs, Gusti bean) throws SQLException {
				bean.setDisponibile("T".equalsIgnoreCase(getStringValue(rs)));
			}

			@Override
			protected Object getValue(Gusti bean) {
				return bean.getDisponibile()?"T":"F";
			}
		});
		fields.add(new FieldImpl<Gusti>("IMAGE", false, true, 0, Types.BLOB) {
			@Override
			public void checkValue(Gusti bean) {
				checkValue(bean.getImage());
			}

			@Override
			public void setValue(ResultSet rs, Gusti bean) throws SQLException {
				bean.setImage((rs.getBytes(getName())));
			}

			@Override
			protected Object getValue(Gusti bean) {
				return bean.getImage();
			}
		});
	}

	public GustiDaoImpl() {
		super("GUSTI", fields);
		setKeyGenerator(new FirebirdGenerator<Gusti>("id_gusto") {
			@Override
			protected void setInteger(Gusti bean, Integer x) {
				bean.setIdGusto(x);
			}
		});
	}

	@Override
	public Gusti newIstance() {
		return new GustiImpl();
	}

	protected Tuple newKey(Integer idGusto) {
		return new Unit<Integer>(idGusto);
	}

	public Gusti getByPrimaryKey(Integer idGusto) throws LogicException {
		return get(new Unit<Integer>(idGusto));
	}

	@Override
	public List<Gusti> getDisponibili() throws LogicException {
		return getByWhere("DISPONIBILE = ?", "T");
	}
}
