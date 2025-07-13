package com.parquesoftti.panaderia.controller;

import com.parquesoftti.panaderia.model.Producto;
import com.parquesoftti.panaderia.service.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductoController {

    private final ProductoService productoService;

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProducts() {
        List<Producto> productos = productoService.getAllProducts();
        return ResponseEntity.ok(productos);
    }

    // Obtener producto por nombre
    @GetMapping("/name")
    public ResponseEntity<Producto> getProductByName(@RequestParam String name) {
        Producto producto = productoService.getProductByName(name);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado con nombre: " + name);
        }
        return ResponseEntity.ok(producto);
    }

    // Eliminar producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<Producto> save(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.save(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    // Actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> update(@PathVariable Long id, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.update(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }
}