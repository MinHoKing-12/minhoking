package message;

import java.util.List;

public interface iMessage {
	
	public int getPageCount(String type, String id, String select, String str);	// 전체 페이지 갯수
	
	public List<MessageDto> getMsgList(String type, int page, String id, String select, String str);	// 해당 페이지의 리스트 불러오기
	
	public boolean writeMsg(MessageDto dto);	// 메세지 작성
	
	public MessageDto getMessageDto(int seq);	// 메세지 DTO값 가져오기	-- detail
	
	public void readMessage(int seq);	// 메세지 읽기
	
	public boolean deleteMessage(int seq);	// 메세지 삭제
	
	public boolean multidelMessage(int[] seq);	// 메세지 다중삭제

	public int notReadMsgCount(String id);	// 새쪽지 카운트
	
	public int msgCount(String type, String id);	// 받은쪽지, 보낸쪽지 카운트

	public List<MessageDto> getMsgList(String type, int page, String id, String select, String str, String category);

	public int getPageCount(String type, String id, String select, String str, String category);
	
}
