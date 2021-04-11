package com.uc.services;

import com.uc.entities.Inventory;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Stateless //EJB: session bean won't store client data. pool shared by all users. good for outputting data.
@Remote //EJB: exposed through JNDI for other applications
public class InventoryService {
	@PersistenceContext //JEE: accesses persistence context, which accesses db
	private EntityManager entityManager;

	//inventory list of all stores. unused.
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

	//read all
	public List<Inventory> getStoreInventory(Long storeId) {
		return entityManager.createNamedQuery("Inventory.getStoreInventory", Inventory.class)
				.setParameter("storeId", storeId)
				.getResultList().stream().sorted().collect(Collectors.toList());
	}

	//update
	public void updateInventory(Inventory inventory) throws Exception {
		Inventory oldInv = getInventory(inventory);
		if (oldInv == null) throw new Exception("No item with that id exists.");
		inventory.setAddedDate(oldInv.getAddedDate());
		entityManager.merge(inventory);
	}

	//delete
	public void deleteInventory(Inventory inventory) throws Exception {
		if (getInventory(inventory) == null) throw new Exception("No item with that id exists.");
		entityManager.remove(getInventory(inventory));
	}
}
