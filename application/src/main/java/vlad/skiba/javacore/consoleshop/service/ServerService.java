package vlad.skiba.javacore.consoleshop.service;

import vlad.skiba.javacore.consoleshop.model.Catalog;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public interface ServerService {

    void answerOnClientsRequests(ObjectOutputStream clientOutputStream, ObjectInputStream clientInputStream,
                                 Catalog catalog) throws IOException, ClassNotFoundException;

}