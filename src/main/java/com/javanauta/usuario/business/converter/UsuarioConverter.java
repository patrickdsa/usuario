package com.javanauta.usuario.business.converter;

import com.javanauta.usuario.business.dto.EnderecoDTO;

import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDTO.getTelefones()))
                .build();
    }


public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS) {
    return enderecoDTOS.stream().map(this::paraEndereco).toList();
}

public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
    return Endereco.builder()
            .rua(enderecoDTO.getRua())
            .complemento(enderecoDTO.getComplemento())
            .numero(enderecoDTO.getNumero())
            .estado(enderecoDTO.getEstado())
            .cidade(enderecoDTO.getCidade())
            .cep(enderecoDTO.getCep())
            .build();
}

public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS){
    return telefoneDTOS.stream().map(this::paraTelefone).toList();
}

public Telefone paraTelefone (TelefoneDTO telefoneDTO){
    return Telefone.builder()
            .ddd(telefoneDTO.getDdd())
            .numero(telefoneDTO.getNumero())
            .build();
}

public UsuarioDTO paraUsuarioDTO(Usuario usuarioDTO) {
    return UsuarioDTO.builder()
            .nome(usuarioDTO.getNome())
            .email(usuarioDTO.getEmail())
            .senha(usuarioDTO.getSenha())
            .enderecos(paraListaEnderecoDTO(usuarioDTO.getEnderecos()))
            .telefones(paraListaTelefoneDTO(usuarioDTO.getTelefones()))
            .build();
}


public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos) {
    return enderecos.stream().map(this::paraEnderecoDTO).toList();
}

public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
    return EnderecoDTO.builder()
            .rua(endereco.getRua())
            .complemento(endereco.getComplemento())
            .numero(endereco.getNumero())
            .estado(endereco.getEstado())
            .cidade(endereco.getCidade())
            .cep(endereco.getCep())
            .build();
}

public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefones){
    return telefones.stream().map(this::paraTelefoneDTO).toList();
}

public TelefoneDTO paraTelefoneDTO (Telefone telefone){
    return TelefoneDTO.builder()
            .ddd(telefone.getDdd())
            .numero(telefone.getNumero())
            .build();
}
}


