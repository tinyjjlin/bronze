package com.tiny.publicAccount.wechatMsg;

public class ImageRequestMsg extends WeChatMsg {
	private String picUrl;
	private String mediaId;
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
