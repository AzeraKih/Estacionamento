package br.com.mvc.controller.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.mvc.model.Marca;

public class MarcaDAO {
	
	EntityManagerFactory emf;
	EntityManager em;
	
	public MarcaDAO() {
		emf = Persistence.createEntityManagerFactory("PU_ESTACIONAMENTO");
		em = emf.createEntityManager();
	}
	
	public boolean insert(Marca obj) {		
		try{
			em.getTransaction().begin();
			em.persist(obj);
			em.getTransaction().commit();
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public List<Marca> select(String where){
		
		String stmt = "SELECT c FROM Marca c ";
		if(!where.isEmpty()) {
			stmt += " WHERE " + where;
		}
		
		em.getTransaction().begin();
		Query query = em.createQuery(stmt);
		@SuppressWarnings("unchecked")
		List<Marca> results = query.getResultList();
		em.getTransaction().commit();
		return results;
	}
	
	public boolean delete(int id) {
		
		Marca obj = this.find(id);
		
		if(obj != null) {
			em.getTransaction().begin();
			em.remove(obj);
			em.getTransaction().commit();
			return true;
		}else {
			return false;
		}
	}
	
	public boolean update(Marca obj) {
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
		return true;
	}
	
	public Marca find(int id) {
		em.getTransaction().begin();
		Marca obj = em.find(Marca.class, id);
		em.getTransaction().commit();
		return obj;
	}
	
	public void close() {
		em.close();
	}
}
