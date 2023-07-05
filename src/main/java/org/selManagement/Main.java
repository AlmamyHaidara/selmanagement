package org.selManagement;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import org.selManagement.Vue.Dashboard;
import org.selManagement.Vue.LoginForm;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                LoginForm login = new LoginForm();
//                login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                login.addWindowListener(new WindowAdapter() {
//                    public void windowClosing(WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                login.setVisible(true);

                Dashboard dashboard = new Dashboard(1);
                dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dashboard.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                dashboard.setVisible(true);
            }
        });
    }
}
