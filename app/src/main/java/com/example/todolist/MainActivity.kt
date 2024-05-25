package com.example.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //cria um binding do activity_main.xml para kotlin - elimina a necessidade do findViewById() - deve ser ativado no build.gradle
    private lateinit var adapter: ToDoAdapter //fornece os dados para a visualização kotlin -> xml
    private lateinit var dbHelper: ToDoDatabaseHelper //facilita a interação com o SQLite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //converte arquivo activity_main.xml em uma hierarquia de objetos View programáveis
        setContentView(binding.root) //define o layout inflado como o conteúdo da atividade

        dbHelper = ToDoDatabaseHelper(this) //instancia a classe ToDoDatabaseHelper
        adapter = ToDoAdapter(mutableListOf(), dbHelper) //instancia a classe ToDoAdapter
        //mutableList: lista inicialmente vazia de tamanho variável sobre a qual podem ser feitas operações como adicionar, remover, substituir, acessar e iterar

        binding.recyclerView.adapter = adapter //define que `adapter` é o adaptador que adaptará para o recyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this) //define o layoutManager padrão para o recyclerView

        binding.buttonAdd.setOnClickListener {//setOnClickListener: escuta o evento de um botão (buttonAdd) sendo clicado
            val task = binding.editTextTask.text.toString() //transforma o texto da task (editTextTask) em uma string e o armazena em um valor
            if (task.isNotEmpty()) {
                dbHelper.addTask(task)
                adapter.addTask(task)
                binding.editTextTask.text.clear()
            } //adiciona a task no banco de dados e no adaptador
        }

        //binding: referencia o arquivo XML
        //layoutInflater: referencia os itens do arquivo XML
        //recyclerView, buttonAdd e editTextTask são IDs no arquivo XML

        adapter.setTasks(dbHelper.getTasks()) //inicializa o banco de dados
    }
}