/**********************************************************
 * 项目名称：山寨腾讯“爱消除”游戏7日教程
 * 作          者：郑敏新
 * 腾讯微博：SuperCube3D
 * 日          期：2013年12月
 * 声          明：版权所有   侵权必究
 * 本源代码供网友研究学习OpenGL ES开发Android应用用，
 * 请勿全部或部分用于商业用途
 ********************************************************/

package elong.CrazyLink.Core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import elong.CrazyLink.CrazyLinkConstent;
import elong.CrazyLink.CrazyLinkConstent.E_SCENARIO;
import elong.CrazyLink.R;
import elong.CrazyLink.Control.CtlDisappear;
import elong.CrazyLink.Control.CtlExchange;
import elong.CrazyLink.Control.CtlLifeAdd;
import elong.CrazyLink.Control.CtlLifeDel;
import elong.CrazyLink.Control.CtlMonster;
import elong.CrazyLink.Control.CtlTip1;
import elong.CrazyLink.Control.CtlTip2;
import elong.CrazyLink.CrazyLinkConstent.E_SOUND;
import elong.CrazyLink.CrazyLinkConstent.E_TIP;
import elong.CrazyLink.Draw.DrawAnimal;
import elong.CrazyLink.Draw.DrawAutoTip;
import elong.CrazyLink.Draw.DrawBomb;
import elong.CrazyLink.Draw.DrawBackGround;
import elong.CrazyLink.Draw.DrawExplosion;
import elong.CrazyLink.Draw.DrawLife;
import elong.CrazyLink.Draw.DrawLifeAdd;
import elong.CrazyLink.Draw.DrawLifeDel;
import elong.CrazyLink.Draw.DrawMonster;
import elong.CrazyLink.Draw.DrawSingleScore;
import elong.CrazyLink.Draw.DrawTimeBar;
import elong.CrazyLink.Draw.DrawTip1;
import elong.CrazyLink.Draw.DrawDisappear;
import elong.CrazyLink.Draw.DrawExchange;
import elong.CrazyLink.Draw.DrawFill;
import elong.CrazyLink.Draw.DrawGrid;
import elong.CrazyLink.Draw.DrawLoading;
import elong.CrazyLink.Draw.DrawScore;
import elong.CrazyLink.Draw.DrawTip2;
import elong.CrazyLink.Interface.IControl;


public class ControlCenter {

	Context mContext;
	public static E_SCENARIO mScene;
	
	static ActionTokenPool mToken;		//操作令牌，只有获取到令牌才能操作
	
	static int mAnimalPic[][];			//对应格子显示的图片，调用DrawAnimal渲染
	static int mPicBak[][];				//mPic的副本，用于autotip计算
	static int mEffect[][];				//0：不显示；1：显示动物；2:显示交换特效；3:跌落特效；4:消除特效
	static int mDisappearToken[][];		//消除特效获取的TOKEN
	
	static int mSingleScoreW = 0;	//显示当次奖励的位置
	static int mSingleScoreH = 0;
	
	int menuTextureId;
	int resultTextureId;
    int animalTextureId;				//动物素材纹理id
    int[] loadingTextureId = new int[10];			//加载动画素材纹理id
    int gridTextureId;				//网格素材纹理id
    int scoreTextureId;
    int lifeTextureId;
    int congratulationTextureId;
    int tip2TextureId;
    int lifeAddTextureId;
    int lifeDelTextureId;
    int timeBarTextureId;
    int fireTextureId;
    int explosionTextureId;
    int monsterTextureId;
    int bombTextureId;
    
    static int mAutoTipTimer = 0;			//自动提示计时器
    
    static public DrawBackGround drawMenuBackGround;
    static public DrawBackGround drawResultBackGround;
	static public DrawAnimal drawAnimal;
	static public DrawLoading drawLoading;
	static public DrawGrid drawGrid;
	static ArrayList<DrawExchange> mDrawExchangeList = new ArrayList<DrawExchange>();
	static ArrayList<DrawDisappear> mDrawDisappearList = new ArrayList<DrawDisappear>();
	static public DrawFill drawFill;
	static public DrawScore drawScore;
	static public DrawScore drawResultScore;
	static public DrawLife drawLife;
	static public DrawSingleScore drawSingleScore;
	static public DrawTip1 drawTip1;
	static public DrawTip2 drawTip2;
	static public DrawAutoTip drawAutoTip;
	static public DrawExplosion drawExplosion;
	static public DrawMonster drawMonster;
	static public DrawBomb drawBomb;
	static public DrawLifeAdd drawLifeAdd;
	static public DrawLifeDel drawLifeDel;
	static public DrawTimeBar drawTimeBar;

	
	public static Score mScore;		//计算分数
	public static Sound mSound;
	public static Timer mTimer;	
	
	public static boolean mIsLoading = false;	//显示正在加载
	public static boolean mIsAutoTip = false;	//处于自动提示状态
	
	ArrayList<IControl> mControlList = new ArrayList<IControl>();	//渲染类的控制列表
	
	static final int EFT_NONE  = 0;			//空白
	static final int EFT_NORMAL  = 1;		//正常，无特殊效果
	static final int EFT_EXCHANGE  = 2;		//交换效果
	static final int EFT_FILL  = 3;			//跌落效果	
	static final int EFT_AUTOTIP  = 4;		//自动提示效果
	static final int EFT_DISAPPEAR  = 5;	//消除效果
	
	static final int ANIMAL_BOMB = 8;		//炸弹
	static final int ANIMAL_LASER = 9;		//激光
	static final int ANIMAL_MONSTER = 10;	//怪兽	



	public ControlCenter(Context context)
	{
		mScene = E_SCENARIO.MENU;
		mContext = context;
		mScore = new Score();
		mSound = new Sound(context);
		mTimer = new Timer(CrazyLinkConstent.MAX_TIME);
	    mAnimalPic = new int[(int) CrazyLinkConstent.GRID_NUM][(int) CrazyLinkConstent.GRID_NUM];
	    mPicBak = new int[(int) CrazyLinkConstent.GRID_NUM][(int) CrazyLinkConstent.GRID_NUM];
	    mEffect = new int[(int) CrazyLinkConstent.GRID_NUM][(int) CrazyLinkConstent.GRID_NUM];
	    mDisappearToken = new int[(int) CrazyLinkConstent.GRID_NUM][(int) CrazyLinkConstent.GRID_NUM];
	    mToken = new ActionTokenPool();
		init();
	}
	
	//初始化逻辑，保证初始化以后的状态没有处于消除状态的
	public static void init()
	{
		for(int i = 0; i < (int)CrazyLinkConstent.GRID_NUM; i++)
		{
			for(int j = 0; j < (int)CrazyLinkConstent.GRID_NUM; j++) 
			{
				mAnimalPic[i][j] = getRandom();
				while (isInLine(mAnimalPic, i,j))
				{
					mAnimalPic[i][j] = getRandom();	
				}
				mEffect[i][j] = EFT_NORMAL;
				mDisappearToken[i][j] = -1;
			}
		}
		
	}
	
	public static int takeToken()
	{
		return mToken.takeToken();
	}
	
	public static void freeToken(int token)
	{
		mToken.freeToken(token);
	}

	//产生1~7的随机数
	static int getRandom()
	{
		int data = (int) (Math.random()*100);
		return (data % 7) + 1;
	}
	
	//指定坐标(col,row)在X方向是否已经成行
	static boolean isInLineX(int pic[][], int col, int row)
	{
		int picId = pic[col][row];
		if(0 == col)
		{
			if(picId == pic[col+1][row] && picId == pic[col+2][row])
			{
				return true;
			}
		}
		else if(1 == col)
		{
			if((picId == pic[col-1][row] && picId == pic[col+1][row])
					|| (picId == pic[col+1][row] && picId == pic[col+2][row]))
			{
				return true;
			}
		}
		else if(col > 1 && col < 5)
		{
			if((picId == pic[col-2][row] && picId == pic[col-1][row])
					|| (picId == pic[col-1][row] && picId == pic[col+1][row])
					|| (picId == pic[col+1][row] && picId == pic[col+2][row]))
			{
				return true;
			}
		}
		else if(5 == col)
		{
			if((picId == pic[col-2][row] && picId == pic[col-1][row])
					|| (picId == pic[col-1][row] && picId == pic[col+1][row]))
			{
				return true;
			}
		}
		else if(6 == col)
		{
			if(picId == pic[col-1][row] && picId == pic[col-2][row])
			{
				return true;
			}
		}
		return false;
	}
	
	//指定坐标(col,row)在Y方向是否已经成行
	static boolean isInLineY(int pic[][], int col, int row)
	{
		int picId = pic[col][row];
		if(0 == row)
		{
			if(picId == pic[col][row+1] && picId == pic[col][row+2])
			{
				return true;
			}
		}
		else if(1 == row)
		{
			if((picId == pic[col][row-1] && picId == pic[col][row+1])
					|| (picId == pic[col][row+1] && picId == pic[col][row+2]))
			{
				return true;
			}
		}
		else if(row > 1 && row < 5)
		{
			if((picId == pic[col][row-2] && picId == pic[col][row-1])
					|| (picId == pic[col][row-1] && picId == pic[col][row+1])
					|| (picId == pic[col][row+1] && picId == pic[col][row+2]))
			{
				return true;
			}
		}
		else if(5 == row)
		{
			if((picId == pic[col][row-2] && picId == pic[col][row-1])
					|| (picId == pic[col][row-1] && picId == pic[col][row+1]))
			{
				return true;
			}
		}
		else if(6 == row)
		{
			if(picId == pic[col][row-1] && picId == pic[col][row-2])
			{
				return true;
			}
		}
		return false;
	}

	//消除检测算法，只要X或Y方向有一个方向成行，就认为满足消除条件
	static boolean isInLine(int pic[][], int col, int row)
	{
		return isInLineX(pic, col, row) || isInLineY(pic, col, row);
	}

	//将成行的动物标记出来
	static void markDisappear(int token)
	{
		if(-1 == token) return;
		int markCount = 0;
		for(int i = 0; i < (int)CrazyLinkConstent.GRID_NUM; i++)
		{
			for(int j = 0; j < (int)CrazyLinkConstent.GRID_NUM; j++) 
			{
				if (isInLine(mAnimalPic, i, j) && -1 == mDisappearToken[i][j])	
				{
					mEffect[i][j] = EFT_DISAPPEAR;
					mDisappearToken[i][j] = token;
					markCount++;
				}
			}
		}		
		if (markCount > 0)
		{
			if(3 == markCount)
				mSound.play(E_SOUND.DISAPPEAR3);
			else if(4 == markCount)
				mSound.play(E_SOUND.DISAPPEAR4);
			else if(markCount >= 5)
				mSound.play(E_SOUND.DISAPPEAR5);
			
	    	DrawDisappear drawDisappear = getDrawDisappear(token);
	    	if(drawDisappear != null) 
	    	{
	    		drawDisappear.control.setToken(token);
	    		drawDisappear.control.start();
	    	}
			drawExplosion.control.start();
			mScore.increase();
			mScore.calcTotal(markCount);
			mScore.increase(markCount);			
		}
		else
		{
			freeToken(token);
			mScore.reset();
		}
	}
	

	//消除完成后，要将对应的格子置为EFT_NORMAL
	static int clearPic(int token)
	{
		int clearCount = 0;
		if(-1 == token) return 0;
		for(int i = 0; i < (int)CrazyLinkConstent.GRID_NUM; i++)
		{
			for(int j = 0; j < (int)CrazyLinkConstent.GRID_NUM; j++) 
			{
				if (EFT_DISAPPEAR == mEffect[i][j] && (token == mDisappearToken[i][j])) 
				{
					mAnimalPic[i][j] = 0;
					mEffect[i][j] = EFT_NORMAL;
					mDisappearToken[i][j] = -1;
					clearCount++;
				}
			}
		}				
		return clearCount;
	}
	
	//判断是否需要触发消除特效
	static boolean isNeedClear()
	{
		for(int i = 0; i < (int)CrazyLinkConstent.GRID_NUM; i++)
		{
			for(int j = 0; j < (int)CrazyLinkConstent.GRID_NUM; j++) 
			{
				if (isInLine(mAnimalPic, i,j) && (-1 == mDisappearToken[i][j]))
				{
					return true;
				}
			}
		}						
		return false;
	}
	
	//标记跌落特效
	static void fillGrid(int col, int row)
	{
		if(0 == mAnimalPic[col][row])
		{
			for(int i = row; i < (int)CrazyLinkConstent.GRID_NUM; i++)
			{
				mEffect[col][i] = EFT_FILL;
			}
		}
	}
	
	//循环标记要填充（跌落）的格子
	static void markFill()
	{
		for(int i = 0; i < (int)CrazyLinkConstent.GRID_NUM; i++)
		{
			for(int j = 0; j < (int)CrazyLinkConstent.GRID_NUM; j++) 
			{
				fillGrid(i,j);
			}
		}				
		fillMethod();
		drawFill.control.start();
		mSound.play(E_SOUND.FILL);
	}
	
	static void unMark(int mark)
	{
		for(int i = 0; i < (int)CrazyLinkConstent.GRID_NUM; i++)
		{
			for(int j = 0; j < (int)CrazyLinkConstent.GRID_NUM; j++) 
			{
				if(mark == mEffect[i][j]) mEffect[i][j] = EFT_NORMAL;					
			}
		}				
	}

	static void unMarkDisappear(int token)
	{
		for(int i = 0; i < (int)CrazyLinkConstent.GRID_NUM; i++)
		{
			for(int j = 0; j < (int)CrazyLinkConstent.GRID_NUM; j++) 
			{
				if((token == mDisappearToken[i][j]) && (EFT_DISAPPEAR == mEffect[i][j])) 
				{
					mEffect[i][j] = EFT_NORMAL;
				}
				if(token == mDisappearToken[i][j]) 
				{
					mDisappearToken[i][j] = -1;
				}
				
			}
		}				
	}

	
	//是否需要填充（跌落）
	static boolean isNeedFill()
	{
		for(int i = 0; i < (int)CrazyLinkConstent.GRID_NUM; i++)
		{
			for(int j = 0; j < (int)CrazyLinkConstent.GRID_NUM; j++) 
			{
				if(0 == mAnimalPic[i][j])
				{
					return true;
				}
			}
		}						
		return false;
	}
	
	//跌落算法
	static void fillMethod()
	{
		for(int i = 0; i < (int)CrazyLinkConstent.GRID_NUM; i++)
		{
			for(int j = 0; j < (int)CrazyLinkConstent.GRID_NUM; j++) 
			{
				if(0 == mAnimalPic[i][j])		//0代表该格子是空的，需要跌落
				{
					if(j < (int)CrazyLinkConstent.GRID_NUM - 1)
					{
						//从上一行中跌落
						mAnimalPic[i][j] = mAnimalPic[i][j + 1];
						mAnimalPic[i][j + 1] = 0;
					}
					else
					{
						//如果消除的是最高的一行，则随机产生跌落动物
						mAnimalPic[i][j] = getRandom();	
					}
				}
			}
		}				
			
	}
	
	static void exchange(int pic[][], int col1, int row1, int col2, int row2)
	{
		//对交换的坐标进行有效性校验，如果是无效的，则不进行交换
		if(col1 < 0 || col1 > 6) return;
		if(col2 < 0 || col2 > 6) return;
		if(row1 < 0 || row1 > 6) return;
		if(row2 < 0 || row2 > 6) return;
		int picId = pic[col1][row1];
		pic[col1][row1] = pic[col2][row2];
		pic[col2][row2] = picId;
	}

    static void setSingleScorePosition(int col, int row)
    {
    	if(drawSingleScore.control.isRun()) return;
    	mSingleScoreW = col;
    	mSingleScoreH = row;
    }
    
    //注册渲染类的控制对象到控制中心的控制列表
    void controlRegister(IControl control)
    {
    	if(control != null)	mControlList.add(control);
    }
    
    //自动提示识别算法
    //只需要交换一步就能成行的，认为满足自动提示条件
    boolean autoTipMethod(int col, int row)
    {
		for(int i = 0; i < (int)CrazyLinkConstent.GRID_NUM; i++)
		{
			for(int j = 0; j < (int)CrazyLinkConstent.GRID_NUM; j++) 
			{
				exchange(mPicBak, i, j, i-1, j);
				if(isInLine(mPicBak, i, j)) return true;
				exchange(mPicBak, i-1, j, i, j);

				exchange(mPicBak, i, j, i+1, j);
				if(isInLine(mPicBak, i, j)) return true;
				exchange(mPicBak, i+1, j, i, j);

				exchange(mPicBak, i, j, i, j-1);
				if(isInLine(mPicBak, i, j)) return true;
				exchange(mPicBak, i, j-1, i, j);

				exchange(mPicBak, i, j, i, j+1);
				if(isInLine(mPicBak, i, j)) return true;
				exchange(mPicBak, i, j+1, i, j);
			}
		}
    	return false;
    }

    //自动提示
    void autoTip()
    {	
		for(int i = 0; i < (int)CrazyLinkConstent.GRID_NUM; i++)
		{
			for(int j = 0; j < (int)CrazyLinkConstent.GRID_NUM; j++) 
			{
				mPicBak[i][j] = mAnimalPic[i][j];
			}
		}

		for(int i = 1; i < (int)CrazyLinkConstent.GRID_NUM - 1; i++)
		{
			for(int j = 1; j < (int)CrazyLinkConstent.GRID_NUM - 1; j++) 
			{
				if(autoTipMethod(i, j))
				{
					markAutoTip();
					return;
				}
			}
		}
    }
    
	//将可以自动提示的动物标识出来
	static void markAutoTip()
	{
		boolean isAutoTip = false;
		for(int i = 0; i < (int)CrazyLinkConstent.GRID_NUM; i++)
		{
			for(int j = 0; j < (int)CrazyLinkConstent.GRID_NUM; j++) 
			{
				if (isInLine(mPicBak, i, j))	
				{
					mEffect[i][j] = EFT_AUTOTIP;
					isAutoTip = true;
				}
			}
		}		
		if(isAutoTip) drawAutoTip.control.start();
	}
    
	//将自动提示标识清除
	static void clearAutoTip()
	{
		unMark(EFT_AUTOTIP);
		mIsAutoTip = false;
		mAutoTipTimer = 0;
	}
	
	
	static int getPicId(int col, int row)
	{
		int pic = mAnimalPic[col][row];
		if(isMonster(col,row)) 
		{
			CtlMonster ctl;
			ctl = (CtlMonster)drawMonster.control;
			pic = ctl.getPicId();
		}
		else if(isBomb(col,row))
		{
			pic = ANIMAL_BOMB;
		}
		else if(isLaser(col,row))
		{
			//pic = ANIMAL_LASER;
		}
		return pic;
	}
	
	//正常的渲染效果
	static boolean isNormalEFT(int col, int row)
	{
		if(EFT_NORMAL == mEffect[col][row]) return true;
		else return false;
	}
	
	public static boolean isMonster(int col, int row)
	{
		if(ANIMAL_MONSTER == mAnimalPic[col][row]) return true;
		else return false;
	}
	
	public static boolean isBomb(int col, int row)
	{
		if(ANIMAL_BOMB == mAnimalPic[col][row]) return true;
		else return false;
	}
	
	public static boolean isLaser(int col, int row)
	{
		if(ANIMAL_LASER == mAnimalPic[col][row]) return true;
		else return false;
	}

	
	static void markSpecialAnimal(int token, int col, int row)
	{
		if(isMonster(col, row))
		{
			mSound.play(E_SOUND.MONSTER);
			markMonster(token, col, row);
		}
		else if(isBomb(col, row))
		{
			mSound.play(E_SOUND.BOMB);
			markBomb(token, col, row);
		}
		else if(isLaser(col, row))
		{
			//markLaser(token, col,row);
		}
		else
		{
			freeToken(token);
		}
	}

	//标记怪兽可消除的格子
	static void markMonster(int token, int col, int row)
	{
		if(-1 == token) return;
		if (isMonster(col,row))
		{
			CtlMonster ctl = (CtlMonster)drawMonster.control;		
			int picId = ctl.getPicId();
			int markCount = 0;
			mAnimalPic[col][row] = EFT_DISAPPEAR + token;
			for(int i = 0; i < (int)CrazyLinkConstent.GRID_NUM; i++)
			{
				for(int j = 0; j < (int)CrazyLinkConstent.GRID_NUM; j++)
				{
					if(picId == mAnimalPic[i][j] && -1 == mDisappearToken[i][j])
					{
						mEffect[i][j] = EFT_DISAPPEAR;
						mDisappearToken[i][j] = token;
						markCount++;
					}						
				}
			}
	    	DrawDisappear drawDisappear = getDrawDisappear(token);
	    	if(drawDisappear != null) 
	    	{
	    		drawDisappear.control.setToken(token);
	    		drawDisappear.control.start();
	    	}

			drawExplosion.control.start();
			mScore.increase();
			mScore.increase(markCount);			
		}
	}
	
	//标记炸弹可消除的格子
	static void markBomb(int token, int col, int row)
	{
		if(-1 == token) return;
		if (isBomb(col,row))
		{
			int markCount = 0;
			for(int i = col-1; i <= col+1; i++)
			{
				if(i >= 0 && i < (int)CrazyLinkConstent.GRID_NUM)
				{
					for(int j = row-1; j <= row+1; j++)
					{
						if(j >= 0 && j < (int)CrazyLinkConstent.GRID_NUM)
						{
							if(-1 == mDisappearToken[i][j])
							{
								mEffect[i][j] = EFT_DISAPPEAR;
								mDisappearToken[i][j] = token;
								markCount++;
							}
						}						
					}
				}
			}
	    	DrawDisappear drawDisappear = getDrawDisappear(token);
	    	if(drawDisappear != null) 
	    	{
	    		drawDisappear.control.setToken(token);
	    		drawDisappear.control.start();
	    	}

			drawExplosion.control.start();
			mScore.increase();
			mScore.increase(markCount);			
		}
	}

	//标记激光可消除的格子
	static void markLaser(int token, int col, int row)
	{
		if(-1 == token) return;
		if (isLaser(col,row))
		{
			int markCount = 0;
			for(int i = 0; i < (int)CrazyLinkConstent.GRID_NUM; i++)
			{
				if(-1 == mDisappearToken[col][i])
				{
					mEffect[col][i] = EFT_DISAPPEAR;
					mDisappearToken[col][i] = token;
					markCount++;
				}
				if(-1 == mDisappearToken[i][row])
				{
					mEffect[i][row] = EFT_DISAPPEAR;					
					mDisappearToken[i][row] = token;					
					markCount++;
				}
			}
	    	DrawDisappear drawDisappear = getDrawDisappear(token);
	    	if(drawDisappear != null) 
	    	{
	    		drawDisappear.control.setToken(token);
	    		drawDisappear.control.start();
	    	}

			drawExplosion.control.start();
			mScore.increase();
			mScore.increase(markCount);			
		}
	}

	
	//生成特殊动物
	static void genSpecialAnimal()
	{
		int cnt = 0;
		int data = (int) (Math.random()*1000);
		data = (data % ((int)CrazyLinkConstent.GRID_NUM * (int)CrazyLinkConstent.GRID_NUM));
		int x = 0;
		int y = 0;
		x = data / (int)CrazyLinkConstent.GRID_NUM;
		y = data % (int)CrazyLinkConstent.GRID_NUM;
		
		while(!isNormalEFT(x,y))
		{
			data = (data++) % ((int)CrazyLinkConstent.GRID_NUM * (int)CrazyLinkConstent.GRID_NUM);
			x = data / (int)CrazyLinkConstent.GRID_NUM;
			y = data % (int)CrazyLinkConstent.GRID_NUM;
			cnt++;
			if(cnt>10) break;     	//预防无法正常退出
		}
		int animal = data % 2;
		switch (animal)
		{
		case 0:
			mAnimalPic[x][y] = ANIMAL_MONSTER;
			break;
		case 1:
			mAnimalPic[x][y] = ANIMAL_BOMB;
			break;
		case 2:
			mAnimalPic[x][y] = ANIMAL_LASER;
			break;
		default:
			break;
		}
	}

	//初始化纹理对象
	public void initTexture(GL10 gl)
	{
		menuTextureId = initTexture(gl, R.drawable.cover);
		resultTextureId = initTexture(gl, R.drawable.result);
    	animalTextureId = initTexture(gl, R.drawable.animal);	//初始化纹理对象    	
    	for(int i = 0; i < 10; i++)
    	{
    		loadingTextureId[i] = initTexture(gl, R.drawable.loading_01 + i);
    	}    	
    	gridTextureId = initTexture(gl, R.drawable.grid);
    	scoreTextureId = initTexture(gl, R.drawable.number);
    	lifeTextureId = initTexture(gl, R.drawable.life);
    	congratulationTextureId = initTexture(gl, R.drawable.word);
    	tip2TextureId = initTexture(gl, R.drawable.word2);
    	fireTextureId = initTexture(gl, R.drawable.autotip);
    	explosionTextureId = initTexture(gl, R.drawable.explosion);
    	monsterTextureId = initTexture(gl, R.drawable.animal);
    	bombTextureId = initTexture(gl, R.drawable.bomb);
    	lifeAddTextureId = initTexture(gl, R.drawable.life_add);
    	lifeDelTextureId = initTexture(gl, R.drawable.life_del);
    	timeBarTextureId = initTexture(gl, R.drawable.time);
	}
	
	//初始化渲染对象
	public void initDraw(GL10 gl)
	{
		drawMenuBackGround = new DrawBackGround(menuTextureId);
		drawResultBackGround = new DrawBackGround(resultTextureId);
    	drawAnimal = new DrawAnimal(animalTextureId);			//创建动物素材对象    	
    	drawGrid = new DrawGrid(gridTextureId);					//创建棋盘素材对象
    	drawFill = new DrawFill(drawAnimal);
    	drawScore = new DrawScore(scoreTextureId);
    	drawResultScore = new DrawScore(scoreTextureId);
    	drawLife = new DrawLife(gl, lifeTextureId);
    	drawSingleScore = new DrawSingleScore(gl);
    	drawTip1 = new DrawTip1(congratulationTextureId);
    	drawTip2 = new DrawTip2(tip2TextureId);
    	drawLifeAdd = new DrawLifeAdd(lifeAddTextureId);
    	drawLifeDel = new DrawLifeDel(lifeDelTextureId);
    	drawTimeBar = new DrawTimeBar(gl, timeBarTextureId, CrazyLinkConstent.MAX_TIME);

    	drawLoading = new DrawLoading(loadingTextureId);		//创建加载动画素材    	
    	drawAutoTip = new DrawAutoTip(fireTextureId);
    	drawExplosion = new DrawExplosion(explosionTextureId);
    	drawMonster = new DrawMonster(monsterTextureId);
    	drawBomb = new DrawBomb(bombTextureId);
    
    	//将渲染类的控制对象注册到控制中心列表    	
    	controlRegister(drawFill.control);
    	controlRegister(drawLoading.control);
    	controlRegister(drawSingleScore.control);
    	controlRegister(drawTip1.control);
    	controlRegister(drawTip2.control);
    	controlRegister(drawAutoTip.control);
    	controlRegister(drawExplosion.control);
    	controlRegister(drawMonster.control);
    	controlRegister(drawBomb.control);
    	controlRegister(drawLifeAdd.control);
    	controlRegister(drawLifeDel.control);
    	
    	initExchangeList();
    	initDisappearList();
	}
	
	void initExchangeList()
	{
		DrawExchange drawExchange;
		for(int i = 0; i < CrazyLinkConstent.MAX_TOKEN; i++)
		{
			drawExchange = new DrawExchange(drawAnimal);
			controlRegister(drawExchange.control);
			mDrawExchangeList.add(drawExchange);
		}
	}
	
	void initDisappearList()
	{
		DrawDisappear drawDisappear;
		for(int i = 0; i < CrazyLinkConstent.MAX_TOKEN; i++)
		{
			drawDisappear = new DrawDisappear(drawAnimal);
			controlRegister(drawDisappear.control);
			mDrawDisappearList.add(drawDisappear);
		}
	}
	
	static DrawExchange getDrawExchange(int token)
	{
		if(-1 == token) return null;
		return mDrawExchangeList.get(token);
	}

	static DrawDisappear getDrawDisappear(int token)
	{
		if(-1 == token) return null;
		return mDrawDisappearList.get(token);
	}
	
	void drawExchangeRun(GL10 gl)
	{
		DrawExchange drawExchange;
		CtlExchange ctl;
		for(int i = 0; i < CrazyLinkConstent.MAX_TOKEN; i++)
		{
			drawExchange = mDrawExchangeList.get(i);
			ctl = (CtlExchange)drawExchange.control;
			if(ctl.isRun()) drawExchange.draw(gl);				
		}		
	}

	void drawDisappeareRun(GL10 gl, int col, int row)
	{
		int token = mDisappearToken[col][row];
		if(-1 == token) return;
		DrawDisappear drawDisappear = mDrawDisappearList.get(token);
		if(drawDisappear != null)
		{
			CtlDisappear ctl = (CtlDisappear)drawDisappear.control;
			if(ctl.isRun()) drawDisappear.draw(gl, getPicId(col, row), col, row);
		}
	}
	
	//初始化纹理的方法
	private int initTexture(GL10 gl, int drawableId)
	{
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		int currTextureId = textures[0];
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);//ָ����С���˷���
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);//ָ���Ŵ���˷���
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);//ָ��S�������ͼģʽ
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);//ָ��T�������ͼģʽ
		InputStream is = mContext.getResources().openRawResource(drawableId);
		Bitmap bitmapTmp;
		try{
			bitmapTmp = BitmapFactory.decodeStream(is);
		}
		finally{
			try{
				is.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
		bitmapTmp.recycle();
		return currTextureId;
	}

	
	//消息类型
	public static final int EXCHANGE_START = 1;
	public static final int EXCHANGE_END = 2;
	public static final int LOADING_START = 3;
	public static final int LOADING_END = 4;
	public static final int DISAPPEAR_END = 5;
	public static final int FILL_END = 6;
	public static final int SCREEN_TOUCH = 7;
	public static final int GEN_SPECIALANIMAL = 8;
	public static final int READY_GO = 9;
	public static final int LEVEL_UP = 10;
	public static final int GAME_OVER_START = 11;
	public static final int GAME_OVER_END = 12;
	public static final int LIFEADD_START = 13;
	public static final int LIFEADD_END = 14;
	public static final int LIFEDEL_START = 15;
	public static final int LIFEDEL_END = 16;


	
	//消息处理
    public static Handler mHandler = new Handler(){   
        @Override  
		public void handleMessage(Message msg) 
		{
			    // process incoming messages here
			switch(msg.what)
			{
				case EXCHANGE_START:
				{
					mSound.play(E_SOUND.SLIDE);
					clearAutoTip();
					Bundle b = msg.getData();
					int token = b.getInt("token");
					int col1 = b.getInt("col1");
					int col2 = b.getInt("col2");
					int row1 = b.getInt("row1");
					int row2 = b.getInt("row2");				
			    	mEffect[col1][row1] = EFT_EXCHANGE;			//处于交换状态
			    	mEffect[col2][row2] = EFT_NONE;
			    	setSingleScorePosition(col1, row1);
			    	int pic1 = getPicId(col1, row1);
			    	int pic2 = getPicId(col2, row2);
			    	DrawExchange drawExchange = getDrawExchange(token);
			    	if(drawExchange != null) drawExchange.init(token, pic1, col1, row1, pic2, col2, row2);
					break;
				}
				case EXCHANGE_END:
				{
					Bundle b = msg.getData();
					int token = b.getInt("token");
					int col1 = b.getInt("col1");
					int col2 = b.getInt("col2");
					int row1 = b.getInt("row1");
					int row2 = b.getInt("row2");
					exchange(mAnimalPic, col1, row1, col2, row2);
			    	mEffect[col1][row1] = EFT_NORMAL;			//交换状态解除
			    	mEffect[col2][row2] = EFT_NORMAL;
					markDisappear(token);				
					break;
				}
				case LOADING_START:	
					mScene = E_SCENARIO.GAME;
					mIsLoading = true;
			    	drawLoading.control.start();
			    	break;
				case LOADING_END:
				{
					mIsLoading = false;
					mSound.play(E_SOUND.READYGO);
					CtlTip2 ctl = (CtlTip2) drawTip2.control;
					ctl.init(E_TIP.READYGO.ordinal());	//ready go
					mTimer.start();
					break;
				}
				case DISAPPEAR_END:
				{				
					Bundle b = msg.getData();
					int token = b.getInt("token");				
					int clearCnt = clearPic(token);
					unMarkDisappear(token);
					mScore.award(clearCnt);
					if(mScore.getAward() > 0)
					{
						CtlTip1 ctl = (CtlTip1) drawTip1.control;
						if(4 == clearCnt)
							mSound.play(E_SOUND.COOL);
						else if(5 == clearCnt)
							mSound.play(E_SOUND.PERFECT);
						else if(clearCnt > 5)
							mSound.play(E_SOUND.SUPER);
						ctl.init(clearCnt);
						drawSingleScore.control.start();
					}
					//clearInline();
					freeToken(token);
					markFill();
					break;
				}
				case FILL_END:	
					unMark(EFT_FILL);
					if(isNeedFill())
					{
						markFill();
					}
					else
					{
						//clearStatus(token);
						if(isNeedClear())
						{
							int token = takeToken();
							markDisappear(token);
						}
					}
					clearAutoTip();
					break;
				case SCREEN_TOUCH:
					Bundle b = msg.getData();
					int token = b.getInt("token");
					int col = b.getInt("col1");
					int row = b.getInt("row1");
					setSingleScorePosition(col, row);
					markSpecialAnimal(token, col, row);
					break;
				case GEN_SPECIALANIMAL:
					genSpecialAnimal();
					break;
				case READY_GO:
				{
					CtlTip2 ctl = (CtlTip2) drawTip2.control;
					ctl.init(0);
					break;
				}
				case LEVEL_UP:
				{
					CtlTip2 ctl = (CtlTip2) drawTip2.control;
					ctl.init(E_TIP.LEVELUP.ordinal());	//level up
					mSound.play(E_SOUND.LEVELUP);
					break;
				}
				case GAME_OVER_START:
				{
					CtlTip2 ctl = (CtlTip2) drawTip2.control;
					ctl.init(E_TIP.GAMEOVER.ordinal());	//game over
					mSound.play(E_SOUND.TIMEOVER);
					break;
				}
				case GAME_OVER_END:
				{
					mScene = E_SCENARIO.RESULT;	
					break;
				}
				case LIFEADD_START:
				{
					CtlLifeAdd ctl = (CtlLifeAdd) drawLifeAdd.control;
					ctl.init(mScore.getLife());
					break;
				}
				case LIFEADD_END:
				{
					mScore.increaseLife();
					break;
				}
				case LIFEDEL_START:
				{
					CtlLifeDel ctl = (CtlLifeDel) drawLifeDel.control;
					ctl.init(mScore.getLife());
					break;
				}
				case LIFEDEL_END:
				{
					mScore.decreaseLife();
					break;
				}
			}
		}
    };
	
    
    
    //控制中心的动作执行
    public void run()
    {
    	mAutoTipTimer++;
    	if(mAutoTipTimer > CrazyLinkConstent.AUTOTIP_DELAY)
    	{
    		if(!mIsAutoTip)
    		{
    			mIsAutoTip = true;
    			autoTip();
    		}
    	}
		IControl control = null;		
		for(int i=0;i<mControlList.size();i++){ 
			control = mControlList.get(i);
			control.run();
		}
    }
    
    //菜单场景渲染
    public void drawMenuScene(GL10 gl)
    {
    	drawMenuBackGround.draw(gl);
    }
    
    //游戏结果场景渲染
    public void drawResultScene(GL10 gl)
    {
    	drawScore.draw(gl,mScore.getScore(), 1);
    	drawResultBackGround.draw(gl);
    }

    
	//游戏场景渲染
	public void drawGameScene(GL10 gl)
	{
		if(mIsLoading)
		{
			drawLoading.draw(gl);
			return;
		}
		drawScore.draw(gl,mScore.getScore(), 0);
		drawLife.draw(gl, mScore.mLife);
		drawSingleScore.draw(gl, mSingleScoreW, mSingleScoreH, mScore.getAward());
		drawTip1.draw(gl);
		drawTip2.draw(gl);
		drawLifeAdd.draw(gl);
		drawLifeDel.draw(gl);
		drawTimeBar.draw(gl, mTimer.getLeftTime());
		
		for(int i = 0; i < (int)CrazyLinkConstent.GRID_NUM; i++)
		{
			for(int j = 0; j < (int)CrazyLinkConstent.GRID_NUM; j++)
			{
				switch (mEffect[i][j])
				{
				case EFT_NORMAL:	//正常显示
					if(isMonster(i,j))
						drawMonster.draw(gl, i, j);
					else if(isBomb(i,j))
						drawBomb.draw(gl, i, j);
					else if(isLaser(i,j))
						drawMonster.draw(gl, i, j);
					else
						drawAnimal.draw(gl,getPicId(i,j),i,j);
					break;
				case EFT_EXCHANGE:	//交换特效
					drawExchangeRun(gl);							
					break;
				case EFT_FILL:	//跌落特效
					drawFill.draw(gl, getPicId(i,j), i, j);
					break;
				case EFT_AUTOTIP:	//自动提示特效
					drawAutoTip.draw(gl, i, j);
					drawAnimal.draw(gl,getPicId(i,j),i,j);
					break;
				case EFT_DISAPPEAR:		//消除特效
					drawExplosion.draw(gl, i, j);
					drawDisappeareRun(gl, i, j);											
					break;
				default:
					
					break;
				}				                  
			}
		}
    	drawGrid.draw(gl);
		
	}

    
}
