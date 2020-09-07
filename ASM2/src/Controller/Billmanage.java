package Controller;

import Models.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Billmanage implements Serializable {
    public static String filePath = "C:\\Users\\Admin\\Desktop\\BILLS.dat";
    private ArrayList<Bill> bills = new ArrayList<>();
    public Itemmanage itemmanage;
    public Customermanage customermanage;

    private void appendBill(Bill newbill) {
        getBillfromfile().add(newbill);
        Filemanage.saveToFile(bills, Billmanage.filePath);
    }

    public ArrayList<Bill> getBillfromfile() {
        ArrayList<Model> objects = Filemanage.readFromFile(Billmanage.filePath);
        final ArrayList<Bill> bills = new ArrayList<>();
        try {
            for (Object object : objects) {
                if (object instanceof Bill) {
                    bills.add((Bill) object);
                }
            }
        } catch (Exception e) {
            System.err.println("Không thể lấy thông tin hóa đơn từ file:" + e.toString());
        } finally {
            this.bills = bills;
            return bills;
        }
    }

    public void showMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println(" ");
            System.out.println("====================================================");
            System.out.println("|                  Quản lý hóa đơn                 |");
            System.out.println("====================================================");
            System.out.println("| 1. Tạo mới hóa đơn                               |");
            System.out.println("| 2. Hiển thị hóa đơn theo mã nhân viên thanh toán |");
            System.out.println("| 3. Hiển thị hóa đơn theo ngày tạo                |");
            System.out.println("| 0. Trở về menu chính                             |");
            System.out.println("====================================================");
            try {
                System.out.printf("#Chọn: ");
                choice = getScanner().nextInt();
                switch (choice) {
                    case 1:
                        this.input();
                        break;
                    case 2:
                        this.hienthitheomanhanvien();
                        break;
                    case 3:
                        System.out.println("");
                        System.out.println("====================  Hoán đơn trong ngày =======================");
                        System.out.printf("Nhập ngày (VD: 02/12/2018): ");
                        this.hienthitheongay(Helper.convertStringToDate(getScanner().nextLine()));
                        break;
                    default:
                        if (choice != 0 && choice > 3) {
                            System.err.println("Bạn đã chọn sai vui lòng chọn lại từ (0-3)!");
                        }
                        break;
                }
            } catch (Exception e) {
                System.err.println("Bạn đã chọn sai vui lòng chọn lại từ (0-3)!");
            }
        }
    }

    public void input() {
        System.out.println("========== Tạo mới hóa đơn =========");
        Bill newbill = new Bill();
        newbill.itemmanage = this.itemmanage;
        newbill.customermanage = this.customermanage;
        newbill.input();
        if (newbill.getvalidate()) {
            Item item = itemmanage.getitemfrombill(newbill.getMahang());
            Customer customer = customermanage.getcustomerfrombill(newbill.getMakhachhang());
            newbill.setTong(item.getGia() * newbill.getSoluong());
            customermanage.tinhdiemthuong(newbill.getMakhachhang(), newbill.getTong());
            newbill.setNgaytao(new Date());
            appendBill(newbill);
            System.out.println(" ");
            System.out.println("- Tổng tiền của hóa đơn là: " + newbill.getTong() + " VND ");
            System.out.println(" ");
            System.out.println("Nhấn phím Enter để tiếp tục!");
            getScanner().nextLine();
        }
    }

    public Scanner getScanner() {
        return new Scanner(System.in);
    }

    public void hienthitheomanhanvien() {
        System.out.printf("Nhập mã nhân viên thanh toán: ");
        String manhanvien = getScanner().nextLine();
        List<Bill> ketqua = getBillfromfile().stream()
                .filter(bill -> bill.getManhanvien().equalsIgnoreCase(manhanvien))
                .collect(Collectors.toList());
        if(ketqua.isEmpty()) {
            System.err.println("Không tìm thấy nhân viên này");
            System.out.println(" ");
        }else {
            System.out.println("================================================================================");
            System.out.println("|                           Hóa đơn theo mã nhân viên                          |");
            System.out.println("================================================================================");
            System.out.println("| Mã hóa đơn    | Ngày tạo               | Số mặt hàng        | Tổng tiền      |");
            System.out.println("================================================================================");
            for (Bill bill : ketqua) {
                bill.showtheomanhanvien();
            }
            System.out.println("================================================================================");
        }
    }

    public void hienthitheongay(Date date) {
        List<Bill> ketqua = this.getBillfromfile().stream()
                .filter(bill -> Helper.compare2Dates(bill.getNgaytao(), date))
                .collect(Collectors.toList());
        if (ketqua.isEmpty()) {
            System.err.println("Không tìm thấy hóa đơn");
            return;
        } else {
            System.out.println("===============================================================");
            System.out.println("|                     Hóa đơn trong ngày                      |");
            System.out.println("===============================================================");
            System.out.println("| Mã hóa đơn    | Giờ tạo hóa đơn        | Tổng tiền          |");
            System.out.println("===============================================================");
            for (Bill bill : ketqua) {
                bill.showtheongaytao();
            }
            System.out.println("===============================================================");
        }
    }
}
