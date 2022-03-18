package com.example.projeto.secundario.crud.funcionamento

import funcionamento.UsuarioData
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/usuarios")
class UsuarioConnection {

    private var conexaoBD : UsuarioController = UsuarioController()

    @PostMapping
    fun postUsers(@RequestBody usuarioData: UsuarioData) : String{
      return conexaoBD.postUsers(usuarioData)
    }

    @GetMapping
    fun getUsers(@RequestBody usuarioData:UsuarioData): String{
        return conexaoBD.getUsers(usuarioData)
    }

    @DeleteMapping
    fun deleteUser(@RequestBody usuarioData: UsuarioData) : String{
        conexaoBD.deleteUser(usuarioData)
        return String.format("Deletado com sucesso")
    }

    @PutMapping
    fun putUser( @RequestBody usuarioData: UsuarioData): String{
        return conexaoBD.putUser(usuarioData)
    }



}