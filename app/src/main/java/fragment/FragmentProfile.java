package fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.projectcuoikhoa.DiaChiCuaHang;
import com.example.projectcuoikhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import activiti.DangKi;
import activiti.LogIn;
import activiti.MainActivity;
import activiti.Profile.ChangePassword;
import activiti.Profile.DanhSachYeuThich;
import activiti.Profile.DonHangDaMua;
import activiti.Profile.LienHeVoiChungToi;
import activiti.Profile.Setting;
import activiti.Profile.SuaThongTin;
import activiti.Profile.UpdateAvatar;
import de.hdodenhof.circleimageview.CircleImageView;
import model.User;

import static activiti.MainActivity.MY_REQUEST_CODE;


public class FragmentProfile extends Fragment implements ValueEventListener {
    public static FragmentProfile newInstance() {

        Bundle args = new Bundle();

        FragmentProfile fragment = new FragmentProfile();
        fragment.setArguments(args);
        return fragment;
    }

    TextView tvProfileName,tvProfileEmail;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String userID;
    Button btnDangXuat,btnSetAvt;
    TextView tvYeuThich,tvYeuThich2,tvLichSu,tvLichSu2,tvThongTinCuaHang,tvThongTinCuaHang2,setting,setting2,clickDeThayDoiAnh;
    CircleImageView ciAvatar;
    Uri mUri;
    ProgressDialog progressDialog;
    TextView doimatkhau,doimmatkhau2,suathongtin,suathongtin2,lienhevoichungtoi,lienhevoichungtoi2;
    ImageButton btnBackSetting;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_profile,container,false);

        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        tvYeuThich = view.findViewById(R.id.tvYeuThich);
        tvYeuThich2 = view.findViewById(R.id.tvYeuThich2);
        tvLichSu = view.findViewById(R.id.tvLichSu);
        tvLichSu2 = view.findViewById(R.id.tvLichSu2);
        tvThongTinCuaHang = view.findViewById(R.id.tvThongTinCuaHang);
        tvThongTinCuaHang2 = view.findViewById(R.id.tvThongTinCuaHang2);
    //    setting = view.findViewById(R.id.setting);
    //    setting2 = view.findViewById(R.id.setting2);
        ciAvatar = view.findViewById(R.id.ciAvatar);
        btnSetAvt = view.findViewById(R.id.btnSetAvt);
        progressDialog = new ProgressDialog(getActivity());
        clickDeThayDoiAnh = view.findViewById(R.id.clickDeThayDoiAnh);

        btnBackSetting = view.findViewById(R.id.btnBackSetting);
        doimatkhau = view.findViewById(R.id.doimatkhau);
        doimmatkhau2 = view.findViewById(R.id.doimatkhau2);
        suathongtin = view.findViewById(R.id.suathongtin);
        suathongtin2 = view.findViewById(R.id.suathongtin2);
        lienhevoichungtoi = view.findViewById(R.id.lienhevoichungtoi);
        lienhevoichungtoi2 = view.findViewById(R.id.lienhevoichungtoi2);


 //       String idUser = getArguments().getString("id");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
 //       databaseReference = FirebaseDatabase.getInstance().getReference("user");
        userID = firebaseUser.getUid();

  //      firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users").child(userID).child("information");
        databaseReference.addValueEventListener(this);


 //       System.out.println("ID = " + idUser);

      /*  databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 User user = snapshot.getValue(User.class);
                 if(user != null){
                     tvProfileName.setText(user.getName());
                     tvProfileEmail.setText(user.getUser());
                 }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FragmentProfile.this,"Có gì đó sai sai",Toast.LENGTH_SHORT).show();
            }
        });  */

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog=new AlertDialog.Builder(getContext())
                        .setTitle("Xác nhận đăng xuất")
                        .setMessage("Bạn có chắc chắn đăng xuất không ?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getContext(), LogIn.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                alertDialog.show();
            }
        });
        tvYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhSachYeuThich.class);
                startActivity(intent);
            }
        });
        tvYeuThich2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhSachYeuThich.class);
                startActivity(intent);
            }
        });
        tvLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DonHangDaMua.class);
                startActivity(intent);
            }
        });
        tvLichSu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DonHangDaMua.class);
                startActivity(intent);
            }
        });
        tvThongTinCuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DiaChiCuaHang.class);
                startActivity(intent);
            }
        });
        tvThongTinCuaHang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DiaChiCuaHang.class);
                startActivity(intent);
            }
        });
        doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangePassword.class);
                startActivity(intent);
            }
        });
        doimmatkhau2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangePassword.class);
                startActivity(intent);
            }
        });
        suathongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SuaThongTin.class);
                startActivity(intent);
            }
        });
        suathongtin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SuaThongTin.class);
                startActivity(intent);
            }
        });
        lienhevoichungtoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LienHeVoiChungToi.class);
                startActivity(intent);
            }
        });
        lienhevoichungtoi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LienHeVoiChungToi.class);
                startActivity(intent);
            }
        });

 /*       ciAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateAvatar.class);
                startActivity(intent);
            }
        });  */

        btnSetAvt.setVisibility(View.INVISIBLE);

        setUserAvatar();
        initListener();

        return view;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        User user = snapshot.getValue(User.class);
        Log.d("kiemtra",user.toString());
        tvProfileName.setText(user.getName());
        tvProfileEmail.setText(user.getUser());
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }


    private void setUserAvatar() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        Glide.with(this).load(user.getPhotoUrl()).error(R.drawable.avatardefult1).into(ciAvatar);
    }

    private void initListener() {
        ciAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
                btnSetAvt.setVisibility(View.VISIBLE);
                clickDeThayDoiAnh.setVisibility(View.INVISIBLE);
            }
        });
        btnSetAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAvatar();
            }
        });
    }

    private void onClickRequestPermission(){
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity == null){
            return;
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            mainActivity.openGallery();
            return;
        }
        if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            mainActivity.openGallery();
        }else {
            String[] permisstions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            this.requestPermissions(permisstions,MY_REQUEST_CODE);
        }
    }
    public void setBitmapImageView(Bitmap bitmapImageView){
        ciAvatar.setImageBitmap(bitmapImageView);
    }

    public void setmUri(Uri mUri) {
        this.mUri = mUri;
    }

    private void updateAvatar(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        progressDialog.show();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(mUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        btnSetAvt.setVisibility(View.INVISIBLE);
                        clickDeThayDoiAnh.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(),"Đổi ảnh đại diện thành công",Toast.LENGTH_SHORT).show();
                            setUserAvatar();
                        }
                    }
                });
    }
}
