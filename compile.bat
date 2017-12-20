javac -d classes -cp classes src/autoservisas/Main.java src/autoservisas/ui/UserInterface.java src/autoservisas/sql/SQL.java
java -cp classes;lib/postgresql-9.2-1002.jdbc4.jar autoservisas.Main