package Models;

import Controller.Customermanage;
import Controller.Filemanage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Customer extends Model implements Serializable{
    private String makhachhang,tenkhachhang,sodienthoai;
    private int diemthuong=0;
    public Customer(){
        makhachhang="";
        tenkhachhang = "";
        sodienthoai = "";
        diemthuong = 0;
    }
    @Override
    public void input() {
        Boolean validateMaKhachHang = Customer.validateMaKhachHang(this.makhachhang);
        while (validateMaKhachHang == false) {
            System.out.printf("- Nhập mã khách hàng (VD: KH01): ");
            this.makhachhang = getScanner().nextLine();
            validateMaKhachHang = Customer.validateMaKhachHang(this.makhachhang);
            if (!validateMaKhachHang) {
                System.err.println("Vui lòng nhập đúng định dạng!");
                System.out.println(" ");
            }
        }
        if (makhachhang.equalsIgnoreCase("KH00")) {
            this.tenkhachhang = "No name";
            this.sodienthoai = "";
            this.diemthuong = 0;
        } else {
            System.out.printf("- Nhập tên khách hàng: ");
            this.tenkhachhang = getScanner().nextLine();

            System.out.printf("- Nhập số điện thoại: ");
            this.sodienthoai = getScanner().nextLine();
            }
    }
    @Override
    public void update(ArrayList list2) {
        System.out.printf("- Sửa tên khách hàng: ");
        String tenkhachhang = getScanner().nextLine();

        System.out.printf("- Sửa số điện thoại: ");
        String sodienthoai = getScanner().nextLine();

        System.out.printf("Bạn có muốn cập nhật thông tin? (Y/N): ");
        Boolean save = getScanner().nextLine().equalsIgnoreCase("y") ? true : false;
        if(save ==  true) {
            this.tenkhachhang = tenkhachhang;
            this.sodienthoai = sodienthoai;
            Filemanage.saveToFile(list2, Customermanage.filePath);
            System.out.println("Thông tin đã được cập nhật!");
        }else {
            System.out.println("Không thực hiện cập nhật thông tin");
        }
    }

    public int getDiemthuong() {
        return diemthuong;
    }

    public void setDiemthuong(int diemthuong) {
        this.diemthuong = diemthuong;
    }

    public String getMakhachhang() {
        return makhachhang;
    }

    public void setMakhachhang(String makhachhang) {
        this.makhachhang = makhachhang;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }
    public static Boolean validateMaKhachHang(String makhachhang) {
        Pattern pattern = Pattern.compile("^KH[0-9]{1,2}$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(makhachhang).matches();
    }
    public void show(){
        System.out.printf("| %-14s| %-23s| %-19s| %-15d|\n",makhachhang,tenkhachhang,sodienthoai,diemthuong);
    }

}
