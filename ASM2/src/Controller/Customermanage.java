package Controller;

import Models.Bill;
import Models.Customer;
import Models.Item;
import Models.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Customermanage implements Imanage,Serializable {
    public Itemmanage itemmanage;
    public static String filePath = "C:\\Users\\Admin\\Desktop\\CUSTOMERS.dat";
    private ArrayList<Customer> customers = new ArrayList<>();

    private void appendcustomer(Customer newcustomer) {
        getcustomerfromfile().add(newcustomer);
        Filemanage.saveToFile(customers, Customermanage.filePath);
    }

    public ArrayList<Customer> getcustomerfromfile() {
        ArrayList<Model> objects = Filemanage.readFromFile(Customermanage.filePath);
        final ArrayList<Customer> customers = new ArrayList<>();
        try {
            for (Object object : objects) {
                if (object instanceof Customer) {
                    customers.add((Customer) object);
                }
            }
        } catch (Exception e) {
            System.err.println("Không thể lấy thông tin khách hàng từ file:" + e.toString());
        } finally {
            this.customers = customers;
            return customers;
        }
    }
    @Override
    public void showMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println(" ");
            System.out.println("======================================= ");
            System.out.println("|          Quản lý khách hàng         | ");
            System.out.println("======================================= ");
            System.out.println("| 1. Xem danh sách khách hàng         | ");
            System.out.println("| 2. Cập nhật thông tin khách hàng    | ");
            System.out.println("| 3. Thêm mới một khách hàng          | ");
            System.out.println("| 0. Trở về menu chính                | ");
            System.out.println("======================================= ");
            try {
                System.out.printf("#Chọn: ");
                choice = getScanner().nextInt();
                switch (choice) {
                    case 1:
                        this.showAll();
                        break;
                    case 2:
                        this.update();
                        break;
                    case 3:
                        this.input();
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
    @Override
    public void input() {
        System.out.println("======== Thêm mới khách hàng ========");
        Boolean continueOrNot = true;
        while (continueOrNot == true) {
            Customer newcustomer = new Customer();
            newcustomer.input();
            appendcustomer(newcustomer);
            System.out.printf("Bạn có muốn tiếp tục?(Y/N): ");
            continueOrNot = getScanner().nextLine().equalsIgnoreCase("y") ? true : false;
        }
        Filemanage.saveToFile(this.customers, Customermanage.filePath);
    }
    @Override
    public void update() {
        System.out.println("======== Cập nhật thông tin khách hàng ========");
        Boolean continueOrNot = true;
        while (continueOrNot == true) {
            System.out.printf("- Nhập mã khách hàng: ");
            final String makhachhang = getScanner().nextLine();
            ArrayList<Customer> list2 = this.getcustomerfromfile();
            Boolean isFound = false;
            for (Customer eachcustomer : list2) {
                if (eachcustomer.getMakhachhang().equalsIgnoreCase(makhachhang)) {
                    eachcustomer.update(list2);
                    isFound = true;
                    break;
                }
            }
            if (isFound == false) {
                System.err.println("Không tìm thấy khách hàng có mã: " + makhachhang);
            }
            System.out.println(" ");
            System.out.println("Bạn có muốn tiếp tục(Y/N):");
            continueOrNot = getScanner().nextLine().equalsIgnoreCase("y") ? true : false;
        }
    }

    public Customer getcustomerfrombill(String makhachhang) {
        try {
            return getcustomerfromfile().stream()
                    .filter(customer -> customer.getMakhachhang().equalsIgnoreCase(makhachhang))
                    .collect(Collectors.toList()).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void showAll() {
        System.out.println("================================================================================");
        System.out.println("|                           Danh sách khách hàng                               |");
        System.out.println("================================================================================");
        System.out.println("| Mã khách hàng | Tên khách hàng         | Số điện thoại      | Điểm thưởng    |");
        System.out.println("================================================================================");
        for (Customer customer : getcustomerfromfile()) {
            customer.show();
        }
        System.out.println("================================================================================");
    }

    @Override
    public Scanner getScanner() {
        return new Scanner(System.in);
    }

    public Boolean kiemTraTonTaiMakhachhang(String makhachhang) {
        return !getcustomerfromfile().stream()
                .filter(customer -> customer.getMakhachhang().equalsIgnoreCase(makhachhang))
                .collect(Collectors.toList()).isEmpty();
    }

    public void tinhdiemthuong(String makhachhang, int tong) {
        ArrayList<Customer> list = this.getcustomerfromfile();
        Boolean found = false;
        for (Customer customer : list) {
            if (customer.getMakhachhang().equalsIgnoreCase(makhachhang)) {
                found = true;
                if(customer.getMakhachhang().equalsIgnoreCase("KH00")){
                    break;
                }else {
                    int diem = customer.getDiemthuong();
                    customer.setDiemthuong(tong / 1000);
                    diem+=customer.getDiemthuong();
                    customer.setDiemthuong(diem);
                    Filemanage.saveToFile(list,Customermanage.filePath);
                }
            }
        }
    }
}
