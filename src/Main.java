import client.ClientController;
import client.ClientGUI;
import data_base_client.DataBaseClients;
import data_base_client.Repository;
import server.ServerWindow;
import server.ServiceServer;

public class Main {

        public static void main(String[] args) {
            Repository database = new DataBaseClients();
            ServerWindow serverWindow = new ServerWindow();
            ServiceServer serviceServer = new ServiceServer(database, serverWindow);

            database.addClient("user", "password");

            ClientController clientController1 = new ClientController(serverWindow);
            new ClientGUI(clientController1);

            ClientController clientController2 = new ClientController(serverWindow);
            new ClientGUI(clientController2);
        }

}
