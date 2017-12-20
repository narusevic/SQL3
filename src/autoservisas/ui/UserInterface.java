package autoservisas.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import autoservisas.sql.SQL;

public class UserInterface {
    public void runUI() {
        SQL db = new SQL();
        BufferedReader bufRead = new BufferedReader(new InputStreamReader(System.in)); 
        int choice = 1;
        
        printChoices();
        
        while (choice != 0) 
        {
            try 
            {
                choice = Integer.parseInt(bufRead.readLine());
                
                switch (choice) 
                {
                    case 0: 
                        break;
                    case 1: 
                        getCustomer(bufRead, db);
                        break;
                    case  2: 
                        assignMechanicVechicle(bufRead, db);
                        break;
                    case  3:    
                        addMechanic(bufRead, db);
                        break;
                    case 4: 
                        updateCustomerTelephone(bufRead, db);
                        break;
                    case 5: 
                        unassignMechanic(bufRead, db);
                        break;
                    default: 
                        System.out.println("Wrong choice");
                        break;
                }
            } 
            catch (IOException e) 
            {
               System.out.println("Exception then reading input");
            } 
            catch(NumberFormatException e) 
            {
               System.out.println("Wrong input");
            }
        }
        
        db.closeConnection();
    }
    
    private void printChoices() 
    {
        System.out.println("Menu:");
        System.out.println("[0] - end session");
        System.out.println("[1] - find customer");
        System.out.println("[2] - assign mechanic to vechicle");
        System.out.println("[3] - hire new mechanic");
        System.out.println("[4] - update customer telephone number");
        System.out.println("[5] - unassign mechanic from vechile");
    }
    
    private void getCustomer(BufferedReader bufRead, SQL db) 
    {
        List<List> result = new LinkedList<List>();
      
        try 
        {
            result = db.queryDb("SELECT * FROM Uzsakovas");
            
            System.out.println("Customers:");

            for (int i = 0; i < result.size(); i++) 
            {
                System.out.println((String) result.get(i).get(0) + " " + 
                    result.get(i).get(1) + " " + result.get(i).get(2) + " " + result.get(i).get(3));
            }
            
            System.out.println("Please input customer name");
            
            result = db.queryDb("SELECT * FROM Uzsakovas WHERE Vardas = '" + bufRead.readLine() + "'");
            
            if (result.isEmpty()) 
            {
                System.out.println("This customer does not exist");
            } 
            else 
            {
                System.out.println("Customer:");
		for (int i = 0; i < result.get(0).size(); i++)
		{
                	System.out.println(result.get(0).get(i));
		}
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void assignMechanicVechicle(BufferedReader bufRead, SQL db) 
    {
        List<List> result = new LinkedList<List>();
        
        try 
        {
            result = db.queryDb("SELECT * FROM Mechanikas");
            
            System.out.println("Mechanics:");

            for (int i = 0; i < result.size(); i++) 
            {
                System.out.println((String) result.get(i).get(0) + " " + result.get(i).get(1)
                    + " " + result.get(i).get(2));
            }
            
            result = db.queryDb("SELECT * FROM Masina");
            
            System.out.println("Vechicles:");

            for (int i = 0; i < result.size(); i++) 
            {
                System.out.println((String) result.get(i).get(0) + " " + 
                        result.get(i).get(1) + " " + result.get(i).get(2)
                        + " " + result.get(i).get(3) + " " + result.get(i).get(4));
            }
                        
            System.out.println("Input mechanic's name, surname and vechicle license number");
            
            String name = bufRead.readLine();
            String surname = bufRead.readLine();
            String valstNr = bufRead.readLine();

            result = db.queryDb("INSERT INTO Taisymas VALUES ('" + valstNr + "', Asmens_kodas)"
                    + "SELECT Asmens_kodas "
                    + "FROM Mechanikas "
                    + "WHERE Vardas = '" + name + "' AND pavarde = '" + surname + "'");

        } 
        catch (Exception e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void addMechanic(BufferedReader bufRead, SQL db) {
        System.out.println("Input data of new mechanic: Name, Surname, and " + 
		    "Personal identification number");
        
        try 
        {
            db.queryDb("INSERT INTO Mechanikas VALUES "
                    + "('" + bufRead.readLine() + "', '" + bufRead.readLine() 
                    + "'," + bufRead.readLine() + ")");
        } 
        catch (Exception e) 
        {
            System.out.println("Error: " + e.getMessage());
        }        
    }
    
    private void updateCustomerTelephone(BufferedReader bufRead, SQL db) 
    {
        List<List> result = new LinkedList<List>();
        
        try 
        {
            result = db.queryDb("SELECT * FROM Uzsakovas");
            
            System.out.println("Customers:");
            for (int i = 0; i < result.size(); i++) 
            {
                System.out.println((String) result.get(i).get(0) + " " + 
                        result.get(i).get(1) + " " + result.get(i).get(2) + 
                        " " + result.get(i).get(3));
            }
            
            System.out.println("Input new telephone number and customer's id:");
            
            result = db.queryDb("UPDATE Uzsakovas SET telefono_numeris = " + 
                    bufRead.readLine() + " WHERE ID = " + bufRead.readLine());
            
        }  
        catch (Exception e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void unassignMechanic(BufferedReader bufRead, SQL db) 
    {
        List<List> result = new LinkedList<List>();
        
        try 
        {
            result = db.queryDb("SELECT * FROM Taisymas");
            
            System.out.println("Mechanics assigned to vechiles:");

            for (int i = 0; i < result.size(); i++) 
            {
                System.out.println((String) result.get(i).get(0) + " " + 
                        result.get(i).get(1));
            }
            
            System.out.println("Input vechile license number and mechanic personal identification number:");
            
            result = db.queryDb("DELETE FROM Taisymas WHERE Automobilio_Valst_NR = '" + 
                    bufRead.readLine() +  "' AND Mechaniko_AK = " + bufRead.readLine());
            
        } 
        catch (Exception e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }    
}
