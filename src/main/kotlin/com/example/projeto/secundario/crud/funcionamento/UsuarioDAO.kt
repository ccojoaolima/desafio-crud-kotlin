package com.example.projeto.secundario.crud.funcionamento

import funcionamento.UsuarioData
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class UsuarioDAO {
    val url = "jdbc:postgresql://"
    var sql:String = ""

    fun conectarComDB(): Connection?{
        var conexao : Connection? = null
        try {
            //Class.forName("org.postgresql.Driver")
            conexao = DriverManager.getConnection("jdbc:postgresql://"+ "ec2-54-85-113-73.compute-1.amazonaws.com"
                    +":" + "5432" +"/"+ "d8fl16q7sbepu7",
                "trtoeuoapaghay",
                "7950e8a122a8c659873ec01c9bb6a5640013c1d30a5f5c7f10a7c3f2ea6621e1")
        } catch (e: Exception) {
            e.printStackTrace()
            println("não foi possível conectar com o banco")
        }
        return conexao
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun inserirUsuario(usuarioData: UsuarioData){
        try {
            val sql = "INSERT INTO usuario(nome, email, telefone, estado_civil, data_nasc, " +
                "senha) VALUES(?, ?, ?, ?, ?, ?);"

            val prepareQuery = conectarComDB()?.prepareStatement(sql)
            var i: Int = 1
            prepareQuery?.setString(i++, usuarioData.nome)
            prepareQuery?.setString(i++, usuarioData.email)
            prepareQuery?.setString(i++, usuarioData.telefone)
            prepareQuery?.setString(i++, usuarioData.estadoCivil)
            prepareQuery?.setString(i++, usuarioData.dataNasc)
            prepareQuery?.setString(i++, usuarioData.senha)
            prepareQuery?.execute()
            conectarComDB()?.close()
        }
        catch (e: Exception) {
        e.printStackTrace()
        println("não foi possível conectar com o banco")
        }
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun selecionarUsuario(listaUsers: HashMap<String, String>): List<UsuarioData> {

        val listaFinalResult: MutableList<UsuarioData> = mutableListOf()

        try {
            var sql = String.format("SELECT * FROM usuario ")
            for (key in listaUsers.keys){
                if(key != ""){
                    if(!sql.contains("WHERE")){
                        sql += String.format("WHERE %s = '%s' ", key, listaUsers[key])
                    }else {
                        sql += String.format("AND %s = '%s' ", key, listaUsers[key])
                    }
                }
            }

            sql += String.format(" ORDER BY id_usuario LIMIT 10")

            val prepareQuery = conectarComDB()?.prepareStatement(sql)
            val rs = prepareQuery?.executeQuery()

            while (rs!!.next()) {
                listaFinalResult.add(
                    UsuarioData(
                        id = rs.getInt("id_usuario"),
                        nome = rs.getString("nome"),
                        telefone = rs.getString("telefone"),
                        email = rs.getString("email"),
                        estadoCivil = rs.getString("estado_civil"),
                        dataNasc = rs.getString("data_nasc"),
                        senha = rs.getString("senha")
                    )
                )
                conectarComDB()?.close()
            }
        }catch (e: Exception) {
            e.printStackTrace()
            println("não foi possível conectar com o banco")
        }
        return listaFinalResult;
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun deleteUsuario(usuarioData: UsuarioData){
        try {
            val sql = "DELETE FROM usuario WHERE nome = '${usuarioData.nome}';"
            println(sql)
            val prepareQuery = conectarComDB()?.prepareStatement(sql)
            prepareQuery?.execute()
            conectarComDB()?.close()
        }catch (e: Exception) {
            e.printStackTrace()
            println("Não foi possível conectar com o banco")
        }
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun updateUsuario(listaUsers: HashMap<String, String>, nomeUser: String){
        val listaValues: MutableList<String> = mutableListOf()
        try{
            for (key in listaUsers.keys){
                listaValues.add(when(key){
                    "email" -> "email = '${listaUsers[key]}' "
                    "telefone" -> " telefone = '${listaUsers[key]}' "
                    "estado_civil" -> " estado_civil = '${listaUsers[key]}' "
                    else -> ""
                })

            }

            val camposListaValues = listaValues.toString().replace("[", "").replace("]", "")
            val sql = "UPDATE usuario SET ${camposListaValues} WHERE id_usuario = (SELECT id_usuario FROM usuario WHERE nome = '$nomeUser')"

            val prepareQuery = conectarComDB()?.prepareStatement(sql)

            prepareQuery?.execute()
            conectarComDB()?.close()

        }
        catch (e: Exception) {
            e.printStackTrace()
            println("não foi possível conectar com o banco")
        }
    }

}