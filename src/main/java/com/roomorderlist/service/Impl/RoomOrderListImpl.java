package com.roomorderlist.service.Impl;


import com.activities_order.model.ActivityOrderRepository;
import com.activities_order.model.ActivityOrderVO;
import com.roomorder.model.RoomOrderVO;
import com.roomorderlist.model.RoomOrderListRepository;
import com.roomorderlist.model.RoomOrderListVO;
import com.roomorderlist.service.RoomOrderListService;
import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class RoomOrderListImpl implements RoomOrderListService {

    @Autowired
    RoomOrderListRepository roomOrderListRepository;

    @Autowired
    ActivityOrderRepository activityOrderRepository;

    @Override
    public RoomOrderListVO addRoomOrderList(RoomOrderListVO roomOrderListVO) {
        RoomOrderListVO insertRoomOrderList = roomOrderListRepository.save(roomOrderListVO);
        return insertRoomOrderList;
    }


    //------------------------------綠界金流方法---------------------------------//
    public String roomEcpayCheckout(RoomOrderListVO roomOrderListVO) {


        AllInOne all = new AllInOne("");
        AioCheckOutALL obj = new AioCheckOutALL();

        String roomOrderDescription = roomOrderListVO.getRoomTypeVO().getRoomTypeName();

//       String singleProductDetail = new StringBuilder(paymentDescription)
//				.append(activityOrderVO.getEnrollNumber().toString())
//				.append("X")
//				.append(activityOrderVO.getSessionVO().getItemVO().getActivityPrice().toString())
//				.append("=$")
//				.append(activityOrderVO.getEnrollNumber()* activityOrderVO.getSessionVO().getItemVO().getActivityPrice() )
//				.toString();


        //將訂單時間(當前時間)轉為字串放入，以避免編號重複的問題
        Date date = new Date();
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderDateString = formatter1.format(date);

        // 訂單號碼(規定大小寫英文+數字)
        obj.setMerchantTradeNo( orderDateString + roomOrderListVO.getRoomOrderListId());
        // 交易時間(先把毫秒部分切掉)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        LocalDate time = roomOrderListVO.getRoomOrder().getRoomOrderDate();
        obj.setMerchantTradeDate(sdf.format(date));
        // 總金額(總金額)
        obj.setTotalAmount( String.valueOf( roomOrderListVO.getRoomOrder().getPayAmount()) );
        // 交易描述(沒改)
        obj.setTradeDesc("test Description");
        // 交易商品明細
        obj.setItemName(roomOrderDescription);
        // 交易結果回傳網址，只接受 https 開頭的網站，可以使用 ngrok
        obj.setReturnURL("http://211.23.128.214:5000/");
        obj.setNeedExtraPaidInfo("N");
        // 商店轉跳網址 (Optional)
        //obj.setClientBackURL("http://localhost:8080/backend/rentalorder/rentalCart"); // 問小吳上雲怕爆開(路徑問題)
        String form = all.aioCheckOut(obj, null);

        // 付款完後把付款狀態改為 1 (已付款)

        System.out.println("aaa"+ form);
        return form ;

    }

    //家維新增 == 會員專區用
    public List<ActivityOrderVO> findByMemberId(Integer memberId){
        return activityOrderRepository.findByMemberId(memberId);
    }

}
