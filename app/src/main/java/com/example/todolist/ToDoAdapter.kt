package com.example.todolist


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToDoAdapter(private val tasks: MutableList<String>, private val dbHelper: ToDoDatabaseHelper) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //ponteiro que transforma os itens do arquivo XML em Views programáveis
        val textViewTask: TextView = itemView.findViewById(R.id.textViewTask)
        val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false) //converte arquivo xml em uma hierarquia de objetos View programáveis
        return ToDoViewHolder(view)
    } //quando o ViewHolder é chamado, o layout da lista é inflado e uma nova instância do ToDoViewHolder é criada.

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) { //é chamado quando o RecyclerView precisa atualizar um item - quando ele é notificado
        val task = tasks[position] //define a posição da task específica que precisa ser alterada
        holder.textViewTask.text = task //atribue o texto para o xml do item

        holder.buttonDelete.setOnClickListener {//configura o OnClickListener para o buttonDelete
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                dbHelper.deleteTask(tasks[position])
                removeTask(position)
            }
        } //remove a tarefa do banco de dados
    }

    override fun getItemCount(): Int {
        return tasks.size
    } //retorna o número de itens da lista de tarefas

    fun addTask(task: String) {
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)
    } //adiciona uma nova tarefa à lista de tarefas e notifica o RecyclerView sobre a inserção

    fun setTasks(newTasks: List<String>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    } //atualiza a lista de tarefas (chamado quando o aplicativo é aberto)

    fun removeTask(position: Int) {
        tasks.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, tasks.size)
    } //remove uma tarefa e notifica o RecyclerView sobre a remoção

}