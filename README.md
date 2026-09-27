# Lab A4: Calculator & BMI - Android Application

Dự án Android mô ứng dụng Máy tính cơ bản kết hợp tính chỉ số BMI, tích hợp các lớp kiểm tra dữ liệu an toàn và các tính năng nâng cao

## Các tính năng nâng cao đã thực hiện

### 1. NC3: Đổi màu kết quả phân loại BMI 
* **Mô tả:** Sử dụng phương thức `ContextCompat.getColor()` kết hợp với `setTextColor()` để thay đổi màu sắc văn bản phân loại BMI dựa trên kết quả tính toán thực tế
* **Mức phân màu:**
    * Gầy (< 18.5): Xanh dương (`holo_blue_dark`)
    * Bình thường (18.5 - 22.9): Xanh lá (`holo_green_dark`)
    * Thừa cân (23 - 24.9): Xanh cam (`holo_orange_dark`)
    * Béo phì (≥ 25): Xanh đỏ (`holo_red_dark`)

### 2. NC4: Sử dụng TextInputLayout quản lý lỗi hiện đại
* **Mô tả:** Nâng cấp các ô nhập liệu từ `EditText` thông thường sang thẻ bọc chuẩn Material Design `TextInputLayout` kết hợp `TextInputEditText`
* **Lợi ích:** Khi xảy ra lỗi dữ liệu (như để trống hoặc chia cho 0), khung viền tự động chuyển đỏ và hiển thị dòng chữ thông báo lỗi ngay dưới ô nhập, mang lại trải nghiệm chuyên nghiệp và bắt mắt hơn

---
## Hệ thống kiểm tra dữ liệu 4 lớp
Ứng dụng áp dụng quy trình kiểm tra dữ liệu ngặt nghèo gồm 4 lớp:
1. **Kiểm tra rỗng (Empty Check):** Chặn ngay từ đầu nếu người dùng chưa nhập liệu
2. **Kiểm tra định dạng (Format Check):** Dùng `try-catch` (`NumberFormatException`) bẫy lỗi nhập sai ký tự/chữ cái
3. **Kiểm tra nghiệp vụ (Business Rules):** Chặn lỗi chia cho 0 hoặc giá trị âm bất hợp lý
4. **Kiểm tra miền giá trị / Quy đổi:** Tự động quy đổi đơn vị chiều cao (cm/m) cho phần BMI