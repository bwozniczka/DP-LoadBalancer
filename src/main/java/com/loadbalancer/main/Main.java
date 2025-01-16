package com.loadbalancer.main;

import com.loadbalancer.facade.DatabaseFacade;
import java.sql.SQLException;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DatabaseFacade databaseFacade = new DatabaseFacade();

        try {
            // Inicjalizacja połączeń
            databaseFacade.connect();
            System.out.println("Połączono z bazami danych!");

            // Przykładowe użycie połączeń
            Connection postgres1 = databaseFacade.getPostgresConnection1();
            Connection postgres2 = databaseFacade.getPostgresConnection2();
            Connection mysql = databaseFacade.getMysqlConnection();

            System.out.println("Połączenia gotowe do użycia!");

        } catch (SQLException e) {
            System.err.println("Błąd podczas łączenia z bazami danych: " + e.getMessage());
        } finally {
            databaseFacade.close();
            System.out.println("Zamknięto połączenia.");
        }
    }
}
