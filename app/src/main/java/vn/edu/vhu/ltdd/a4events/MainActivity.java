package vn.edu.vhu.ltdd.a4events;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "A4_221A010861";

    // khai báo các view cả TextInputLayout cho NC4
    private TextInputLayout tilSoA, tilSoB;
    private EditText edtSoA, edtSoB, edtCanNang, edtChieuCao;
    private TextView tvKetQua, tvBmi, tvPhanLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        // ánh xạ view
        tilSoA = findViewById(R.id.tilSoA);
        tilSoB = findViewById(R.id.tilSoB);
        edtSoA = findViewById(R.id.edtSoA);
        edtSoB = findViewById(R.id.edtSoB);
        tvKetQua = findViewById(R.id.tvKetQua);
        edtCanNang = findViewById(R.id.edtCanNang);
        edtChieuCao = findViewById(R.id.edtChieuCao);
        tvBmi = findViewById(R.id.tvBmi);
        tvPhanLoai = findViewById(R.id.tvPhanLoai);

        Button btnCong = findViewById(R.id.btnCong);
        Button btnTru = findViewById(R.id.btnTru);
        Button btnNhan = findViewById(R.id.btnNhan);
        Button btnChia = findViewById(R.id.btnChia);
        Button btnXoa = findViewById(R.id.btnXoa);
        Button btnTinhBmi = findViewById(R.id.btnTinhBmi);

        btnCong.setOnClickListener(v -> tinhToan('+'));
        btnTru.setOnClickListener(v -> tinhToan('-'));

        View.OnClickListener chung = v -> {
            int id = v.getId();
            if (id == R.id.btnNhan) {
                tinhToan('*');
            } else if (id == R.id.btnChia) {
                tinhToan('/');
            }
        };
        btnNhan.setOnClickListener(chung);
        btnChia.setOnClickListener(chung);

        btnXoa.setOnClickListener(v -> xoaTrang());
        btnTinhBmi.setOnClickListener(v -> tinhBmi());
    }

    // NC4 - TextInputLayout
    private void tinhToan(char phepToan) {
        String chuoiA = edtSoA.getText().toString().trim();
        String chuoiB = edtSoB.getText().toString().trim();

        // kiểm tra rỗng cho ô A bằng TextInputLayout
        if (chuoiA.isEmpty()) {
            tilSoA.setError(getString(R.string.err_empty));
            tilSoA.requestFocus();
            return;
        } else {
            tilSoA.setError(null); // Xóa lỗi khi đã nhập đúng
        }

        // kiểm tra rỗng cho ô B bằng TextInputLayout
        if (chuoiB.isEmpty()) {
            tilSoB.setError(getString(R.string.err_empty));
            tilSoB.requestFocus();
            return;
        } else {
            tilSoB.setError(null); // Xóa lỗi khi đã nhập đúng
        }

        double a, b;
        try {
            a = Double.parseDouble(chuoiA);
            b = Double.parseDouble(chuoiB);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Dữ liệu nhập không phải số", e);
            Toast.makeText(this, R.string.err_not_number, Toast.LENGTH_SHORT).show();
            return;
        }

        // xử lý chia cho 0 trên TextInputLayout của số B
        if (phepToan == '/' && b == 0) {
            tilSoB.setError(getString(R.string.err_divide_zero));
            Toast.makeText(this, R.string.err_divide_zero, Toast.LENGTH_SHORT).show();
            return;
        } else {
            tilSoB.setError(null);
        }

        double ketQua;
        switch (phepToan) {
            case '+':
                ketQua = a + b;
                break;
            case '-':
                ketQua = a - b;
                break;
            case '*':
                ketQua = a * b;
                break;
            default:
                ketQua = a / b;
                break;
        }

        tvKetQua.setText(String.format(Locale.getDefault(), "%.2f %c %.2f = %.2f", a, phepToan, b, ketQua));
        Log.d(TAG, "Phép tính thành công");
    }

    private void xoaTrang() {
        edtSoA.setText("");
        edtSoB.setText("");
        tilSoA.setError(null);
        tilSoB.setError(null);
        tvKetQua.setText(R.string.result_placeholder);
        edtSoA.requestFocus();
    }

    //  NC3 - Đổi màu kết quả
    private void tinhBmi() {
        String strCanNang = edtCanNang.getText().toString().trim();
        String strChieuCao = edtChieuCao.getText().toString().trim();

        if (strCanNang.isEmpty()) {
            edtCanNang.setError(getString(R.string.err_empty));
            edtCanNang.requestFocus();
            return;
        }
        if (strChieuCao.isEmpty()) {
            edtChieuCao.setError(getString(R.string.err_empty));
            edtChieuCao.requestFocus();
            return;
        }

        try {
            double canNang = Double.parseDouble(strCanNang);
            double chieuCao = Double.parseDouble(strChieuCao);

            if (canNang <= 0 || chieuCao <= 0) {
                Toast.makeText(this, R.string.err_positive, Toast.LENGTH_SHORT).show();
                return;
            }

            if (chieuCao > 3) {
                chieuCao = chieuCao / 100.0;
            }

            double bmi = canNang / (chieuCao * chieuCao);
            tvBmi.setText(String.format(Locale.getDefault(), "BMI = %.1f", bmi));

            String kqPhanLoai = phanLoai(bmi);
            tvPhanLoai.setText(kqPhanLoai);

            // NC3 - Đổi màu chữ kết quả BMI
            if (bmi < 18.5) {
                tvPhanLoai.setTextColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark));
            } else if (bmi < 23) {
                tvPhanLoai.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
            } else if (bmi < 24.9) {
                tvPhanLoai.setTextColor(ContextCompat.getColor(this, android.R.color.holo_orange_dark));
            } else {
                tvPhanLoai.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
            }

        } catch (NumberFormatException e) {
            Log.e(TAG, "Lỗi nhập liệu BMI", e);
            Toast.makeText(this, R.string.err_not_number, Toast.LENGTH_SHORT).show();
        }
    }

    private String phanLoai(double bmi) {
        if (bmi < 18.5) return getString(R.string.bmi_under);
        if (bmi < 23) return getString(R.string.bmi_normal);
        if (bmi < 24.9) return getString(R.string.bmi_over);
        return getString(R.string.bmi_obese);
    }
}