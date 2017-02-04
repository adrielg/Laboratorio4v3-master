package dam.isi.frsf.utn.edu.ar.laboratorio4v2;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;

import dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo.Reserva;

/*UnBroadcastReceiveres una especie de receptor de los eventos que produce el sistema operativo Android.
  Típicamente, unBroadcastReceiverse utiliza para mostrar notificaciones de los eventos que ocurren
  en nuestro teléfono móvil, como por ejemplo el descubrimiento de una red wifio el agotamiento de la batería.
*/
public class PendientesReceiver extends BroadcastReceiver {
    @Override

    /* onReceive método que se ejecutará cada vez que se produzca el evento al que este suscrito nuestro broadcast receiver.*/
    public void onReceive(Context context, Intent intent) {
        /*CANCELAMOS alarmas existentes*/
        cancelRepeatingAlarm(context);

        AltaReservaActivity.seleccionado.setConfirmada(true);
        Departamento.buscarYConfirmarReserva(AltaReservaActivity.seleccionado);
        MainActivity.usuario.getReservas().add(AltaReservaActivity.seleccionado);

        sendNotificacion(context,"Reserva Confirmada");
    }

    /*REPETIR ALARMA*/
    public void sendRepeatingAlarm(Context context){
        Calendar cal = Calendar.getInstance(); //create a calendar
        Intent intent = new Intent(context,PendientesReceiver.class); //Obtener un intent para invocar al receptor

/*Un PendingIntent es un token que damos a otra aplicación (Por ejemplo, Notification Manager, Alarm Manager u otra aplicación),
que permite a esta otra aplicación usar los permisos de nuestra aplicación para ejecutar trozos de códigos predefinidos.*/
        PendingIntent pi = getDistinctPendingIntent(context,intent,2);

        /*Programar la alarma, una que se repita*/
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC,cal.getTimeInMillis(),(3*10),pi); //AlarmManager.RTC para lanzar la alarma cuando el dispositivo se “despierte”.
    }

    /*CANCELAR ALARMA*/
    public void cancelRepeatingAlarm(Context context){
        Intent intent = new Intent(context, PendientesReceiver.class);//Usar el intent usado para invocar PendientesReceiver

        PendingIntent sender = PendingIntent.getBroadcast(context, 2, intent, 0); // construimos un “PendingIntent” apropiado para “broadcast”

        /*Cancelar la alarma*/
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    /*Enviando la notificación*/
    private void sendNotificacion(Context context,String message){

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nm = (NotificationManager) context.getSystemService(ns);

        Intent intent = new Intent(context,AltaReservaActivity.class);
        intent.putExtra("listaReservas",(ArrayList<Reserva>) MainActivity.usuario.getReservas());
        intent.putExtra("esReserva",false);

        PendingIntent pi = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context.getApplicationContext())
                .setContentIntent(pi)
                .setContentTitle("Notificación de Reservalo.com")
                .setSmallIcon(android.R.drawable.ic_menu_send)
                .setContentText(message)
                .setSound(MainActivity.usuario.getRingstone());//no funciona bien
        nm.notify(1,mBuilder.build());
    }

    /*Usado en el medoto sendRepeatingAlarm()*/
    protected PendingIntent getDistinctPendingIntent(Context context,Intent intent, int requestId){
        return PendingIntent.getBroadcast(context,requestId,intent,0);
    }
}
