/**********************************************************
 * ��Ŀ���ƣ�ɽկ������������Ϸ7�ս̳�
 * ��          �ߣ�֣����
 * ��Ѷ΢����SuperCube3D
 * ��          �ڣ�2013��10��
 * ��          ������Ȩ����   ��Ȩ�ؾ�
 * ��Դ���빩�����о�ѧϰOpenGL ES����AndroidӦ���ã�
 * ����ȫ���򲿷�������ҵ��;
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
	
	public static int LIFE_UP = 9;			//���������ڸ�ֵʱ��������ֵ
	public static int LIFE_TIMEOUT = 10;	//��������������ֵʱʧȥһ������
	
	public static int MAX_TIME = 10;		//һ����Ϸ��ʱ�䣬����

	
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
