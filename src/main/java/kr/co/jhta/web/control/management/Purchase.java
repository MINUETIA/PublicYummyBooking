package kr.co.jhta.web.control.management;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.jhta.web.service.purchase.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/purchase")
public class Purchase {

    private final PurchaseService purchaseService;

    @GetMapping("/list")
    public String search(Model model,
                         @RequestParam(value = "status", defaultValue = "0") int status,
                         @RequestParam(value = "name", defaultValue = "noname")String name,
                         @RequestParam(value = "code", defaultValue = "PS0000")String code,
                         @RequestParam(value = "start", defaultValue = "2000-01-01")String start,
                         @RequestParam(value = "end", defaultValue = "9999-12-31")String end  ) {

        model.addAttribute("name", name);
        model.addAttribute("code", code);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("status", status);

        if(status == 1 || status == 2 ){
            model.addAttribute("list", purchaseService.getAllByStatus(status));
            return"views/manage/purchaseList.html";
        }
        if(!name.equals("noname")){
            model.addAttribute("list", purchaseService.getAllByName(name));
            return"views/manage/purchaseList.html";
        }
        if(!code.equals("PS0000")){
            model.addAttribute("list", purchaseService.getAllByCode(code));
            return"views/manage/purchaseList.html";
        }
        if(!start.equals("2000-01-01") && end.equals("9999-12-31")){

            model.addAttribute("list", purchaseService.getAllByStartDay(start));
            return"views/manage/purchaseList.html";
        }
        if(start.equals("2000-01-01") && !end.equals("9999-12-31")){

            model.addAttribute("list", purchaseService.getAllByEndDay(end));
            return"views/manage/purchaseList.html";
        }
        if(!start.equals("2000-01-01") && !end.equals("9999-12-31")){

            model.addAttribute("list", purchaseService.getAllByStartDayEndDay(start,end));
            return"views/manage/purchaseList.html";
        }

        model.addAttribute("list", purchaseService.getAll());

        return"views/manage/purchaseList.html";
    }


    @GetMapping("/downloadExcel")
    @ResponseBody
    public void download(HttpServletResponse response,
                         @RequestParam(value = "status", defaultValue = "0") int status,
                         @RequestParam(value = "name", defaultValue = "noname")String name,
                         @RequestParam(value = "code", defaultValue = "PS0000")String code,
                         @RequestParam(value = "start", defaultValue = "2000-01-01")String start,
                         @RequestParam(value = "end", defaultValue = "9999-12-31")String end
                         ) throws IOException {


        List<HashMap<String, Object>> list = null;
        list= purchaseService.getAll();

        if(status == 1 || status == 2 ){
            list= purchaseService.getAllByStatus(status);
        }
        if(!name.equals("noname")){
            list= purchaseService.getAllByName(name);
        }
        if(!code.equals("PS0000")){
            list= purchaseService.getAllByCode(code);
        }
        if(!start.equals("2000-01-01") && end.equals("9999-12-31")){
            list= purchaseService.getAllByStartDay(start);
        }
        if(start.equals("2000-01-01") && !end.equals("9999-12-31")){
            list= purchaseService.getAllByEndDay(end);
        }
        if(!start.equals("2000-01-01") && !end.equals("9999-12-31")){
            list= purchaseService.getAllByStartDayEndDay(start,end);

        }

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("구매입고 등록");
        int rowNo = 0;

        Row headerRow = sheet.createRow(rowNo++);
        headerRow.createCell(0).setCellValue("번호");
        headerRow.createCell(1).setCellValue("회사");
        headerRow.createCell(2).setCellValue("입고번호");
        headerRow.createCell(3).setCellValue("상태");
        headerRow.createCell(4).setCellValue("등록일");
        headerRow.createCell(5).setCellValue("거래유형");
        headerRow.createCell(6).setCellValue("담당자");
        headerRow.createCell(7).setCellValue("수량");
        headerRow.createCell(8).setCellValue("단가");
        headerRow.createCell(9).setCellValue("공급가액");
        headerRow.createCell(10).setCellValue("부가세");
        headerRow.createCell(11).setCellValue("수정일");
        headerRow.createCell(12).setCellValue("수정자");

        int no = 0;
        for (HashMap<String, Object> map : list) {
            Row row = sheet.createRow(rowNo++);
            row.createCell(0).setCellValue(++no);
            row.createCell(1).setCellValue((String) map.get("vendorname"));
            row.createCell(2).setCellValue((String) map.get("purchaseno"));
            row.createCell(3).setCellValue((String) map.get("regdate"));
            row.createCell(4).setCellValue((Integer) map.get("type"));
            row.createCell(5).setCellValue((String) map.get("manager"));
            row.createCell(6).setCellValue((Integer) map.get("cnt"));
            row.createCell(7).setCellValue((Integer) map.get("price"));
            row.createCell(8).setCellValue((Integer) map.get("sprice"));
            row.createCell(9).setCellValue((Integer) map.get("tax"));
            row.createCell(10).setCellValue((Integer) map.get("total"));
            row.createCell(11).setCellValue((String) map.get("modifydate"));
            row.createCell(12).setCellValue((String) map.get("modifier"));

        }

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=purchaseList.xls");

        workbook.write(response.getOutputStream());
        workbook.close();

    }

    @GetMapping("/modify")
    @ResponseBody
    public String modify(
            @RequestParam(value = "type", defaultValue = "0") int type,
            @RequestParam(value = "vendorname", defaultValue = "noname")String name,
            @RequestParam(value = "purchaseno", defaultValue = "0")String purchaseno,
            @RequestParam(value = "price", defaultValue = "0")int price,
            @RequestParam(value = "cnt", defaultValue = "0")int cnt,
            @RequestParam(value = "sprice", defaultValue = "0")int sprice){

        // 이름으로 벤더번호 가져오기
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("type", type);
        map.put("purchaseno", purchaseno);
        map.put("price", price);
        map.put("cnt", cnt);
        map.put("sprice", sprice);

        boolean result = purchaseService.getOne(purchaseno);

        if(result){
            System.out.println(map);
            purchaseService.modifyData(map);
        }else {
            purchaseService.insertOne(map);
        }

        return "OK";
    }
}
