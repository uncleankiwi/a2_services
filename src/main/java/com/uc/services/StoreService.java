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
	public Store getStore(Store store) {
		return entityManager.find(Store.class, store.getId());
	}

	//update
	public void updateStore(Store store) {
		entityManager.merge(getStore(store));
	}

	//delete
	public void deleteStore(Store store) {
		entityManager.remove(getStore(store));
	}
}
