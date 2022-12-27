package com.snapp.boxdemo.service;

import com.snapp.boxdemo.mapper.ClientMapper;
import com.snapp.boxdemo.model.dto.ClientDto;
import com.snapp.boxdemo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper = ClientMapper.INSTANCE;

    @Override
    public ClientDto getClient(long id) {
        return repository.findById(id).map(mapper::clientToClientDto).orElse(null);
    }

    @Override
    public void removeClient(long id) {
        repository.deleteById(id);
    }

    @Override
    public ClientDto getClientByMail(String mail) {
        return mapper.clientToClientDto(repository.findByEmail(mail).orElse(null));
    }

    @Override
    public ClientDto updateClient(ClientDto dto) {
        return mapper.clientToClientDto(repository.save(mapper.clientDtoToClient(dto)));
    }

    @Override
    public ClientDto saveClient(ClientDto dto) {
        return mapper.clientToClientDto(repository.save(mapper.clientDtoToClient(dto)));
    }

    @Override
    public boolean exist(long id) {
        return repository.existsById(id);
    }
}
