package server;
import client.ClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame implements ServiceView {
    private JTextArea textField;
    private JButton btnStart;
    private JButton btnStop;
    private boolean serverConnected;
    private List<String> chatHistory;
    private List<ClientView> connectedClients;

    public ServerWindow() {
        chatHistory = new ArrayList<>();
        connectedClients = new ArrayList<>();
        initGUI();
        this.serverConnected = false;
    }

    private void initGUI() {
        setTitle("Server Chat");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(700, 200);
        setSize(400, 400);
        setLayout(new BorderLayout());

        add(controlPanel(), BorderLayout.NORTH);
        add(chatField(), BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel controlPanel() {
        JPanel panel = new JPanel();
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("Сервер запущен!\n");
                serverConnected = true;
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.append("Сервер остановлен!\n");
                serverConnected = false;
                notifyAllClients("Сервер остановлен");
                clearChat();
            }
        });

        panel.add(btnStart);
        panel.add(btnStop);

        return panel;
    }

    private JPanel chatField() {
        JPanel panel = new JPanel();
        textField = new JTextArea(15, 35);
        textField.setLineWrap(true);
        textField.setEditable(false);
        JScrollPane area = new JScrollPane(textField);
        panel.add(area);
        return panel;
    }

    @Override
    public boolean serverConnected() {
        return serverConnected;
    }

    @Override
    public void clientConnected(ClientView client) {
        if (serverConnected) {
            String message = client.getUserName() + " подключился.";
            textField.append(message + "\n");
            chatHistory.add(message);
            connectedClients.add(client);
            broadcastMessage(message);
        }
    }

    @Override
    public void sendMessage(String message) {
        textField.append(message + "\n");
        chatHistory.add(message);
        broadcastMessage(message);
    }

    @Override
    public boolean validateUser(String userName, String password) {
        // Предполагается, что все пользователи валидны для этого примера
        return true;
    }

    @Override
    public String getChatHistory() {
        StringBuilder history = new StringBuilder();
        for (String msg : chatHistory) {
            history.append(msg).append("\n");
        }
        return history.toString();
    }

    @Override
    public void clientDisconnected(ClientView client) {
        if (serverConnected) {
            String message = client.getUserName() + " отсоединился.";
            textField.append(message + "\n");
            chatHistory.add(message);
            connectedClients.remove(client);
            broadcastMessage(message);
        }
    }

    private void broadcastMessage(String message) {
        for (ClientView client : connectedClients) {
            client.receiveMessage(message);
        }
    }

    private void clearChat() {
        chatHistory.clear();
    }

    private void notifyAllClients(String message) {
        for (ClientView client : connectedClients) {
            client.receiveMessage(message);
            client.receiveMessage("Соединение разорвано.");
        }
    }
}
