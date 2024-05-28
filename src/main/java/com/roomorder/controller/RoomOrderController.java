package com.roomorder.controller;


import com.member.model.MemberService;
import com.room.model.RoomService;
import com.room.model.RoomVO;
import com.roomorder.service.impl.RoomOrderImpl;
import com.roomorder.model.RoomOrderRepository;
import com.roomorder.model.RoomOrderVO;
import com.roomorderlist.model.RoomOrderListRepository;
import com.roomorderlist.model.RoomOrderListVO;
import com.roomorderlist.service.Impl.RoomOrderListImpl;
import com.roompromotion.model.RoomPromotionService;
import com.roomschedule.model.RoomScheduleRepository;
import com.roomtype.model.RoomTypeVO;
import com.roomtype.service.impl.RoomTypeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/roomOrder")
public class RoomOrderController {


    @Autowired
    private RoomOrderImpl roomOrderImpl;

    @Autowired
    private RoomOrderRepository roomOrderRepository;



    // 獲取所有活動
    @GetMapping("/listAllRoomOrder")
    public String getAllRoomOrder(Model model,RoomOrderVO roomOrderVO) {
        List<RoomOrderVO> allRoomOrder = roomOrderRepository.findAll();

        model.addAttribute("allRoomOrder", allRoomOrder);

        return "back_end/roomorder/listAllRoomOrder";
    }

    @PostMapping("/findOneRoomOrder")
    public String findMemRoomOrder(@RequestParam("memberId") Integer  memberId, Model model) throws Exception {
        List<RoomOrderVO> getOneList = roomOrderImpl.getOneRoomOrder(memberId);
        List<RoomOrderVO> findOneRoomOrder = roomOrderImpl.getAllRoomOrder();
        model.addAttribute("findOneRoomOrder", findOneRoomOrder);

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

        List<RoomOrderVO> checkInBetween = roomOrderImpl.getDateRO(start, end);
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

        List<RoomOrderVO> roomOrderBetween = roomOrderImpl.getOrderDateRO(ROstart, ROend);
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

    @PostMapping("/updateROS")
    public String updateROS(@Valid @ModelAttribute RoomOrderVO roomOrderVO, BindingResult result,
                            Model model, @RequestParam Integer roomOrderId) {

        if (result.hasErrors()) {
            return "redirect:/roomOrder/findOneRoomOrder"; // 如果有錯誤，直接重定向
        }

        try {
            RoomOrderVO existingOrder = roomOrderRepository.findById(roomOrderId).orElse(null);
            if (existingOrder == null) {
                // 處理查找失敗的情況，例如顯示錯誤消息或重定向
                return "redirect:/roomOrder/findOneRoomOrder";
            }

            boolean newStatus = !existingOrder.getRoomOrderStatus();
            existingOrder.setRoomOrderStatus(newStatus);
            roomOrderRepository.save(existingOrder);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/roomOrder/errorPage"; // 假設有一個專門的錯誤頁面
        }

        return "redirect:/roomOrder/findOneRoomOrder";
    }


    @PostMapping("/updateRoomOrder")
    public String findByRoomOrderID(@ModelAttribute RoomOrderVO roomOrderVO,
            @RequestParam("roomOrderId") String roomOrderId,
            Model model) throws Exception {

       Optional<RoomOrderVO> roomOrderOptional = roomOrderRepository.findById(Integer.valueOf(roomOrderId));
        if (!roomOrderOptional.isPresent()) {
            // 如果没有找到 RoomOrder，可以添加错误消息到模型，或重定向到一个错误页面或列表页面
            model.addAttribute("errorMessage", "No room order found with ID " + roomOrderId);
            return "back_end/admin/adminLogin"; // 假设有一个显示错误的页面
        }
        model.addAttribute("findByRoomOrderID", roomOrderVO);
        return "back_end/roomorder/updateRoomOrder";
    }


}