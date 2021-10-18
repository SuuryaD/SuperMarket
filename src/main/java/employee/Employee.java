package employee;

import org.mindrot.jbcrypt.BCrypt;

/** Contains all the details of an employee. */
public class Employee {

  private static int NUMBER = 1;
  private final int id;
  private final String name;
  private final String username;
  private final String Password;
  private final Level level;

  public Employee(int id,String name, String username, String password, boolean isAdmin) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.Password = BCrypt.hashpw(password, BCrypt.gensalt(10));
    if (isAdmin) {
      level = Level.ADMIN;
    } else {
      level = Level.CASHIER;
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

  public boolean isAdmin() {
    return level == Level.ADMIN;
  }

  enum Level {
    CASHIER,
    ADMIN
  }
}
