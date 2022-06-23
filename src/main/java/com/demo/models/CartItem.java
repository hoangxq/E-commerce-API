package com.demo.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "cart_items")
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	private Long quantity;
	
	
	
}
