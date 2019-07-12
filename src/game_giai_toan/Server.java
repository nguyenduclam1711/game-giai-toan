/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_giai_toan;

import Model.ProjectStatus;
import Model.Question;
import Model.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    /*
     *Tập lưu trữ tất cả các writer của từng client
     *Giúp dễ dàng truyền tin nhắn
     */
    private static HashSet<ObjectOutputStream> writers = new HashSet<ObjectOutputStream>();

    /*
     *Tập lưu trữ tất cả username
     */
    private static HashSet<String> loginedUser = new HashSet<String>();
    /*
     * Port của server
     */
    private static final int PORT = 9999;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);

        try {
            while (true) {
                new Handler(server.accept()).start();
            }
        } finally {
            server.close();
        }
    }

    private static class Handler extends Thread {

        private Socket client;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        public Handler(Socket socket) {
            client = socket;
        }

        public ArrayList<User> UsersFromDB() {
            ArrayList<User> users = new ArrayList<>();
            User user1 = new User("", "");
            User user2 = new User("", "");
            users.add(user1);
            users.add(user2);

            try {
                String dbURL = "jdbc:mysql://localhost/account";
                String username = "root";
                String password = "";

                Connection conn = DriverManager.getConnection(dbURL, username, password);
                if (conn != null) {
                    System.out.println("Kết nối csdl thành công");
                }

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from account");

                int i = 0;
                while (rs.next()) {
                    users.get(i).setUserName(rs.getString(2));
                    users.get(i).setPassWord(rs.getString(3));
                    i++;
                }

            } catch (SQLException e) {
            }
            return users;
        }

        @Override
        public void run() {
            String userName = "";
            try {
                in = new ObjectInputStream(client.getInputStream());
                out = new ObjectOutputStream(client.getOutputStream());
                ArrayList<Question> questions = new ArrayList<>(); // random câu hỏi
                for (int i = 0; i < 2; i++) {
                    Question newQuest = new Question(0, 0, 0, 0);
                    Random rand = new Random();
                    newQuest.setOperation(rand.nextInt(4));
                    int operation = newQuest.getOperation();

                    switch (operation) {
                        case 0: // cong
                            newQuest.setA(rand.nextInt(100));
                            newQuest.setB(rand.nextInt(100));
                            newQuest.setResult(newQuest.getA() + newQuest.getB());
                            break;
                        case 1: // tru
                            newQuest.setA(rand.nextInt(100));
                            newQuest.setB(rand.nextInt(newQuest.getA()));
                            newQuest.setResult(newQuest.getA() - newQuest.getB());
                            break;
                        case 2: // nhan
                            newQuest.setA(rand.nextInt(50));
                            newQuest.setB(rand.nextInt(10));
                            newQuest.setResult(newQuest.getA() * newQuest.getB());
                            break;
                        case 3: // chia
                            newQuest.setB(rand.nextInt(10) + 1);
                            newQuest.setResult(rand.nextInt(50));
                            newQuest.setA(newQuest.getB() * newQuest.getResult());
                            break;
                    }
                    questions.add(newQuest);
                }
                for (int i = 0; i < questions.size(); i++) {
                    System.out.println(questions.get(i).toString());;
                }

                while (true) {
                    ArrayList<User> users = UsersFromDB();  // lấy thông tin user từ DB

                    User user = (User) in.readObject(); // nhận thông tin đăng nhập từ client
                    userName = user.getUserName();
                    
                    // kiểm tra thông tin đăng nhập
                    if ((user.getUserName().equals(users.get(0).getUserName()) && user.getPassWord().equals(users.get(0).getPassWord()))
                            || (user.getUserName().equals(users.get(1).getUserName()) && user.getPassWord().equals(users.get(1).getPassWord()))) {

                        if (!loginedUser.contains(user.getUserName())) {
                            loginedUser.add(userName);
                            System.out.println("SUCCESS");
                            out.writeObject(ProjectStatus.SUCCESS);
                            break;
                        } else {
                            System.out.println("ALREADY LOGINED");
                            out.writeObject(ProjectStatus.ALREADYLOGINED);
                        }
                    } else {
                        System.out.println("ERROR");
                        out.writeObject(ProjectStatus.ERROR);
                    }
                }
                
//                if (loginedUser.size() == 1) {
                    out.writeObject(ProjectStatus.STANDBY);
                    System.out.println("STAND BY PHASE");
//                }

                if (loginedUser.size() == 2) {
                    out.writeObject(ProjectStatus.PLAY);
                    out.writeObject(questions); // gửi câu hỏi    

                    ProjectStatus status = (ProjectStatus) in.readObject();
                    if (status == ProjectStatus.TIMEOUT) {
                        int score = (int) in.readObject();
                        out.writeObject(ProjectStatus.SENDRESULT);
                        System.out.println("SEND RESULT");
                        out.writeObject(score);
                    }
                }

//                out.writeObject(questions); // gửi câu hỏi    
//
//                ProjectStatus status = (ProjectStatus) in.readObject();
//                if (status == ProjectStatus.TIMEOUT) {
//                    int score = (int) in.readObject();
//                    out.writeObject(ProjectStatus.SENDRESULT);
//                    System.out.println("SEND RESULT");
//                    out.writeObject(score);
//                }
            } catch (Exception e) {
            }
//            finally {
//                if (userName != "") {
//                    loginedUser.remove(userName);
//                }
//                try {
//                    client.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
        }
    }
}
