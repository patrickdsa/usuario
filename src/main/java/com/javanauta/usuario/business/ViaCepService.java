package com.javanauta.usuario.business;

import com.javanauta.usuario.business.dto.ViaCepDTO;
import com.javanauta.usuario.infrastructure.clients.ViaCepClient;
import com.javanauta.usuario.infrastructure.exceptions.IllegalArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor

public class ViaCepService {

    private final ViaCepClient viaCepClient;

    public ViaCepDTO buscaDadosEndereco (String cep){
        try{
            return viaCepClient.buscaDadosEndereco(processarCep(cep));
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Erro: ", e);
        }
    }

    private String processarCep(String cep){
        String cepFormatado = cep.replace(" ", "")
                .replace("-", "");

        if(!cepFormatado.matches("\\d+") || !Objects.equals(cepFormatado.length(), 8) ){
            throw new IllegalArgumentException("O cep contém catacteres inválidos");
        }

        return cepFormatado;
    }
}
