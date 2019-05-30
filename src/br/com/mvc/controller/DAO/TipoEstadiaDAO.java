package br.com.mvc.controller.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.mvc.model.TipoEstadia;

public class TipoEstadiaDAO {
	
	EntityManagerFactory emf;
	EntityManager em;
	
	public TipoEstadiaDAO() {
		emf = Persistence.createEntityManagerFactory("PU_ESTACIONAMENTO");
		em = emf.createEntityManager();
	}
	
	public boolean insert(TipoEstadia obj) {		
		try{
			em.getTransaction().begin();
			em.persist(obj);
			em.getTransaction().commit();
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public List<TipoEstadia> select(String where){
		
		String stmt = "SELECT c FROM TipoEstadia c ";
		if(!where.isEmpty()) {
			stmt += " WHERE " + where;
		}
		
		em.getTransaction().begin();
		Query query = em.createQuery(stmt);
		@SuppressWarnings("unchecked")
		List<TipoEstadia> results = query.getResultList();
		em.getTransaction().commit();
		return results;
	}
	
	public boolean delete(int id) {
		
		TipoEstadia obj = this.find(id);
		
		if(obj != null) {
			em.getTransaction().begin();
			em.remove(obj);
			em.getTransaction().commit();
			return true;
		}else {
			return false;
		}
	}
	
	public boolean update(TipoEstadia obj) {
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
		return true;
	}
	
	public TipoEstadia find(int id) {
		em.getTransaction().begin();
		TipoEstadia obj = em.find(TipoEstadia.class, id);
		em.getTransaction().commit();
		return obj;
	}
	
	public void close() {
		em.close();
	}
}
