package layout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class comida(
    @PrimaryKey val id: Long,
    var nombre: String? = null,
    var descripcion: String? = null,
    var precio: Int = 0,
    var imagen: String? = null
)