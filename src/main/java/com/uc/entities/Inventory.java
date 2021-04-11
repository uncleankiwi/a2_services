package com.uc.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data //lombok: @ToString, @EqualsAndHashCode, @Getter (all fields), @Setter (for non-final fields), @RequiredArgsConstructor
@Entity //java ee: persistence
@Builder //lombok: builder pattern
@AllArgsConstructor //lombok: constructor with all params
@NoArgsConstructor //lombok: constructor with no params
@NamedQuery(name = "Inventory.getInventoryList", query = "SELECT i FROM Inventory i")
@NamedQuery(name = "Inventory.getStoreInventory", query = "SELECT i FROM Inventory i WHERE i.store.id = :storeId")
@ToString //lombok: implement toString()
public class Inventory implements Comparable<Inventory>, Serializable {
	@Id
	@GeneratedValue(generator="INVENTORY_GENERATOR")
	private Long id;
	private String name;
	private String sport;
	private int quantity;
	private float price;
	private Date addedDate;
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "shopid")
	@ToString.Exclude //otherwise it'll cause an infinite loop
	private Store store;

	@Override
	public int compareTo(Inventory o) {
		//compare by updatedDate first then addedDate. reverse order for both so that newest is at top.
		//treat nulls as 1970.
		Long thisUpdatedDate = this.updatedDate != null ? this.updatedDate.getTime() : 0;
		Long oUpdatedDate = o.updatedDate != null ? o.updatedDate.getTime() : 0;
		int updatedCompare = -1 * thisUpdatedDate.compareTo(oUpdatedDate);
		if (updatedCompare != 0) {
			return updatedCompare;
		}
		else {
			Long thisAddedDate = this.addedDate != null ? this.addedDate.getTime() : 0;
			Long oAddedDate = o.addedDate != null ? o.addedDate.getTime() : 0;
			return -1 * thisAddedDate.compareTo(oAddedDate);
		}
	}

	@PrePersist //method is automatically called before persist (i.e. before this object is put in the db row)
	private void initCreateDate() {
		this.addedDate = new Date();
	}

	@PreUpdate //call method before updating a db row with object
	private void updateUpdatedDate() {
		this.updatedDate = new Date();
	}
}
