package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ListStorage implements Storage{

    ArrayList<Person> customers = new ArrayList<>();

    public ListStorage(){
        //Läs in sparade kunder från fil
        loadFromFile();
    }

    public void addCustomer(Person customer){
        customers.add(customer);
        saveToFile();
    }

    public Person findFirstCustomer(String name){
        for (Person c : customers ) {
            if( c.getName().equals(name))
                return c;
        }
        return new Person();
    }

    public void close(){
        saveToFile();
    }

    @Override
    public List<Person> getAll() {
        return customers;
    }

    private void loadFromFile() {
        String path = System.getProperty("user.home")
                + File.separator + "Documents"
                + File.separator + "customers.bin";
        File file = new File(path);

        //Load from file, run this code first in your program on start.
        try (ObjectInputStream in =
                     new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            customers = (ArrayList<Person>) in.readObject();
        } catch (FileNotFoundException e) {
            //On first start you will end up here. No file available.
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        }
    }

    private void saveToFile() {
        String path = System.getProperty("user.home")
                + File.separator + "Documents"
                + File.separator + "customers.bin";
        File file = new File(path);

        //Save object to file, run before closing the program
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            out.writeObject(customers);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
