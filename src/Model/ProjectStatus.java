/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

public enum ProjectStatus implements Serializable {
    ERROR, //đăng nhập sai
    SUCCESS, //đăng nhập đúng
    ALREADYLOGINED, //đã đăng nhập
    TIMEOUT, //hết giờ
    SENDRESULT, //gửi kết quả
    STANDBY, //chờ
    PLAY, //chơi
}
