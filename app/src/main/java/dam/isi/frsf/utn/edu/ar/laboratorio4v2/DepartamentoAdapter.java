package dam.isi.frsf.utn.edu.ar.laboratorio4v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class DepartamentoAdapter extends ArrayAdapter<dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo.Departamento> {
    private LayoutInflater inflater;

    public DepartamentoAdapter(Context contexto, List<dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo.Departamento> items) {
        super(contexto, R.layout.fila, items);
        inflater = LayoutInflater.from(contexto);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DecimalFormat df = new DecimalFormat("#.##");
        View row = convertView;

        if (row == null) row = inflater.inflate(R.layout.fila, parent, false);

        TextView txtCiudad = (TextView) row.findViewById(R.id.ciudad);              //Nombre de la Ciudad
        txtCiudad.setText(this.getItem(position).getCiudad().getNombre());

        TextView txtDescripcion = (TextView) row.findViewById(R.id.descripcion);    //Una Descripción (consiste en "Único!! Alojamiento #n **")
        txtDescripcion.setText("Unico!! " + this.getItem(position).getDescripcion());

        TextView txtPrecio = (TextView) row.findViewById(R.id.precio);              //Solamente el Precio, sin descuentos
        txtPrecio.setText("$" + (df.format(this.getItem(position).getPrecio())));

        TextView txtCapacidad = (TextView) row.findViewById(R.id.capacidadMax);     //Cantidad de Huespedes máxima
        txtCapacidad.setText(this.getItem(position).getCapacidadMaxima()+".");
        return (row);

    }
}
