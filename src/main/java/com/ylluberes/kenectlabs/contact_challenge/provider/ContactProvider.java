package com.ylluberes.kenectlabs.contact_challenge.provider;


public interface ContactProvider<T,R>{
    T getContacts (R REQUEST);
}
