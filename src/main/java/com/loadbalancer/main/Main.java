package com.loadbalancer.main;

import com.loadbalancer.balancer.LoadBalancer;
import com.loadbalancer.balancer.RoundRobinStrategy;
import com.loadbalancer.connection.DatabaseConnection;
import com.loadbalancer.facade.DatabaseConnectionManagerFacade;
import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DatabaseConnectionManagerFacade databaseFacade = new DatabaseConnectionManagerFacade();

        try {
            // Inicjalizacja połączeń
            databaseFacade.connect();
            System.out.println("Połączono z bazami danych!");

            // Przykładowe użycie połączeń
            Connection postgres1 = databaseFacade.getPostgresConnection1();
            Connection postgres2 = databaseFacade.getPostgresConnection2();
            Connection mysql = databaseFacade.getMysqlConnection();

            // LoadBalancer lb = LoadBalancer.getInstance();
            // lb.setStrategy(new RoundRobinStrategy());
            // lb.setDatabases(new List<DatabaseConnection>() {
            //     postgres1,
            //     postgres2,
            //     mysql
            // });

            System.out.println("Połączenia gotowe do użycia!");

        } catch (SQLException e) {
            System.err.println("Błąd podczas łączenia z bazami danych: " + e.getMessage());
        } finally {
            databaseFacade.close();
            System.out.println("Zamknięto połączenia.");
        }
    }
}
