package data_base_client;

public interface Repository {
    void addClient(String name, String password);
    boolean validateClient(String name, String password);
}
