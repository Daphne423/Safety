package com.daphne.safety;

import java.util.ArrayList;

public class ContactsList {
    public ArrayList<ContactActivity> contactArrayList;

    ContactsList() {

        contactArrayList = new ArrayList<ContactActivity>();
    }

    public ContactsList(String phId, String name, String phNo, String label) {
    }

    public int getCount() {

        return contactArrayList.size();
    }

    public void addContact(ContactActivity contact) {
        contactArrayList.add(contact);
    }

    public void removeContact(ContactActivity contact) {
        contactArrayList.remove(contact);
    }

    public ContactActivity getContact(int id) {

        for (int i = 0; i < this.getCount(); i++) {
            if (Integer.parseInt(contactArrayList.get(i).id) == id)
                return contactArrayList.get(i);
        }

        return null;
    }
}