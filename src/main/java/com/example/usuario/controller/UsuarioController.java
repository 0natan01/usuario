package com.example.usuario.controller;

import com.example.usuario.business.UsuarioService;
import com.example.usuario.business.dto.EnderecoDto;
import com.example.usuario.business.dto.TelefoneDto;
import com.example.usuario.business.dto.UsuarioDto;

import com.example.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<UsuarioDto> salvaUsuario(@RequestBody UsuarioDto usuarioDto){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDto));
    }


    @PostMapping("/login")
    public String login(@RequestBody UsuarioDto usuarioDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDto.getEmail() , usuarioDto.getSenha())
        );
        return "Bearer" +  jwtUtil.generateToken(authentication.getName());
    }


    @GetMapping
    public ResponseEntity<UsuarioDto> buscaUsuarioPorEmail(@RequestParam String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

   @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email){
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
   }

   @PutMapping
    public ResponseEntity<UsuarioDto> atualizaDadosUsuario(@RequestBody UsuarioDto dto ,
                                                           @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(dto , token));
   }

   @PutMapping("/endereco")
    public ResponseEntity<EnderecoDto> atualizaEndereco(@RequestBody EnderecoDto dto,
                                                        @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto));
   }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDto> atualizaTelefone(@RequestBody TelefoneDto dto,
                                                        @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));
    }

    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDto> cadastroEndereco(@RequestBody EnderecoDto dto,
                                                       @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastroEndereco(token , dto));
    }

    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDto> cadastroTelefone(@RequestBody TelefoneDto dto,
                                                        @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastroTelefone(token , dto));
    }
}
