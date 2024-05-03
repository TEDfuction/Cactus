package com.websocket.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.notification.model.NotificationService;
import com.websocket.model.MessageStuff;

//通知寄送WebSocket

@ServerEndpoint("/NotificationAlert/{memNo}")

public class NotificationAlert {
	
	//用Map存取對應user的session
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	
	Gson gson = new Gson();
	
	//未讀取之訊息的值
	Integer count;
	
	//Spring為單例模式，與WebSocket相反，故無法使用Autowired注入
	private static NotificationService notiSvc;
	
	@Autowired
	public void setNotificationService(NotificationService notiSvc) {
		this.notiSvc = notiSvc;
	}

	
	@OnOpen
	public void onOpen(@PathParam("memNo")String memNo, Session userSession) throws IOException {
		
		//依傳入之參數(會員編號)存取對應的session
		//若是後台員工，則參數傳遞0做為區別
		sessionsMap.put(memNo, userSession);

		System.out.println("Session ID = " + userSession.getId() + ", connected;");
		System.out.println("memNo = " + memNo);
	}

	@OnMessage
	public void sendAndChangeMessage(@PathParam("memNo")String memNo, Session userSession, String message) {
		
		//將Map中已存取的Key與Session都全部取出
		Set<String> memNos = sessionsMap.keySet();
		Collection<Session> sessions = sessionsMap.values();
		
		//將前台傳送的JSON物件轉為字串並交由GSON包裝成DTO的各屬性已方便調用
		MessageStuff ms = gson.fromJson(message, MessageStuff.class);
		String action = ms.getAction();
		String memberId = ms.getMemberId();
		String notiId = ms.getNotificationId();
//		System.out.println(action + "==" + memberId + "==" + notiId);
//		System.out.println("-------------------------------------------------------------");
		
		
		//後台員工遞送資料時
		if(action.equals("send")) {
			
			
			for(String memberNo : memNos) {
				//將所有已存取的會員編號一一取出做比對，把未讀訊息數量推播給對應會員的頁面做更新
				if(memberNo.equals(memberId) && sessionsMap.get(memberNo).isOpen()) {
//					System.out.println("aaaa"+sessionsMap.get(memberNo).getId()+"aaaaa");
					
					//為了讓資料先進行新增後再做查詢，小睡一下~(偷吃步)
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//推播未讀訊息之個數
					count = notiSvc.getNotiUnread( Integer.valueOf(memberId) );
					System.out.println("ccccc"+count+"cccccc");
					sessionsMap.get(memberNo).getAsyncRemote().sendText( String.valueOf(count) );
				}
			}	

		}
		
		
		//前台會員點擊訊息做閱讀時
		if(action.equals("change")) {
			
			//將狀態由未讀改為已讀
			notiSvc.readMsg( Integer.valueOf(notiId) );
			Integer count = notiSvc.getNotiUnread( Integer.valueOf(memberId) );
			
			//比對後回傳給會員頁面做更新
			for(String memberNo : memNos) {
				if( memberNo.equals(memberId) && sessionsMap.get(memberNo).isOpen() ) {
					sessionsMap.get(memberNo).getAsyncRemote().sendText( String.valueOf(count) );
				}
			}
		}
	}


	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}
	

}
