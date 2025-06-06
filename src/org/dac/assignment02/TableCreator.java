package org.dac.assignment02;
import java.sql.*;
import java.util.*;

public class TableCreator {
    static final String URL = "jdbc:mysql://localhost:3306/wbja_b1";
    static final String USER = "root";
    static final String PASS = "cdac";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Create Table\n2. Display Columns\n3. Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 : {
                	createTable();
                	break;
                }
                case 2 :{
                	displayColumns();
                	break;
                }
                case 3 : {
                	System.exit(0);
                	break;
                }
                default : System.out.println("Invalid choice!");
            }
        }
    }

    static void createTable() {
        System.out.print("Enter table name: ");
        String tableName = sc.nextLine();
        List<String> columns = new ArrayList<>();
        List<String> types = new ArrayList<>();
        String primaryKey = "";

        while (true) {
            System.out.println("1. Add Column\n2. Set Primary Key\n3. Save Table");
            System.out.print("Choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 : {
                    System.out.print("Column name: ");
                    String col = sc.nextLine();
                    System.out.println("Select Type: 1.VARCHAR 2.INT 3.FLOAT");
                    int typeOption = sc.nextInt();
                    sc.nextLine();
                    String type ="";
                    switch (typeOption) {
                        case 1 : {
                        	type = "VARCHAR(50)";
                        	break;
                        }
                        case 2 : {
                        	type = "INT";
                        	break;
                        }
                        case 3 :{
                        	type = "FLOAT";
                        }
                        default :{
                        	type = "VARCHAR(50)";
                        }
                    };
                    columns.add(col);
                    types.add(type);
                    break;
                }

                case 2 : {
                    if (columns.isEmpty()) {
                        System.out.println("No columns added yet.");
                        break;
                    }
                    System.out.println("Select primary key column:");
                    for (int i = 0; i < columns.size(); i++) {
                        System.out.println((i + 1) + ". " + columns.get(i));
                    }
                    int pkIndex = sc.nextInt() - 1;
                    primaryKey = columns.get(pkIndex);
                    break;
                }

                case 3 : {
                    StringBuilder query = new StringBuilder("CREATE TABLE " + tableName + " (");
                    for (int i = 0; i < columns.size(); i++) {
                        query.append(columns.get(i)).append(" ").append(types.get(i)).append(", ");
                    }
                    if (!primaryKey.isEmpty()) {
                        query.append("PRIMARY KEY (").append(primaryKey).append(")");
                    } else {
                        query.setLength(query.length() - 2);
                    }
                    query.append(");");

                    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                         Statement stmt = conn.createStatement()) {
                            
                        stmt.executeUpdate(query.toString());
                        System.out.println("Table created successfully!");

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
    }

    static void displayColumns() {
        System.out.print("Enter table name: ");
        String table = sc.nextLine();
        String query = "SELECT * FROM " + table + " LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println("Columns in table '" + table + "':");
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.println("- " + rsmd.getColumnName(i));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

