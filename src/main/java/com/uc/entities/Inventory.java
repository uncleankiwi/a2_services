package com.uc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data //lombok: @ToString, @EqualsAndHashCode, @Getter (all fields), @Setter (for non-final fields), @RequiredArgsConstructor
@Entity //java ee: persistence
@Builder
@AllArgsConstructor //lombok: constructor with all params
@NoArgsConstructor //lombok: constructor with no params
@NamedQuery(name = "Inventory.getInventoryList", query = "SELECT i FROM Inventory i")
public class Inventory implements Comparable<Inventory>, Serializable {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String sport;
	private int quantity;
	private float price;
	private Date addedDate;
	private Date updatedDate;

	@Override
	public int compareTo(Inventory o) {
		return this.updatedDate.compareTo((o.updatedDate));
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
