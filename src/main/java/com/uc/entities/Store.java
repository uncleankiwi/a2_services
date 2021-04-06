package com.uc.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
//lombok: @ToString, @EqualsAndHashCode, @Getter (all fields), @Setter (for non-final fields), @RequiredArgsConstructor
@Entity //java ee: persistence
@Builder
@AllArgsConstructor //lombok: constructor with all params
@NoArgsConstructor //lombok: constructor with no params
@NamedQuery(name = "Store.getStoreList", query = "SELECT s FROM Store s")
@ToString
public class Store implements Serializable {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String location;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "shopId")
	private List<Inventory> storeInventory;

	@PrePersist //method is automatically called before persist (i.e. before this object is put in the db row)
	private void initInventory() {
		this.storeInventory = new ArrayList<>();
	}

	public List<Inventory> getStoreInventory(){return storeInventory;}

	public void setStoreInventory(List<Inventory> inventories){
		this.storeInventory.clear();
		this.storeInventory.addAll(inventories);
	}
}
