package br.com.mvc.controller.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.mvc.model.Modelo;

public class ModeloDAO {
	EntityManagerFactory emf;
	EntityManager em;
	
	public ModeloDAO() {
		emf = Persistence.createEntityManagerFactory("PU_ESTACIONAMENTO");
		em = emf.createEntityManager();
	}
	
	public boolean insert(Modelo obj) {		
		try{
			em.getTransaction().begin();
			em.persist(obj);
			em.getTransaction().commit();
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public List<Modelo> select(String where){
		String stmt = "SELECT c FROM Modelo c ";
		if(!where.isEmpty()) {
			stmt += " WHERE " + where;
		}
		em.getTransaction().begin();
		Query query = em.createQuery(stmt);
		@SuppressWarnings("unchecked")
		List<Modelo> results = query.getResultList();
		em.getTransaction().commit();
		return results;
	}
	
	public boolean delete(int id) {
		Modelo obj = this.find(id);
		if(obj != null) {
			em.getTransaction().begin();
			em.remove(obj);
			em.getTransaction().commit();
			return true;
		}else {
			return false;
		}
	}
	
	public boolean update(Modelo obj) {
		em.getTransaction().begin();
		System.out.println(obj.getId());
		em.merge(obj);
		em.getTransaction().commit();
		return true;
	}
	
	public Modelo find(int id) {
		em.getTransaction().begin();
		Modelo obj = em.find(Modelo.class, id);
		em.getTransaction().commit();
		return obj;
	}
	
	public void close() {
		em.close();
	}
}
