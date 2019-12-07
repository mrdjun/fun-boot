package com.fun.common.utils.app;

import com.fun.common.utils.text.Convert;
import org.apache.commons.lang.RandomStringUtils;
import org.n3r.idworker.Sid;

/**
 * APP 随机生成封装类
 *
 * @author DJun
 * @date 2019/11/25
 */
public class AppRandomUtils {
    private final static String NAME_STR = "无梦相赠, 旧事安然, 一纸愁肠, 酒笙倾凉, 堇色安年, 涧花烟雨, 执念成殇, 锦墨涵秋, 余笙南吟, 蝶梦无边, 月舞寒殇, 庸人安好, 南宫沐风, 剪烛西窗, 清樽独醉, 枫林叶落, 落花无声, 墨色芊华, 踏雪无痕, 北城孤酒, 笑傲苍穹, 无梦为安, 临江暮雪, 涧边幽草, 浮世繁华, 淡雅红颜, 微凉·雅娅, 红尘烟雨, 孤城暮雨, 素衣青丝ら, 如梦初醒, 落羽成霜ミ, 陌上蔷薇, 墨染倾城ゞ, 安之若素, 风在云颠, 为你葑訫, 南城旧梦, 月夜幽梦, 南岸青栀, 听风逝夜, 寒光竹影, 半宛清愁, 故人城殇, 暗似黛绿, 茯叶云水, 醉枫染墨, 把心藏好, 落花忆梦, 长裙绿衣, 醉月湖畔, 阡陌红尘, 一曲笙歌, 南巷清风, 古巷青灯, 北朽暖栀, 绮梦千年, 孤岛弥音, 木槿花开, 竹影清风, 唯你是命, 如风过境, 北朽暖栀, 眉黛如画℡, 温润。而泽, 微风香水, 恰上心头, 低吟こ曾经, 雾隐初霁, 夏慕槿苏, 落樱纷飞, 南笙挽风, 青袂婉约, 凉笙墨染, 柠檬の夏, 且聽風吟, 孤城浪子℡, 暮染轻纱, 落花忆梦, 温茶予月, 半盏流年, 永不言棄 , 素笺淡墨, 眉间点砂, 清宁时光, 无梦为安, 菲羽凌曦, 堇色安年, 失无所失, 剑已封鞘, 玉泽流光, 寒烟似雪, 尽是敷衍, 时光荏苒, 梦里飞花, 南岸青栀, 千城暮雪, 北柠陌寒";
    private final static String[] HEAD_IMG = {
            "/fun/img/avatar/avatar01.jpg",
            "/fun/img/avatar/avatar02.jpg",
            "/fun/img/avatar/avatar03.jpg",
            "/fun/img/avatar/avatar04.jpeg",
            "/fun/img/avatar/avatar05.jpg",
            "/fun/img/avatar/avatar06.jpg"
    };

    /**
     * 获取随机昵称
     */
    public static String getUsername() {
        String[] nameArr = Convert.toStrArray(NAME_STR);
        return rdStr(nameArr, "一只小趣");
    }

    /**
     * 获取随机头像
     */
    public static String getHead() {
        return rdStr(HEAD_IMG, "/fun/img/avatar/default.jpg");
    }

    /**
     * 获取随机16位字符串
     */
    public static String getStr16() {
        return Sid.nextShort();
    }

    /**
     * 获取随机18位字符串
     */
    public static String getStr18() {
        return Sid.next();
    }

    /**
     * 随机返回一个数组中值
     *
     * @param str        数组
     * @param defaultStr 默认返回值
     * @return String
     */
    private static String rdStr(String[] str, String defaultStr) {
        int strLen = str.length;
        int len = String.valueOf(strLen).length();
        int num = Integer.parseInt(RandomStringUtils.randomNumeric(len));
        if (num < strLen) {
            return str[num];
        }
        return defaultStr;
    }

}
