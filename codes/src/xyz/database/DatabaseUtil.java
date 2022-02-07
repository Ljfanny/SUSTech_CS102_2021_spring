package xyz.database;

import xyz.Main;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class DatabaseUtil {
    static class Grade {
        String name;
        String id;
        String grade;

        public Grade(String name, String id, String grade, String difficulty) {
            this.name = name;
            this.id = id;
            this.grade = grade;
            this.difficulty = difficulty;
        }

        String difficulty;
    }

    static Connection con = null;
    public static UserInformation player;
    public static UserInformation opponent;
    public static ArrayList<UserInformation> users = new ArrayList<>();
    public static ArrayList<Grade> grades = new ArrayList<>();

    public DatabaseUtil() {
        openDB("identifier.sqlite");
    }

    public static void openDB(String dbPath) {
        try {
            // CLASSPATH must be properly set, for instance on
            // a Linux system or a Mac:
            // $ export CLASSPATH=.:sqlite-jdbc-version-number.jar
            // Alternatively, run the program with
            // $ java -cp .:sqlite-jdbc-version-number.jar BasicJDBC
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
//            System.err.println("Cannot find the driver.");
            System.exit(1);
        }
        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            con.setAutoCommit(false);
//            System.err.println("Successfully connected to the database.");
            loadData();
        } catch (Exception e) {
//            System.err.println("openDB" + e.getMessage());
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
        }
    }

    private static void querySqlite_master() {
        // We query the sqlite_master table that contains
        // the names of all other tables in the database,
        // and for each table we count how many rows it
        // contains.
        if (con != null) {
            try {
                Statement stmt1;
                ResultSet rs1;
                Statement stmt2;
                ResultSet rs2;
                int tabcnt = 0;
                stmt1 = con.createStatement();
                rs1 = stmt1.executeQuery("select name from sqlite_master" + " where type='table'");
                while (rs1.next()) {
                    stmt2 = con.createStatement();
                    rs2 = stmt2.executeQuery("select count(*) from " + rs1.getString(1));
                    if (rs2.next()) {
//                        System.out.println(rs1.getString(1) + ":\t" + rs2.getInt(1) + " rows");
                    }
                    rs2.close();
                    tabcnt++;
                }
                rs1.close();
                if (tabcnt == 0) {
//                    System.out.println("No tables in the file");
                }
                con.commit();
            } catch (Exception e) {
//                System.err.println("querySqlite_master:" + e.getMessage());
                System.exit(1);
            }
        }
    }


    public static void printPerson() {
        if (con != null) {
            try {
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery("select * from person");
                while (rs.next()) {
                    // read the result set
//                    System.out.println("name = " + rs.getString("name"));
//                    System.out.println("id = " + rs.getInt("id"));
                }
                rs.close();
            } catch (SQLException e) {
                // if the error message is "out of memory",
                // it probably means no database file is found
//                System.err.println("printPerson:" + e.getMessage());
            }
        }
    }


    public static boolean register(String id, String password) {
        if (con != null) {
            try {
                String SQL = "select * from users where user_id = ?";
                PreparedStatement pstmt = con.prepareStatement(SQL);
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(Main.background, "Can't find the User!");
                    return false;
                } else {
                    if (password.equals(rs.getString("password"))) {
                        player = new UserInformation(rs.getString(1), rs.getString(3));
                        getInformation(player.id,1);
//                        System.err.println("Register successfully!");
//                        System.err.println("Welcome back, " + player.name);
                        return true;
                    } else {
//                        System.err.println("Wrong password!");
                        JOptionPane.showMessageDialog(Main.background, "Wrong password!");
                        return false;
                    }
                }
            } catch (SQLException e) {
                // if the error message is "out of memory",
                // it probably means no database file is found
//                System.err.println("queryWithParameters:" + e.getMessage());
            }
        }
        return true;
    }

    public static boolean signUp(String id, String password, String name) {
        if (con != null) {
            try {
                String SQL = "insert into users values(?,?,?);";
                String SQL2 = "insert into info (uid,name) values (?,?);";
                PreparedStatement pstmt = con.prepareStatement(SQL);
                PreparedStatement pstmt2 = con.prepareStatement(SQL2);
                pstmt2.setString(2,name);
                pstmt2.setString(1, id);
                pstmt.setString(1, id);
                pstmt.setString(2, password);
                pstmt.setString(3, name);
                pstmt.executeUpdate();
                pstmt2.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                // if the error message is "out of memory",
                // it probably means no database file is found
//                System.err.println("queryWithParameters:" + e.getMessage());
            }
        }
        return true;
    }

    public static void loadData() {
        if (con != null) {
            String sql = "select * from info;";
            try {
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next())
                    users.add(new UserInformation(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getInt(12), rs.getInt(13), rs.getInt(14), rs.getInt(15), rs.getInt(16), rs.getInt(17), rs.getInt(18)));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    public static void updateInfo(String col, String uid) {
        if (con != null) {
            String sql = "update info set " + col + "=+" + col + "+1 where uid=?;";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, uid);
                preparedStatement.executeUpdate();
                con.commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    public static void getInformation(String uid, int mode) {//1 means player,2means opsite
        if (con != null) {
            try {
                String SQL = "select * from info where uid = ?";
                PreparedStatement pstmt = con.prepareStatement(SQL);
                pstmt.setString(1, uid);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(Main.background, "Can't find the User!");
                } else {
                    if (mode == 1)
                        player = new UserInformation(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getInt(12), rs.getInt(13), rs.getInt(14), rs.getInt(15), rs.getInt(16), rs.getInt(17), rs.getInt(18));
                    else
                        opponent = new UserInformation(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getInt(12), rs.getInt(13), rs.getInt(14), rs.getInt(15), rs.getInt(16), rs.getInt(17), rs.getInt(18));

                }
            } catch (SQLException e) {
                // if the error message is "out of memory",
                // it probably means no database file is found
//                System.err.println("queryWithParameters:" + e.getMessage());
            }
        }
    }

    public static String[][] rankList(String dif) {
        String[][] result = new String[][]{{""}};
        if (con != null) {
            try {
                grades.clear();
                String SQL = "select user_id,name,grade_time,difficulty from grade join users u on u.user_id=grade.f_user_id where difficulty=?   order by grade_time;\n";
                PreparedStatement preparedStatement = con.prepareStatement(SQL);
                preparedStatement.setString(1, dif);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next())
                    grades.add(new Grade(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
//
                result = new String[grades.size()][4];
                for (int i = 0; i < grades.size(); i++) {
                    result[i][0] = grades.get(i).id;
                    result[i][1] = grades.get(i).name;
                    result[i][2] = grades.get(i).grade;
                    result[i][3] = grades.get(i).difficulty;
                }
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static String[][] rankList2(String dif) {
        String[][] result = new String[][]{{""}};
        Collections.sort(users, new Comparator<UserInformation>() {
            @Override
            public int compare(UserInformation o1, UserInformation o2) {
                return o2.exp - o1.exp;
            }
        });
        result = new String[users.size()][5];
        for (int i = 0; i < users.size(); i++) {
            result[i][0] = users.get(i).id;
            result[i][1] = users.get(i).name;
            result[i][2] = String.valueOf(users.get(i).level);
            result[i][3] = String.valueOf(users.get(i).exp);
            result[i][4] = (((String.format("%.2f",users.get(i).totalWinRate)).equals("NaN"))?"0":String.format("%.2f",users.get(i).totalWinRate))+"%";

        }
        return result;
    }

    public static boolean record(String id, String time, String model) {
        if (con != null) {
            try {
                String SQL = "insert into grade values(?,?,?)";
                PreparedStatement pstmt = con.prepareStatement(SQL);
                pstmt.setString(1, id);
                pstmt.setString(2, time);
                pstmt.setString(3, model);
                pstmt.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                // if the error message is "out of memory",
                // it probably means no database file is found
//                System.err.println("queryWithParameters:" + e.getMessage());
            }
        }
        return true;
    }

    public static void main(String[] args) {

        loadData();
        querySqlite_master();
        printPerson();
        querySqlite_master();
        closeDB();
    }
}
