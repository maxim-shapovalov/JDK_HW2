package server;

import data_base_client.Repository;

public class ServiceServer {
    private Repository repository;
    private ServiceView serviceView;

    public ServiceServer(Repository repository, ServiceView serviceView) {
        this.repository = repository;
        this.serviceView = serviceView;
    }

    public boolean connectServer() {
        return serviceView.serverConnected();
    }

    public void showMessageServerWindow(String txt) {
        serviceView.sendMessage(txt);
    }
}
