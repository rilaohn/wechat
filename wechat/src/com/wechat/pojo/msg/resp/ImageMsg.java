package com.wechat.pojo.msg.resp;

/**
* 类名: ImageMessage <br>
* 描述: 图片消息<br>
* 开发人员： 廖日辰 <br>
* 创建时间： Mar 03, 2017 <br>
* 发布版本：V1.0  <br>
 */
public class ImageMsg extends BaseMsg {

	private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }
}
