package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame{
    private JLabel labelName;
    private JLabel labelPassword;
    private JLabel labelIp;
    private JLabel labelPort;
    private JTextField userName;
    private JTextField userPassword;
    private JTextField ip;
    private JTextField port;
    private JTextArea fieldChat;
    private JTextArea message;
    private JButton btnLogin;
    private JButton btnSend;
    private boolean serverConnected;
    private ClientView clientView;

    public ClientGUI(ClientView clientView) {
        this.clientView = clientView;
        clientView.setClientGUI(this);
        initGUI();
        this.serverConnected = false;
    }

    private void initGUI() {
        setTitle("Client Chat");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(300, 200);
        setSize(400, 400);
        setLayout(new BorderLayout());

        add(topPanel(), BorderLayout.NORTH);
        add(chatField(), BorderLayout.CENTER);
        add(messageAndSend(), BorderLayout.SOUTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (serverConnected) {
                    clientView.disconnect();
                }
            }
        });

        setVisible(true);
    }

    private JPanel topPanel() {
        JPanel topPanel = new JPanel(new GridLayout(6, 2));
        labelName = new JLabel("User name: ");
        userName = new JTextField();
        labelPassword = new JLabel("User password:");
        userPassword = new JTextField();
        labelIp = new JLabel("IP address:");
        ip = new JTextField("127.0.0.1");
        labelPort = new JLabel("Port:");
        port = new JTextField("8080");
        btnLogin = new JButton("Login");

        topPanel.add(labelName);
        topPanel.add(userName);
        topPanel.add(labelPassword);
        topPanel.add(userPassword);
        topPanel.add(labelIp);
        topPanel.add(ip);
        topPanel.add(labelPort);
        topPanel.add(port);
        topPanel.add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!userName.getText().isEmpty() && !userPassword.getText().isEmpty()) {
                    clientView.checkLogin(userName.getText(), userPassword.getText());
                    serverConnected = clientView.connectService();
                    if (serverConnected) {
                        fieldChat.append("Вы успешно подключились!\n");
                    } else {
                        fieldChat.append("Сервер отключен или неверные учетные данные!\n");
                    }
                } else {
                    fieldChat.append("Поля имя и пароль не должны быть пустыми!\n");
                }
            }
        });

        return topPanel;
    }

    private JPanel chatField() {
        JPanel panel = new JPanel(new BorderLayout());
        fieldChat = new JTextArea(15, 35);
        fieldChat.setLineWrap(true);
        fieldChat.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(fieldChat);
        scrollPane.setPreferredSize(new Dimension(350, 250)); // Устанавливаем размер окна чата
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel messageAndSend() {
        JPanel panel = new JPanel(new BorderLayout());
        message = new JTextArea(3, 25);
        btnSend = new JButton("Отправить");

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serverConnected && !message.getText().isEmpty()) {
                    clientView.sendMessage(message.getText());
                    message.setText("");
                }
            }
        });

        panel.add(new JScrollPane(message), BorderLayout.CENTER);
        panel.add(btnSend, BorderLayout.EAST);

        return panel;
    }

    public void displayMessage(String message) {
        fieldChat.append(message + "\n");
    }
}
