package cl.telematica.android.usmvende;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

public class RegistroProducto extends AppCompatActivity implements View.OnClickListener{

    EditText txtNP,txtDP,txtPP,txtNV;
    Button btnRP,btnVP;
    TextView tvIsConnected;
    Producto person;
    String NP,DP,PP,NV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_producto);

        //Obtenemos una referencia a los controles de la interfaz
        txtNP = (EditText) findViewById(R.id.txtNombreProducto);
        txtDP = (EditText) findViewById(R.id.txtDescpProducto);
        txtPP = (EditText) findViewById(R.id.txtPrecioProducto);
        txtNV = (EditText) findViewById(R.id.txtNombreVendedor);
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
        btnRP = (Button) findViewById(R.id.btnRegistrarProducto);
        btnVP = (Button) findViewById(R.id.btnVenderProducto);

        // check if you are connected or not
        if(isConnected()){
            tvIsConnected.setBackgroundColor(0xFF00CC00);
            tvIsConnected.setText("You are connected");
        }
        else{
            tvIsConnected.setText("You are NOT connected");
        }
        // add click listener to Button "POST"
        btnRP.setOnClickListener(this);
        btnVP.setOnClickListener(this);

    }
    public static String POST(String targeturl, Producto person){
        String result = "";
        String json = "";
        StringTokenizer tokens = new StringTokenizer(targeturl, "/");
        String first= tokens.nextToken();
        String second= tokens.nextToken();
        String third= tokens.nextToken();

        //System.out.println(separado2);
        try {
            URL url = new URL(targeturl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            if(third.trim().equals("nuevo_producto")) {
                jsonObject.put("producto", person.getNombreP());
                jsonObject.put("descripcion", person.getDescripcion());
                jsonObject.put("precio", person.getPrecio());
                jsonObject.put("vendedor", person.getNombreV());
            }
            else if (third.trim().equals("vender")){
                jsonObject.put("vendedor", person.getNombreV());
                jsonObject.put("gps", person.getLocalizacion());
            }
            else{}

            //convert JSONObject to JSON to String
            json = jsonObject.toString();
            System.out.println(json);
            OutputStream os = connection.getOutputStream();
            os.write(json.getBytes());
            os.flush();

            // read the response
            InputStream in = new BufferedInputStream(connection.getInputStream());
            // convert inputstream to string
            if(in != null)
                result = convertInputStreamToString(in);
            else
                result = "Did not work!";

            System.out.println(result);
            in.close();
            connection.disconnect();

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }


        return result;
    }


    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    @Override
    public void onClick(View v) {
        String NP,DP,PP,NV;
        NP = txtNP.getText().toString();
        DP = txtDP.getText().toString();
        PP = txtPP.getText().toString();
        NV = txtNV.getText().toString();

        switch(v.getId()){
            case R.id.btnRegistrarProducto:
                if(!validate()) {
                    Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                    break;
                }
                // call AsynTask to perform network operation on separate thread
                new HttpAsyncTask().execute("http://usmvende.telprojects.xyz/nuevo_producto",NP,DP,PP,NV);
                break;
            case R.id.btnVenderProducto:
                // call AsynTask to perform network operation on separate thread
                new HttpAsyncTask().execute("http://usmvende.telprojects.xyz/vender",NP,DP,PP,NV);
                break;
        }

    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            person= new Producto();

            GPSTracker mGPS = new GPSTracker(getBaseContext());
            if(mGPS.canGetLocation ){
                mGPS.getLocation();
                System.out.println(mGPS.getLatitude());
                System.out.println(mGPS.getLongitude());
                person.setLocalizacion(String.valueOf(mGPS.getLatitude()+"/"+String.valueOf(mGPS.getLongitude())));
            }else{
                System.out.println("Unable");
            }
            person.setNombreP(urls[1]);
            person.setDescripcion(urls[2]);
            person.setPrecio(urls[3]);
            person.setNombreV(urls[4]);

            return POST(urls[0],person);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validate(){
        if(txtNP.getText().toString().trim().equals(""))
            return false;
        else if(txtDP.getText().toString().trim().equals(""))
            return false;
        else if(txtPP.getText().toString().trim().equals(""))
            return false;
        else if(txtNV.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        System.out.println(result);
        return result;

    }
}
