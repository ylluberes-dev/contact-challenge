package com.ylluberes.kenectlabs.contactchallenge.provider;


public interface ContactProvider<T, R> {
    T getContacts(R REQUEST);
}
