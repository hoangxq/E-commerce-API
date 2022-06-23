package com.demo.models;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "receiver_name")
	private String receiverFullname;
	
	@Column(name = "receiver_address")
	private String receiverAddress;
	
	@Column(name = "receiver_phone")
	private String receiverPhone;
	
	@Column(name = "time_order")
	private LocalDateTime timeOrder;

	public Order(User user, String receiverFullname, String receiverAddress, String receiverPhone,
			LocalDateTime timeOrder) {
		super();
		this.user = user;
		this.receiverFullname = receiverFullname;
		this.receiverAddress = receiverAddress;
		this.receiverPhone = receiverPhone;
		this.timeOrder = timeOrder;
	}

	
}
