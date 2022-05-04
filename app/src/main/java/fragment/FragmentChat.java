package fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcuoikhoa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import activiti.MainActivity;
import adapter.MessageAdapter;
import data.SQLiteHelper;
import model.Message;

public class FragmentChat extends Fragment {
    public static FragmentChat newInstance() {

        Bundle args = new Bundle();

        FragmentChat fragment = new FragmentChat();
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView rcvMessage;
    MessageAdapter messageAdapter;
    ArrayList<Message> messageList;

    EditText edMessage;
    ImageButton btnGui;
    FirebaseUser firebaseUser;
    SQLiteHelper sqLiteHelper;
    String idAcount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_chat,container,false);

 //       sqLiteHelper = new SQLiteHelper(view.getContext(),"Data.sqlite",null,5);
        sqLiteHelper = new SQLiteHelper(view.getContext(),"Data.sqlite1",null,5);

  //      sqLiteHelper.QueryData("ALTER TABLE IF NOT EXISTS TinNhan(Id INTEGER PRIMARY KEY AUTOINCREMENT,IdAcount VARCHAR(100), mesage NVARCHAR(200))");
  //      sqLiteHelper.QueryData("CREATE TABLE IF NOT EXISTS TinNhan1(Id INTEGER PRIMARY KEY AUTOINCREMENT,IdAcount VARCHAR(100), mesage NVARCHAR(200))");

        edMessage = view.findViewById(R.id.edMessage);
        btnGui = view.findViewById(R.id.btnGui);
        rcvMessage = view.findViewById(R.id.rcvMessage);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        idAcount = firebaseUser.getUid();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvMessage.setLayoutManager(linearLayoutManager);

        messageAdapter = new MessageAdapter();
        messageAdapter.setData(MainActivity.messageArrayList);

        GetData();

        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mesage = edMessage.getText().toString().trim();
                if(TextUtils.isEmpty(mesage)){
                    return;
                }
                sqLiteHelper.QueryData("INSERT INTO TinNhan2 VALUES(null,'"+ idAcount +"','"+mesage+"')");
         //       MainActivity.messageArrayList.add(new Message(id,mesage));
                messageAdapter.notifyDataSetChanged();
                rcvMessage.scrollToPosition(MainActivity.messageArrayList.size() - 1);
                GetData();
                edMessage.setText("");
            }
        });
        return view;
    }

    void GetData(){
        MainActivity.messageArrayList.clear();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        idAcount = firebaseUser.getUid();
        Cursor dataTinNhan = sqLiteHelper.GetData("SELECT * FROM TinNhan2 WHERE IdAcount ='"+idAcount+"'");
        while (dataTinNhan.moveToNext()){
            String idT = dataTinNhan.getString(1);
            String tinNhan = dataTinNhan.getString(2);
            MainActivity.messageArrayList.add(new Message(idT,tinNhan));
        }
        rcvMessage.setAdapter(messageAdapter);
    }
}
