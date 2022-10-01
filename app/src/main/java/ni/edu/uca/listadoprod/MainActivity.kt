package ni.edu.uca.listadoprod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import dataadapter.ProductoAdapter
import dataclass.Producto
import kotlinx.coroutines.NonCancellable.start
import ni.edu.uca.listadoprod.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var listProd = ArrayList<Producto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciar()
    }

    private fun limpiar() {
        with(binding) {
            etID.setText("")
            etNombreProd.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }

    fun agregarProd() {
        with(binding) {


            try {

                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listProd.add(prod)

            } catch (ex: Exception) {
                Toast.makeText(
                    this@MainActivity, "Error: ${ex.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rcvLista.adapter = ProductoAdapter(listProd,
                { producto -> selecionarProducto(producto) },
                { position -> eliminarProducto(position) },
                { position -> actualizarProducto(position) }

            )

        }
        limpiar()
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


    fun actualizarProducto(position: Int) {
        with(binding) {
            
if (etID.text.isNullOrBlank()){
                return
            } else{
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listProd.set(position, prod)
                rcvLista.adapter?.notifyItemRemoved(position)
            }

        }
    }


    fun selecionarProducto(producto: Producto) {
        with(binding) {

            etID.text = producto.id.toString().toEditable()
            etNombreProd.text = producto.nombre.toEditable()
            etPrecio.text = producto.precio.toString().toEditable()
        }
    }

    fun eliminarProducto(position: Int) {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage("¿Usted desea eliminar este producto?")
            .setCancelable(false)
            .setPositiveButton("Si") { dialog, id ->
                with(binding) {
                    listProd.removeAt(position)
                    rcvLista.adapter?.notifyItemRemoved(position)
                    limpiar()
                }

            }.setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun iniciar() {
        binding.btnAgregar.setOnClickListener {

            agregarProd()


        }
        binding.btnLimpiar.setOnClickListener {
            limpiar()
        }


    }
}
