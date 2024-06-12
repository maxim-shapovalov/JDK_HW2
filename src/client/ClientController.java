package client;
import server.ServiceView;

public class ClientController implements ClientView{
    private ServiceView serviceView;
    private ClientGUI clientGUI;
    private String userName;
    private boolean serverConnected;

    public ClientController(ServiceView serviceView) {
        this.serviceView = serviceView;
        this.serverConnected = false;
    }

    @Override
    public void setClientGUI(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
    }

    @Override
    public void sendMessage(String message) {
        if (serverConnected) {
            String fullMessage = userName + ": " + message;
            serviceView.sendMessage(fullMessage);
        }
    }

    @Override
    public void receiveMessage(String message) {
        clientGUI.displayMessage(message);
    }

    @Override
    public void checkLogin(String userName, String password) {
        this.userName = userName;
        if (serviceView.serverConnected()) {
            serverConnected = serviceView.validateUser(userName, password);
            if (serverConnected) {
                serviceView.clientConnected(this);
                clientGUI.displayMessage("Вы успешно подключились!\n");
                clientGUI.displayMessage(serviceView.getChatHistory());
            } else {
                clientGUI.displayMessage("Неверные учетные данные!\n");
            }
        } else {
            clientGUI.displayMessage("Сервер отключен!\n");
        }
    }

    @Override
    public void disconnect() {
        if (serverConnected) {
            serviceView.clientDisconnected(this);
            serverConnected = false;
        }
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public boolean connectService() {
        return serverConnected && serviceView.serverConnected();
    }
}
