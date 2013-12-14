/**********************************************************
 * 项目名称：山寨腾讯“爱消除”游戏7日教程
 * 作          者：郑敏新
 * 腾讯微博：SuperCube3D
 * 日          期：2013年12月
 * 声          明：版权所有   侵权必究
 * 本源代码供网友研究学习OpenGL ES开发Android应用用，
 * 请勿全部或部分用于商业用途
 ********************************************************/

package elong.CrazyLink.Draw;
import javax.microedition.khronos.opengles.GL10;

import elong.CrazyLink.Control.CtlDisappear;
import elong.CrazyLink.Interface.IControl;

public class DrawDisappear {

	DrawAnimal drawAnimal;
	
	public IControl control;
	
	public DrawDisappear(DrawAnimal drawAnimal)
	{
		this.drawAnimal = drawAnimal;
		
		control = new CtlDisappear();
	}
	
	public void draw(GL10 gl, int witch, int col, int row)
	{
		if(!control.isRun()) return;
		if(0 == ((CtlDisappear)control).getCount() % 2)	//这里实现闪动	
		{
			gl.glPushMatrix();
			drawAnimal.draw(gl, witch, col, row);
			gl.glPopMatrix();
		}		
	}
	
}
