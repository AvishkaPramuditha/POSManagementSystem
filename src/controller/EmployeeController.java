package controller;

import database.DbConnection;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeController {
    public boolean addEmployee(Employee employee){
        Connection connection=null;
        try {
            connection=DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employee VALUES (?,?,?,?,?,?,?)");
            preparedStatement.setString(1,employee.getEmployeeID());
            preparedStatement.setString(2,employee.getEmployeeNIC());
            preparedStatement.setString(3,employee.getEmployeeName());
            preparedStatement.setString(4,employee.getEmployeeAddress());
            preparedStatement.setString(5,employee.getEmployeeContactNumber());
            preparedStatement.setString(6,employee.getJobRole());
            preparedStatement.setString(7,employee.getWorkingHours());

            if (preparedStatement.executeUpdate()>0) {
                    if (addPasswordTable(employee.getEmployeeID(), employee.getUserName(), employee.getPassword())){
                        if (employee.getJobRole().equals("Rider")){
                            if (addDriverTable(employee.getEmployeeID(), employee.getBikeNo(), employee.getDrivingLicenseNumber())) {
                                connection.commit();
                                return true;
                            } else {
                                connection.rollback();
                                return false;
                            }
                    }else{
                            connection.commit();
                            return true;
                        }
                    } else {
                        connection.rollback();
                        return false;
                    }

            }else{
                connection.rollback();
                return false;
            }
        }catch (SQLException | ClassNotFoundException e){e.printStackTrace();}finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

   public Employee searchEmployee(String employeeID) throws SQLException, ClassNotFoundException {
       ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT e.EmployeeID,e.EmployeeNIC,e.EmployeeName,e.EmployeeAddress,e.EmployeeContactNumber,e.JobRole,e.WorkingHours, d.BikeNO,d.DrivingLicenseNumber FROM employee e LEFT JOIN driver d ON e.EmployeeID=d.EmployeeID WHERE e.EmployeeID='" + employeeID + "'").executeQuery();
     if (resultSet.next()){
         return new Employee(
                 resultSet.getString(1),
                 resultSet.getString(2),
                 resultSet.getString(3),
                 resultSet.getString(4),
                 resultSet.getString(5),
                 resultSet.getString(6),
                 resultSet.getString(7),
                 resultSet.getString(8),
                 resultSet.getString(9)
         );
     }
     return null;
   }

   public boolean deleteEmployee(String employeeID, String jobRoll){
            Connection connection=null;
            try {
                connection=DbConnection.getInstance().getConnection();
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employee WHERE EmployeeID='" + employeeID + "'");
                if (preparedStatement.executeUpdate()>0){
                    connection.commit();
                    return true;
                }else{
                    connection.rollback();
                    return false;
                }
            }catch (SQLException | ClassNotFoundException e){e.printStackTrace();}finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return false;
   }

   public boolean updateEmployee(Employee employee){
        Connection connection=null;
        try {
            connection=DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE employee SET EMployeeNIC=?,EmployeeName=?,EmployeeAddress=?,EmployeeContactNumber=?,JobRole=?,WorkingHours=? WHERE EmployeeID=?");
            preparedStatement.setString(1,employee.getEmployeeNIC());
            preparedStatement.setString(2,employee.getEmployeeName());
            preparedStatement.setString(3,employee.getEmployeeAddress());
            preparedStatement.setString(4,employee.getEmployeeContactNumber());
            preparedStatement.setString(5,employee.getJobRole());
            preparedStatement.setString(6,employee.getWorkingHours());
            preparedStatement.setString(7,employee.getEmployeeID());

            if (preparedStatement.executeUpdate()>0){
             if (employee.getJobRole().equals("Rider")){
                 if (isIn(employee.getEmployeeID())){
                     if (updateDriver(employee.getEmployeeID(),employee.getBikeNo(),employee.getDrivingLicenseNumber())){
                         connection.commit();
                         return true;
                     }else {
                         connection.rollback();
                         return false;
                     }
                 }else{
                     if(addDriverTable(employee.getEmployeeID(),employee.getBikeNo(),employee.getDrivingLicenseNumber())){
                         connection.commit();
                         return true;
                     }else{
                         connection.rollback();
                         return false;
                     }
                 }
             }else {
                 if (isIn(employee.getEmployeeID())){
                     if(deleteDriver(employee.getEmployeeID())){
                         connection.commit();
                         return true;
                     }else {
                         connection.rollback();
                         return false;
                     }
                 }
             }

            }else{
                connection.rollback();
                return false;
            }
        }catch (SQLException | ClassNotFoundException e){e.printStackTrace();}finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
   }

   private boolean isIn(String employeeID) throws SQLException, ClassNotFoundException {
       ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT Em ployeeID from driver WHERE EmployeeID='" + employeeID + "'").executeQuery();
       return resultSet.next();
   }
   private boolean deleteDriver(String employeeID) throws SQLException, ClassNotFoundException {
        return DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM driver WHERE EmployeeID='"+employeeID+"'").executeUpdate()>0;
   }
   private boolean updateDriver( String employeeID,String bikeNo,String drivingLicenseNumber) throws SQLException, ClassNotFoundException {
       PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE driver SET BikeNO=?,DrivingLicenseNumber=? WHERE EmployeeID=?");
       preparedStatement.setString(1,bikeNo);
       preparedStatement.setString(2,drivingLicenseNumber);
       preparedStatement.setString(3,employeeID);
       return preparedStatement.executeUpdate()>0;
   }

    private boolean addDriverTable(String employeeID,String bikeNo,String drivingLicenseNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO driver VALUES (?,?,?)");
        preparedStatement.setString(1,employeeID);
        preparedStatement.setString(2,bikeNo);
        preparedStatement.setString(3,drivingLicenseNumber);
        return preparedStatement.executeUpdate()>0;
    }
    private boolean addPasswordTable(String employeeID, String userName, String password) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT  INTO password VALUES (?,?,?)");
        preparedStatement.setString(1,employeeID);
        preparedStatement.setString(2,userName);
        preparedStatement.setString(3,password);
        return preparedStatement.executeUpdate()>0;
    }



}
