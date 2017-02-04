package dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by mdominguez on 22/09/16.
 */
public class Departamento implements Serializable {

    private Integer id;
    private Double precio;
    private String descripcion;
    private Double descuento;
    private Integer capacidadMaxima;
    private Boolean noFumador;
    private String correoElectronico;
    private Integer cantidadHabitaciones;
    private Integer cantidadCamas;
    private String telefonoPropietario;

    private Ciudad ciudad;
    private List<Reserva> reservas;

    public Departamento(){
        Random r = new Random();
        this.noFumador = r.nextInt()%2==0;
        this.correoElectronico = "hotel_"+(10+r.nextInt(99))+"@mail.com";
        this.precio =r.nextDouble()*(100+r.nextInt(200));
        this.descuento=r.nextDouble()*0.5;
        this.capacidadMaxima=2+r.nextInt(8);
        this.descripcion = " Alojamiento #"+(100+r.nextInt(99))+" **";
        this.cantidadHabitaciones = 1+r.nextInt(3);
        this.cantidadCamas =this.cantidadHabitaciones+r.nextInt(2);
        this.telefonoPropietario= "+54"+(111111+r.nextInt());
        this.ciudad = Ciudad.CIUDADES[r.nextInt(5)];
        this.reservas=new ArrayList<Reserva>();
    }


    public Departamento(Integer id){
        this();
        this.id=id;
    }

    //Agre -ACA- 22/01/2017
    public Departamento(String descripcion){
        this();
        this.descripcion=descripcion;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(Integer capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Integer getCantidadHabitaciones() {
            return cantidadHabitaciones;
        }

    public void setCantidadHabitaciones(Integer cantidadHabitaciones) {  this.cantidadHabitaciones = cantidadHabitaciones;  }

    public Integer getCantidadCamas() {
            return cantidadCamas;
        }

    public void setCantidadCamas(Integer cantidadCamas) {
            this.cantidadCamas = cantidadCamas;
        }

    public String getTelefonoPropietario() {
            return telefonoPropietario;
        }

    public void setTelefonoPropietario(String telefonoPropietario) { this.telefonoPropietario = telefonoPropietario; }

    public Boolean getNoFumador(){ return noFumador; }

    public void setNoFumador(Boolean noF){ noFumador = noF;}


    @Override
    public String toString() {
        return "Departamento{" +
                "id=" + id +
                ", precio=" + precio +
                ", descripcion='" + descripcion + '\'' +
                ", ciudad=" + ciudad.toString() +
                '}';
    }

    private static final List<Departamento> _ALOJAMIENTOS_DISPONIBLES = new ArrayList<Departamento>();
    private static Boolean _FLAG_INICIALIZADO = false;

    public static List<Departamento> getAlojamientosDisponibles(){
        Random rReservas = new Random();
        if(_FLAG_INICIALIZADO) return  _ALOJAMIENTOS_DISPONIBLES;

        for(int i=0;i<100;i++){
            Departamento aux = new Departamento(i+1);
            _ALOJAMIENTOS_DISPONIBLES.add(aux);
            int cantidadReservasSimular = 1+rReservas.nextInt(5);
            for(int j=0;j<cantidadReservasSimular;j++){

                Integer inicioreserva = rReservas.nextInt(10) * (rReservas.nextInt()%2==0?1:-1);
                Calendar inicio = Calendar.getInstance();
                inicio.add(Calendar.DAY_OF_YEAR,inicioreserva);
                Date fechaInicio = inicio.getTime();
                inicio.add(Calendar.DAY_OF_YEAR, rReservas.nextInt(8)+1);
                Reserva r = new Reserva((i*10)+j,fechaInicio ,inicio.getTime(),aux);
                aux.getReservas().add(r);
            }
        }
        _FLAG_INICIALIZADO=true;
        return _ALOJAMIENTOS_DISPONIBLES;
    }
    public static void buscarYConfirmarReserva(Reserva miReserva){

        for(Departamento d: _ALOJAMIENTOS_DISPONIBLES){
            if(miReserva.getAlojamiento().toString().equals(d.toString())) {
                for (int i = 0; i < d.getReservas().size(); i++)
                    if (d.getReservas().get(i).toString().equals(miReserva.toString())) {
                        d.getReservas().get(i).setConfirmada(miReserva.getConfirmada());
                        return;
                    }
            }
        }
    }

}
