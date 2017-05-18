package com.mfpad.epdlevel2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;  
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	View vg;
	String L2_LAUNCHER_CONFIG = "/sdcard/l2launch.db";
	//String L2_LAUNCHER_CONFIG = "/data/data/com.mfpad.oh6/l2launch.db";
	Cursor cursor = null;
	int[]  drawable_id_2 = new int[] {
			R.drawable.h2_book_lang, R.drawable.h2_book_eng, R.drawable.h2_book_math,
			R.drawable.h2_book_politic, R.drawable.h2_book_physic, R.drawable.h2_book_chem,
			R.drawable.h2_book_bio, R.drawable.h2_book_history, R.drawable.h2_book_geography,
			R.drawable.h2_book_art, R.drawable.h2_book_music
	};
	
	int[]  drawable_id_3 = new int[] {
			R.drawable.h2_practice, R.drawable.h2_myhomework
   	};
	int[]  drawable_id_4 = new int[] {
			R.drawable.h2_outsiteread, R.drawable.h2_newsenglish,R.drawable.h2_knowall,
			R.drawable.h2_chemistry, R.drawable.h2_physic_room
   	};
	int[]  drawable_id_5 = new int[] {
			R.drawable.h2_mypicture, R.drawable.h2_localdoc,R.drawable.h2_writenotes,
			R.drawable.h2_classtable
   	};
	int[]  drawable_id_6 = new int[] {
			R.drawable.h2_dictionary, R.drawable.h2_calculator, R.drawable.h2_notes,
			R.drawable.h2_musicplayer, R.drawable.h2_news, R.drawable.h2_year,
			R.drawable.h2_clock, R.drawable.h2_alarm, R.drawable.h2_filemanager
   	};
	int[]  drawable_id_7 = new int[] {
			R.drawable.h2_searchpic, R.drawable.h2_baidu, R.drawable.h2_google
			
   	};
	int[]  drawable_id_8 = new int[] {
			R.drawable.h2_contact, R.drawable.h2_email, R.drawable.h2_wechat
			
   	};
	int[]  drawable_id_9 = new int[] {
			R.drawable.h2_browser, R.drawable.h2_icloud, R.drawable.h2_sharespace
			
   	};
	int[]  drawable_id_12 = new int[] {
			R.drawable.h2_sysupgrade, R.drawable.h2_restore, R.drawable.h2_wallpaper
			
   	};
	
	String[] apksname_2 = new String[] { "课堂教材","语文","英语","数学" ,"政治","物理","化学","生物","历史" ,"地理","美术","音乐"};
	String[] apksname_3 = new String[] { "课后习作", "课外练习", "我的作业" };
	String[] apksname_4 = new String[] { "课外参考", "课外阅读", "听新闻学英语","百科知识库","化学元素表","理科实验室" };
	String[] apksname_5 = new String[] { "我的文件", "我的图片", "本地文件","手写笔记","课程表" };
	String[] apksname_6 = new String[] { "应用工具", "中英字典", "计算器" , "记事本" , "音乐播放" , "新闻阅读" , "万年历",
			"时钟", "闹铃", "档案总管"};
	String[] apksname_7 = new String[] { "搜索", "快图搜索", "百度搜索","谷歌搜索" };
	String[] apksname_8 = new String[] { "通讯", "联络人", "电子邮件","微信" };
	String[] apksname_9 = new String[] { "互联网络", "网络浏览器", "我的網絡云盤","教室共享空间" };
	String[] apksname_12 = new String[] { "系统设定", "系统更新", "一键还原","待机桌布更换" };
	
	//"课堂教材" 少了 --> 英语","数学" ,"政治","物理","化...
	String[] classname_2 = new String[] { "com.mfpad.oh6.classDocument","","" };
	String[] pkgname_2 = new String[] { "com.mfpad.oh6","","" };

	//"课后习作"
	String[] classname_3 = new String[] { "R.drawable.p_math_3","R.drawable.p_eng_4" };
	String[] pkgname_3 = new String[] { "R.id.imagefullView1","R.id.imagefullView1" };
	
	//"课外参考" -- 少了 百科知识库 , "化学元素表","理科实验室"
	String[] classname_4 = new String[] { "org.geometerplus.android.fbreader.FBReader","com.iyuba.CSvoa.activity.MainActivity","","R.drawable.chem_table2_v","" };
	String[] pkgname_4 = new String[] { "org.geometerplus.zlibrary.ui.android", "com.iyuba.CSvoa","","R.id.imagefullView1","" };

	//"我的文件"  -- 少了 "本地文件","课程表"
	String[] classname_5 = new String[] { "com.alensw.PicFolder.GalleryActivity","","com.evernote.skitch.app.SkitchHomeActivity","R.drawable.demo_class" };
	String[] pkgname_5 = new String[] { "com.alensw.PicFolder","","com.evernote.skitch","R.id.imagefullView1" };
	
	//"应用工具" -- 少了 "中英字典", "记事本" ,"新闻阅读" , "万年历", "时钟", "闹铃", "档案总管"
	String[] classname_6 = new String[] { "","com.foxconn.ebook.CALCULATOR.CalculatorActivity","","com.android.music.MusicBrowserActivity","","","","","com.google.android.youtube.HomeActivity" };
	String[] pkgname_6 = new String[] { "","com.foxconn.ebook.CALCULATOR","","com.android.music","","","","","com.google.android.youtube" };

	//"搜索" -- 少了 "谷歌搜索"
	String[] classname_7 = new String[] { "com.funit.mob.picSearch.activity.MainActivity","com.baidu.searchbox.MainActivity","" };
	String[] pkgname_7 = new String[] { "com.funit.mob.pics","com.baidu.searchbox","" };

	//"通讯" -- 少了 "联络人"
	String[] classname_8 = new String[] { "","com.android.email.activity.Welcome","com.tencent.mm.ui.LauncherUI" };
	String[] pkgname_8 = new String[] { "","com.android.email","com.tencent.mm" };

	//"互联网络"   -- 少了 "我的網絡云盤","教室共享空间"
	String[] classname_9 = new String[] { "com.android.browser.BrowserActivity","","" };
	String[] pkgname_9 = new String[] { "com.android.browser","","" };

	//"系统设定"   -- 少了 "系统更新", "一键还原","待机桌布更换"
	String[] classname_12 = new String[] { "com.android.settings.Settings","","" };
	String[] pkgname_12 = new String[] { "com.android.settings","FINISH","" };	

	
	private ImageView iv_material, iv_reading, iv_myDocument, iv_info;
	private ImageView iv_Comm, iv_bulletinBoard, iv_dictionary, iv_myPicture;
	private ImageView iv_myMusic, iv_note, iv_calendar, iv_alarmclock;
	private ImageView iv_calculator, iv_game, iv_systemUpdate, iv_systemSetting;
	private ImageView iv_showip,iv_applist;
	private ImageView iv_refer,iv_appTool,iv_webinternet,iv_syssetting,iv_search;
	private ImageView iv_apks,iv_mydoc,iv_stuinfo;
	
	private TextView  tv_name;
	private TextView  tv,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
	private ImageView iv,iv1,iv2,iv3,iv4,iv5,iv6,iv7,iv8,iv9;
	int  page_level = 1;
	Bundle thisInstanceState;
	Context ctx;
	LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(  LayoutParams. FILL_PARENT, LayoutParams.FILL_PARENT);  
	int barColor = Color.rgb(0xc1, 0xc1, 0xc1);

	int[] layout_id = new int[] {
			R.id.T2Layout01, R.id.T2Layout02, R.id.T2Layout03,
			R.id.T2Layout04, R.id.T2Layout05, R.id.T2Layout06
   	};
	
	
	int[] txtitem_id = new int[] {
   			R.id.textItem1, R.id.textItem2, R.id.textItem3,
   			R.id.textItem4, R.id.textItem5, R.id.textItem6
   	};
	int[]  rpos_id = new int[] {
   			R.id.pos1, R.id.pos2, R.id.pos3,
   			R.id.pos4, R.id.pos5, R.id.pos6, R.id.pos7
   	};
	
	
	List<String> apksnameX;
	List<Drawable> apksimageX;
	List<Drawable> apksimg_2;
	List<Drawable> apksimg_3, apksimg_4, apksimg_5, apksimg_6;
	List<Drawable> apksimg_7, apksimg_8, apksimg_9, apksimg_12;

	int name_sum_2;
	int name_sum_3,img_sum_3,name_sum_4,img_sum_4;   // 有幾個項目 ?
	int name_sum_5,img_sum_5,name_sum_6,img_sum_6;
	int name_sum_7,img_sum_7,name_sum_8,img_sum_8;   
	int name_sum_9,img_sum_9,name_sum_12,img_sum_12;
	int posnow_3,posnow_4,posnow_5,posnow_6;  //現在 指標的位置
	int posnow_7,posnow_8,posnow_9,posnow_12,posnow_2; 
	int posleft_3,posleft_4,posleft_5,posleft_6;    //後面還有多少
	int posleft_7,posleft_8,posleft_9,posleft_12,posleft_2; 
	int pkg_sum_2,pkg_sum_3,pkg_sum_4,pkg_sum_5,pkg_sum_6; //pkg 已登記有幾個?
	int pkg_sum_7,pkg_sum_8,pkg_sum_9,pkg_sum_12;
	
	List<apk_element> apklist;
	apk_element ae= null;
	Intent intent_new;
	SQLiteDatabase db = null;
	
	private void init_object()
	{
		int i;
		
		apksimg_2 = new ArrayList<Drawable>();
		pkg_sum_2 = pkgname_2.length;
		name_sum_2 = apksname_2.length;
		if( name_sum_2 > 0 ) name_sum_2--;
		posnow_2 = 0;
		posleft_2 = name_sum_2;
		for( i=0;i< name_sum_2;  i++ )
			apksimg_2.add( getResources().getDrawable(drawable_id_2[i]) );
		
		apksimg_3 = new ArrayList<Drawable>();
		pkg_sum_3 = pkgname_3.length;
		name_sum_3 = apksname_3.length;
		if( name_sum_3 > 0 ) name_sum_3--;
		posnow_3 = 0;
		posleft_3 = name_sum_3;
		for( i=0;i< name_sum_3;  i++ )
			apksimg_3.add( getResources().getDrawable(drawable_id_3[i]) );
		
		apksimg_4 = new ArrayList<Drawable>();
		pkg_sum_4 = pkgname_4.length;
		name_sum_4 = apksname_4.length;
		if( name_sum_4 > 0 ) name_sum_4--;
		posnow_4 = 0;
		posleft_4 = name_sum_4;
		for( i=0;i< name_sum_4;  i++ )
			apksimg_4.add( getResources().getDrawable(drawable_id_4[i]) );
		
		apksimg_5 = new ArrayList<Drawable>();
		pkg_sum_5 = pkgname_5.length;
		name_sum_5 = apksname_5.length;
		if( name_sum_5 > 0 ) name_sum_5--;
		posnow_5 = 0;
		posleft_5 = name_sum_5;
		for( i=0;i< name_sum_5;  i++ )
			apksimg_5.add( getResources().getDrawable(drawable_id_5[i]) );
		
		apksimg_6 = new ArrayList<Drawable>();
		pkg_sum_6 = pkgname_6.length;
		name_sum_6 = apksname_6.length;
		if( name_sum_6 > 0 ) name_sum_6--;
		posnow_6 = 0;
		posleft_6 = name_sum_6;
		for( i=0;i< name_sum_6;  i++ )
			apksimg_6.add( getResources().getDrawable(drawable_id_6[i]) );
		
		apksimg_7 = new ArrayList<Drawable>();
		pkg_sum_7 = pkgname_7.length;
		name_sum_7 = apksname_7.length;
		if( name_sum_7 > 0 ) name_sum_7--;
		posnow_7 = 0;
		posleft_7 = name_sum_7;
		for( i=0;i< name_sum_7;  i++ )
			apksimg_7.add( getResources().getDrawable(drawable_id_7[i]) );
	
		apksimg_8 = new ArrayList<Drawable>();
		pkg_sum_8 = pkgname_8.length;
		name_sum_8 = apksname_8.length;
		if( name_sum_8 > 0 ) name_sum_8--;
		posnow_8 = 0;
		posleft_8 = name_sum_8;
		for( i=0;i< name_sum_8;  i++ )
			apksimg_8.add( getResources().getDrawable(drawable_id_8[i]) );
		
		apksimg_9 = new ArrayList<Drawable>();
		pkg_sum_9 = pkgname_9.length;
		name_sum_9 = apksname_9.length;
		if( name_sum_9 > 0 ) name_sum_9--;
		posnow_9 = 0;
		posleft_9 = name_sum_9;
		for( i=0;i< name_sum_9;  i++ )
			apksimg_9.add( getResources().getDrawable(drawable_id_9[i]) );
		
		apksimg_12 = new ArrayList<Drawable>();
		pkg_sum_12 = pkgname_12.length;
		name_sum_12 = apksname_12.length;
		if( name_sum_12 > 0 ) name_sum_12--;
		posnow_12 = 0;
		posleft_12 = name_sum_12;
		for( i=0;i< name_sum_12;  i++ )
			apksimg_12.add( getResources().getDrawable(drawable_id_12[i]) );
		
		apklist = new ArrayList<apk_element>();
        intent_new = getIntent() ;
		File file = new File( L2_LAUNCHER_CONFIG );
		   
		     if( file.exists())
		     {    	
		    	db = null; 
		    	db = SQLiteDatabase.openDatabase( L2_LAUNCHER_CONFIG, null, 0);
		        String TABLE_NAME = "tiles";
			    String TABLE_ROW_ONE = "id";
			    String TABLE_ROW_TWO = "title";
			    String TABLE_ROW_THREE = "appname";
			    String TABLE_ROW_FOUR = "pkgname";
			    
			    String data1, data2, data3, data4;
			    int sum;
			    
			  //--------------------從 Sqlite 表中找準備啟動的Package and Classname -------------------------
			    if( db != null )
			    {
			     //	 Toast.makeText(getApplicationContext(), L2_LAUNCHER_CONFIG + ": open db OK.", Toast.LENGTH_SHORT).show();
			    
			    	if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.GINGERBREAD) {
			    	     // only for gingerbread and newer versions
			    		startManagingCursor(cursor);
			    	}
			    	
			    		cursor = null;

				    cursor = db.query
			        ( 
			                TABLE_NAME,
			                new String[] { TABLE_ROW_ONE, TABLE_ROW_TWO , TABLE_ROW_THREE , TABLE_ROW_FOUR},
			                null,
			                null, null, null, null, null
			        );
				    
			        sum = 0;
			       
			       
				   sum = cursor.getCount();
				   
					//    if( cursor!= null && cursor.getCount()>0)
				       if( sum >  0)
					    {
					    	
					    	cursor.moveToFirst();
					//    	sum = cursor.getCount();
					    	for( i = 0; i< sum; i++)
					    	{
					    		
					    		cursor.moveToPosition(i);
					    		data1 = cursor.getString(0);  // id in sqlite
					    		data2 = cursor.getString(1);  // APK-title in sqlite
					    		data3 = cursor.getString(2);  // class name 
					    		data4 = cursor.getString(3);  // package name  
					    		
					    		ae= new apk_element();
					    		ae.setUid( data1 );
					    		ae.setName( data2 );
			        		   	ae.setClassname( data3 );
			        		   	ae.setPackagename( data4 );
			        		   	apklist.add( ae );
			        		   	
			        		   
					    	}  
					    }
				       
				       if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.GINGERBREAD) {
				    	     // only for gingerbread and newer versions
				    	   stopManagingCursor(cursor); 	    
							 // cursor.close();
				    	}   
				
		          db.close();
		          
			    	
			    }  //if( db != null )
		      
		         
		         
		        }// if( file.exist...
		        
		
		
		
	}
	/*
	@Override
    protected void onDestroy()
    {
        super.onDestroy();
   
    }
    */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		//getView(R.layout.homepage, this);
		thisInstanceState = savedInstanceState;
		init_object();
		
		jumpto_homepage();
	
		
	}
	
  private void jumpto_homepage()
  {
	  	page_level = 10;
		//setContentView(R.layout.level2_b_page);	
		setContentView(R.layout.homepage);

		ctx = this;
		
		findViews();
		readName();    // 讀取用戶姓名檔
		setListener();
  }

	//--------------------------------------------------------
	private void touch_animation( final ImageView action_img ) //點選時的動畫(黑 + 白 )
	{
		action_img.setBackgroundColor(Color.BLACK);
		new Handler().postDelayed(new Runnable(){  
	 	     public void run() {
	 	    	action_img.setBackgroundColor(Color.WHITE);
	 	     }  
	 	   },200);
	} 
	//--------------------------------------------------------
	private OnClickListener myClick = new OnClickListener() {
	
		public void onClick(View v) {
			
		//	Util.ButtonVoice(K3Main.this, R.raw.click);  //音效
			Intent intent = new Intent();
			int press =0, posnow = 0;
			String pkgname="", classname="";
			String str_num, title;
	    	int num;
	    	
			if(  page_level >=20  )
			{
				switch ( page_level )  // 現在正在12格的哪一個格內 ?
				{
					case 22: posnow = posnow_2; break;
					case 23: posnow = posnow_3; break;
					case 24: posnow = posnow_4; break;
					case 25: posnow = posnow_5; break;
					case 26: posnow = posnow_6; break;
					case 27: posnow = posnow_7; break;
					case 28: posnow = posnow_8; break;
					case 29: posnow = posnow_9; break;
					case 32: posnow = posnow_12; break;
					default: posnow=0; break;
				}
				switch ( v.getId() )   // 點選按下的是那一個 item? (加上跨頁數)
				{
				 
				    case (R.id.T2Layout01): press = posnow + 0; break;
				    case (R.id.T2Layout02): press = posnow + 1; break;
				    case (R.id.T2Layout03): press = posnow + 2; break;
				    case (R.id.T2Layout04): press = posnow + 3; break;
				    case (R.id.T2Layout05): press = posnow + 4; break;
				    case (R.id.T2Layout06): press = posnow + 5; break;
				    default: press = -1;    // 按下 非橫條的項目
				}
				
			    if( press != -1 )  // 是按下 橫條的項目
			    {
			    	
			    	/*
						switch ( page_level )  // 取出 pkgname , classname;
						{
						  
							case 22: if( press >= pkg_sum_2 ) return;
									 pkgname = pkgname_2[press];  classname = classname_2[press]; break;
							case 23: if( press >= pkg_sum_3 ) return;
									 pkgname = pkgname_3[press];  classname = classname_3[press]; break;
							case 24: if( press >= pkg_sum_4 ) return;
									 pkgname = pkgname_4[press];  classname = classname_4[press]; break;
							case 25: if( press >= pkg_sum_5 ) return;
									 pkgname = pkgname_5[press];  classname = classname_5[press]; break;
							case 26: if( press >= pkg_sum_6 ) return;
								 	 pkgname = pkgname_6[press];  classname = classname_6[press]; break;
							case 27: if( press >= pkg_sum_7 ) return;
									 pkgname = pkgname_7[press];  classname = classname_7[press]; break;
							case 28: if( press >= pkg_sum_8 ) return;
									 pkgname = pkgname_8[press];  classname = classname_8[press]; break;
							case 29: if( press >= pkg_sum_9 ) return;
									 pkgname = pkgname_9[press];  classname = classname_9[press]; break;
							case 32: if( press >= pkg_sum_12 ) return;
									 pkgname = pkgname_12[press];  classname = classname_12[press]; break;
							default: pkgname ="";  classname =""; break;
						}  
						   */
			    	
						 int user_choice =  ( page_level-20)*100 + (press+1);
						 
						 for( int i=0;i < apklist.size();  i++)
					  	 {
					  		  str_num = apklist.get(i).getUid();
					  		  num = Integer.parseInt(str_num);
					  		  
					  		  if( num == user_choice )
					  		  {
					  			  classname = apklist.get(i).getClassname();
					  			  pkgname = apklist.get(i).getPackagename();
					  			  
					  			  if( classname==null || pkgname==null || classname.length()==0 || pkgname.length()==0 )
					  			  {
					  				  title = apklist.get(i).getName();
					  			//	  Toast.makeText(this, position + " selected", Toast.LENGTH_SHORT).show();
					  				  Toast.makeText(getApplicationContext(), "[ "+title+" ] no define.", Toast.LENGTH_SHORT).show();
					  				 return;
					  			  } //if( class_name.isEmpty()
					  		  }
						 
						  }  // for( int i=0;
						
						String first;
						if( pkgname.length() == 0)  //空字串, 離開
							return;
						
						first = pkgname.substring(0, 1);
						if( first.equals("R") )  // R 開頭, 內建圖片
						{
							page_level += 10;
				   			setContentView(R.layout.pic_display);
				   			showImage_fromFilepath( R.id.imagefullView1 , "/sdcard/math.jpg" );
				   			/*
				   			if( classname.equals( "R.drawable.p_math_3" ))
				   				((ImageView) findViewById(R.id.imagefullView1)).setImageDrawable(getResources().getDrawable(R.drawable.p_math_3));
				   			else if( classname.equals( "R.drawable.p_eng_4" ))
				   				((ImageView) findViewById(R.id.imagefullView1)).setImageDrawable(getResources().getDrawable(R.drawable.p_eng_4));
				   			else if( classname.equals( "R.drawable.chem_table2_v" ))
				   				((ImageView) findViewById(R.id.imagefullView1)).setImageDrawable(getResources().getDrawable(R.drawable.chem_table2_v));
				   			else if( classname.equals( "R.drawable.demo_class" ))
				   				((ImageView) findViewById(R.id.imagefullView1)).setImageDrawable(getResources().getDrawable(R.drawable.demo_class));
				   			*/
				   			return;
						} 
						else if( first.equals("/") )  // '/' 開頭, 外部圖片
						{
							page_level += 10;
				   			setContentView(R.layout.pic_display);
				   			showImage_fromFilepath( R.id.imagefullView1 , pkgname+classname );
				   			return;
						}
						else if( first.equals("F") )  // F 開頭, 離開
						{
						  finish();
						  return;
						}
						                      //----- 以下執行新程序 Intent --------
						intent.setComponent(new ComponentName( pkgname, classname )); 
			   			startActivity(intent);
			   			return;

		   	}   //if( press != -1 )
			}  // if(  page_level >=20  )
			
			switch (v.getId()) {
			
					case (R.id.stuinfo): {  // (1)
						page_level = 21;  //第二層,第一區塊
						setContentView(R.layout.pic_display);
			   			((ImageView) findViewById(R.id.imagefullView1)).setImageDrawable(getResources().getDrawable(R.drawable.demo_student));
			   			
			   			break;
					}
					case (R.id.material): {  // (2)
						touch_animation(  iv_material );  //動畫
						page_level = 22;  //第二層,第二區塊
						jumpTo_page(page_level);
						//intent.setComponent(new ComponentName("com.datalogics.dlreader", "com.datalogics.dlreader.SplashActivity"));
						//intent.setComponent(new ComponentName("org.geometerplus.zlibrary.ui.android", "org.geometerplus.android.fbreader.FBReader"));
						//startActivity(intent);
						break;
					}
					case (R.id.reading): {  // (3)
						touch_animation(  iv_reading );  //動畫
						page_level = 23;  //第二層,第三區塊
						
						jumpTo_page(page_level);
						break;
					}
					case (R.id.myrefer): {  // (4)
						touch_animation(  iv_refer );  //動畫
						page_level = 24;  //第二層,第四區塊
						
						jumpTo_page(page_level);
						break;
					}
					case (R.id.mydoc): {  // (5)
						touch_animation( iv_mydoc );  //動畫
						page_level = 25;  //第二層,第五區塊
						
						jumpTo_page(page_level);
						break;
					}
					case (R.id.appTool): {  // (6)
						touch_animation(  iv_appTool );
						page_level = 26;  //第二層,第六區塊
						
						jumpTo_page(page_level);
						break;
					}
					case (R.id.search): {  // (7)
						touch_animation(  iv_search );
						page_level = 27;  //第二層,第七區塊
						
						jumpTo_page(page_level);
						break;
					}
					case (R.id.imgComm): {  // (8)
						touch_animation(  iv_Comm );
						page_level = 28;  //第二層,第七區塊大 
						   
						jumpTo_page(page_level);
						break;
					}
					case (R.id.webinternet): {  // (9)
						touch_animation(  iv_webinternet );
						page_level = 29;  //第二層,第九區塊
						
						jumpTo_page(page_level);
						break;
					}
					case (R.id.apks): {  // (10)
						touch_animation(  iv_apks );
						intent.setComponent(new ComponentName("com.test.launcher", "com.test.launcher.LauncherListActivity"));
						startActivity(intent);
						break;
					}
					case (R.id.info): {  // (11)
						touch_animation(  iv_info );
						page_level = 20;
			   			setContentView(R.layout.pic_display);
			   			((ImageView) findViewById(R.id.imagefullView1)).setImageDrawable(getResources().getDrawable(R.drawable.demo_school));
			   			
						//intent.setComponent(new ComponentName("com.android.calculator2", "com.android.calculator2.Calculator"));
						//startActivity(intent);
						break;
					}
					case (R.id.syssetting): {  // (12)
						touch_animation(  iv_syssetting );
						page_level = 32;  //第二層,第12區塊
						
						jumpTo_page(page_level);
						//intent.setComponent(new ComponentName("com.test.launcher", "com.test.launcher.LauncherListActivity"));
						//startActivity(intent);
						break;
					}
					case (R.id.pos0): {  // Home button
											//第一層
						jumpto_homepage();
						break;
					}
			
			}  // switch
			
			if( v.getId() == rpos_id[6]  )   //橫條 式 user press Page-Down
			{
				switch ( page_level )
			   	{  
					   	case 22:    //課堂教材
			   				posleft_2 -= 5;
			   				posnow_2 += 5;
			   				jumpTo_page( page_level );
			   			break;
			   			
			   			case 26:    //應用工具
			   		
			   					posleft_6 -= 5;
			   					posnow_6 += 5;
			   					jumpTo_page( page_level );
			   			
			   			break;
			   	
				
			   	}
			}
			else if( v.getId() == rpos_id[5]  )   //橫條 式 user press Page-Up
			{
				switch ( page_level )
			   	{
					   	case 22:    //課堂教材
			   				posleft_2 += 5;
			   				posnow_2 -= 5;
			   				jumpTo_page( page_level );
			   			break;
			   			
			   			case 26:    //應用工具
			   				posleft_6 += 5;
			   				posnow_6 -= 5;
			   				jumpTo_page( page_level );
			   			break;
			   	}
				
			}  // else if
		
			
		}   //public void onClick(
		
	};   //private OnClickListener
	
	/*    // 静默安装 , 但 找不到 IPackageInstallObserver class, 無法compiler
	public void install(String apkPath,String apkName)
	{
		File file = new File(apkPath);  
        if( !file.exists())  
            return ;  
        Uri mPackageURI = Uri.fromFile(file);  
    int installFlags = 0;  
    PackageManager pm = getPackageManager();  
	        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);  
        if(info != null){  
           try {  
               PackageInfo pi = pm.getPackageInfo(info.packageName,PackageManager.GET_UNINSTALLED_PACKAGES);  
                if( pi != null){  
                   installFlags |= PackageManager.INSTALL_REPLACE_EXISTING;   
            }  
            } catch (NameNotFoundException e) {  
        }  
       //把包名和apkName对应起来，后面需要使用  
        map.put(info.packageName, apkName);  
        IPackageInstallObserver observer = new PackageInstallObserver();  
        pm.installPackage(mPackageURI, observer, installFlags, info.packageName);  
	        }  
    }  
   */
	private void setItems_4( int pageLevel ) {  //直列 6列型式
		int i=0,j;
		LinearLayout ll;
		int posnow = 0;     //現在 指標的位置
		int posleft = 0;    //後面還有多少
		
		for( i=0 ;i < 6; i++ )
		{
			ll = (LinearLayout) findViewById(layout_id[i]);          
			ll.setOnClickListener(myClick);
			ll.setBackgroundColor(barColor);
			ll.setVisibility(LinearLayout.VISIBLE);
		}
		switch( page_level)
		{
			case 22:
				apksnameX = new ArrayList<String>();
				for(i=0; i<apksname_2.length; i++)
					apksnameX.add( apksname_2[i]);
				
				apksimageX = new ArrayList<Drawable>();
				for(i=0; i< apksimg_2.size(); i++)
					apksimageX.add( apksimg_2.get(i));
				
				posnow = posnow_2;   
				posleft = posleft_2;  
				break;
			
			case 23:
				apksnameX = new ArrayList<String>();
				for(i=0; i<apksname_3.length; i++)
					apksnameX.add( apksname_3[i]);
				
				apksimageX = new ArrayList<Drawable>();
				for(i=0; i< apksimg_3.size(); i++)
					apksimageX.add( apksimg_3.get(i));
				
				posnow = posnow_3;   
				posleft = posleft_3;  
				break;
				
			case 24:
				apksnameX = new ArrayList<String>();
				for(i=0; i<apksname_4.length; i++)
					apksnameX.add( apksname_4[i]);
				
				apksimageX = new ArrayList<Drawable>();
				for(i=0; i< apksimg_4.size(); i++)
					apksimageX.add( apksimg_4.get(i));
				
				posnow = posnow_4;   
				posleft = posleft_4;  
				break;	
				
			case 25:
				apksnameX = new ArrayList<String>();
				for(i=0; i<apksname_5.length; i++)
					apksnameX.add( apksname_5[i]);
				
				apksimageX = new ArrayList<Drawable>();
				for(i=0; i< apksimg_5.size(); i++)
					apksimageX.add( apksimg_5.get(i));
				
				posnow = posnow_5;   
				posleft = posleft_5;  
				break;	
				
			case 26:
				apksnameX = new ArrayList<String>();
				for(i=0; i<apksname_6.length; i++)
					apksnameX.add( apksname_6[i]);
				
				apksimageX = new ArrayList<Drawable>();
				for(i=0; i< apksimg_6.size(); i++)
					apksimageX.add( apksimg_6.get(i));
				
				posnow = posnow_6;   
				posleft = posleft_6;  
				break;
			case 27:
				apksnameX = new ArrayList<String>();
				for(i=0; i<apksname_7.length; i++)
					apksnameX.add( apksname_7[i]);
				
				apksimageX = new ArrayList<Drawable>();
				for(i=0; i< apksimg_7.size(); i++)
					apksimageX.add( apksimg_7.get(i));
				
				posnow = posnow_7;   
				posleft = posleft_7;  
				break;	
			case 28:
				apksnameX = new ArrayList<String>();
				for(i=0; i<apksname_8.length; i++)
					apksnameX.add( apksname_8[i]);
				
				apksimageX = new ArrayList<Drawable>();
				for(i=0; i< apksimg_8.size(); i++)
					apksimageX.add( apksimg_8.get(i));
				
				posnow = posnow_8;   
				posleft = posleft_8;  
				break;	
			case 29:
				apksnameX = new ArrayList<String>();
				for(i=0; i<apksname_9.length; i++)
					apksnameX.add( apksname_9[i]);
				
				apksimageX = new ArrayList<Drawable>();
				for(i=0; i< apksimg_9.size(); i++)
					apksimageX.add( apksimg_9.get(i));
				
				posnow = posnow_9;   
				posleft = posleft_9;  
				break;	
			case 32:
				apksnameX = new ArrayList<String>();
				for(i=0; i<apksname_12.length; i++)
					apksnameX.add( apksname_12[i]);
				
				apksimageX = new ArrayList<Drawable>();
				for(i=0; i< apksimg_12.size(); i++)
					apksimageX.add( apksimg_12.get(i));
				
				posnow = posnow_12;   
				posleft = posleft_12;  
				break;					
		}
					
		/*
		 *   ------------------  以 下開始 顯 示 ----------------------
		 */
		
				if( posleft > 0 )
				{ 
					 // 1st item is Title text
					((TextView) findViewById(R.id.l2textTitle)).setText(apksnameX.get(0));
				}
				
				if( posnow == 0 && posleft <= 6 )   // only 1 page
				{
					for( i=0 ;i < posleft; i++ )
					{
						((TextView) findViewById(txtitem_id[i])).setText(apksnameX.get(i+1));
						((ImageView) findViewById(rpos_id[i])).setImageDrawable(apksimageX.get(i));
					}
					for( i=posleft ;i < 6; i++ )
					{
						ll = (LinearLayout) findViewById(layout_id[i]);
						ll.setVisibility(LinearLayout.INVISIBLE);
					}
				}
				else if( posnow == 0 && posleft > 6 )  //in 1st page, more pages
				{
					for( i=0 ;i < 5; i++ )
					{
						((TextView) findViewById(txtitem_id[i])).setText(apksnameX.get(i+1));
						((ImageView) findViewById(rpos_id[i])).setImageDrawable(apksimageX.get(i));
					}
					
					((LinearLayout) findViewById(layout_id[5])).setBackgroundColor(Color.WHITE); //白底
					((LinearLayout) findViewById(layout_id[5])).setClickable(false);
					((TextView) findViewById(txtitem_id[5])).setText("");  //最後一列不顯示字形
					((ImageView)findViewById(rpos_id[5])).setVisibility(LinearLayout.INVISIBLE); //page-up Button不顯示
					((ImageView)findViewById(rpos_id[6])).setVisibility(LinearLayout.VISIBLE);
					((ImageView) findViewById(rpos_id[6])).setImageDrawable(getResources().getDrawable(R.drawable.h2_more_down));        
					((ImageView) findViewById(rpos_id[6])).setOnClickListener(myClick);
					
				}
				else if( posnow !=0 && posleft < 6 )  // Last page
				{
					
					for( i=posnow,j=0 ;i < posnow+posleft; i++,j++ )
					{
						((TextView) findViewById(txtitem_id[j])).setText(apksnameX.get(i+1));
						((ImageView) findViewById(rpos_id[j])).setImageDrawable(apksimageX.get(i));
					} 
					for( i=posleft ;i < 5; i++ )
					{
						ll = (LinearLayout) findViewById(layout_id[i]);
						ll.setVisibility(LinearLayout.INVISIBLE);
					}
				
					ll = (LinearLayout) findViewById(layout_id[5]);
					ll.setBackgroundColor(Color.WHITE);
				
					((ImageView)findViewById(rpos_id[5])).setVisibility(LinearLayout.VISIBLE);
					((ImageView) findViewById(rpos_id[5])).setImageDrawable(getResources().getDrawable(R.drawable.h2_more_up));
					((ImageView) findViewById(rpos_id[5])).setOnClickListener(myClick);
					((ImageView)findViewById(rpos_id[6])).setVisibility(LinearLayout.INVISIBLE);
					
				}
				else if( posnow !=0 && posleft >= 6 )  //[UP picture]+ Pages +[Down]
				{
					
					for( i=posnow,j=0 ;i < posnow + 5; i++,j++ )
					{
						((TextView) findViewById(txtitem_id[j])).setText(apksnameX.get(i+1));
						((ImageView) findViewById(rpos_id[j])).setImageDrawable(apksimageX.get(i));
					}
					
					((LinearLayout) findViewById(layout_id[5])).setBackgroundColor(Color.WHITE); //白底
					((TextView) findViewById(txtitem_id[5])).setText("");  //最後一列不顯示字形
					((ImageView) findViewById(rpos_id[6])).setVisibility(LinearLayout.VISIBLE);
					((ImageView) findViewById(rpos_id[6])).setImageDrawable(getResources().getDrawable(R.drawable.h2_more_down));        
					((ImageView) findViewById(rpos_id[6])).setOnClickListener(myClick);
					((ImageView) findViewById(rpos_id[5])).setVisibility(LinearLayout.VISIBLE);
					((ImageView) findViewById(rpos_id[5])).setImageDrawable(getResources().getDrawable(R.drawable.h2_more_up));
					((ImageView) findViewById(rpos_id[5])).setOnClickListener(myClick);
					
				}
				((ImageView) findViewById(R.id.pos0)).setOnClickListener(myClick);
				
	}
	
	private void setItems_2( int pageLevel ) {  //方塊型式
		
		LinearLayout ll1 = (LinearLayout) findViewById(R.id.T2BLayout01);          
		ll1.setOnClickListener(myClick);
		ll1.setBackgroundColor(barColor); 
		ll1.setVisibility(LinearLayout.VISIBLE);
		
		LinearLayout ll2 = (LinearLayout) findViewById(R.id.T2BLayout02);          
		ll2.setOnClickListener(myClick);
		ll2.setBackgroundColor(barColor); 
		ll2.setVisibility(LinearLayout.VISIBLE);
		
		LinearLayout ll3 = (LinearLayout) findViewById(R.id.T2BLayout03);          
		ll3.setOnClickListener(myClick);
		ll3.setBackgroundColor(barColor); 
		ll3.setVisibility(LinearLayout.VISIBLE);
		
		LinearLayout ll4 = (LinearLayout) findViewById(R.id.T2BLayout04);          
		ll4.setOnClickListener(myClick);
		ll4.setBackgroundColor(barColor); 
		ll4.setVisibility(LinearLayout.VISIBLE);
		
		LinearLayout ll5 = (LinearLayout) findViewById(R.id.T2BLayout05);          
		ll5.setOnClickListener(myClick);
		ll5.setBackgroundColor(barColor); 
		ll5.setVisibility(LinearLayout.VISIBLE);
		
		LinearLayout ll6 = (LinearLayout) findViewById(R.id.T2BLayout06);          
		ll6.setOnClickListener(myClick);
		ll6.setBackgroundColor(barColor); 
		ll6.setVisibility(LinearLayout.VISIBLE);
		
		LinearLayout ll7 = (LinearLayout) findViewById(R.id.T2BLayout07);          
		ll7.setOnClickListener(myClick);
		ll7.setBackgroundColor(barColor); 
		ll7.setVisibility(LinearLayout.VISIBLE);
		
		LinearLayout ll8 = (LinearLayout) findViewById(R.id.T2BLayout08);          
		ll8.setOnClickListener(myClick);
		ll8.setBackgroundColor(barColor); 
		ll8.setVisibility(LinearLayout.VISIBLE);
		
		LinearLayout ll9 = (LinearLayout) findViewById(R.id.T2BLayout09);          
		ll9.setOnClickListener(myClick);
		ll9.setBackgroundColor(barColor); 
		ll9.setVisibility(LinearLayout.VISIBLE);
		
		 tv = (TextView) findViewById(R.id.l2textTitle);
		 tv1 = (TextView) findViewById(R.id.textItem1);
	   	 tv2 = (TextView) findViewById(R.id.textItem2);
	   	 tv3 = (TextView) findViewById(R.id.textItem3);
	     tv4 = (TextView) findViewById(R.id.textItem4);
	   	 tv5 = (TextView) findViewById(R.id.textItem5);
	   	 tv6 = (TextView) findViewById(R.id.textItem6);
	   	 tv7 = (TextView) findViewById(R.id.textItem7);
	   	 tv8 = (TextView) findViewById(R.id.textItem8);
	   	 tv9 = (TextView) findViewById(R.id.textItem9);
	   	 
	   	
		switch( page_level)
		{
		
			case 26:
			
				tv.setText("应用工具");
				tv1.setText("中英字典");
				tv2.setText("计算器");
				tv3.setText("记事本");
				tv4.setText("音乐播放");
				tv5.setText("新闻阅读");
				tv6.setText("万年历"); 
				tv7.setText("时钟");
				tv8.setText("闹铃");
				tv9.setText("档案总管");
				
				((ImageView) findViewById(R.id.posb1)).setImageDrawable(getResources().getDrawable(R.drawable.h2_dictionary));
				((ImageView) findViewById(R.id.posb2)).setImageDrawable(getResources().getDrawable(R.drawable.h2_calculator));
				((ImageView) findViewById(R.id.posb3)).setImageDrawable(getResources().getDrawable(R.drawable.h2_notes));
				((ImageView) findViewById(R.id.posb4)).setImageDrawable(getResources().getDrawable(R.drawable.h2_musicplayer));
				((ImageView) findViewById(R.id.posb5)).setImageDrawable(getResources().getDrawable(R.drawable.h2_news));
				((ImageView) findViewById(R.id.posb6)).setImageDrawable(getResources().getDrawable(R.drawable.h2_year));
				((ImageView) findViewById(R.id.posb7)).setImageDrawable(getResources().getDrawable(R.drawable.h2_clock));
				((ImageView) findViewById(R.id.posb8)).setImageDrawable(getResources().getDrawable(R.drawable.h2_alarm));
				((ImageView) findViewById(R.id.posb9)).setImageDrawable(getResources().getDrawable(R.drawable.h2_filemanager));
				
			
				
				break;

		}
		
	}
	
	private void findViews() {

		iv_stuinfo = (ImageView) findViewById(R.id.stuinfo);
		iv_material = (ImageView) findViewById(R.id.material);
		iv_reading = (ImageView) findViewById(R.id.reading);
		
		iv_refer = (ImageView) findViewById(R.id.myrefer);
		iv_mydoc = (ImageView) findViewById(R.id.mydoc);
		iv_appTool = (ImageView) findViewById(R.id.appTool);
		

		iv_search = (ImageView) findViewById(R.id.search);
		iv_Comm = (ImageView)findViewById(R.id.imgComm);
		iv_webinternet = (ImageView) findViewById(R.id.webinternet);
		
		iv_apks = (ImageView)findViewById(R.id.apks);
		iv_info = (ImageView) findViewById(R.id.info);
		iv_syssetting = (ImageView) findViewById(R.id.syssetting);
		
	}
	
	private void setListener() {

		iv_stuinfo.setOnClickListener(myClick);
		iv_material.setOnClickListener(myClick);
		iv_reading.setOnClickListener(myClick);
		
		iv_refer.setOnClickListener(myClick);
		iv_mydoc.setOnClickListener(myClick);
		iv_appTool.setOnClickListener(myClick);
	
		iv_search.setOnClickListener(myClick);
		iv_Comm.setOnClickListener(myClick);
		iv_webinternet.setOnClickListener(myClick);
		
		iv_apks.setOnClickListener(myClick);
		iv_info.setOnClickListener(myClick);
		iv_syssetting.setOnClickListener(myClick);
	
	}
	
	//----------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		//menu.add(0, 0, 0, "EXIT退出本程序"); 
		return true;
	}
	//----------------------------------------------------
		@Override 
		public boolean onOptionsItemSelected(MenuItem item) {  
			// TODO Auto-generated method stub    
		    finish( );
		    return super.onOptionsItemSelected(item); 
		}		
	@Override
	public boolean onKeyDown( int keyCode, KeyEvent event ) 
	{
		// if( event.getKeyCode() == KeyEvent.KEYCODE_POWER)  // 這個 code攔截不到
		if( event.getKeyCode() == KeyEvent.KEYCODE_BACK)
		{
			page_level -= 10;   // 倒往前1階層
			if( page_level == 0)
			{
				;
			       //   	finish();   //
			}
			else if( page_level <= 20 )
		    {
				page_level = 10;
				onCreate( thisInstanceState );
		    }
		    else
		    	jumpTo_page( page_level );
		}
		
	    return true;
	   //	return super.onKeyDown( keyCode, event);   //Original code
	}
	
	//@Override
	//public void onBackPressed() { }

	
	private void  jumpTo_page( int pageLevel )
	{
		
		switch( page_level)
		{
			case 10:
					setContentView(R.layout.homepage);
					break;
			case 22:
					//page_level = 10;
					//onCreate( thisInstanceState );
					setContentView(R.layout.level2_d_page); 
					vg = findViewById (R.layout.level2_d_page);
					//vg.invalidate();
					//vg.postInvalidate();
					//new Thread(new GameThread()).start();
					setItems_4(pageLevel); 	break;  //直列
		//			setContentView(R.layout.level2_page); setItems_1(pageLevel); 
					
			case 23:
					//setContentView(R.layout.level2_page); setItems_1(pageLevel);  break;
					setContentView(R.layout.level2_d_page); setItems_4(pageLevel); 	break;  //直列
			case 24:
		//			setContentView(R.layout.level2_c_page); setItems_1(pageLevel);  break;	
		//		    setContentView(R.layout.level2_page); setItems_1(pageLevel);  break;	
					setContentView(R.layout.level2_d_page); setItems_4(pageLevel); 	break;  //直列
			case 25:
		//			setContentView(R.layout.level2_page); setItems_1(pageLevel);  break;	
					setContentView(R.layout.level2_d_page); setItems_4(pageLevel); 	break;  //直列
			case 26:
			//		setContentView(R.layout.level2_b_page); setItems_2(pageLevel); 	break; //方塊
			//		setContentView(R.layout.level2_c_page); setItems_3(pageLevel); 	break;  //直列
					setContentView(R.layout.level2_d_page); setItems_4(pageLevel); 	break;  //直列
			case 27:
				setContentView(R.layout.level2_d_page); setItems_4(pageLevel); 	break;  //直列
			//	setContentView(R.layout.level2_page); setItems_1(pageLevel); 	break;
				
			case 28:
				setContentView(R.layout.level2_d_page); setItems_4(pageLevel); 	break;  //直列
				//	setContentView(R.layout.level2_page); setItems_1(pageLevel); 	break;
			case 29:
			//	setContentView(R.layout.level2_page); setItems_1(pageLevel); 	break;
				setContentView(R.layout.level2_d_page); setItems_4(pageLevel); 	break;  //直列
			case 30:
	//			setContentView(R.layout.level2_page); setItems_1(pageLevel); 	
				setContentView(R.layout.level2_d_page); setItems_4(pageLevel); 	break;  //直列
				
			case 31:
	//			setContentView(R.layout.level2_page); setItems_1(pageLevel); 	
				break;
			case 32:
				setContentView(R.layout.level2_d_page); setItems_4(pageLevel); 	break;  //直列
			//	setContentView(R.layout.level2_page); setItems_1(pageLevel); 	break;
				
		}
		
	}
	//-------------------------------------------------------------     
    // 執行命令但不關注結果輸出
    public static  int execRootCmdSilent(String cmd) 
    { 
    	int result = - 1; 
    	DataOutputStream dos = null; 
    	try 
    	{ 
    		Process p = Runtime.getRuntime().exec("su"); 
    		dos = new DataOutputStream(p.getOutputStream()); 
    		Log.i( "RootCmd", cmd); 
    		dos.writeBytes( cmd + "\n"); 
    		dos.flush(); 
    		dos.writeBytes("exit\n"); 
    		dos.flush(); 
    		p.waitFor(); 
    		result = p.exitValue(); 
    		} 
	    	catch (Exception e ) 
	    	{ e.printStackTrace(); } 
    	    finally 
    	    { 
    	    	if (dos != null) 
    	    	{ 
    	    		try { dos.close(); } 
    	    	    catch (IOException e) { e.printStackTrace(); } 
    	     } 
    	 } 
    	return result; 
    } 
  //-------------------------------------------------------------
    private void readName()     // 讀取用戶姓名檔
    {
		
		String temp[]=new String[2];
		String line=null;
		
		tv_name = (TextView) findViewById(R.id.textName);
		tv_name.setTextSize(22);
		
		File filename=new File("/mnt/sdcard/name.txt");		
		if(filename.exists()){
		try{
		FileInputStream fis=new FileInputStream(filename);
		BufferedReader bur=new BufferedReader(new InputStreamReader(fis));	
		
		while((line=bur.readLine())!=null){			
			temp=line.split("，");			
		 }
		bur.close();		
		int int0=temp[0].indexOf("=");
		//int int1=temp[1].indexOf("=");
		String name=temp[0].substring(int0+1, temp[0].length());		
		//String color=temp[1].substring(int1+1, temp[1].length());
	
		if(name!=null){
			tv_name.setText(name);
		
		}else
			tv_name.setText("无学生注册使用");
		}catch (Exception e)
		{
		 e.printStackTrace();
		} //try
		} // if
		else
			tv_name.setText("江苏省南通市天星湖中學 一年級101教室");
			//天星湖中學
			//東方中學
			//竹行中學
			//農場中學
			//  tv_name.setText("富士康电子书包");
    } // readname()
    
  //------------------------------------------------------------- 
    class GameThread implements Runnable { 
    	public void run() { 
    	while (!Thread.currentThread().isInterrupted()) { 
    	try { 
    	Thread.sleep(100); 
    	} catch (InterruptedException e) { 
    	Thread.currentThread().interrupt(); 
    	} 

    	// 使用postInvalidate可以直接在線程中更新界面 
    	vg.postInvalidate(); 
    	} 
    	} 
    	} 
  //------------------------------------------------------------- 
    
    private void showImage_fromFilepath( int drawable_id , String jpg_filepath )
    {
    	
    	File imgFile = new  File(jpg_filepath);
    	if(imgFile.exists()){

    	    Bitmap myBitmap = BitmapFactory.decodeFile(jpg_filepath);  //imgFile.getAbsolutePath());

    	    ImageView myImage = (ImageView) findViewById( drawable_id );
    	    myImage.setImageBitmap(myBitmap);

    	}
    	
    	
    }
    
}
