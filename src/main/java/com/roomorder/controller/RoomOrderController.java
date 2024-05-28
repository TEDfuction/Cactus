package com.roomorder.controller;


import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.room.model.RoomService;
import com.room.model.RoomVO;
import com.roomorder.service.impl.RoomOrderImpl;
import com.roomorder.model.RoomOrderRepository;
import com.roomorder.model.RoomOrderVO;
import com.roomorderlist.model.RoomOrderListVO;
import com.roompromotion.model.RoomPromotionService;
import com.roompromotion.model.RoomPromotionVO;
import com.roomtype.service.impl.RoomTypeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/roomOrder")
public class RoomOrderController {


    @Autowired
    RoomService roomService;

    @Autowired
    private RoomOrderImpl roomorderImpl;

    @Autowired
    private RoomOrderRepository roomOrderRepository;

    @Autowired
    private MemberService memSvc;

    @Autowired
    private RoomPromotionService roomPromotionSvc;

    @Autowired
    private RoomTypeImpl roomTypeImpl;


    @GetMapping("/showCheckIn")
    public String showCheckIN(Model model) {
        List<RoomOrderVO> roomOrders = roomorderImpl.getAllRoomOrder();
        model.addAttribute("roomOrders", roomOrders);
        return "back_end/roomorder/showCheckIN";
    }

    @PostMapping("/uploadImage")
    public String checkIn(@RequestParam ("roomOrderId") Integer roomOrderId
            ,@RequestParam("image") MultipartFile image
            ,Model model) {

        String contentType = image.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            model.addAttribute("message", "檔案不是圖片！請重新確認");
            RoomOrderVO roomOrder = roomorderImpl.getOneRoomOrderById(roomOrderId);
            model.addAttribute("roomOrder", roomOrder);
            return "back_end/roomorder/showOneCheck";
        }

//        try {
//            BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
//            if (bufferedImage == null) {
//                model.addAttribute("message", "檔案不是有效的图片！");
//                return "back_end/roomorder/showCheckIN";
//            }
//        } catch (IOException e) {
//            model.addAttribute("message", "文件读取失败！");
//            return "back_end/roomorder/showCheckIN";
//        }

        RoomOrderVO roomOrder = roomorderImpl.getOneRoomOrderById(roomOrderId);
        if (roomOrder != null) {
            try {
                byte[] bytes = image.getBytes();
                roomOrder.setIdConfirm(bytes);
                roomorderImpl.updateOneRoomOrder(roomOrder);
                RoomVO roomVO = roomOrder.getRoomOrderList().getRoom();
                roomVO.setRoomSaleStatus((byte) 1);
                roomService.updateRoom(roomVO);
                model.addAttribute("message", "圖片上傳成功！");
            }catch (Exception e){
                e.printStackTrace();
                model.addAttribute("message", "圖片上傳失敗！");
                List<RoomOrderVO> roomOrders = roomorderImpl.getAllRoomOrder();
                model.addAttribute("roomOrders", roomOrders);
                return "back_end/roomorder/showCheckIN";
            }
        }
        RoomOrderVO roomOrder1 = roomorderImpl.getOneRoomOrderById(roomOrderId);
        model.addAttribute("roomOrder", roomOrder1);
        return "back_end/roomorder/showOneCheck";

    }

    @GetMapping("/getImage")
    @ResponseBody
    public String getImage(@RequestParam("roomOrderId") Integer roomOrderId) {
        RoomOrderVO roomOrder = roomorderImpl.getOneRoomOrderById(roomOrderId);
        if (roomOrder != null && roomOrder.getIdConfirm() != null) {
            return Base64.getEncoder().encodeToString(roomOrder.getIdConfirm());
        } else {
            return "";
        }
    }

    @PostMapping("/checkOut")
    public String checkOut(@RequestParam ("roomOrderId") Integer roomOrderId,Model model) {
        RoomOrderVO roomOrder = roomorderImpl.getOneRoomOrderById(roomOrderId);
        model.addAttribute("roomOrder", roomOrder);
        RoomVO roomVO = roomOrder.getRoomOrderList().getRoom();
        roomVO.setRoomSaleStatus((byte) 2);
        roomService.updateRoom(roomVO);
        model.addAttribute("message", "CheckOut完成!");
        return "back_end/roomorder/showOneCheck";
    }



    // 獲取所有活動
    @GetMapping("/listAllRoomOrder")
    public String getAllRoomOrder(Model model) {
        List<RoomOrderVO> allRoomOrder = roomOrderRepository.findAll();


        model.addAttribute("allRoomOrder", allRoomOrder);

        return "back_end/roomorder/listAllRoomOrder";
    }

    @PostMapping("/findOneRoomOrder")
    public String findMemRoomOrder(@RequestParam("memberId") Integer  memberId, Model model) throws Exception {
        List<RoomOrderVO> getOneList = roomorderImpl.getOneRoomOrder(memberId);
//        List<RoomOrderVO> findOneRoomOrder = roomorderImpl.getAllRoomOrder();
//        model.addAttribute("findOneRoomOrder", findOneRoomOrder);

        if(getOneList == null) {
            model.addAttribute("errorMessage", "查無資料");
            return "back_end/roomorder/selectRoomOrder";
        }
        model.addAttribute("selectSearch", getOneList);
        model.addAttribute("memberVO", memberId);
        model.addAttribute("findOneRoomOrder", "true");

        return "back_end/roomorder/listOneRoomOrder";
    }


    @PostMapping("/findDateRO")
    public String findCheckInRO(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            Model model) throws Exception {

        model.addAttribute("start", start);
        model.addAttribute("end", end);

        List<RoomOrderVO> checkInBetween = roomorderImpl.getDateRO(start, end);
        model.addAttribute("selectSearch",checkInBetween);
//        model.addAttribute("today", LocalDate.now());

        return "back_end/roomorder/listOneRoomOrder";
    }


    @PostMapping("/findOrderDateRO")
    public String findOrderDateRO(
            @RequestParam("ROstart") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ROstart,
            @RequestParam("ROend") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ROend,
            Model model) throws Exception {

        model.addAttribute("ROstart", ROstart);
        model.addAttribute("ROend", ROend);

        List<RoomOrderVO> roomOrderBetween = roomorderImpl.getOrderDateRO(ROstart, ROend);
        model.addAttribute("selectSearch",roomOrderBetween);

        return "back_end/roomorder/listOneRoomOrder";
    }

    @GetMapping("/selectRO")
    public String selectRO(Model model) {

        LocalDate today = LocalDate.now();
        model.addAttribute("today", today);
        List<RoomOrderVO> seleRoomOrder = roomOrderRepository.findAll();
        model.addAttribute("seleRoomOrder", seleRoomOrder);
        return "back_end/roomorder/selectRoomOrder";
    }

    @PostMapping("/updateRTS")
    public String updateRTS(@Valid @ModelAttribute RoomOrderVO roomOrderVO,
                            BindingResult result, Model model,
                            @RequestParam Integer roomOrderId) {
        // 檢查是否有綁定或驗證錯誤
        if (result.hasErrors()) {
            // 添加屬性以顯示錯誤或返回到表單
            model.addAttribute("roomOrderVO", roomOrderVO);
            return "redirect:/roomOrder/findOneRoomOrder";  // 假設 'roomOrderForm' 是包含表單的視圖名
        }

        RoomOrderVO existingOrder = roomOrderRepository.findById(roomOrderId).orElse(null);
        if (existingOrder == null) {
            return "redirect:/roomOrder/findOneRoomOrder";
        }

        boolean newStatus = !existingOrder.getRoomOrderStatus();
        existingOrder.setRoomOrderStatus(newStatus);

        roomOrderRepository.save(existingOrder);

        return "redirect:/roomOrder/findOneRoomOrder";
    }

    @PostMapping("/roomOrderTotalList")
    public String roomOrderTotalList(@Valid @ModelAttribute RoomOrderVO roomOrderVO,
                                     @RequestParam("roomTypeName") String roomTypeName,
                                     @RequestParam("roomGuestAmount") String roomGuestAmount,
                                     @RequestParam("roomSize") String roomSize,
                                     @RequestParam("roomAmount") String roomPrice,
                                     @RequestParam("selectCheckIn") String selectCheckInStr,
                                     @RequestParam("selectCheckOut") String selectCheckOutStr,
//                                     @RequestParam("roomTypeId") Integer roomTypeId,
                                     @RequestParam("promotionTitle") String promotionTitle,
                                     @RequestParam("promotionPrice")Integer  promotionPrice,
                                     @RequestParam("emails")String emails,
                                     HttpSession session, ModelMap model) {

        // 從Session中取得會員資料
//        String email = (String) session.getAttribute("account");
        Integer memberId = memSvc.findByEmail(emails).getMemberId();
        System.out.println(memberId);
        Integer findPromotionId = roomPromotionSvc.getByPromotionTitle(promotionTitle);

        Integer roomTypeId =  roomTypeImpl.getRoomTypeIdByName(roomTypeName).get().getRoomTypeId();

        if (memberId != null) {
            // 創建新的訂單實體
            RoomOrderVO roomOrder = new RoomOrderVO();
            roomOrder.setMemberId(memSvc.findByPK(memberId));
            roomOrder.setPromotionId(roomPromotionSvc.findByPK(findPromotionId));
            roomOrder.setRoomOrderDate(LocalDate.now());
            roomOrder.setRoomOrderStatus(Boolean.TRUE);
            roomOrder.setRoomAmount(Integer.valueOf(roomPrice));
            roomOrder.setPromotionPrice(promotionPrice);
            roomOrder.setCheckInDate(LocalDate.parse(selectCheckInStr));
            roomOrder.setCheckOutDate(LocalDate.parse(selectCheckOutStr));
            // 儲存訂單
            roomorderImpl.addRoomOrder(roomOrder);

            // 重定向到成功頁面
            return "redirect:/orderSuccess";
        } else {
            model.addAttribute("error", "無法驗證會員身份");
            return "front_end/room/roomFront"; // 返回到表單頁面並顯示錯誤
        }
    }
}