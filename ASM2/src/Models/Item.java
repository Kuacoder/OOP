package Models;

import Controller.Filemanage;
import Controller.Itemmanage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Item extends Model implements Serializable{
    private String mahang,tenhang,kieudonggoi;
    private int soluong=0,gia=0;
    public Item(){
        mahang = "";
        tenhang = "";
        kieudonggoi = "";
        soluong = 0;
        gia = 0;
    }
    @Override
    public void input() {

        Boolean validateMaHang = Item.validateMaHang(this.mahang);
        while(validateMaHang == false) {
            System.out.printf("- Nhập mã mặt hàng (VD: A00): ");
            this.mahang = getScanner().nextLine();
            validateMaHang = Item.validateMaHang(this.mahang);
            if(!validateMaHang){
                System.err.println("Vui lòng nhập đúng định dạng!");
                System.out.println(" ");
            }
        }
        System.out.printf("- Nhập tên mặt hàng: ");
        this.tenhang = getScanner().nextLine();

        System.out.printf("- Nhập kiểu đóng gói: ");
        this.kieudonggoi = getScanner().nextLine();

        System.out.printf("- Nhập số lượng: ");
        this.soluong = getScanner().nextInt();

        System.out.printf("- Nhập giá: ");
        this.gia = getScanner().nextInt();
    }

    @Override
    public void update(ArrayList list2) {
        System.out.printf("- Sửa tên mặt hàng: ");
        String tenhang = getScanner().nextLine();

        System.out.printf("- Sửa kiểu đóng gói: ");
        String kieudonggoi = getScanner().nextLine();

        System.out.printf("- Sửa số lượng: ");
        Integer soluong = getScanner().nextInt();

        System.out.printf("- Sửa giá:");
        Integer gia = getScanner().nextInt();

        System.out.printf("Bạn có muốn cập nhật thông tin? (Y/N): ");
        Boolean save = getScanner().nextLine().equalsIgnoreCase("y") ? true : false;
        if(save ==  true) {
            this.tenhang = tenhang;
            this.kieudonggoi = kieudonggoi;
            this.soluong = soluong;
            this.gia = gia;
            Filemanage.saveToFile(list2,Itemmanage.filePath);
            System.out.println("Thông tin đã được cập nhật!");
        }
        else {
            System.out.println("Không thực hiện cập nhật thông tin");
        }
    }
    public static Boolean validateMaHang(String mahang) {
        Pattern pattern = Pattern.compile("^A[0-9]{1,2}$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(mahang).matches();
    }

    public String getMahang() {
        return mahang;
    }

    public void setMahang(String mahang) {
        this.mahang = mahang;
    }

    public String getTenhang() {
        return tenhang;
    }

    public void setTenhang(String tenhang) {
        this.tenhang = tenhang;
    }

    public String getKieudonggoi() {
        return kieudonggoi;
    }

    public void setKieudonggoi(String kieudonggoi) {
        this.kieudonggoi = kieudonggoi;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
    public void show(){
        System.out.printf("| %-12s| %-15s| %-23s| %-9d| %-10s|\n",mahang,tenhang,kieudonggoi,soluong,gia);
    }
}
