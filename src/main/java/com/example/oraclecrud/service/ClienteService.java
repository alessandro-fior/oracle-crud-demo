package com.example.oraclecrud.service;

import com.example.oraclecrud.model.Cliente;
import com.example.oraclecrud.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente create(Cliente cliente) {
        return repository.save(cliente);
    }

    public List<Cliente> list() {
        return repository.findAll();
    }

    public Cliente get(Long id) {
        return repository.findById(id);
    }

    public int update(Cliente cliente) {
        return repository.update(cliente);
    }

    public int delete(Long id) {
        return repository.delete(id);
    }
}
