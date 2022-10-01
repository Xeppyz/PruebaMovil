package dataadapter

import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dataclass.Producto
import ni.edu.uca.listadoprod.databinding.ItemlistaBinding

data class ProductoAdapter(
    val listProd: List<Producto>,
    private val onclickerVer: (Producto) -> Unit,
    private val onclickerEliminar: (Int) -> Unit,
    private val onclikcerActualizar: (Int) -> Unit,
) :
    RecyclerView.Adapter<ProductoAdapter.ProductoHolder>() {
    inner class ProductoHolder(val binding: ItemlistaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun cargar(
            producto: Producto, onclickerListener: (Producto) -> Unit,
            onclickerDelete: (Int) -> Unit,
            onclickerUptade: (Int) -> Unit
        ) {
            with(binding) {
                tvCodProd.text = producto.id.toString()
                tvNombreProd.text = producto.nombre
                tvPrecioProd.text = producto.precio.toString()
                itemView.setOnClickListener { onclickerListener(producto) }
                binding.btnEliminar.setOnClickListener { onclickerDelete(adapterPosition) }
                binding.btnEditar.setOnClickListener { onclickerUptade(adapterPosition) }

            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val binding = ItemlistaBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false

        )
        return ProductoHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoHolder, position: Int) {
        holder.cargar(listProd[position], onclickerVer, onclickerEliminar, onclikcerActualizar)
    }

    override fun getItemCount(): Int = listProd.size


}
