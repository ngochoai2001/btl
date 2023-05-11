package com.example.btl.FragmentOngKinh;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.example.btl.adapter.MayAnhAdapter;
import com.example.btl.model.MayAnh;

public class FragmentOngKinhCanon extends Fragment {
    String urlStrSPHot = "https://demo5667093.mockable.io/LensTest ";
    ArrayList<MayAnh> mayAnhList;
    View view;
    RecyclerView rvOngKinhCanon;

    public static FragmentOngKinhCanon newInstance() {

        Bundle args = new Bundle();

        FragmentOngKinhCanon fragment = new FragmentOngKinhCanon();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ongkinh_fragment,container,false);
        rvOngKinhCanon = view.findViewById(R.id.rvOngKinh);

        new OngKinhCanon().execute();

        return view;
    }
    class OngKinhCanon extends AsyncTask<Void,Void,String> {
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url =new URL(urlStrSPHot);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();

                int byteChar;

                while((byteChar=is.read()) != -1){
                    result+=(char) byteChar;
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String unused) {
            super.onPostExecute(unused);
            mayAnhList = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(result);
                for (int i=0;i < array.length();i++){
                    JSONObject jsonObject =array.getJSONObject(i);
                    int id =jsonObject.getInt("id");
                    String brand = jsonObject.getString("brand");
                    String name = jsonObject.getString("name");
                    int price =jsonObject.getInt("price");
                    int amount =jsonObject.getInt("amount");
                    String avatar =jsonObject.getString("avatar");
                    if(brand.equals("Canon")) {
                        mayAnhList.add(new MayAnh(id, brand, name, price, amount, avatar));
                    }
                }
                MayAnhAdapter mayAnhAdapter = new MayAnhAdapter(mayAnhList,getContext());
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),2,RecyclerView.VERTICAL,false);

                rvOngKinhCanon.setLayoutManager(layoutManager);
                rvOngKinhCanon.setAdapter(mayAnhAdapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
