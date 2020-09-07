package Models;

import Controller.Customermanage;
import Controller.Itemmanage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Bill implements Serializable{
    private static int sothutuhang = 1;
    private static int sothutumahang = 1;
    private int NO;
    private String mahoadon, makhachhang, mahang, manhanvien;
    private int soluong, tong;
    private Date ngaytao;
    public Customermanage customermanage;
    public Itemmanage itemmanage;
    private Boolean isvalidate = false;

    public Boolean getvalidate() {
        return isvalidate;
    }

    public Bill() {
        mahoadon = "";
        makhachhang = "";
        mahang = "";
        soluong = 0;
        tong = 0;
    }

    public String getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(String mahoadon) {
        this.mahoadon = mahoadon;
    }

    public String getMakhachhang() {
        return makhachhang;
    }

    public void setMakhachhang(String makhachhang) {
        this.makhachhang = makhachhang;
    }

    public String getMahang() {
        return mahang;
    }

    public void setMahang(String mahang) {
        this.mahang = mahang;
    }

    public String getManhanvien() {
        return manhanvien;
    }

    public void setManhanvien(String manhanvien) {
        this.manhanvien = manhanvien;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getTong() {
        return tong;
    }

    public void setTong(int tong) {
        this.tong = tong;
    }

    public Date getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Date ngaytao) {
        this.ngaytao = ngaytao;
    }

    public void input() {
        boolean tieptuctaohoadon =true;
        Boolean validateMaHoaDon = Bill.validateMaHoaDon(this.mahoadon);
        while (tieptuctaohoadon==true) {
            String mahang = "";
            isvalidate = true;
            System.out.println("Nhập mã nhân viên đang thực hiện thanh toán: ");
             manhanvien = getScanner().nextLine();
            while (validateMaHoaDon == false) {
                System.out.println(" ");
                System.out.printf("- Nhập mã hóa đơn (VD: HD00): ");
                this.mahoadon = getScanner().nextLine();
                validateMaHoaDon = Bill.validateMaHoaDon(this.mahoadon);
                if (!validateMaHoaDon) {
                    System.err.printf("Vui lòng nhập đúng định dạng!");
                    System.out.println("...");
                }
            }
            System.out.printf("- Nhập mã khách hàng: ");
            String maKhachhang = getScanner().nextLine();
            if (!customermanage.kiemTraTonTaiMakhachhang(maKhachhang)) {
                isvalidate = false;
                System.err.println("Mã khách hàng không tồn tại");
            } else {
                boolean continueornot = true;
                while (continueornot) {
                    NO = this.soluong;
                    System.out.printf("- Nhập mã hàng %d: ", sothutumahang++);
                    mahang = getScanner().nextLine();
                    if (!itemmanage.kiemTraTonTaiMahang(mahang)) {
                        isvalidate = false;
                        System.err.println("Mặt hàng này không có trong cửa hàng!");
                    } else {
                        System.out.printf("- Nhập số lượng %d: ", sothutuhang++);
                         this.soluong = getScanner().nextInt();
                    }
                    if (!itemmanage.muahang(mahang, soluong)) {
                        isvalidate = false;
                    }
                    this.soluong += NO;
                    System.out.println(" ");
                    System.out.println("Bạn có muốn tiếp tục mua hàng(Y/N):");
                    continueornot = getScanner().nextLine()
                            .equalsIgnoreCase("y") ? true : false;
                }

                if (isvalidate) {
                    this.manhanvien = manhanvien;
                    this.mahoadon = mahoadon;
                    this.makhachhang = maKhachhang;
                    this.mahang = mahang;
                    this.soluong = soluong;
                }
            }
            System.out.println("...");
            System.out.println("Bạn có muốn tạo hóa đơn mới(Y/N): ");
            tieptuctaohoadon = getScanner().nextLine().equalsIgnoreCase("y") ? true : false;
        }
    }

    public void showtheomanhanvien(){
        System.out.printf("| %-14s| %-23s| %-19d| %-15d|\n",mahoadon,Helper.convertDateToString(ngaytao),soluong,tong);
    }
    public void showtheongaytao(){
        System.out.printf("| %-14s| %-23s| %-19d|\n",mahoadon,Helper.getHourMinuteFromDate(ngaytao),tong);
    }
    public Scanner getScanner(){
        return new Scanner(System.in);
    }
    public static Boolean validateMaHoaDon(String mahoadon) {
        Pattern pattern = Pattern.compile("^HD[0-9]{1,2}$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(mahoadon).matches();
    }

}
