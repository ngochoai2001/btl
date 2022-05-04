package FragmentOngKinh;

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

import com.example.projectcuoikhoa.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import adapter.MayAnhAdapter;
import model.MayAnh;

public class FragmentOngKinhSony extends Fragment {
    String urlStrSPHot = "https://demo5667093.mockable.io/LensTest ";
    ArrayList<MayAnh> mayAnhList;
    View view;
    RecyclerView rvOngKinhSony;

    public static FragmentOngKinhSony newInstance() {

        Bundle args = new Bundle();

        FragmentOngKinhSony fragment = new FragmentOngKinhSony();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ongkinh_fragment,container,false);
        rvOngKinhSony = view.findViewById(R.id.rvOngKinh);

        new OngKinhSony().execute();

        return view;
    }
    class OngKinhSony extends AsyncTask<Void,Void,String> {
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
                    if(brand.equals("Sony")) {
                        mayAnhList.add(new MayAnh(id, brand, name, price, amount, avatar));
                    }
                }
                MayAnhAdapter mayAnhAdapter = new MayAnhAdapter(mayAnhList,getContext());
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),2,RecyclerView.VERTICAL,false);

                rvOngKinhSony.setLayoutManager(layoutManager);
                rvOngKinhSony.setAdapter(mayAnhAdapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

