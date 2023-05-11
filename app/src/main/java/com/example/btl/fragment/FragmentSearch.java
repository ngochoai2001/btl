package com.example.btl.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.btl.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.example.btl.adapter.TimKiemAdapter;
import com.example.btl.model.MayAnh;

public class FragmentSearch extends Fragment {

    public static FragmentSearch newInstance() {

        Bundle args = new Bundle();

        FragmentSearch fragment = new FragmentSearch();
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView rcvTimKiem;
    TimKiemAdapter timKiemAdapter;
    String urlStrSPHot = "https://demo5667093.mockable.io/SanPhamHot";
    List<MayAnh>  mayAnhList;
    SearchView edTimKiemm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.activity_fragment_search,container,false);
         rcvTimKiem = view.findViewById(R.id.rcvTimKiem);
         edTimKiemm = view.findViewById(R.id.edTimKiemm);

         LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
         rcvTimKiem.setLayoutManager(linearLayoutManager);

         new DataGetProduct().execute();

      //   timKiemAdapter = new TimKiemAdapter(mayAnhList,getContext());
      //   rcvTimKiem.setAdapter(timKiemAdapter);

         RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
         rcvTimKiem.addItemDecoration(itemDecoration);


         return view;
    }
    class DataGetProduct extends AsyncTask<Void,Void,String> {
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
                    mayAnhList.add(new MayAnh(id, brand, name, price, amount, avatar));

                    TimKiemAdapter timKiemAdapter = new TimKiemAdapter(mayAnhList,getContext());
                    rcvTimKiem.setAdapter(timKiemAdapter);

                    edTimKiemm.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            timKiemAdapter.getFilter().filter(query);
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            timKiemAdapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}