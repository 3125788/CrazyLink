/**********************************************************
 * 项目名称：山寨“爱消除”游戏7日教程
 * 作          者：郑敏新
 * 腾讯微博：SuperCube3D
 * 日          期：2013年10月
 * 声          明：版权所有   侵权必究
 * 本源代码供网友研究学习OpenGL ES开发Android应用用，
 * 请勿全部或部分用于商业用途
 ********************************************************/

package elong.CrazyLink;

public class CrazyLinkConstent {
	public static final float GRID_NUM = 7.0f;
	public static int UNIT_SIZE = (int) (96 * CrazyLinkConstent.GRID_NUM);
	public static float screentRatio = 0;
	public static float translateRatio = 0;
	public static float denisty = 0;
	public static float widthPixel = 0;
	
	public static int DELAY_MS = 50;	//延迟50MS
	public static int AUTOTIP_DELAY = 5 * 1000 / DELAY_MS;	//自动提示延迟5秒
	public static int MONSTER_APPEAR = 5;	//MONSTER出现的时机
	
	public static int MAX_TOKEN = 6;		//最大令牌数
	
	public static int MOVE_THRESDHOLDER = 5;  //触发移动的门限，避免误操作

	
	

}
