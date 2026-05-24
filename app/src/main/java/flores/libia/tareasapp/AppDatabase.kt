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
                titulo = "Diseñar interfaz de Login",
                completado = true
            ),

            TaskEntity(
                titulo = "Diseñar interfaz de Registro",
                completado = true
            ),

            TaskEntity(
                titulo = "Configurar modo oscuro para el flujo de autenticación",
                completado = false
            ),

            TaskEntity(
                titulo = "Configurar servicios de Firebase Authentication",
                completado = true
            ),

            TaskEntity(
                titulo = "Integrar registro de usuarios con Firebase Authentication",
                completado = true
            ),

            TaskEntity(
                titulo = "Integrar funcionalidad de login con Firebase Authentication",
                completado = true
            ),

            TaskEntity(
                titulo = "Implementar validación de correos duplicados durante el registro",
                completado = true
            ),

            TaskEntity(
                titulo = "Implementar lógica de validación de entradas de usuario",
                completado = true
            ),

            TaskEntity(
                titulo = "Diseñar e implementar componentes UI para validaciones y manejo de errores",
                completado = true
            ),

            TaskEntity(
                titulo = "Implementar mensajes toast de validación para la pantalla de Login",
                completado = true
            ),

            TaskEntity(
                titulo = "Implementar persistencia de sesión de usuario",
                completado = true
            ),

            TaskEntity(
                titulo = "Agregar funcionalidad de cierre de sesión",
                completado = true
            ),

            TaskEntity(
                titulo = "Implementar funcionalidad de restablecimiento de contraseña",
                completado = true
            ),

            TaskEntity(
                titulo = "Implementar funcionalidad de verificación de correo electrónico",
                completado = true
            ),

            TaskEntity(
                titulo = "Diseñar interfaz de la pantalla Create Visualization",
                completado = true
            ),

            TaskEntity(
                titulo = "Diseñar componentes UI para Create Visualization",
                completado = true
            ),

            TaskEntity(
                titulo = "Implementar caso de uso para manejo de tamaño de archivos",
                completado = true
            ),

            TaskEntity(
                titulo = "Agregar lógica de validación de tamaño de archivos",
                completado = true
            ),

            TaskEntity(
                titulo = "Diseñar interfaz de la pantalla Create Team",
                completado = false
            ),

            TaskEntity(
                titulo = "Implementar campo de nombre del equipo y su validación",
                completado = false
            ),

            TaskEntity(
                titulo = "Implementar funcionalidad Add Members",
                completado = false
            ),

            TaskEntity(
                titulo = "Implementar campo de búsqueda de miembros",
                completado = false
            ),

            TaskEntity(
                titulo = "Implementar sección de personas sugeridas",
                completado = false
            ),

            TaskEntity(
                titulo = "Implementar lista de miembros del equipo",
                completado = false
            ),

            TaskEntity(
                titulo = "Implementar acciones de toolbar para la pantalla Create Team",
                completado = false
            ),

            TaskEntity(
                titulo = "Configurar modo oscuro para la pantalla Create Team",
                completado = false
            ),

            TaskEntity(
                titulo = "Diseñar arquitectura de navegación basada en Coordinators",
                completado = true
            ),

            TaskEntity(
                titulo = "Implementar flujo Coordinator en el módulo de autenticación",
                completado = true
            ),

            TaskEntity(
                titulo = "Preparar arquitectura Coordinator para escalabilidad en toda la aplicación",
                completado = true
            ),

            TaskEntity(
                titulo = "Aplicar correcciones, mejoras y refactors en pull requests",
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