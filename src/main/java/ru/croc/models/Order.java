package ru.croc.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Order {

	@JsonProperty("ordered_at")
	private String orderedAt;

	private List<Item> items;

	public void setOrderedAt(String orderedAt){
		this.orderedAt = orderedAt;
	}

	public String getOrderedAt(){
		return orderedAt;
	}

	public void setItems(List<Item> items){
		this.items = items;
	}

	public List<Item> getItems(){
		return items;
	}
}