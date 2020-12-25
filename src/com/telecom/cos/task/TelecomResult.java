package com.telecom.cos.task;

/**
 * 返回错误状态枚举类
 * @author MrLovelyCbb
 * @since
 * 1.OK 返回成功 <br/>
 * 2.FAITLED 返回失败<br/>
 * 3.CANCELLED 取消全部<br/>
 * 4.NOT_FOLLOWED_ERROR 没有跟踪错误<br/>
 * 5.IO_ERROR 输入输出错误<br/>
 * 6.AUTH_ERROR 智能错误<br/>
 */
public enum TelecomResult {

	OK, FAILED, CANCELLED,

	NOT_FOLLOWED_ERROR, IO_ERROR, AUTH_ERROR
}
