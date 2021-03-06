package br.com.pedro.projetoandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sidd on 01/05/18.
 */

public class CommentAdapter extends BaseAdapter  {
    LayoutInflater mInflater;
    JSONArray listAttributes;
    Context mContext;
    String mFiltro;

    public CommentAdapter(LayoutInflater inflater, JSONArray array, Context context, String filtro) {
        mInflater = inflater;
        listAttributes = array;
        mContext = context;
        mFiltro=filtro;
    }


    @Override
    public int getCount() {
        return listAttributes.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return listAttributes.get(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();

            try {

                JSONObject jsonObject = listAttributes.getJSONObject(i);

                if (mFiltro.equals("") ||mFiltro.equals(jsonObject.getString("content")) ){


                    view = mInflater.inflate(R.layout.card_view, null);

                    holder.nameTextView = (TextView) view.findViewById(R.id.nameTextView);

                    holder.nameTextView.setText(jsonObject.getString("user"));

                    holder.commentTextView = (TextView) view.findViewById(R.id.commentTextView);
                    holder.commentTextView.setText(jsonObject.getString("content"));

                    holder.imageView = (ImageView) view.findViewById(R.id.imageView);
                    if (jsonObject.getString("uploaded_image").contains("https")) {
                        Glide.with(mContext)
                                .load(jsonObject.getString("uploaded_image"))
                                .into(holder.imageView);

                    } else {
                        Glide.with(mContext)
                                .load(jsonObject.getString("image"))
                                .into(holder.imageView);
                    }

                    view.setTag(holder);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            try {
                JSONObject jsonObject = listAttributes.getJSONObject(i);



                if (mFiltro.equals("") ||mFiltro.equals(jsonObject.getString("content")) ) {

                    holder = (ViewHolder) view.getTag();

                    holder.nameTextView.setText(jsonObject.getString("user"));
                    holder.commentTextView.setText(jsonObject.getString("content"));

                    if (jsonObject.getString("uploaded_image").contains("https")) {
                        Glide.with(mContext)
                                .load(jsonObject.getString("uploaded_image"))
                                .into(holder.imageView);

                    } else {
                        Glide.with(mContext)
                                .load(jsonObject.getString("image"))
                                .into(holder.imageView);
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }




        return view;
    }

    public static class ViewHolder {
        public TextView commentTextView;
        public TextView nameTextView;
        public ImageView imageView;
    }


//    public LayoutInflater.Filter getFilter() {
//        // TODO Auto-generated method stub
//        return null;
//    } //Fim do getFilter


}
