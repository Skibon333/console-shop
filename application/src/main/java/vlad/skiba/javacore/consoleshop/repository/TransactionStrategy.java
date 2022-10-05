package vlad.skiba.javacore.consoleshop.repository;

import java.sql.SQLException;
import java.sql.Connection;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
@FunctionalInterface
public interface TransactionStrategy {

    void executeTransaction(Connection connection) throws SQLException;

}