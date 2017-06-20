package com.wechat.pojo.msg.resp;

/**
* 类名: VideoMessage <br>
* 描述: 视频消息 <br>
* 开发人员：廖日辰 <br>
* 创建时间：Mar 03, 2017 <br>
* 发布版本：V1.0  <br>
 */
public class VideoMsg extends BaseMsg {

	// 视频
    private Video Video;

    public Video getVideo() {
        return Video;
    }

    public void setVideo(Video video) {
        Video = video;
    }
}
