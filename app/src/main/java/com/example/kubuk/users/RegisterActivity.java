package com.example.kubuk.users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    EditText textEmailReg, textPasswdReg1, textPasswdReg2, textNombre;
    Button registerBoton;
    RequestQueue request;
    Uri imagenSeleccionada;
    String imgUriReg = "";
    Bitmap bitmapReg;

    static final int REQUEST_PICK_IMAGE_CAPTURE_REG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textEmailReg = findViewById(R.id.textEmailReg);
        textPasswdReg1 = findViewById(R.id.textPasswordReg1);
        textPasswdReg2 = findViewById(R.id.textPasswordReg2);
        textNombre = findViewById(R.id.textNombre);

        /** Called when the user taps the Elegir foto button */
        Button fotoRegistro = findViewById(R.id.registerFotoButton);
        fotoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerImagen();
            }
        });

        request = Volley.newRequestQueue(getApplicationContext());

        /** Called when the user taps the Registrarse button */
        registerBoton = findViewById(R.id.accederButton);
        registerBoton.setOnClickListener(view -> {
            if (validarRegistro()){ //En caso de que todos los datos sean correctos:
                cargarWebService();
            }
        });

    }

    /** Método utilizado para validar el inicio de sesión con los datos que existen en la BBDD remota */
    private void cargarWebService() {

        //TODO: Modificar el php con la BBDD remota a usar
        String url = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/lgonzalez184/WEB/registrarUserKubuk.php?email="
                +textEmailReg.getText().toString() + "&passwd=" +textPasswdReg1.getText().toString() + "&nombre=" +textNombre.getText().toString();

        url = url.replace(" ", "%20");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this,this);
        request.add(stringRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorServidor), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(String response) {
        if (response.equals("Registro_done")){
            subirImgFirebase();
            DialogFragment registraseAlert = new RegistrarseDialogFragment();
            registraseAlert.show(getSupportFragmentManager(),"registrarse_dialog");
            Log.i("REGISTRO", "Registrado");
        }else if (response.equals("Registro_emailexiste")){
            Toast.makeText(getApplicationContext(), getString(R.string.emailYaExiste), Toast.LENGTH_SHORT).show();
            Log.i("REGISTRO", "Email Existe");
        }
        else {
            Toast.makeText(getApplicationContext(),  getString(R.string.errorRegistro), Toast.LENGTH_SHORT).show();
            Log.i("REGISTRO", "Error registro");
        }
    }

    /** Método utilizado para validar los datos del formulario de registro */
    public boolean validarRegistro() {
        boolean valido = true;
        //GestorDB dbHelper = GestorDB.getInstance(this);

        //Validamos el nombre
        String nombre = textNombre.getText().toString();
        if (nombre.equals("")) { //En caso de que esté vacío
            Toast.makeText(getApplicationContext(), getString(R.string.nombreVacio), Toast.LENGTH_SHORT).show();
            textNombre.setText("");
            valido = false;
        } else if (nombre.length() > 11) { //En caso de que supere la longitud máxima
            Toast.makeText(getApplicationContext(), getString(R.string.nombreLargo), Toast.LENGTH_SHORT).show();
            textNombre.setText("");
            valido = false;
        }

        //Validamos el email
        String email = textEmailReg.getText().toString();
        Pattern patternEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        if (email.equals("")){ //Si el email está vacío
            Toast.makeText(getApplicationContext(), getString(R.string.emailVacio), Toast.LENGTH_SHORT).show();
            textEmailReg.setText("");
            valido = false;
        }else if (!patternEmail.matcher(email).matches()) { //Si el email no es correcto
            Toast.makeText(getApplicationContext(), getString(R.string.emailNoValido), Toast.LENGTH_SHORT).show();
            textEmailReg.setText("");
            valido = false;
        }

        //Validamos la contraseña
        //NOTA: No se comprueba si la contraseña de confirmación no son correctas, ya que, en caso de que no coincidan, salta ya un error.
        String passwd = textPasswdReg1.getText().toString();
        String passwdConf = textPasswdReg2.getText().toString();
        if (!passwdConf.equals(passwd)) { //Si son distintas
            Toast.makeText(getApplicationContext(), getString(R.string.passwdNoCoincide), Toast.LENGTH_SHORT).show();
            textPasswdReg1.setText("");
            textPasswdReg2.setText("");
            valido = false;
        } else {
            if (passwd.equals("")) { //Si el EditText de Password está vacío
                Toast.makeText(getApplicationContext(), getString(R.string.passwdVacia), Toast.LENGTH_SHORT).show();
                textPasswdReg1.setText("");
                textPasswdReg2.setText("");
                valido = false;
            } else if (passwd.length() < 8 || passwd.length() > 16) { //Si el EditText de Password no cumple la longitud
                Toast.makeText(getApplicationContext(), getString(R.string.passwdLarga), Toast.LENGTH_SHORT).show();
                textPasswdReg1.setText("");
                textPasswdReg2.setText("");
                valido = false;
            }
        }

        return valido;

    }

    /** Método utilizado para obtener una imagen, en este caso de la galería */
    public void obtenerImagen(){
        Intent intentFoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intentFoto, REQUEST_PICK_IMAGE_CAPTURE_REG);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_PICK_IMAGE_CAPTURE_REG) && resultCode == RESULT_OK) {
            //Obtengo la imagen seleccionada de la galeria
            imagenSeleccionada = data.getData();
            try {
                bitmapReg = MediaStore.Images.Media.getBitmap(getContentResolver(),imagenSeleccionada);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageView imgPreviewReg = findViewById(R.id.imagenperfilReg);
            imgPreviewReg.setImageURI(imagenSeleccionada);
            imgUriReg=imagenSeleccionada.toString();
        }

    }


    /** Método utilizado para subir la imagen del usuario a Firebase */
    public void subirImgFirebase(){

        //En caso de que el usuario no haya elegido ninguna imagen
        if (imagenSeleccionada == null) {
            imagenSeleccionada = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.defaultuser);
        }

        String email = textEmailReg.getText().toString();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference spaceRef = storageRef.child(email +".jpg");
        spaceRef.putFile(imagenSeleccionada);

    }

}
