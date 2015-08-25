package it.qube.persistence.impl;

import it.mengoni.exception.LogicException;
import it.mengoni.persistence.dao.AbstractRelationDao;
import it.mengoni.persistence.dao.Field;
import it.mengoni.persistence.dao.FieldImpl;
import it.mengoni.persistence.dao.PkFieldImpl;
import it.qube.persistence.dao.GelatiDao;
import it.qube.persistence.dto.Gelati;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.javatuples.Tuple;
import org.javatuples.Unit;

public class GelatiDaoImpl extends AbstractRelationDao<Gelati> implements GelatiDao {
	private static final List<Field<Gelati>> fields = new ArrayList<Field<Gelati>>();
	static {
		fields.add(new PkFieldImpl<Gelati>("ID_GELATO", 10, Types.INTEGER) {
			@Override
			public void checkValue(Gelati bean) {
				checkValue(bean.getIdGelato());
			}

			@Override
			public void setValue(ResultSet rs, Gelati bean) throws SQLException {
				bean.setIdGelato((rs.getInt(getName())));
			}

			@Override
			protected Object getValue(Gelati bean) {
				return bean.getIdGelato();
			}
		});
		fields.add(new FieldImpl<Gelati>("NUMERO_TAVOLO", false, false, 10, Types.INTEGER) {
			@Override
			public void checkValue(Gelati bean) {
				checkValue(bean.getNumeroTavolo());
			}

			@Override
			public void setValue(ResultSet rs, Gelati bean) throws SQLException {
				bean.setNumeroTavolo((rs.getInt(getName())));
			}

			@Override
			protected Object getValue(Gelati bean) {
				return bean.getNumeroTavolo();
			}
		});
		fields.add(new FieldImpl<Gelati>("GUSTO_UNO", false, true, 10, Types.INTEGER) {
			@Override
			public void checkValue(Gelati bean) {
				checkValue(bean.getGustoUno());
			}

			@Override
			public void setValue(ResultSet rs, Gelati bean) throws SQLException {
				bean.setGustoUno((rs.getInt(getName())));
			}

			@Override
			protected Object getValue(Gelati bean) {
				return bean.getGustoUno();
			}
		});
		fields.add(new FieldImpl<Gelati>("GUSTO_DUE", false, true, 10, Types.INTEGER) {
			@Override
			public void checkValue(Gelati bean) {
				checkValue(bean.getGustoDue());
			}

			@Override
			public void setValue(ResultSet rs, Gelati bean) throws SQLException {
				bean.setGustoDue((rs.getInt(getName())));
			}

			@Override
			protected Object getValue(Gelati bean) {
				return bean.getGustoDue();
			}
		});
		fields.add(new FieldImpl<Gelati>("GUSTO_TRE", false, true, 10, Types.INTEGER) {
			@Override
			public void checkValue(Gelati bean) {
				checkValue(bean.getGustoTre());
			}

			@Override
			public void setValue(ResultSet rs, Gelati bean) throws SQLException {
				bean.setGustoTre((rs.getInt(getName())));
			}

			@Override
			protected Object getValue(Gelati bean) {
				return bean.getGustoTre();
			}
		});
		fields.add(new FieldImpl<Gelati>("PANNA", false, true, 1, Types.CHAR) {
			@Override
			public void checkValue(Gelati bean) {
				checkValue(bean.getPanna()?"T":"F");
			}

			@Override
			public void setValue(ResultSet rs, Gelati bean) throws SQLException {
				bean.setPanna(Boolean.valueOf("T".equalsIgnoreCase(getStringValue(rs))));
			}

			@Override
			protected Object getValue(Gelati bean) {
				return bean.getPanna()?"T":"F";
			}
		});
		fields.add(new FieldImpl<Gelati>("TAGLIA", false, false, 10, Types.INTEGER) {
			@Override
			public void checkValue(Gelati bean) {
				checkValue(bean.getTaglia());
			}

			@Override
			public void setValue(ResultSet rs, Gelati bean) throws SQLException {
				bean.setTaglia((rs.getInt(getName())));
			}

			@Override
			protected Object getValue(Gelati bean) {
				return bean.getTaglia();
			}
		});
		fields.add(new FieldImpl<Gelati>("PAGATO", false, true, 1, Types.CHAR) {
			@Override
			public void checkValue(Gelati bean) {
				checkValue(bean.getPagato()?"T":"F");
			}

			@Override
			public void setValue(ResultSet rs, Gelati bean) throws SQLException {
				bean.setPagato(Boolean.valueOf("T".equalsIgnoreCase(getStringValue(rs))));
			}

			@Override
			protected Object getValue(Gelati bean) {
				return bean.getPagato()?"T":"F";
			}
		});
		fields.add(new FieldImpl<Gelati>("FATTO", false, true, 1, Types.CHAR) {
			@Override
			public void checkValue(Gelati bean) {
				checkValue(bean.getFatto()?"T":"F");
			}

			@Override
			public void setValue(ResultSet rs, Gelati bean) throws SQLException {
				bean.setFatto(Boolean.valueOf("T".equalsIgnoreCase(getStringValue(rs))));
			}

			@Override
			protected Object getValue(Gelati bean) {
				return bean.getFatto()?"T":"F";
			}
		});
	}

	public GelatiDaoImpl() {
		super("GELATI", fields);
		setKeyGenerator(new FirebirdGenerator<Gelati>("id_gelato") {
			@Override
			protected void setInteger(Gelati bean, Integer x) {
				bean.setIdGelato(x);
			}
		});
	}

	@Override
	public Gelati newIstance() {
		return new GelatiImpl();
	}

	protected Tuple newKey(Integer idGelato) {
		return new Unit<Integer>(idGelato);
	}

	public Gelati getByPrimaryKey(Integer idGelato) throws LogicException {
		return get(new Unit<Integer>(idGelato));
	}

	@Override
	public List<Gelati> getByTavolo(Integer numeroTavolo) throws LogicException {
		return getByWhere("NUMERO_TAVOLO = ?", numeroTavolo);
	}

	@Override
	public List<Gelati> getByTavoloDaPagare(Integer numeroTavolo) throws LogicException {
		return getByWhere("NUMERO_TAVOLO = ? and PAGATO <> ? ", numeroTavolo, "T");
	}

	@Override
	public List<Gelati> getDaPagare() throws LogicException {
		return getByWhere("and PAGATO <> ? ", "T");
	}

	@Override
	public List<Gelati> getPagati() throws LogicException {
		return getByWhere("and PAGATO = ? ", "T");
	}

	@Override
	public List<Gelati> getDaFare() throws LogicException {
		return getByWhere("and FATTO <> ? ", "T");
	}

	@Override
	public List<Gelati> getFatti() throws LogicException {
		return getByWhere("and FATTO = ? ", "T");
	}

	@Override
	public List<Gelati> getByTavoloPagati(Integer numeroTavolo) throws LogicException {
		return getByWhere("NUMERO_TAVOLO = ? and PAGATO = ? ", numeroTavolo, "T");
	}

	@Override
	public List<Gelati> getByTavoloFatti(Integer numeroTavolo) throws LogicException {
		return getByWhere("NUMERO_TAVOLO = ? and FATTO = ?", numeroTavolo, "T");
	}

	@Override
	public List<Gelati> getByTavoloDaFare(Integer numeroTavolo) throws LogicException {
		return getByWhere("NUMERO_TAVOLO = ? and FATTO <> ? ", numeroTavolo, "T");
	}

	@Override
	public List<Gelati> getBy(Integer numeroTavolo, Boolean pagati,
			Boolean fatti) throws LogicException {
		StringBuilder where = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		if (numeroTavolo!=null){
			where.append("NUMERO_TAVOLO = ?");
			params.add(numeroTavolo);
		}
		if (pagati!=null){
			if (where.length()>0)
				where.append(" and ");
			if (pagati)
				where.append("PAGATO = ?");
			else
				where.append("PAGATO <> ?");
			params.add("T");
		}
		if (fatti!=null){
			if (where.length()>0)
				where.append(" and ");
			if (fatti)
				where.append("FATTO = ?");
			else
				where.append("FATTO <> ?");
			params.add("T");
		}
		return getByWhere(where.toString(), params.toArray());
	}
}