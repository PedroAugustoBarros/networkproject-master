package br.com.pedro.projetoandroid;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//public class DetalhesActivity extends AppCompatActivity implements OnMapReadyCallback {
public class DetalhesActivity extends AppCompatActivity {

    String id;

    Double Lat = 0.0;
    Double Long = 0.0;

//    private GoogleMap mMap;
//
//    GoogleMap mGoogleMap;
//
//    SupportMapFragment map;
    int posicaoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);


        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("ITEM_EXTRA"));

            Log.e("VALOR Detalhes", "Example Item: " + jsonObject.getString("content"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        try {
//            Lat = jsonObject.getDouble("lat");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        try {
//            Long = jsonObject.getDouble("lng");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//         GoogleMap googleMap = (findViewById(R.id.mapView_detalhes);
//        googleMap.addMarker(new MarkerOptions().position(new LatLng( Lat, Long)).title("Marker"));


        ImageView imageView_imagem = findViewById(R.id.imageView_imagem);
        TextView textV_content = findViewById(R.id.textV_content);
        TextView textV_criado = findViewById(R.id.textV_criado);

        try {
            textV_content.setText(jsonObject.getString("content"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            textV_criado.setText(jsonObject.getString("created_at"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (jsonObject.getString("uploaded_image").contains("https")) {
                Glide.with(this)
                        .load(jsonObject.getString("uploaded_image"))
                        .into(imageView_imagem);

            } else {
                Glide.with(this)
                        .load(jsonObject.getString("image"))
                        .into(imageView_imagem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            id = jsonObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Button button = (Button) findViewById(R.id.but_deletar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comments(id);

                finish();
            }
        });


//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.mapView_detalhes);
//        mapFragment.getMapAsync(this);


//        loadMap();

    }

//    private void loadMap() {
//        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment));
//        map.getMapAsync(this);
//    }


//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        mGoogleMap = googleMap;
////        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
//
////        mGoogleMap = googleMap;
//
////        String nomeLocal=MyData.nameArray[posicaoID];
////        Double Lat=MyData.Lat[posicaoID];
////        Double Long=MyData.Long[posicaoID];
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(Lat, Long);
////        LatLng sydney = new LatLng(-34, 151);
////        mMap.addMarker(new MarkerOptions().position(sydney).title(nomeLocal));
////        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
//        mMap.setMinZoomPreference(16.0f);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        mGoogleMap.setMyLocationEnabled(true);
//        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
//        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLn) {
//            }
//        });
//    }


    private void comments(String id) {
//        progress = ProgressDialog.show(ListActivity.this, "", "Autenticando usuário.", true, true);
        Network net = new Network(DetalhesActivity.this);


        net.deletar(id, new Network.HttpCallback() {
            @Override
            public void onSuccess(final JSONArray response) {
//                progress.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        list = (ListView) findViewById(R.id.listView);
//                        CommentAdapter meuAdapter = new CommentAdapter(getLayoutInflater(), response, DetalhesActivity.this, filtro);
//                        Log.d("Json Lista",""+response);
//                        list.setAdapter(meuAdapter);
//
//
//                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Object listItem = list.getItemAtPosition(position);
//                                Log.d("Clique","Clique: "+ listItem);
//
//                                JSONObject  jsonObject = null;
//                                try {
//                                    jsonObject = response.getJSONObject(position);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                                Intent mIntent = new Intent(DetalhesActivity.this, DetalhesActivity.class);
//                                mIntent.putExtra("ITEM_EXTRA", jsonObject.toString());
//                                startActivity(mIntent);
//
//
//                            }
//                        });


                    }
                });
            }

            @Override
            public void onFailure(String response, final Throwable throwable) {
//                progress.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new PersistentCookieStore(getApplicationContext()).removeAll();
                        AlertDialog.Builder alert = new AlertDialog.Builder(DetalhesActivity.this);
                        try {
                            if (throwable == null || throwable.getMessage() == null) {
                                alert.setTitle("Erro ao coletar dados");
                            } else if (throwable != null) {
                                if (throwable.getMessage().contains("Unable to resolve host")) {//
                                    alert.setTitle("Sem internet!");
                                } else {
                                    alert.setTitle("Falha na conexão, tente novamente.");
                                }
                            }
                        } catch (NullPointerException e) {
                        }
                        alert.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        });
                        alert.show();
                    }
                });
            }

            @Override
            public void onSuccess(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }


}
