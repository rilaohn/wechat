package com.wechat.pojo.msg.resp;

/**
* 类名: MusicMessage <br>
* 描述: 音乐消息 <br>
* 开发人员：廖日辰 <br>
* 创建时间：Mar 03, 2017 <br>
* 发布版本：V1.0  <br>
 */
public class MusicMsg extends BaseMsg {

	// 音乐
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
