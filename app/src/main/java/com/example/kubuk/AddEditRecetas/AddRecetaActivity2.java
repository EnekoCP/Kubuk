package com.example.kubuk.AddEditRecetas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCaseGroup;
import androidx.camera.core.ViewPort;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Size;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.R;
import com.example.kubuk.User;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;

import java.nio.ByteBuffer;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddRecetaActivity2 extends AppCompatActivity {

    private final Executor executor = Executors.newSingleThreadExecutor();
    Camera camera;
    PreviewView mPreviewView;


    ImageView imagen1, imagen2, imagen3;
    Button capturar,añadir;
    EditText observaciones;

    int cont;

    String foto1 = "empty",foto2 = "empty",foto3 = "empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receta2);

        imagen1 = findViewById(R.id.imageView1);
        imagen2 = findViewById(R.id.imageView2);
        imagen3 = findViewById(R.id.imageView3);

        capturar = findViewById(R.id.capturar);
        observaciones = findViewById(R.id.observaciones);

        mPreviewView = findViewById(R.id.camera);

        añadir = findViewById(R.id.añadirReceta);

        cont=0;

        startCamera();

    }

    private void startCamera() {  //EMPIEZA LA EJECUCION DESPUES DE ARRANCAR LOS SERVICIOS NECESARIOS

        //EMPEZAMOS ARRANCANDO LA CAMARA

        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {

                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                start(cameraProvider);  //LLAMAR A ScanWorker

            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @SuppressLint("RestrictedApi")
    private void start(@NonNull ProcessCameraProvider cameraProvider) {

        //LIVE PREVIEW DE LA CAMARA
        Preview preview = new Preview.Builder()
                .build();

        //SELECTOR DE CAMARA
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();


        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setImageQueueDepth(1) //MAXIMO ANALIZAR UNA FOTO A LA VEZ
                .setTargetResolution(new Size(1280, 720)) //ESPECIFICANDO RESOLUCION
                .build();

        ImageCapture.Builder builder = new ImageCapture.Builder();

        //SET DE CONFIGURACION DE CAPTURA
        final ImageCapture imageCapture = builder
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                //.setTargetRotation(view.getDisplay().getRotation())
                .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
                .build();

        //ORIENTACION DE LA CAPTURA AUTOMATICA

        OrientationEventListener orientationEventListener = new OrientationEventListener((Context) this) {
            @Override
            public void onOrientationChanged(int orientation) {
                int rotation;

                // Monitors orientation values to determine the target rotation value
                if (orientation >= 45 && orientation < 135) {
                    rotation = Surface.ROTATION_270;
                } else if (orientation >= 135 && orientation < 225) {
                    rotation = Surface.ROTATION_180;
                } else if (orientation >= 225 && orientation < 315) {
                    rotation = Surface.ROTATION_90;
                } else {
                    rotation = Surface.ROTATION_0;
                }

                imageCapture.setTargetRotation(rotation);
            }
        };

        orientationEventListener.enable();

        //

        //DEFINIENDO RECTANGULO DE CAPTURA IGUAL A LA PREVIEW
        ViewPort viewPort = ((PreviewView) findViewById(R.id.camera)).getViewPort();
        UseCaseGroup useCaseGroup = new UseCaseGroup.Builder()
                .addUseCase(preview)
                .addUseCase(imageAnalysis)
                .addUseCase(imageCapture)
                .setViewPort(viewPort)
                .build();


        //Tratando excepcion de cambio de camara ,si no se encuentra trasera a frontal
        try {
            camera = cameraProvider.bindToLifecycle(this, cameraSelector, useCaseGroup);
        } catch (Exception e) {
            // No se ha encontrado camara trasera y pasa a frontal

            cameraSelector = new CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                    .build();

            try {
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, useCaseGroup);
            } catch (Exception e2) {

                //En el caso de que no se encuentre ninguna camara se reiniciara la aplicacion

                Toast.makeText(AddRecetaActivity2.this,
                        "Camara no detectada ,reinicie aplicacion", Toast.LENGTH_LONG).show();

                //startActivity(reinicio);
            }
        }


        preview.setSurfaceProvider(mPreviewView.getSurfaceProvider());


        //BOTON CAPTURAR
        capturar.setOnClickListener(v -> {

            System.out.println("CAPTURANDO!");

            imageCapture.takePicture(executor,
                    new ImageCapture.OnImageCapturedCallback() { //TOMAMOS LA FOTO
                        @Override
                        public void onCaptureSuccess(@NonNull ImageProxy image) {

                            System.out.println("CAPTURADO!");
                            Bitmap imagenBitmap = imageProxyToBitmap(image); //PASAMOS LA FOTO A BITMAP

                            //GUARDADO DE IMAGEN EN EL SERVIDOR
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imagenBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] fototransformada = stream.toByteArray();
                            String fotoen64 = Base64.encodeToString(fototransformada, Base64.DEFAULT);


                            Bitmap finalRecortado = imagenBitmap;

                            runOnUiThread(() -> {
                                // TODO Auto-generated method stub
                                if (cont==0){
                                    foto1 = fotoen64;
                                    imagen1.setRotation(0); //EN PC SE VE GIRADO PERO EN MOVIL EN VERTICAL
                                    imagen1.setImageBitmap(finalRecortado); //SETEAMOS FOTO TOMADA
                                    cont = cont+1;
                                }else if(cont==1){
                                    foto2 = fotoen64;
                                    imagen2.setRotation(0); //EN PC SE VE GIRADO PERO EN MOVIL EN VERTICAL
                                    imagen2.setImageBitmap(finalRecortado); //SETEAMOS FOTO TOMADA
                                    cont = cont+1;
                                }else{
                                    foto3 = fotoen64;
                                    imagen3.setRotation(0); //EN PC SE VE GIRADO PERO EN MOVIL EN VERTICAL
                                    imagen3.setImageBitmap(finalRecortado); //SETEAMOS FOTO TOMADA
                                    cont = 0;
                                }

                            });
                            image.close();
                        }

                    });
        });

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                realizarGuardado(foto1, foto2, foto3);
            }
        });
    }

    private void realizarGuardado(final String foto1, final String foto2, final String foto3) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/add.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.print(response);
                        Toast.makeText(getApplicationContext(), "Guardado exitoso", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL GUARDADO", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("imagen1", foto1);
                parametros.put("imagen2", foto2);
                parametros.put("imagen3", foto3);
                parametros.put("observaciones", observaciones.getText().toString());
                parametros.put("name", getIntent().getStringExtra("name"));
                parametros.put("descripcion", getIntent().getStringExtra("descripcion"));
                parametros.put("ingredientes", getIntent().getStringExtra("ingredientes"));
                parametros.put("user", User.getUsuario());


                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private Bitmap imageProxyToBitmap(ImageProxy image) {
        ImageProxy.PlaneProxy planeProxy = image.getPlanes()[0];
        ByteBuffer buffer = planeProxy.getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}