package br.com.mvc.controller.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.mvc.model.Veiculo;

public class VeiculoDAO {
	
	EntityManagerFactory emf;
	EntityManager em;
	
	public VeiculoDAO() {
		emf = Persistence.createEntityManagerFactory("PU_ESTACIONAMENTO");
		em = emf.createEntityManager();
	}
	
	public boolean insert(Veiculo obj) {		
		try{
			em.getTransaction().begin();
			em.persist(obj);
			em.getTransaction().commit();
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public List<Veiculo> select(String where){
		
		String stmt = "SELECT c FROM Veiculo c ";
		if(!where.isEmpty()) {
			stmt += " WHERE " + where;
		}
		
		em.getTransaction().begin();
		Query query = em.createQuery(stmt);
		@SuppressWarnings("unchecked")
		List<Veiculo> results = query.getResultList();
		em.getTransaction().commit();
		return results;
	}
	
	public boolean delete(int id) {
		
		Veiculo obj = this.find(id);
		
		if(obj != null) {
			em.getTransaction().begin();
			em.remove(obj);
			em.getTransaction().commit();
			return true;
		}else {
			return false;
		}
	}
	
	public boolean update(Veiculo obj) {
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
		return true;
	}
	
	public Veiculo find(int id) {
		em.getTransaction().begin();
		Veiculo obj = em.find(Veiculo.class, id);
		em.getTransaction().commit();
		return obj;
	}
	
	public void close() {
		em.close();
	}
}
