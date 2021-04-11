package com.uc.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
//lombok: @ToString, @EqualsAndHashCode, @Getter (all fields), @Setter (for non-final fields), @RequiredArgsConstructor
@Entity //java ee: persistence
@Builder //lombok: builder pattern
@AllArgsConstructor //lombok: constructor with all params
@NoArgsConstructor //lombok: constructor with no params
@NamedQuery(name = "Store.getStoreList", query = "SELECT s FROM Store s")
@ToString //lombok: implement toString()
public class Store implements Serializable {
	@Id
	@GeneratedValue(generator="STORE_GENERATOR")
	private Long id;
	private String name;
	private String location;

	@OneToMany(mappedBy = "store", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@ToString.Exclude //for brevity
	private List<Inventory> storeInventory;

	@PrePersist //method is automatically called before persist (i.e. before this object is put in the db row)
	private void initInventory() {
		this.storeInventory = new ArrayList<>();
	}
}
