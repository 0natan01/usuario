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
    public Usuario paraUsuario(UsuarioDto usuarioDto) {
        return Usuario.builder()
                .nome(usuarioDto.getNome())
                .email(usuarioDto.getEmail())
                .senha(usuarioDto.getSenha())
                .enderecos(paraListaEndereco(usuarioDto.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDto.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDto> enderecoDtos) {
        return enderecoDtos.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDto enderecoDto) {
        return Endereco.builder()
                .rua(enderecoDto.getRua())
                .numero(enderecoDto.getNumero())
                .cidade(enderecoDto.getCidade())
                .estado(enderecoDto.getEstado())
                .complemento(enderecoDto.getComplemento())
                .cep(enderecoDto.getCep())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDto> telefoneDtos) {
        return telefoneDtos.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDto telefoneDto) {
        return Telefone.builder()
                .ddd(telefoneDto.getDdd())
                .numero(telefoneDto.getNumero())
                .build();
    }

    public UsuarioDto paraUsuarioDto(Usuario usuario) {
        return UsuarioDto.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDto(usuario.getEnderecos()))
                .telefones(paraListaTelefoneDto(usuario.getTelefones()))
                .build();
    }

    public List<EnderecoDto> paraListaEnderecoDto(List<Endereco> endereco) {
        return endereco.stream().map(this::paraEnderecoDto).toList();
    }

    public EnderecoDto paraEnderecoDto(Endereco endereco) {
        return EnderecoDto.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .build();
    }

    public List<TelefoneDto> paraListaTelefoneDto(List<Telefone> telefone) {
        return telefone.stream().map(this::paraTelefoneDto).toList();
    }

    public TelefoneDto paraTelefoneDto(Telefone telefone) {
        return TelefoneDto.builder()
                .id(telefone.getId())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }

    public Usuario updateUsuario(UsuarioDto usuarioDto, Usuario entity) {
        return Usuario.builder()
                .nome(usuarioDto.getNome() != null ? usuarioDto.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(usuarioDto.getSenha() != null ? usuarioDto.getSenha() : entity.getSenha())
                .email(usuarioDto.getEmail() != null ? usuarioDto.getEmail() : entity.getEmail())
                .telefones(entity.getTelefones())
                .enderecos(entity.getEnderecos())
                .build();
    }


    public Endereco updateEndereco(EnderecoDto dto, Endereco entity) {
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .build();
    }


    public Telefone updateTelefone(TelefoneDto dto, Telefone entity) {
        return Telefone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .build();
    }

    public Endereco paraEnderecoEntity(EnderecoDto dto , Long idUsuario ){
        return Endereco.builder()
                .complemento(dto.getComplemento())
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .usuario_id(idUsuario)
                .build();
    }

    public Telefone paraTelefoneEntity(TelefoneDto dto , Long idUsuario){
        return Telefone.builder()
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .usuario_id(idUsuario)
                .build();
    }
}

