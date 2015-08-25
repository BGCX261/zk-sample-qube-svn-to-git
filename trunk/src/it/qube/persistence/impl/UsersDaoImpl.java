package it.qube.persistence.impl;

import it.mengoni.exception.LogicException;
import it.mengoni.persistence.dao.AbstractRelationDao;
import it.mengoni.persistence.dao.Field;
import it.mengoni.persistence.dao.FieldImpl;
import it.qube.persistence.dao.UsersDao;
import it.qube.persistence.dto.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.javatuples.Tuple;
import org.javatuples.Unit;

public class UsersDaoImpl extends AbstractRelationDao<Users> implements UsersDao {
	private static final List<Field<Users>> fields = new ArrayList<Field<Users>>();
	static {
		fields.add(new FieldImpl<Users>("USERNAME", true, false, 30, Types.VARCHAR) {
			@Override
			public void checkValue(Users bean) {
				checkValue(bean.getUsername());
			}

			@Override
			public void setValue(ResultSet rs, Users bean) throws SQLException {
				bean.setUsername((getStringValue(rs)));
			}

			@Override
			protected Object getValue(Users bean) {
				return bean.getUsername();
			}
		});
		fields.add(new FieldImpl<Users>("FIRSTNAME", false, false, 40, Types.VARCHAR) {
			@Override
			public void checkValue(Users bean) {
				checkValue(bean.getFirstname());
			}

			@Override
			public void setValue(ResultSet rs, Users bean) throws SQLException {
				bean.setFirstname(getStringValue(rs));
			}

			@Override
			protected Object getValue(Users bean) {
				return bean.getFirstname();
			}
		});
		fields.add(new FieldImpl<Users>("LASTNAME", false, false, 40, Types.VARCHAR) {
			@Override
			public void checkValue(Users bean) {
				checkValue(bean.getLastname());
			}

			@Override
			public void setValue(ResultSet rs, Users bean) throws SQLException {
				bean.setLastname(getStringValue(rs));
			}

			@Override
			protected Object getValue(Users bean) {
				return bean.getLastname();
			}
		});
		fields.add(new FieldImpl<Users>("HASHED_PASSWORD", false, false, 80, Types.VARCHAR) {
			@Override
			public void checkValue(Users bean) {
				checkValue(bean.getLogin().getHashedPassword());
			}

			@Override
			public void setValue(ResultSet rs, Users bean) throws SQLException {
				bean.getLogin().setHashedPassword(getStringValue(rs));
			}

			@Override
			protected Object getValue(Users bean) {
				return bean.getLogin().getHashedPassword();
			}
		});
		fields.add(new FieldImpl<Users>("EMAIL", false, true, 80, Types.VARCHAR) {
			@Override
			public void checkValue(Users bean) {
				checkValue(bean.getEmail());
			}

			@Override
			public void setValue(ResultSet rs, Users bean) throws SQLException {
				bean.setEmail((getStringValue(rs)));
			}

			@Override
			protected Object getValue(Users bean) {
				return bean.getEmail();
			}
		});
		fields.add(new FieldImpl<Users>("ROLE", false, true, 1, Types.VARCHAR) {
			@Override
			public void checkValue(Users bean) {
				checkValue(bean.getRole());
			}

			@Override
			public void setValue(ResultSet rs, Users bean) throws SQLException {
				bean.setRole((getStringValue(rs)));
			}

			@Override
			protected Object getValue(Users bean) {
				return bean.getRole();
			}
		});
		fields.add(new FieldImpl<Users>("SALT", false, false, 40, Types.VARCHAR) {
			@Override
			public void checkValue(Users bean) {
				checkValue(bean.getLogin().getSalt());
			}

			@Override
			public void setValue(ResultSet rs, Users bean) throws SQLException {
				bean.getLogin().setSalt(getStringValue(rs));
			}

			@Override
			protected Object getValue(Users bean) {
				return bean.getLogin().getSalt();
			}
		});
	}

	public UsersDaoImpl() {
		super("USERS", fields);
	}

	@Override
	public Users newIstance() {
		return new UsersImpl();
	}

	protected Tuple newKey(String username) {
		return new Unit<String>(username);
	}

	public Users getByPrimaryKey(String username) throws LogicException {
		return get(new Unit<String>(username));
	}

	@Override
	public Users getUserByEmail(String email) throws LogicException {
		List<Users> list = getByWhere("email = ?", email);
		if (list.size()==1)
			return list.get(0);
		return null;
	}

	@Override
	public void updateLogin(String username, String password)throws LogicException {
		Users user = getByPrimaryKey(username);
		if (user!=null){
			user.getLogin().setPassword(password);
			update(user);
		}
	}
}
