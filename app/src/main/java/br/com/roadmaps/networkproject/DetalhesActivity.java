package br.com.roadmaps.networkproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetalhesActivity extends AppCompatActivity {

    String id;

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


        ImageView imageView_imagem = findViewById(R.id.imageView_imagem);

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


        Button button= (Button) findViewById(R.id.but_deletar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comments(id);

                finish();
            }
        });

    }




    private void comments(String id){
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
                        } catch (NullPointerException e) {}
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
                    public void run() {}
                });
            }
        });
    }



}
