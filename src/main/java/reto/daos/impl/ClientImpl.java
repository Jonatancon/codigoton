package reto.daos.impl;

import reto.daos.ClientDao;
import reto.models.Client;
import reto.utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClientImpl extends ConnectionDB implements ClientDao {

    private PreparedStatement SQL;
    private ResultSet result;

    @Override
    public List<Client> findAll(String query) {
        Client client;
        List<Client> clientList = new ArrayList<>();
        Connection connection = connecDB();

        try{
            SQL = connection.prepareStatement(query);
            result = SQL.executeQuery();

            while (result.next()){
                client = new Client(result.getInt("id"), result.getString("code"), result.getByte("male"),
                        result.getInt("type"), result.getString("location"), result.getString("company"),
                        result.getByte("encrypt"), result.getBigDecimal("total"));
                clientList.add(client);
            }
        }catch (Exception e){
            System.out.println("No se pudo conectar a la base de datos"+e);
        }finally {
            try {
                connection.close();
            } catch (Exception error) {
                System.out.println("error: " + error);
            }
        }
        return clientList;
    }

}
