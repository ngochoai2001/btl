package activiti.VanDeVeLogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcuoikhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {

    Toolbar toolBarLaylaiMatKhau;
    Button btnXacNhanResetPassWord;
    EditText edEmailLayPassWord;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView tvThongBaoResetMK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        toolBarLaylaiMatKhau = findViewById(R.id.toolBarLaylaiMatKhau);
        toolBarLaylaiMatKhau.setTitle("Quên mật khẩu");
        btnXacNhanResetPassWord =findViewById(R.id.btnXacNhanResetPassWord);
        edEmailLayPassWord = findViewById(R.id.edEmailLayPassWord);
        firebaseAuth = FirebaseAuth.getInstance();
        tvThongBaoResetMK = findViewById(R.id.tvThongBaoResetMK);

        setSupportActionBar(toolBarLaylaiMatKhau);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnXacNhanResetPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edEmailLayPassWord.getText().toString() == "") {
                    tvThongBaoResetMK.setText("Vui lòng nhập email");
                }else{
                    firebaseAuth.sendPasswordResetEmail(edEmailLayPassWord.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPassword.this, "Pass word đã được gửi tới email", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                tvThongBaoResetMK.setText("Không tồn tại email");
                            }
                        }
                    });
                }
            }
        });
    }
}