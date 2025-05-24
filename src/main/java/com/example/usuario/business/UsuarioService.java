package com.example.usuario.business;

import com.example.usuario.business.converter.UsuarioConverter;
import com.example.usuario.business.dto.EnderecoDto;
import com.example.usuario.business.dto.TelefoneDto;
import com.example.usuario.business.dto.UsuarioDto;
import com.example.usuario.infrastructure.entity.Endereco;
import com.example.usuario.infrastructure.entity.Telefone;
import com.example.usuario.infrastructure.entity.Usuario;
import com.example.usuario.infrastructure.exceptions.ConflictExeptions;
import com.example.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.example.usuario.infrastructure.repository.EnderecoRepository;
import com.example.usuario.infrastructure.repository.TelefoneRepository;
import com.example.usuario.infrastructure.repository.UsuarioRepository;
import com.example.usuario.infrastructure.security.JwtUtil;
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

    public UsuarioDto salvaUsuario(UsuarioDto usuarioDto){
        emailExiste(usuarioDto.getEmail());
        usuarioDto.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDto);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDto(usuario);
    }


    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictExeptions("Email ja cadastrado" + email);
            }
        } catch (ConflictExeptions e){
            throw new ConflictExeptions("Email ja cadastrado" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDto buscarUsuarioPorEmail(String email){
        try{
            return usuarioConverter.paraUsuarioDto(usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email nao encontrado" + email)));
        } catch (ResourceNotFoundException e){
            throw new  ResourceNotFoundException("Email nao encontrado" + email);
        }

   }

    public void deletarUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDto atualizaDadosUsuario(UsuarioDto dto , String token){
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null );

        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email nao encontrado!"));

        Usuario usuario = usuarioConverter.updateUsuario(dto , usuarioEntity);

        return usuarioConverter.paraUsuarioDto(usuarioRepository.save(usuario));
    }


    public EnderecoDto atualizaEndereco(Long idEndereco , EnderecoDto enderecoDto){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("Id nao encontrado" + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDto , entity);

        return usuarioConverter.paraEnderecoDto(enderecoRepository.save(endereco));
    }

    public TelefoneDto atualizaTelefone(Long idTelefone , TelefoneDto telefoneDto){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("Id nao encontrado " + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(telefoneDto , entity);

        return usuarioConverter.paraTelefoneDto(telefoneRepository.save(telefone));
    }

}
