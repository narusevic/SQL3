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
                        findClientObjects(bufRead, db);
                        break;
                    case  2: 
                        addWorkerToTeam(bufRead, db);
                        break;
                    case  3:    
                        addWorker(bufRead, db);
                        break;
                    case 4: 
                        updateWorkerSalary(bufRead, db);
                        break;
                    case 5: 
                        dismissWorker(bufRead, db);
                        break;
                    case 6: 
                        switchWorkerTeams(bufRead, db);
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
    
    private void printChoices() {
        System.out.println("Meniu:");
        System.out.println("[0] - baigti darba");
        System.out.println("[1] - rasti kliento objektus");
        System.out.println("[2] - priskirti darbuotoja komandai");
        System.out.println("[3] - pasamdyti nauja darbuotoja");
        System.out.println("[4] - pakeisti darbuotojo atlyginima");
        System.out.println("[5] - atleisti darbuotoja");
        System.out.println("[6] - sukeisti dvieju darbuotoju komandas");
    }
    
    private void findClientObjects(BufferedReader bufRead, SQL db) {
        List<List> result = new LinkedList<List>();
      
        try {
            result = db.queryDb("SELECT * FROM juva9765.Klientas");
            
            System.out.println("Klientai:");
            for (int i = 0; i < result.size(); i++) {
                System.out.println((String) result.get(i).get(0) + " " + 
                        result.get(i).get(1) + " " + result.get(i).get(2));
            }
            
            System.out.println("Iveskite kliento asmens koda");
            
            result = db.queryDb("SELECT Pavadinimas FROM juva9765.Klientas, "
                    + "juva9765.Objektas WHERE AK = Savininko_AK AND AK = '" + 
                    bufRead.readLine() + "'");
            
            if (result.isEmpty()) {
                System.out.println("Tokio kliento nera arba jis neturi objektu");
            } else {
                System.out.println("Kliento objektai:");
                for (int i = 0; i < result.size(); i++) {
                    System.out.println(result.get(i).get(0));
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void addWorkerToTeam(BufferedReader bufRead, SQL db) {
        List<List> result = new LinkedList<List>();
        
        try {
            result = db.queryDb("SELECT * FROM juva9765.Komanda ORDER BY Komandos_Nr");
            
            System.out.println("Komandos:");
            for (int i = 0; i < result.size(); i++) {
                System.out.println((String) result.get(i).get(0) + " " + result.get(i).get(1));
            }
            
            result = db.queryDb("SELECT AK, Vardas, Pavarde FROM "
                    + "juva9765.Darbuotojas EXCEPT SELECT AK, Vardas, Pavarde FROM "
                    + "juva9765.Komandu_sudetys, juva9765.Darbuotojas "
                    + "WHERE AK = Darbuotojo_AK");
            
            System.out.println("Laisvi Darbuotojai:");
            for (int i = 0; i < result.size(); i++) {
                System.out.println((String) result.get(i).get(0) + " " + 
                        result.get(i).get(1) + " " + result.get(i).get(2));
            }
            
            result = db.queryDb("SELECT * FROM juva9765.Komandu_sudetys");
            
            System.out.println("Komandu sudetys (Komandos_Nr Darbuotojo_AK):");
            for (int i = 0; i < result.size(); i++) {
                System.out.println((String) result.get(i).get(0) + " " + result.get(i).get(1));
            }
            
            System.out.println("Atitinkamai iveskite komandos numeri ir darbuotojo AK:");
            
            result = db.queryDb("INSERT INTO juva9765.Komandu_sudetys VALUES "
                    + "(" + bufRead.readLine() + ",'" + bufRead.readLine() + "')");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void addWorker(BufferedReader bufRead, SQL db) {
        System.out.println("Iveskite naujo darbuotojo asmens koda, varda,"
                + " pavarde ir atlyginima");
        
        try {
            db.queryDb("INSERT INTO juva9765.Darbuotojas VALUES "
                    + "(" + bufRead.readLine() + ",'" + bufRead.readLine() 
                    + "','" + bufRead.readLine() + "'," + bufRead.readLine() + ")");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }        
    }
    
    private void updateWorkerSalary(BufferedReader bufRead, SQL db) {
        List<List> result = new LinkedList<List>();
        
        try {
            result = db.queryDb("SELECT * FROM juva9765.Darbuotojas");
            
            System.out.println("Darbuotojai:");
            for (int i = 0; i < result.size(); i++) {
                System.out.println((String) result.get(i).get(0) + " " + 
                        result.get(i).get(1) + " " + result.get(i).get(2) + 
                        " " + result.get(i).get(3));
            }
            
            System.out.println("Iveskite darbuotojo nauja atlyginima ir asmens koda:");
            
            result = db.queryDb("UPDATE juva9765.Darbuotojas SET atlyginimas = " + 
                    bufRead.readLine() + " WHERE ak = '" + bufRead.readLine() + "'");
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void dismissWorker(BufferedReader bufRead, SQL db) {
        List<List> result = new LinkedList<List>();
        
        try {
            result = db.queryDb("SELECT * FROM juva9765.Darbuotojas");
            
            System.out.println("Darbuotojai:");
            for (int i = 0; i < result.size(); i++) {
                System.out.println((String) result.get(i).get(0) + " " + 
                        result.get(i).get(1) + " " + result.get(i).get(2) + 
                        " " + result.get(i).get(3));
            }
            
            System.out.println("Iveskite darbuotojo asmens koda:");
            
            result = db.queryDb("DELETE FROM juva9765.Darbuotojas WHERE ak = '" + 
                    bufRead.readLine() +  "'");
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void switchWorkerTeams(BufferedReader bufRead, SQL db) {
            List<List> result = new LinkedList<List>();
        
        try {
            db.queryDb("BEGIN");
            
            db.queryDb("ALTER TABLE " + db.getDbUsername() + 
                    ".Komandu_sudetys DISABLE TRIGGER darbuotojuSkMax");
            
            result = db.queryDb("SELECT * FROM juva9765.Komandu_sudetys");
            
            System.out.println("Komandu sudetys (Komandos_Nr Darbuotojo_AK):");
            for (int i = 0; i < result.size(); i++) {
                System.out.println((String) result.get(i).get(0) + " " + result.get(i).get(1));
            }
            
            System.out.println("Atitinkamai iveskite dvieju darbuotoju asmens kodus"
                    + " ir komandos numerius (AK, Nr, AK, Nr):");
            
            String pirmas = bufRead.readLine();
            int pirmoKomNr = Integer.parseInt(bufRead.readLine());
            String antras = bufRead.readLine();
            int antroKomNr = Integer.parseInt(bufRead.readLine());
            
            db.queryDb("UPDATE juva9765.Komandu_sudetys SET Komandos_Nr = " + 
                    antroKomNr + " WHERE Darbuotojo_AK = '" + pirmas + 
                    "' AND Komandos_Nr = " + pirmoKomNr);
            
            db.queryDb("UPDATE juva9765.Komandu_sudetys SET Komandos_Nr = " + 
                    pirmoKomNr + " WHERE Darbuotojo_AK = '" + antras + 
                    "' AND Komandos_Nr = " + antroKomNr);
            
            db.queryDb("ALTER TABLE " + db.getDbUsername() + 
                    ".Komandu_sudetys ENABLE TRIGGER darbuotojuSkMax");
            
            db.queryDb("COMMIT");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            try {
                db.queryDb("ROLLBACK");
            } catch (Exception ex) {
                System.out.println("Error:" + ex.getMessage());
            }
        }
    }
}