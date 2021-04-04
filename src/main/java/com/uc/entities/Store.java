package com.uc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
//lombok: @ToString, @EqualsAndHashCode, @Getter (all fields), @Setter (for non-final fields), @RequiredArgsConstructor
@Entity //java ee: persistence
@Builder
@AllArgsConstructor //lombok: constructor with all params
@NoArgsConstructor //lombok: constructor with no params
@NamedQuery(name = "Store.getStoreList", query = "SELECT s FROM Store s")

public class Store implements Serializable {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String location;

	@OneToMany(targetEntity = Inventory.class, cascade = CascadeType.REMOVE)
	private List<Inventory> storeInventory;
}
