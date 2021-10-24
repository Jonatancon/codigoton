package reto.service;

import reto.daos.ClientDao;
import reto.daos.impl.ClientImpl;
import reto.models.Client;

import java.util.List;

public class ClientService {

    private ClientDao clientDao;

    public ClientService() {
        this.clientDao = new ClientImpl();
    }

    public List<Client> findAllByQuery(String query) {
        return clientDao.findAll(query);
    }
}
