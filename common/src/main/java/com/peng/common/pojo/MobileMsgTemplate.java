package com.peng.common.pojo;


import java.io.Serializable;

/**
 * 短信消息模板
 */
public class MobileMsgTemplate implements Serializable {
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 文本
     */
    private String text;
    /**
     * 类型（通道）
     */
    private String type;

    public MobileMsgTemplate(String mobile, String text, String type) {
        this.mobile = mobile;
        this.text = text;
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
