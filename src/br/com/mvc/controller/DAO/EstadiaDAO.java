package br.com.mvc.controller.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.mvc.model.Estadia;

public class EstadiaDAO {
	
	EntityManagerFactory emf;
	EntityManager em;
	
	public EstadiaDAO() {
		emf = Persistence.createEntityManagerFactory("PU_ESTACIONAMENTO");
		em = emf.createEntityManager();
	}
	
	public boolean insert(Estadia obj) {		
		try{
			em.getTransaction().begin();
			em.persist(obj);
			em.getTransaction().commit();
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public List<Estadia> select(String where){
		
		String stmt = "SELECT c FROM Estadia c ";
		if(!where.isEmpty()) {
			stmt += " WHERE " + where;
		}
		em.getTransaction().begin();
		Query query = em.createQuery(stmt);
		@SuppressWarnings("unchecked")
		List<Estadia> results = query.getResultList();
		em.getTransaction().commit();
		return results;
	}
	
	public boolean delete(int id) {
		
		Estadia obj = this.find(id);
		
		if(obj != null) {
			em.getTransaction().begin();
			em.remove(obj);
			em.getTransaction().commit();
			return true;
		}else {
			return false;
		}
	}
	
	public boolean update(Estadia obj) {
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
		return true;
	}
	
	public Estadia find(int id) {
		em.getTransaction().begin();
		Estadia obj = em.find(Estadia.class, id);
		em.getTransaction().commit();
		return obj;
	}
	
	public void close() {
		em.close();
	}
}
