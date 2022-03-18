package com.example.projeto.secundario.crud.funcionamento

import funcionamento.UsuarioData
import com.google.gson.Gson
class UsuarioController {
    fun getUsers(usuarioData: UsuarioData): String {

        var listaUsers: HashMap<String, String> = hashMapOf()

        if(usuarioData.id != 0){
            listaUsers["id_usuario"] = usuarioData.id.toString()
        }
        if(usuarioData.nome != ""){
            listaUsers["nome"] = usuarioData.nome;
        }
        if(usuarioData.dataNasc != ""){
            listaUsers["data_nasc"] = usuarioData.dataNasc
        }
        if(usuarioData.telefone != ""){
            listaUsers["telefone"] = usuarioData.telefone
        }
        if(usuarioData.estadoCivil != ""){
            listaUsers["estado_civil"] = usuarioData.estadoCivil
        }

        val dataUserInfos: List<UsuarioData> = UsuarioDAO().selecionarUsuario(listaUsers);
        val definitiveUserData = Gson()

        return definitiveUserData.toJson(dataUserInfos);
    }

    fun postUsers(usuarioData: UsuarioData): String{

        if(usuarioData.nome == ""){
            return "Favor inserir um nome"
        }
        if(usuarioData.telefone == ""){
            return "Favor inserir um telefone"
        }
        if(usuarioData.email == ""){
            return "Favor inserir um email válido"
        }
        if(usuarioData.estadoCivil == ""){
           return "Favor inserir estado civil"
        }
        if(usuarioData.senha == ""){
            return "Favor inserir uma senha"
        }

        UsuarioDAO().inserirUsuario(usuarioData)
        return "Usuário inserido com êxito"
    }

    fun deleteUser(usuarioData: UsuarioData): String{
        if(usuarioData.nome == ""){
           return "Favor inserir um nome para realizar a deleção"
        }
        UsuarioDAO().deleteUsuario(usuarioData)
        return "Usuário deletado com êxito!"
    }

    fun putUser(usuarioData: UsuarioData): String{
        var paramUsado: String = ""
        var listaUsers: HashMap<String, String> = hashMapOf()

        if(usuarioData.estadoCivil != ""){
            paramUsado += "\nEstado Civil "
            listaUsers["estado_civil"] = usuarioData.estadoCivil
        }
        if(usuarioData.email != ""){
            paramUsado += "\nEmail "
            listaUsers["email"] = usuarioData.email
        }
        if(usuarioData.telefone != ""){
            paramUsado += "\nTelefone "
            listaUsers["telefone"] = usuarioData.telefone
        }

        if(usuarioData.senha != "" || usuarioData.dataNasc != ""){
            return String.format("Campos inválidos para alteração.")
        }

        UsuarioDAO().updateUsuario(listaUsers, usuarioData.nome);
        return String.format("Os campos: %s foram alterados com sucesso", paramUsado)
    }


}