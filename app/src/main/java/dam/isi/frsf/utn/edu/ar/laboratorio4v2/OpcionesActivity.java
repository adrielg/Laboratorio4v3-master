package dam.isi.frsf.utn.edu.ar.laboratorio4v2;

import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class OpcionesActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }

    @Override
    /*Se setea el Sub-Titulo (summary) con el valor que se cargo*/
    public void onSharedPreferenceChanged(SharedPreferences sharedPref, String key) {
        //boolean entro = false;
        if (key.equals("nombre_usuario")) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPref.getString(key, ""));
        }

        if (key.equals("email_usuario")) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPref.getString(key, ""));
        }

        if (key.equals("check_box_preference_1")) {
            /*NO ESTA ANDANDO como yo quiero. Si se tilda este check se tendria que habilitar ELEGIR ringtone*/
            Toast.makeText(getApplicationContext(), "NO CUMPLE LA FUNCION QUE QUIERO AUN", Toast.LENGTH_SHORT).show();
        }
            /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            if (prefs.getString("check_box_preference_1", "false").equals("true")) {
                Toast.makeText(getBaseContext(), "estado TRUE", Toast.LENGTH_LONG).show();
            }*/
        if (key.equals("ringtone")) {
            Toast.makeText(getApplicationContext(), "Deberia guardar el ringtone ", Toast.LENGTH_LONG).show();

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            String strRingtonePreference = prefs.getString("ringtonePref", "DEFAULT_RINGTONE_URI");

            Uri ringtoneUri = Uri.parse(strRingtonePreference);
            Ringtone ringtone = RingtoneManager.getRingtone(getBaseContext(), ringtoneUri);
            String name = ringtone.getTitle(getBaseContext());

            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(name);
        }
    }

    protected void onResume() {
        super.onResume(); // Registrar escucha
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    /*Observe que a diferencia de onSharedPreferenceChanged, onPreferenceChange se llama antes de
    que se actualice la preferencia, por lo que debe utilizar el parámetro newValue para obtener los
     datos seleccionados en lugar de obtenerlos de la preferencia.*/

    /* @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        updateRingtoneSummary((RingtonePreference) preference, Uri.parse((String) newValue));
        return true;
    }

    private void updateRingtoneSummary(RingtonePreference preference, Uri ringtoneUri) {
        Ringtone ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
        if (ringtone != null)
            preference.setSummary(ringtone.getTitle(this));
        else
            preference.setSummary("Silent");
    }*/

}