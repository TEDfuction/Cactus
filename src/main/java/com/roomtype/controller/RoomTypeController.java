package com.roomtype.controller;

import com.cart.model.Cart;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.room.model.RoomService;
import com.room.model.RoomVO;
import com.roomorder.model.RoomOrderVO;
import com.roomorder.service.impl.RoomOrderImpl;
import com.roomorderlist.model.RoomOrderListRepository;
import com.roomorderlist.model.RoomOrderListVO;
import com.roomorderlist.service.Impl.RoomOrderListImpl;
import com.roompromotion.model.RoomPromotionService;
import com.roomschedule.model.RoomScheduleRepository;
import com.roomschedule.model.RoomScheduleVO;
import com.roomschedule.service.Impl.RoomScheduleImpl;
import com.roomtype.dto.RoomTypeStatus;
import com.roomtype.dto.RoomTypeUpdate;
import com.roomtype.dto.RoomTypeVORequest;
import com.roomtype.model.RoomTypeRepository;
import com.roomtype.model.RoomTypeVO;
import com.roomtype.service.impl.RoomTypeImpl;
import com.roomtypepic.model.RoomTypePicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/roomType")
public class RoomTypeController {


    @Autowired
    private RoomTypeImpl roomTypeImpl;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomTypePicRepository roomTypePicRepository;

    @Autowired
    private MemberService memSvc;

    @Autowired
    private RoomPromotionService roomPromotionSvc;

    @Autowired
    private RoomOrderImpl roomOrderImpl;

    @Autowired
    private RoomOrderListRepository roomOrderListRepository;

    @Autowired
    private RoomService roomSvc;

    @Autowired
    private RoomScheduleImpl roomScheduleImpl;

    @Autowired
    private RoomScheduleRepository roomScheduleRepository;

    @Autowired
    private RoomOrderListImpl roomOrderListImpl;


    // 獲取所有活動
    @GetMapping("/listAllRoomType")
    public String getAllRoomTypes(Model model) {
        List<RoomTypeVO> allRoomTypes = roomTypeRepository.findAll();
        RoomTypeVO roomTypeVO = new RoomTypeVO();
        model.addAttribute("allRoomTypes", allRoomTypes);
        return "back_end/roomtype/listAllRoomType";

    }


    @PostMapping("/updateRTS")
    public String updateRTS(@Valid @ModelAttribute RoomTypeStatus roomTypeStatus,
                            BindingResult result, Model model,
                            @RequestParam Integer roomTypeId) {
        try {
            RoomTypeVO roomTypeVO = roomTypeRepository.findById(roomTypeId).orElse(null);
            if (roomTypeVO == null) {
                return "redirect:back_end/roomType/listAllRoomType";
            }
            model.addAttribute("roomTypeVO", roomTypeVO);

            if (result.hasErrors()) {
                return "back_end/roomType/listAllRoomType";
            }

            // Toggle room type status
            boolean newStatus = !roomTypeVO.getRoomTypeStatus();
            roomTypeVO.setRoomTypeStatus(newStatus);

            roomTypeRepository.save(roomTypeVO);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/roomType/listAllRoomType";
    }


    @GetMapping("/addRoomType")
    public String showAddPage(Model model) {
        RoomTypeVORequest roomTypeVORequest = new RoomTypeVORequest();
        model.addAttribute("roomTypeVORequest", roomTypeVORequest);
        return "back_end/roomtype/addRoomType";
    }


    @PostMapping("/addRoomTypeForm")
    public String addRoomType(@Valid @ModelAttribute RoomTypeVORequest roomTypeVORequest,
                              BindingResult result, ModelMap model) {

        if (result.hasErrors()) {
            return "back_end/roomtype/addRoomType";
        }

        roomTypeImpl.addRoomType(roomTypeVORequest);
        RoomTypeVO roomTypeVO = new RoomTypeVO();
        roomTypeVO.setRoomTypeId(roomTypeVORequest.getRoomTypeId());
        roomTypeVO.setRoomTypeName(roomTypeVORequest.getRoomTypeName());
        roomTypeVO.setRoomTypeContent(roomTypeVORequest.getRoomTypeContent());
        roomTypeVO.setRoomTypePrice(roomTypeVORequest.getRoomTypePrice());
        roomTypeVO.setRoomTypeStatus(Boolean.TRUE);

        return "redirect:/back_end/roomtype/listAllRoomType";
    }


    @GetMapping("/getOneRoomType")
    public String getOneRoomType(@Valid @ModelAttribute RoomTypeVORequest roomTypeVORequest,
                                 BindingResult result, Model model,
                                 @RequestParam Integer roomTypeId) {
        Optional<RoomTypeVO> getOneRoomType = roomTypeRepository.findById(roomTypeId);
        model.addAttribute("roomTypeVO", getOneRoomType);


        return "back_end/roomtype/listAllRoomType";
    }

    @PostMapping("/updateRoomTypeForm")
    public String updateRoomTypeForm(@RequestParam("roomTypeId") Integer roomTypeId,
                                     @Valid @ModelAttribute RoomTypeUpdate roomTypeUpdate,
                                     BindingResult result,
                                     RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("status", "error");
            redirectAttributes.addFlashAttribute("message", "驗證錯誤");
            return "redirect:/back_end/roomType/listAllRoomType"; // 假設有一個顯示錯誤的頁面
        }

        RoomTypeVO roomTypeVO = roomTypeRepository.findById(roomTypeId).orElse(null);
        if (roomTypeVO == null) {
            redirectAttributes.addFlashAttribute("status", "error");
            redirectAttributes.addFlashAttribute("message", "找不到指定的房型");
            return "redirect:/back_end/roomType/listAllRoomType"; // 假設有一個顯示「未找到」的頁面
        }


        roomTypeVO.setRoomTypeName(roomTypeUpdate.getRoomTypeName());
        roomTypeVO.setRoomTypeContent(roomTypeUpdate.getRoomTypeContent());
        roomTypeVO.setRoomTypePrice(roomTypeUpdate.getRoomTypePrice());
        roomTypeRepository.save(roomTypeVO);

        redirectAttributes.addFlashAttribute("status", "success");
        redirectAttributes.addFlashAttribute("message", "資料已更新");
        return "redirect:/roomType/listAllRoomType"; // 成功後重定向到另一個 URL
    }

    @GetMapping("/roomTypeFront")
    public String getByRTFront(Model model) {
        List<RoomTypeVO> listAllRTFront = roomTypeImpl.getRTStatus();
        model.addAttribute("listAllRTFront", listAllRTFront);
        if (roomTypePicRepository == null) {
            // Handle the case where roomTypePicVO is not found
            return "redirect:/error-page"; // or some error handling
        }
        model.addAttribute("roomTypePicVO", roomTypePicRepository);
        return "front_end/room/roomTypeFront";

    }

    @PostMapping("/selectRoomType")
    public String selectRoomType(@RequestParam("roomTypeName") String roomTypeName,
                                 @RequestParam("selectCheckIn") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkInDate,
                                 @RequestParam("selectCheckOut") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOutDate,
                                 @RequestParam(value = "roomGuestAmount", required = false) Integer roomGuestAmount,
                                 @Valid @ModelAttribute RoomTypeVO roomTypeVO,
                                 BindingResult result, Model model) {

        List<Object[]> getSelect = roomTypeImpl.getAvailableRoomTypes(roomTypeName, checkInDate, checkOutDate, roomGuestAmount);
        model.addAttribute("selectRoom", getSelect);

        return "front_end/room/roomFront";
    }

    @GetMapping("/getRoomGuestAmounts")
    @ResponseBody
    public List<Integer> getRoomGuestAmounts(@RequestParam("roomTypeName") String roomTypeName) {
        Optional<RoomTypeVO> roomTypeOptional = roomTypeImpl.getRoomTypeIdByName(roomTypeName);
        if (roomTypeOptional.isPresent()) {
            Integer roomTypeId = roomTypeOptional.get().getRoomTypeId();
            return roomTypeImpl.getRoomGuestAmounts(roomTypeId);
        }
        return new ArrayList<>();
    }

    @PostMapping("/selectRoom")
    public String selectRoom(@RequestParam("roomTypeName") String roomTypeName,
                             @RequestParam("selectCheckIn") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkInDate,
                             @RequestParam("selectCheckOut") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOutDate,
                             @RequestParam(value = "roomGuestAmount", required = false) Integer roomGuestAmount,
                             @Valid @ModelAttribute RoomTypeVO roomTypeVO,
                             BindingResult result, Model model) {

        List<Object[]> getSelect = roomTypeImpl.getAvailableRoomTypes(roomTypeName, checkInDate, checkOutDate, roomGuestAmount);
        model.addAttribute("select", getSelect);

        return "front_end/room/roomFront";
    }


    @PostMapping("/roomOrderTotalList")
    public String roomTypeTotalList(@Valid @ModelAttribute RoomTypeVO roomTypeVO,
                                    @RequestParam("roomTypeName") String roomTypeName,
                                    @RequestParam("roomGuestAmount") String roomGuestAmount,
                                    @RequestParam("roomSize") String roomSize,
                                    @RequestParam("roomAmount") Integer roomAmount,
                                    @RequestParam("payAmount") Integer payAmount,
                                    @RequestParam("selectCheckIn") String selectCheckInStr,
                                    @RequestParam("selectCheckOut") String selectCheckOutStr,
                                    @RequestParam("roomTypeId") Integer roomTypeId,
                                    @RequestParam(value = "promotionTitle", required = false) String promotionTitle,
                                    @RequestParam(value = "promotionPrice", required = false) Integer promotionPrice,
                                    @RequestParam("emails") String emails,
                                    @RequestParam(value = "roomOrderIdReq", required = false) String roomOrderIdReq,
                                    HttpSession session, ModelMap model) {

        // 從Session中取得會員資料
//        String email = (String) session.getAttribute("account");
        Integer memberId = memSvc.findByEmail(emails).getMemberId();
//            System.out.println(memberId);
        Integer findPromotionId = roomPromotionSvc.getByPromotionTitle(promotionTitle);


        if (memberId != null) {
            // 創建新的訂單實體
            RoomOrderVO roomOrder = new RoomOrderVO();
            roomOrder.setMemberId(memSvc.findByPK(memberId));
            if (findPromotionId != null) {
                roomOrder.setPromotionId(roomPromotionSvc.findByPK(findPromotionId));
            } else {
                roomOrder.setPromotionId(null);
            }
            roomOrder.setRoomOrderDate(LocalDate.now());
            roomOrder.setRoomOrderStatus(Boolean.TRUE);
            roomOrder.setRoomAmount(roomAmount);
            roomOrder.setPayAmount(payAmount);
            roomOrder.setPromotionPrice(promotionPrice);
            roomOrder.setCheckInDate(LocalDate.parse(selectCheckInStr));
            roomOrder.setCheckOutDate(LocalDate.parse(selectCheckOutStr));
            roomOrder.setRoomOrderIdReq(roomOrderIdReq);

            // 存RoomOrder訂單
            roomOrderImpl.addRoomOrder(roomOrder);
            RoomOrderVO savedRoomOrder = (RoomOrderVO) roomOrderImpl.addRoomOrder(roomOrder);
            System.out.println(savedRoomOrder);

//            roomOrderList
            RoomTypeVO saveRoomTypeId = roomTypeImpl.findByPK(roomTypeId);
            System.out.println(saveRoomTypeId);
            RoomVO saveRoomId = roomSvc.getByRoomPrice(roomAmount);

//            roomSchedule
            RoomTypeVO saveRoomScheduleRT = roomTypeImpl.getRoomTypeById(roomTypeId);
            LocalDate checkInDate = (LocalDate.parse(selectCheckInStr));
            LocalDate checkOutDate = (LocalDate.parse(selectCheckOutStr));


            if (saveRoomTypeId != null) {
                RoomOrderListVO roomOrderList = new RoomOrderListVO();
                roomOrderList.setRoomOrder(savedRoomOrder);
                roomOrderList.setRoomType(saveRoomTypeId);
                roomOrderList.setRoomVO(saveRoomId);
                roomOrderListRepository.save(roomOrderList);

                long daysBetween = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
                for (int i = 0; i < daysBetween; i++) {
                    RoomScheduleVO roomSchedule = new RoomScheduleVO();
                    roomSchedule.setRoomType(saveRoomScheduleRT);
                    roomSchedule.setRoomScheduleDate(checkInDate.plusDays(i));
                    roomSchedule.setRoomScheduleAmount(Integer.valueOf(roomGuestAmount));
                    roomScheduleRepository.save(roomSchedule);
                }

                String ecpayCheckout = roomOrderListImpl.roomEcpayCheckout(roomOrderList);
                model.addAttribute("ecpayCheckout", ecpayCheckout);


            } else {
                model.addAttribute("error", "無法驗證會員身份");
                return "front_end/room/roomFront"; // 返回到表單頁面並顯示錯誤
            }
        }
        return "front_end/activity/success";
    }

}