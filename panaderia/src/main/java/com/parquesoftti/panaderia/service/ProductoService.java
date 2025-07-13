package com.parquesoftti.panaderia.service;

import com.parquesoftti.panaderia.exception.ProductoNotFoundException;
import com.parquesoftti.panaderia.model.Producto;
import com.parquesoftti.panaderia.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Producto> getAllProducts() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Producto getProductByName(String name) {
        // Si no tienes findByNombre en el repo, reemplázalo con búsqueda manual
        return productoRepository.findAll().stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con nombre: " + name));
    }

    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto update(Long id, Producto producto) {
        Producto existente = findById(id);

        if (existente == null) {
            throw new ProductoNotFoundException("Producto no encontrado con ID: " + id);
        }

        existente.setNombre(producto.getNombre());
        existente.setPrecio(producto.getPrecio());
        existente.setStock(producto.getStock());

        return productoRepository.save(existente);
    }
}
