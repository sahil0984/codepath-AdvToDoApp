package com.forCodePath.todoapp;

public class TodoListModel{
	String todoItem;
	String todoItemDetails;
	int markDone; /* -1 -> Not Started, 0 -> Partial, 1 -> Done */
	
	TodoListModel(String name, String details, int value){
		this.todoItem = name;
		this.todoItemDetails = details;
		this.markDone = value;
	}
	public String getTodoItem(){
		return this.todoItem;
	}
	public String getTodoItemDetails(){
		return this.todoItemDetails;
	}
	public int getMarkDone(){
		return this.markDone;
	}
  
}