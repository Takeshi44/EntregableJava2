package com.parquesoftti.panaderia.controller;

import com.parquesoftti.panaderia.model.Cliente;
import com.parquesoftti.panaderia.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/client")
public class ClienteController {

    private final ClienteService clienteService;

    // Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClients() {
        List<Cliente> clientes = clienteService.getAllClients();
        return ResponseEntity.ok(clientes);
    }

    // Obtener cliente por nombre
    @GetMapping("/name")
    public ResponseEntity<Cliente> getClientByName(@RequestParam String name) {
        Cliente cliente = clienteService.getClientByName(name);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con nombre: " + name);  // Puedes crear una excepción específica si quieres
        }
        return ResponseEntity.ok(cliente);
    }

    // Eliminar cliente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        clienteService.deleteById(id);  // Si falla, lanzará la excepción y el ControllerAdvice la manejará
        return ResponseEntity.noContent().build();
    }

    // Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.save(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }

    // Actualizar un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente clienteActualizado = clienteService.update(id, cliente);
        return ResponseEntity.ok(clienteActualizado);
    }
}