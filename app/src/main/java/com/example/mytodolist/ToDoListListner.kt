package com.example.mytodolist

interface ToDoListListner {
    fun onItemClick(position: Int)
    fun onItemLongClick(position: Int)
}