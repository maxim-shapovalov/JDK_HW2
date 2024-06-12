package client;

public interface ClientView {
    void setClientGUI(ClientGUI clientGUI);
    void sendMessage(String message);
    void receiveMessage(String message);
    void checkLogin(String userName, String password);
    void disconnect();
    boolean connectService();
    String getUserName();
}
