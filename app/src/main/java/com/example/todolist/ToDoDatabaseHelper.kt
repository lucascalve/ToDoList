package com.example.todolist
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ToDoDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object { //contém informações sobre o banco de dados
        private const val DATABASE_NAME = "todolist.db" //nome do banco de dados
        private const val DATABASE_VERSION = 1

        private const val TABLE_TASKS = "tasks" //tabela "tasks"
        private const val COLUMN_ID = "id" //tasks/coluna "id"
        private const val COLUMN_TASK = "task" //tasks/coluna "task"

        private const val SQL_CREATE_ENTRIES = //código para a criação da tabela a ser invocado no onCreate
            "CREATE TABLE $TABLE_TASKS (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_TASK TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES) //executa o método SQL_CREATE_ENTRIES
    } //cria a tabela de tarefas quando o banco de dados é criado pela primeira vez

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    } //exclui a tabela existente e cria uma nova quando a versão do banco de dados é atualizada

    fun addTask(task: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TASK, task)
        }
        db.insert(TABLE_TASKS, null, values)
    } //método que insere uma task na tabela

    fun getTasks(): List<String> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_TASKS,
            arrayOf(COLUMN_TASK),
            null,
            null,
            null,
            null,
            null
        ) //armazena uma query em um valor

        val tasks = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val task = getString(getColumnIndexOrThrow(COLUMN_TASK))
                tasks.add(task)
            } //joga os valores da query em uma lista mutável de strings
        }
        cursor.close()
        return tasks
    } //método que retorna todas as tasks da tabela como uma lista de strings

    fun deleteTask(task: String) {
        val db = writableDatabase
        val selection = "$COLUMN_TASK = ?"
        val selectionArgs = arrayOf(task)
        db.delete(TABLE_TASKS, selection, selectionArgs)
    } //método que remove uma task da tabela
}