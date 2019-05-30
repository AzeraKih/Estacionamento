package br.com.mvc.controller.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.mvc.model.TabelaTipo;

public class TabelaTipoDAO {
	EntityManagerFactory emf;
	EntityManager em;
	
	public TabelaTipoDAO() {
		emf = Persistence.createEntityManagerFactory("PU_ESTACIONAMENTO");
		em = emf.createEntityManager();
	}
	
	public boolean insert(TabelaTipo obj) {		
		try{
			em.getTransaction().begin();
			em.persist(obj);
			em.getTransaction().commit();
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public List<TabelaTipo> select(String where){
		String stmt = "SELECT c FROM TabelaTipo c ";
		if(!where.isEmpty()) {
			stmt += " WHERE " + where;
		}
		em.getTransaction().begin();
		Query query = em.createQuery(stmt);
		@SuppressWarnings("unchecked")
		List<TabelaTipo> results = query.getResultList();
		em.getTransaction().commit();
		return results;
	}
	
	public boolean delete(int id) {
		TabelaTipo obj = this.find(id);
		if(obj != null) {
			em.getTransaction().begin();
			em.remove(obj);
			em.getTransaction().commit();
			return true;
		}else {
			return false;
		}
	}
	
	public boolean update(TabelaTipo obj) {
		em.getTransaction().begin();
		System.out.println(obj.getId());
		em.merge(obj);
		em.getTransaction().commit();
		return true;
	}
	
	public TabelaTipo find(int id) {
		em.getTransaction().begin();
		TabelaTipo obj = em.find(TabelaTipo.class, id);
		em.getTransaction().commit();
		return obj;
	}
	
	public void close() {
		em.close();
	}
}
