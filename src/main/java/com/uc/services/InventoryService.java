package com.uc.services;

import com.uc.entities.Inventory;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Remote
public class InventoryService {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Inventory> getInventoryList() {
		return entityManager.createNamedQuery("Inventory.getInventoryList", Inventory.class).getResultList();
	}

	//create
	public void addStore(Inventory inventory) {
		if (inventory.getId() != null) inventory.setId(null);	//id needs to be null; allow JPA to automatically set it
		entityManager.persist(inventory);
	}

	//read
	public Inventory getInventory(Inventory inventory) {
		return entityManager.find(Inventory.class, inventory.getId());
	}

	//update
	public void updateInventory(Inventory inventory) {
		entityManager.merge(getInventory(inventory));
	}

	//delete
	public void deleteInventory(Inventory inventory) {
		entityManager.remove(getInventory(inventory));
	}
}
