package reto.daos;

import reto.models.Client;

import java.util.List;

public interface ClientDao {

    List<Client> findAll(String query);
}
