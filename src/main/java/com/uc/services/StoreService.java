package com.uc.services;

import com.uc.entities.Store;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Remote
public class StoreService {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Store> getStoreList() {
		return entityManager.createNamedQuery("Store.getStoreList", Store.class)
				.getResultList();
	}

	//create
	public void addStore(Store store) {
		if (store.getId() != null) store.setId(null);	//id needs to be null; allow JPA to automatically set it
		entityManager.persist(store);
	}

	//read
	public Store getStore(Store store) throws Exception {
		try {
			return entityManager.find(Store.class, store.getId());
		} catch (Exception e) {
			throw new Exception("No item with that id exists.");
		}
	}

	//update
	public void updateStore(Store store) throws Exception {
		getStore(store);
		entityManager.merge(store);
	}

	//delete
	public void deleteStore(Store store) throws Exception {
		getStore(store);
		entityManager.remove(getStore(store));
	}
}
