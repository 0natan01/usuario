package com.example.usuario.business.converter;

import com.example.usuario.business.dto.EnderecoDto;
import com.example.usuario.business.dto.TelefoneDto;
import com.example.usuario.business.dto.UsuarioDto;
import com.example.usuario.infrastructure.entity.Endereco;
import com.example.usuario.infrastructure.entity.Telefone;
import com.example.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {
    public Usuario paraUsuario(UsuarioDto usuarioDto){
        return Usuario.builder()
                .nome(usuarioDto.getNome())
                .email(usuarioDto.getEmail())
                .senha(usuarioDto.getSenha())
                .enderecos(paraListaEndereco(usuarioDto.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDto.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDto> enderecoDtos){
        return enderecoDtos.stream().map(this::paraEndereco).toList();
    }

    public  Endereco paraEndereco(EnderecoDto enderecoDto){
        return Endereco.builder()
                .rua(enderecoDto.getRua())
                .numero(enderecoDto.getNumero())
                .cidade(enderecoDto.getCidade())
                .estado(enderecoDto.getEstado())
                .complemento(enderecoDto.getComplemento())
                .cep(enderecoDto.getCep())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDto> telefoneDtos){
        return telefoneDtos.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDto telefoneDto){
        return Telefone.builder()
                .ddd(telefoneDto.getDdd())
                .numero(telefoneDto.getNumero())
                .build();
    }

    public UsuarioDto paraUsuarioDto(Usuario usuarioDto){
        return UsuarioDto.builder()
                .nome(usuarioDto.getNome())
                .email(usuarioDto.getEmail())
                .senha(usuarioDto.getSenha())
                .enderecos(paraListaEnderecoDto(usuarioDto.getEnderecos()))
                .telefones(paraListaTelefoneDto(usuarioDto.getTelefones()))
                .build();
    }

    public List<EnderecoDto> paraListaEnderecoDto(List<Endereco> enderecoDtos){
        return enderecoDtos.stream().map(this::paraEnderecoDto).toList();
    }

    public  EnderecoDto paraEnderecoDto(Endereco enderecoDto){
        return EnderecoDto.builder()
                .rua(enderecoDto.getRua())
                .numero(enderecoDto.getNumero())
                .cidade(enderecoDto.getCidade())
                .estado(enderecoDto.getEstado())
                .complemento(enderecoDto.getComplemento())
                .cep(enderecoDto.getCep())
                .build();
    }

    public List<TelefoneDto> paraListaTelefoneDto(List<Telefone> telefoneDto){
        return telefoneDto.stream().map(this::paraTelefoneDto).toList();
    }

    public TelefoneDto paraTelefoneDto(Telefone telefoneDto){
        return TelefoneDto.builder()
                .ddd(telefoneDto.getDdd())
                .numero(telefoneDto.getNumero())
                .build();
    }

    public Usuario updateUsuario(UsuarioDto usuarioDto , Usuario entity){
        return Usuario.builder()
                .nome(usuarioDto.getNome() != null ? usuarioDto.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(usuarioDto.getSenha() != null ? usuarioDto.getSenha() : entity.getSenha())
                .email(usuarioDto.getEmail() != null ? usuarioDto.getEmail() : entity.getEmail())
                .telefones(entity.getTelefones())
                .enderecos(entity.getEnderecos())
                .build();
    }
}

