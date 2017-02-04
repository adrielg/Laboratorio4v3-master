package dam.isi.frsf.utn.edu.ar.laboratorio4v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo.Reserva;

public class ReservaAdapter extends ArrayAdapter<Reserva> {
    private LayoutInflater inflater;

    public ReservaAdapter(Context contexto, List<Reserva> items) {
        super(contexto, R.layout.fila_reservas, items);

        inflater = LayoutInflater.from(contexto);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DecimalFormat df = new DecimalFormat("#.##");
        View row = convertView;
        if (row == null) row = inflater.inflate(R.layout.fila_reservas, parent, false);

        TextView txtCiudad = (TextView) row.findViewById(R.id.DescDepartamento);
        txtCiudad.setText("Ciudad: " + this.getItem(position).getAlojamiento().getCiudad() + ". Alojamiento: " + this.getItem(position).getAlojamiento().getDescripcion());

        TextView txtId = (TextView) row.findViewById(R.id.id);
        txtId.setText("ID: " + this.getItem(position).getId());

        TextView txtPrecio = (TextView) row.findViewById(R.id.precio);
        txtPrecio.setText("$" + (df.format(this.getItem(position).getAlojamiento().getPrecio())));

        TextView fechaInicio = (TextView) row.findViewById(R.id.fecha_inicio);
        fechaInicio.setText(this.getItem(position).getFechaInicio() + ".");

        TextView fechaFin = (TextView) row.findViewById(R.id.fecha_fin);
        fechaFin.setText(this.getItem(position).getFechaFin() + ".");

        return (row);

    }
}
