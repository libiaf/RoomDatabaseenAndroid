package flores.libia.tareasapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.Volatile

@Database(
    entities = [TaskEntity::class],
    version = 1
)

abstract class AppDatabase: RoomDatabase(){
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val TAREAS_INICIALES = listOf(

            TaskEntity(
                titulo = "Design Login Screen UI",
                completado = true
            ),

            TaskEntity(
                titulo = "Design Registration Screen UI",
                completado = true
            ),

            TaskEntity(
                titulo = "Set up Dark Mode for the Authentication flow",
                completado = false
            ),

            TaskEntity(
                titulo = "Configure Firebase Authentication services",
                completado = true
            ),

            TaskEntity(
                titulo = "Integrate user registration with Firebase Authentication",
                completado = true
            ),

            TaskEntity(
                titulo = "Integrate login functionality with Firebase Authentication",
                completado = true
            ),

            TaskEntity(
                titulo = "Implement duplicate email validation during registration",
                completado = true
            ),

            TaskEntity(
                titulo = "Implement user input validation logic",
                completado = true
            ),

            TaskEntity(
                titulo = "Design and implement validation and error handling UI components",
                completado = true
            ),

            TaskEntity(
                titulo = "Implement validation toast messages for Login Screen",
                completado = true
            ),

            TaskEntity(
                titulo = "Implement user session persistence",
                completado = true
            ),

            TaskEntity(
                titulo = "Add logout functionality",
                completado = true
            ),

            TaskEntity(
                titulo = "Implement password reset functionality",
                completado = true
            ),

            TaskEntity(
                titulo = "Implement email verification functionality",
                completado = true
            ),

            TaskEntity(
                titulo = "Design Create Visualization Screen UI",
                completado = true
            ),

            TaskEntity(
                titulo = "Design Create Visualization Components UI",
                completado = true
            ),

            TaskEntity(
                titulo = "Implement file size handling use case",
                completado = true
            ),

            TaskEntity(
                titulo = "Add file size validation logic",
                completado = true
            ),

            TaskEntity(
                titulo = "Design Create Team Screen UI",
                completado = false
            ),

            TaskEntity(
                titulo = "Implement Team Name input field",
                completado = false
            ),

            TaskEntity(
                titulo = "Implement Add Members",
                completado = false
            ),

            TaskEntity(
                titulo = "Implement Search field for members",
                completado = false
            ),

            TaskEntity(
                titulo = "Implement Suggested People section",
                completado = false
            ),

            TaskEntity(
                titulo = "Implement Team Members list",
                completado = false
            ),

            TaskEntity(
                titulo = "Implement toolbar actions for Create Team screen",
                completado = false
            ),

            TaskEntity(
                titulo = "Set up Dark Mode for Create Team screen",
                completado = false
            ),

            TaskEntity(
                titulo = "Design Coordinator-based navigation architecture",
                completado = true
            ),

            TaskEntity(
                titulo = "Implement Coordinator flow in Authentication module",
                completado = true
            ),

            TaskEntity(
                titulo = "Prepare Coordinator architecture for app-wide scalability",
                completado = true
            ),

            TaskEntity(
                titulo = "Apply fixes, improvements, and pull request refactors",
                completado = true
            )
        )

        fun getInstance(
            context: Context
        ): AppDatabase {
            return INSTANCE ?: synchronized(
                this
            ){
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "tasks_db"
                    )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Insertamos las tareas iniciales en un
                            // hilo separado. NUNCA en el main thread.
                            CoroutineScope(Dispatchers.IO).launch {
                                val dao = getInstance(context).taskDao()
                                TAREAS_INICIALES.forEach { tarea ->
                                    dao.insert(tarea)
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}