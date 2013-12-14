/**********************************************************
 * 项目名称：山寨腾讯“爱消除”游戏7日教程
 * 作          者：郑敏新
 * 腾讯微博：SuperCube3D
 * 日          期：2013年12月
 * 声          明：版权所有   侵权必究
 * 本源代码供网友研究学习OpenGL ES开发Android应用用，
 * 请勿全部或部分用于商业用途
 ********************************************************/

package elong.CrazyLink;

public class CrazyLinkConstent {
	public static final float GRID_NUM = 7.0f;
	public static int UNIT_SIZE = (int) (88 * CrazyLinkConstent.GRID_NUM);
	public static int VIEW_WIDTH = 480;		//����������
	public static int VIEW_HEIGHT = 800;	//��������߶�
	public static int REAL_WIDTH = 480;		//��Ļʵ�ʿ��
	public static int REAL_HEIGHT = 800;	//��Ļʵ�ʸ߶�
	public static int ADP_SIZE = 0;			//�����ĳߴ�
	public static float screentRatio = 0;
	public static float translateRatio = 0;
	public static float denisty = 0;
	public static float widthPixel = 0;
	
	public static int DELAY_MS = 50;	//�ӳ�50MS
	public static int AUTOTIP_DELAY = 5 * 1000 / DELAY_MS;	//�Զ���ʾ�ӳ�5��
	public static int MONSTER_APPEAR = 5;	//MONSTER���ֵ�ʱ��
	
	public static int MAX_TOKEN = 6;		//���������
	
	public static int MOVE_THRESDHOLDER = 5;  //�����ƶ������ޣ����������
	
	public static int LIFE_NUM = 3;			//������
	
	public static int LIFE_UP = 9;			//�������ڸ�ֵʱ��������ֵ
	public static int LIFE_TIMEOUT = 10;	//��������������ֵʱʧȥһ������
	
	public static int MAX_TIME = 100;		//һ����Ϸ��ʱ�䣬����

	
	public enum E_SOUND
	{
		SLIDE,
		FILL,
		DISAPPEAR3,
		DISAPPEAR4,
		DISAPPEAR5,
		READYGO,
		TIMEOVER,
		LEVELUP,
		SUPER,
		COOL,
		GOOD,
		PERFECT,
		BOMB,
		MONSTER,
		LIFEADD,
		LIFEDEL,
	}
	
	public enum E_TIP
	{
		READYGO,
		LEVELUP,
		GAMEOVER,
	}
	
	//��Ϸ�ĳ���
	public enum E_SCENARIO
	{
		MENU,	/*�˵�*/
		GAME,	/*��Ϸ������*/
		RESULT,	/*��Ϸ���*/
	}
	

}
