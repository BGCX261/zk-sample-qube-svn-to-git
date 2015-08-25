package it.qube.persistence;
import it.qube.persistence.impl.TagliaDaoImpl;
import it.qube.persistence.dao.TagliaDao;
import it.qube.persistence.impl.GelatiDaoImpl;
import it.qube.persistence.impl.UsersDaoImpl;
import it.qube.persistence.dao.GelatiDao;
import it.qube.persistence.impl.GustiDaoImpl;
import it.qube.persistence.dao.ResetLoginDao;
import it.qube.persistence.impl.ResetLoginDaoImpl;
import it.qube.persistence.dao.UsersDao;
import it.qube.persistence.dao.GustiDao;
public class DaoFactory{
    public static GelatiDao getGelatiDao()    {
        return new GelatiDaoImpl();
    }

    public static GustiDao getGustiDao()    {
        return new GustiDaoImpl();
    }

    public static ResetLoginDao getResetLoginDao()    {
        return new ResetLoginDaoImpl();
    }

    public static TagliaDao getTagliaDao()    {
        return new TagliaDaoImpl();
    }

    public static UsersDao getUsersDao()    {
        return new UsersDaoImpl();
    }

}
