package com.javanauta.usuario.business;

import com.javanauta.usuario.business.converter.UsuarioConverter;
import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.exceptions.ConflictException;
import com.javanauta.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.repository.EnderecoRepository;
import com.javanauta.usuario.infrastructure.repository.TelefoneRepository;
import com.javanauta.usuario.infrastructure.repository.UsuarioRepository;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        boolean existe = verificaEmailExistente(email);
        if (existe) {
            throw new ConflictException("Email já cadastrado" + email);
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }


    public Usuario buscaUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow
                (() -> new ResourceNotFoundException("Email não encontrado" + email));
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email));

        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity);

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(
                usuarioDTO.getSenha()) : null);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));


    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {
        Endereco enderecoEntity = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado " + idEndereco));
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, enderecoEntity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {
        Telefone telefoneEntity = telefoneRepository.findById(idTelefone).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado " + idTelefone)
        );
        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, telefoneEntity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO enderecoDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(" Email não encontrado " + email));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(enderecoDTO, usuario.getId());

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }

    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO telefoneDTO){

        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado " + email));
        Telefone telefone = usuarioConverter.paraTelefoneEntity(telefoneDTO, usuario.getId());


        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));

    }



}
