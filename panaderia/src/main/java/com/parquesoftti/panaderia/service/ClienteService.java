package com.parquesoftti.panaderia.service;

import com.parquesoftti.panaderia.exception.ClienteNotFoundException;
import com.parquesoftti.panaderia.model.Cliente;
import com.parquesoftti.panaderia.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@AllArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;


    @Transactional(readOnly = true)
    public List<Cliente> getAllClients(){
        return clienteRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Cliente getClientByName(String name) {
        return clienteRepository.findByNombre(name)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con nombre: " + name));
    }
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public Cliente save(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public void deleteById(Long id){
        clienteRepository.deleteById(id);
    }

    public Cliente findById(Long id){
        return clienteRepository.findById(id).orElse(null);
    }


    public Cliente update(Long id,Cliente cliente){
        Cliente tmp= findById(id);

        if(tmp==null){
            throw new ClienteNotFoundException("Cliente no encontrado con ID: " + id);
        }

        tmp.setNombre(cliente.getNombre());
        tmp.setTelefono(cliente.getTelefono());
        return clienteRepository.save(tmp);
    }

}
