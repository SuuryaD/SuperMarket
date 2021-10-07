package employee;

public class Employee {

     enum Level{
        USER,
        ADMIN
    }

    private static int NUMBER = 1;
    private final int id;
    private final String name;
    private final String username;
    private final String Password;

    private final Level level;

    public Employee( String name, String username, String password, boolean isAdmin) {
        this.id = NUMBER;
        NUMBER++;
        this.name = name;
        this.username = username;
        Password = password;
        if(isAdmin){
            level = Level.ADMIN;
        }
        else{
            level = Level.USER;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return Password;
    }

    public Level isAdmin() {
        return level;
    }
}
