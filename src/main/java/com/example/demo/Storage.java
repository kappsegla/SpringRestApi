package com.example.demo;

import java.util.List;

public interface Storage {

   void addCustomer(Person customer);

   Person findFirstCustomer(String name);

   void close();

   List<Person> getAll();
}
