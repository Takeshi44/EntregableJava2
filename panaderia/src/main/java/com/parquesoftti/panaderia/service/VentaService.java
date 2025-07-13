package com.parquesoftti.panaderia.service;

import com.parquesoftti.panaderia.exception.VentaNotFoundException;
import com.parquesoftti.panaderia.model.Venta;
import com.parquesoftti.panaderia.repository.VentaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class VentaService {

    private final VentaRepository ventaRepository;

    @Transactional(readOnly = true)
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Venta findById(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public Venta save(Venta venta) {
        return ventaRepository.save(venta);
    }

    public void deleteById(Long id) {
        ventaRepository.deleteById(id);
    }

    public Venta update(Long id, Venta venta) {
        Venta existente = findById(id);

        if (existente == null) {
            throw new VentaNotFoundException("Venta no encontrada con ID: " + id);
        }

        existente.setCantidad(venta.getCantidad());
        existente.setFecha(venta.getFecha());
        existente.setCliente(venta.getCliente());
        existente.setProducto(venta.getProducto());

        return ventaRepository.save(existente);
    }
}
