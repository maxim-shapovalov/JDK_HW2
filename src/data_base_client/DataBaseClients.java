package data_base_client;
import java.util.HashMap;
import java.util.Map;

public class DataBaseClients implements Repository{
    private Map<String, String> clients;

    public DataBaseClients() {
        clients = new HashMap<>();
    }

    @Override
    public void addClient(String name, String password) {
        clients.put(name, password);
    }

    @Override
    public boolean validateClient(String name, String password) {
        return clients.containsKey(name) && clients.get(name).equals(password);
    }
}
