package com.example.jpa;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ContactService {
    private final ContactRepository repo;

    public ContactService(ContactRepository repo) { this.repo = repo; }

    public List<Contact> findAll() { return repo.findAll(); }
    public Optional<Contact> findById(Long id) { return repo.findById(id); }
    public Optional<Contact> findByEmail(String email) { return repo.findByEmail(email); }

    @Transactional
    public Contact save(Contact contact) { return repo.save(contact); }

    @Transactional
    public void delete(Long id) { repo.deleteById(id); }
}
