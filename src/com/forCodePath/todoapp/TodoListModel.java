package com.forCodePath.todoapp;

public class TodoListModel{
 String todoItem;
 int markDone; /* -1 -> Not Started, 0 -> Partial, 1 -> Done */

 TodoListModel(String name, int value){
	 this.todoItem = name;
	 this.markDone = value;
 }
 public String getTodoItem(){
	 return this.todoItem;
 }
 public int getMarkDone(){
	 return this.markDone;
 }
 
 
}