package com.example.jpa;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    private final ContactService service;

    public ContactController(ContactService service) { this.service = service; }

    @GetMapping
    public List<Contact> list() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> get(@PathVariable Long id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Contact create(@RequestBody Contact contact) { return service.save(contact); }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> update(@PathVariable Long id, @RequestBody Contact contact) {
        if (!service.findById(id).isPresent()) return ResponseEntity.notFound().build();
        contact.setId(id);
        return ResponseEntity.ok(service.save(contact));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
