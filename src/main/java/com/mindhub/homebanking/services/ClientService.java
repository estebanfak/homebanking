package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;
import java.util.List;

public interface ClientService {
    List<ClientDTO> getAll();
    ClientDTO getClientById(Long id);
    ClientDTO getClientCurrent(Authentication authentication);
    Client getClientByEmail(String email);
    void saveClient(Client client);
}
