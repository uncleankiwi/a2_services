package com.vg.services;

import com.vg.entities.Store;

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
		entityManager.persist(store);
	}

	//read
	public Store getStore(Store store) {
		return entityManager.find(Store.class, store.getId());
	}

	//update
	public void updateStore(Store store) {
		Store storeToUpdate = getStore(store);
		entityManager.merge(storeToUpdate);
	}

	//delete
	public void deleteStore(Store store) {
		Store storeToDelete = getStore(store);
		entityManager.remove(storeToDelete);
	}
}
