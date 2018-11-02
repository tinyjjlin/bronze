package com.tiny.publicAccount.wechatMsg;

public class VideoRequestMsg extends WeChatMsg {
	private String mediaId;
	private String Format;
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}

}
