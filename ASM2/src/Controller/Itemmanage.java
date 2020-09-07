package Controller;

import Models.Item;
import Models.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Itemmanage implements Imanage,Serializable {
    public static String filePath = "C:\\Users\\Admin\\Desktop\\ITEMS.dat";
    private ArrayList<Item> items = new ArrayList<>();
    public Customermanage customermanage;
    private void appenditem(Item newitem) {
        getItemfromfile().add(newitem);
        Filemanage.saveToFile(items, Itemmanage.filePath);
    }
    public ArrayList<Item> getItemfromfile() {
        ArrayList<Model> objects =  Filemanage.readFromFile(Itemmanage.filePath);
        final ArrayList<Item> items = new ArrayList<>();
        try {
            for(Object object: objects) {
                if(object instanceof Item) {
                    items.add((Item) object);
                }
            }
        }catch (Exception e){
            System.err.println("Không thể lấy mặt hàng từ file:"+e.toString());
        } finally {
            this.items = items;
            return items;
        }
    }
    public Item getitemfrombill(String mahang) {
        try {
            return getItemfromfile().stream()
                    .filter(item -> item.getMahang().equalsIgnoreCase(mahang))
                    .collect(Collectors.toList()).get(0);
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public void showMenu() {
        int choice = -1;
        while(choice != 0) {
            System.out.println(" ");
            System.out.println("======================================= ");
            System.out.println("|      Quản lý danh sách mặt hàng     | ");
            System.out.println("======================================= ");
            System.out.println("| 1. Xem danh sách các mặt hàng       | ");
            System.out.println("| 2. Cập nhật thông tin mặt hàng      | ");
            System.out.println("| 3. Thêm mới một mặt hàng            | ");
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
            }catch (Exception e){
                System.err.println("Bạn đã chọn sai vui lòng chọn lại từ (0-3)!");
            }
        }
    }

    @Override
    public void input() {
        System.out.println("======== Thêm mới một mặt hàng ========");
        Boolean continueOrNot = true;
        while(continueOrNot == true) {
            Item newitem = new Item();
            newitem.input();
            appenditem(newitem);
            System.out.printf("Bạn có muốn tiếp tục?(Y/N): ");
            continueOrNot = getScanner().nextLine().equalsIgnoreCase("y") ? true : false;
        }
        Filemanage.saveToFile(this.items, Itemmanage.filePath);
    }

    @Override
    public void update() {
        System.out.println("======== Cập nhật thông tin mặt hàng ========");
        Boolean continueOrNot = true;
        while(continueOrNot == true) {
            System.out.printf("- Nhập mã mặt hàng: ");
            final String mahang = getScanner().nextLine();
            ArrayList<Item> list2 = this.getItemfromfile();
            Boolean isFound = false;
            for(Item eachitem: list2) {
                if(eachitem.getMahang().equalsIgnoreCase(mahang)) {
                    eachitem.update(list2);
                    isFound = true;
                    break;
                }
            }
            if(isFound == false) {
                System.err.println("Không tìm thấy mặt hàng với mã: "+mahang);
            }
            System.out.println(" ");
            System.out.println("Bạn có muốn tiếp tục?(Y/N):");
            continueOrNot = getScanner().nextLine().equalsIgnoreCase("y") ? true : false;
        }
    }

    @Override
    public void showAll() {
        System.out.println("================================================================================");
        System.out.println("                           Danh sách mặt hàng                                   ");
        System.out.println("================================================================================");
        System.out.println("| Mã mặt hàng | Tên mặt hàng   | Kiểu đóng gói          | Số lượng | Giá       |");
        System.out.println("================================================================================");
        for(Item item: getItemfromfile()) {
            item.show();
        }
        System.out.println("================================================================================");
    }

    @Override
    public Scanner getScanner() {
        return new Scanner(System.in);
    }

    public  Boolean muahang(String mahang,int soluong) {
        try {
            ArrayList<Item> list2 = (ArrayList<Item>) this.getItemfromfile().stream()
                    .filter(item -> item.getMahang().equalsIgnoreCase(mahang))
                    .collect(Collectors.toList());
            Item founditem = (list2.get(0));
            if(founditem.getSoluong() < soluong) {
                System.err.println("Mặt hàng này hiện tại đang tạm hết hàng!");
                return false;
            }
            founditem.setSoluong(founditem.getSoluong() - soluong);
            Filemanage.saveToFile(list2, Itemmanage.filePath);
            return true;
        }catch (Exception e){
            System.err.println("Không tìm thấy mã mặt hàng!");
            return false;
        }
    }
    public Boolean kiemTraTonTaiMahang(String mahang) {
        return !getItemfromfile().stream()
                .filter(item -> item.getMahang().equalsIgnoreCase(mahang))
                .collect(Collectors.toList()).isEmpty();
    }
}
