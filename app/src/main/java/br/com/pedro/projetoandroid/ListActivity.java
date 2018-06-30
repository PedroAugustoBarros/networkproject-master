package br.com.pedro.projetoandroid;

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
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListActivity extends AppCompatActivity  {
    private ListView list;
    private ProgressDialog progress;
    // Search EditText
    EditText inputSearch;

    String filtro="";

    CommentAdapter meuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        comments();

        inputSearch = (EditText) findViewById(R.id.editText_Pesquisa);

        Button button = (Button) findViewById(R.id.but_adicionar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this,MainActivity.class));
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        recreate();
    }



    private void comments(){
        progress = ProgressDialog.show(ListActivity.this, "", "Autenticando usuário.", true, true);
        Network net = new Network(ListActivity.this);

        net.comments(new Network.HttpCallback() {
            @Override
            public void onSuccess(final JSONArray response) {
                progress.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list = (ListView) findViewById(R.id.listView);
                        CommentAdapter meuAdapter = new CommentAdapter(getLayoutInflater(), response, ListActivity.this, filtro);
                        Log.d("Json Lista",""+response);
                        list.setAdapter(meuAdapter);


                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Object listItem = list.getItemAtPosition(position);
                                Log.d("Clique","Clique: "+ listItem);

                                JSONObject  jsonObject = null;
                                try {
                                    jsonObject = response.getJSONObject(position);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                Intent mIntent = new Intent(ListActivity.this, DetalhesActivity.class);
                                mIntent.putExtra("ITEM_EXTRA", jsonObject.toString());
                                startActivity(mIntent);


                            }
                        });

                        inputSearch.addTextChangedListener(new TextWatcher() {

                            @Override
                            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                                // When user changed the Text
//                                ListActivity.this.meuAdapter.getFilter().filter(cs);

                                filtro=String.valueOf(cs);

                                list = (ListView) findViewById(R.id.listView);
                                CommentAdapter meuAdapter = new CommentAdapter(getLayoutInflater(), response, ListActivity.this, filtro);
                                Log.d("Json Lista",""+response);
                                list.setAdapter(meuAdapter);

                            }

                            @Override
                            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                          int arg3) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void afterTextChanged(Editable arg0) {
                                // TODO Auto-generated method stub
                            }
                        });





//                        list.setOnItemClickListener(new meuAdapter.OnItemClickListener(){
//                            @Override
//                            public void onItemClick(AdapterView<?>adapter,View v, int position){
//                                ItemClicked item = adapter.getItemAtPosition(position);
//
//                                Intent intent = new Intent(Activity.this,destinationActivity.class);
//                                //based on item add info to intent
//                                startActivity(intent);
//                            }
//                        });



                    }
                });
            }

            @Override
            public void onFailure(String response, final Throwable throwable) {
                progress.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new PersistentCookieStore(getApplicationContext()).removeAll();
                        AlertDialog.Builder alert = new AlertDialog.Builder(ListActivity.this);
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





//    public void showActivityComments(View view) {
//        startActivity(new Intent(ListActivity.this,MainActivity.class));
//    }
}
