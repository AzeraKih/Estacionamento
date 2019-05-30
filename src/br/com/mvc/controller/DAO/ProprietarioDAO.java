package br.com.mvc.controller.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.mvc.model.Proprietario;

public class ProprietarioDAO {
	
	EntityManagerFactory emf;
	EntityManager em;
	
	public ProprietarioDAO() {
		emf = Persistence.createEntityManagerFactory("PU_ESTACIONAMENTO");
		em = emf.createEntityManager();
	}
	
	public boolean insert(Proprietario obj) {		
		try{
			em.getTransaction().begin();
			em.persist(obj);
			em.getTransaction().commit();
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public List<Proprietario> select(String where){
		
		String stmt = "SELECT c FROM Proprietario c ";
		if(!where.isEmpty()) {
			stmt += " WHERE " + where;
		}
		
		em.getTransaction().begin();
		Query query = em.createQuery(stmt);
		@SuppressWarnings("unchecked")
		List<Proprietario> results = query.getResultList();
		em.getTransaction().commit();
		return results;
	}
	
	public boolean delete(int id) {
		
		Proprietario obj = this.find(id);
		
		if(obj != null) {
			em.getTransaction().begin();
			em.remove(obj);
			em.getTransaction().commit();
			return true;
		}else {
			return false;
		}
	}
	
	public boolean update(Proprietario obj) {
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
		return true;
	}
	
	public Proprietario find(int id) {
		em.getTransaction().begin();
		Proprietario obj = em.find(Proprietario.class, id);
		em.getTransaction().commit();
		return obj;
	}
	
	public void close() {
		em.close();
	}
}
