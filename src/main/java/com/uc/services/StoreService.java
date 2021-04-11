package com.uc.services;

import com.uc.entities.Store;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless //EJB: session bean won't store client data. pool shared by all users. good for outputting data.
@Remote //EJB: exposed through JNDI for other applications
public class StoreService {
	@PersistenceContext //JEE: accesses persistence context, which accesses db
	private EntityManager entityManager;

	//create
	public void addStore(Store store) {
		if (store.getId() != null) store.setId(null);	//id needs to be null; allow JPA to automatically set it
		entityManager.persist(store);
	}

	//read all
	public List<Store> getStoreList() {
		return entityManager.createNamedQuery("Store.getStoreList", Store.class)
				.getResultList();
	}

	//read
	public Store getStore(Store store) {
		return entityManager.find(Store.class, store.getId());
	}

	//read from id
	public Store getStoreFromId(Long id) {
		return entityManager.find(Store.class, id);
	}

	//update
	public void updateStore(Store store) throws Exception {
		if (getStore(store) == null) throw new Exception("No item with that id exists.");
		entityManager.merge(store);
	}

	//delete
	public void deleteStore(Store store) {
		entityManager.remove(getStore(store));
	}
}
