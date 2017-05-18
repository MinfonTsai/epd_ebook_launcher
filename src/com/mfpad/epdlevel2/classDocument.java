package com.mfpad.epdlevel2;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class classDocument extends Activity 
{
	final String DOCUMENT_PATH = "/sdcard/Document/";
	private TextView[] filename;
	private ImageView[] listViewIcon;
	private String openFilePath = null;
	private ArrayList<String> itemsName = new ArrayList<String>();
	private ArrayList<String> itemsNameWithoutFileNameExtension = new ArrayList<String>();
	
	
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
	List<String> filesnameX;
	List<Drawable> filesimageX;
	int posnow;  //現在 指標的位置
	int posleft;  //後面還有多少
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.level2_d_page);
		
		try {
					// 如果SDCard不存在,直接跳離Activity
			boolean SDCARD_EXIST = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
			if (SDCARD_EXIST == false) {
				//Logger.DeBugLog(Logger.DEBUG, "SDCard 不在, 請檢查.");
				//openNoSDCardDialog();
				Toast.makeText( getBaseContext(), " SDCard 不在, 請檢查...", Toast.LENGTH_LONG).show();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			//getView(R.layout.error, this);
			//setBreadCrumb(getString(R.string.BREADCRUMB_ERROR));
			//openErrorDialog();
			//Logger.DeBugLog(Logger.DEBUG, "MyDocument-initMainView Error");
		}
	 
		init();
		openFilePath = DOCUMENT_PATH;
		java.io.File file = new java.io.File(openFilePath);
		handleFile( file.listFiles() );
		
		setContentView(R.layout.level2_d_page);
		posleft = itemsName.size();
		set_filesItem();
	
		
	}

	
	//-----------------------------------------------
	private void set_filesItem( ) {  //直列 6列型式
		int i=0,j;
		LinearLayout ll;
		
		for( i=0 ;i < 6; i++ )
		{
			ll = (LinearLayout) findViewById(layout_id[i]);          
			ll.setOnClickListener(myClick);
			ll.setBackgroundColor(barColor);
			ll.setVisibility(LinearLayout.VISIBLE);
		}
		
		//    ------------------  以 下開始 顯 示 ----------------------
		
		
				if( posleft > 0 )
				{ 
					 // 1st item is Title text
					((TextView) findViewById(R.id.l2textTitle)).setText("課堂教材列表");
				}
	
		
				if( posnow == 0 && posleft <= 6 )   // only 1 page
				{
					for( i=0 ;i < posleft; i++ )
					{
						((TextView) findViewById(txtitem_id[i])).setText(itemsNameWithoutFileNameExtension.get(i));
						//((ImageView) findViewById(rpos_id[i])).setImageDrawable(apksimageX.get(i));
					}
					set_IconList( posnow,  posnow + posleft );
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
						((TextView) findViewById(txtitem_id[i])).setText(itemsNameWithoutFileNameExtension.get(i));
					//	((ImageView) findViewById(rpos_id[i])).setImageDrawable(apksimageX.get(i));
					}
					set_IconList( posnow,  posnow +5 );
					((LinearLayout) findViewById(layout_id[5])).setBackgroundColor(Color.WHITE); //白底
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
						((TextView) findViewById(txtitem_id[j])).setText(itemsNameWithoutFileNameExtension.get(i));
						//((ImageView) findViewById(rpos_id[j])).setImageDrawable(apksimageX.get(i));
					} 
					set_IconList( posnow,  posnow +posleft );
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
						((TextView) findViewById(txtitem_id[j])).setText(itemsNameWithoutFileNameExtension.get(i));
						//((ImageView) findViewById(rpos_id[j])).setImageDrawable(apksimageX.get(i));
					}
					set_IconList( posnow,  posnow +5 );
					((LinearLayout) findViewById(layout_id[5])).setBackgroundColor(Color.WHITE); //白底
					((TextView) findViewById(txtitem_id[5])).setText("");  //最後一列不顯示字形
					((ImageView)findViewById(rpos_id[6])).setVisibility(LinearLayout.VISIBLE);
					((ImageView) findViewById(rpos_id[6])).setImageDrawable(getResources().getDrawable(R.drawable.h2_more_down));        
					((ImageView) findViewById(rpos_id[6])).setOnClickListener(myClick);
					((ImageView)findViewById(rpos_id[5])).setVisibility(LinearLayout.VISIBLE);
					((ImageView) findViewById(rpos_id[5])).setImageDrawable(getResources().getDrawable(R.drawable.h2_more_up));
					((ImageView) findViewById(rpos_id[5])).setOnClickListener(myClick);
				}
				((ImageView) findViewById(R.id.pos0)).setOnClickListener(myClick);
				
	}
	//-------------------------------------------------------
	private void set_IconList( int start, int end ) {
		int i,j;
		
		for (i =start,j=0; i< end; i++, j++ ) 
		{
			
			//if (itemsName.get(i).endsWith("doc"))
			//	listViewIcon[j].setImageResource(R.drawable.listview_document_doc);
			if (itemsName.get(i).endsWith("doc"))
				((ImageView) findViewById(rpos_id[j])).setImageResource(R.drawable.listview_document_doc);
			else if (itemsName.get(i).endsWith("docx"))
				((ImageView) findViewById(rpos_id[j])).setImageResource(R.drawable.listview_document_docx);
			else if (itemsName.get(i).endsWith("pdf"))
				((ImageView) findViewById(rpos_id[j])).setImageResource(R.drawable.listview_document_pdf);
			else if (itemsName.get(i).endsWith("txt"))
				((ImageView) findViewById(rpos_id[j])).setImageResource(R.drawable.listview_document_txt);
			else if (itemsName.get(i).endsWith("xls"))
				((ImageView) findViewById(rpos_id[j])).setImageResource(R.drawable.listview_document_xls);
			else if (itemsName.get(i).endsWith("xlsx"))
				((ImageView) findViewById(rpos_id[j])).setImageResource(R.drawable.listview_document_xlsx);
			else if (itemsName.get(i).endsWith("ppt"))
				((ImageView) findViewById(rpos_id[j])).setImageResource(R.drawable.listview_document_ppt);
			else if (itemsName.get(i).endsWith("pptx"))
				((ImageView) findViewById(rpos_id[j])).setImageResource(R.drawable.listview_document_pptx);
			else if (itemsName.get(i).endsWith("pps"))
				((ImageView) findViewById(rpos_id[j])).setImageResource(R.drawable.listview_document_pps);
			else if (itemsName.get(i).endsWith("ppsx"))
				((ImageView) findViewById(rpos_id[j])).setImageResource(R.drawable.listview_document_ppsx);
			else if (itemsName.get(i).endsWith("epub"))
				((ImageView) findViewById(rpos_id[j])).setImageResource(R.drawable.listview_document_epub);
			else
				((ImageView) findViewById(rpos_id[j])).setImageResource(R.drawable.listview_document_unknown);
			//name[i].setText(itemsNameWithoutFileNameExtension.get(iTemp));
		}
		
	}
	private OnClickListener myClick = new OnClickListener() {
		public void onClick(View v) {
	
			switch (v.getId()) {
				case (R.id.pos0): {  // Home button
					finish();
					break;
				}
				case (R.id.T2Layout01): {   // user press Item 1
					open_userfile( posnow );
					break;
				}
				case (R.id.T2Layout02): {   // user press Item 2
					open_userfile( posnow + 1 );
					break;
				}
				case (R.id.T2Layout03): {   // user press Item 3
					open_userfile( posnow + 2 );
					break;
				}
				case (R.id.T2Layout04): {   // user press Item 4
					open_userfile( posnow + 3 );
					break;
				}
				case (R.id.T2Layout05): {   // user press Item 5
					open_userfile( posnow + 4 );
					break;
				}
				case (R.id.T2Layout06): {   // user press Item 6
					open_userfile( posnow + 5 );
					break;
				}
			}
			if( v.getId() == rpos_id[6]  )   //橫條 式 user press Page-Down
			{
				posleft -= 5;
   				posnow += 5;
   				set_filesItem();
			}
			else if( v.getId() == rpos_id[5]  )   //橫條 式 user press Page-Up
			{
				posleft += 5;
   				posnow -= 5;
   				set_filesItem();
			}
		}
	};
	//-----------------------------------------------
	private void init() {
		listViewIcon = new ImageView[11];
		filename = new TextView[11];

		for (int i = 1; i <= 10; i++) {
			listViewIcon[i] = null;
			filename[i] = null;
		}
	}
	//-----------------------------------------------
	private void handleFile(File[] files) {
		if (files == null) {
			return;
		}

		for (File file : files) {
			if (!file.isHidden() && !file.isDirectory())
				if (file.getName().endsWith("epub") || file.getName().endsWith("doc")
						|| file.getName().endsWith("docx") || file.getName().endsWith("ppt")
						|| file.getName().endsWith("pptx") || file.getName().endsWith("pps")
						|| file.getName().endsWith("ppsx") || file.getName().endsWith("xls")
						|| file.getName().endsWith("xlsx") || file.getName().endsWith("txt")
						|| file.getName().endsWith("pdf")) {
					itemsName.add(file.getName());					
					//Logger.DeBugLog(Logger.DEBUG, "MyDocument-FileList:" + file.getName());
				}

		}
		
		//remove FileName's Extension from -->itemsName;
		int lastIndexOfDot = 0;
		
		for(int i = 0; i<itemsName.size();i++){
			lastIndexOfDot = itemsName.get(i).lastIndexOf('.');
			itemsNameWithoutFileNameExtension.add(itemsName.get(i).substring(0, lastIndexOfDot));
		}
		
	}
	//-----------------------------------------------
	private void open_userfile( int item )
	{
		String fname;
		fname = DOCUMENT_PATH + itemsName.get( item );
		
		Intent intent = new Intent();
		// 開啟epub
		if ( fname.endsWith("epub") ) {
			try {
				intent.setAction("android.intent.action.MAIN");
				intent.setData(Uri.parse(fname));
				intent.setClassName("org.geometerplus.zlibrary.ui.android", "org.geometerplus.android.fbreader.FBReader");
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
				startActivity(intent);
			//	openLoadingBookToast();
			} catch (Exception e) {
				e.printStackTrace();
		//		openIIIReaderErrorToast();
			}
			return;
		}
		else if ( fname.endsWith("doc") || fname.endsWith("ppt") || fname.endsWith("xls") || fname.endsWith("pdf") || fname.endsWith("txt")) 
		{
			File oFile = new File(fname); 
			if( oFile.exists()) 
			{
			   Uri path = Uri.fromFile(oFile); 
			    Intent xintent = new Intent(Intent.ACTION_VIEW);
			    xintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    xintent.addCategory( "android.intent.category.DEFAULT" ); 
			    xintent.setDataAndType( path, "application/vnd.ms-powerpoint" ); 
			    try
			    {
			        startActivity( xintent );
			    }
			    catch(ActivityNotFoundException e)
			    { 
			        Toast.makeText(classDocument.this, "No Application available to view this file", Toast.LENGTH_LONG).show(); 
			    }
			}
			return;
			
		}
		else if ( fname.endsWith("pdf") ) 
		{
			File pdfFile = new File(fname); 
			if(pdfFile.exists()) 
			{
			 Uri path = Uri.fromFile(pdfFile); 
			    Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
			    pdfIntent.setDataAndType(path, "application/pdf");
			    pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			    try
			    {
			        startActivity(pdfIntent);
			    }
			    catch(ActivityNotFoundException e)
			    { 
			        Toast.makeText(classDocument.this, "No Application available to view pdf", Toast.LENGTH_LONG).show(); 
			    }
			}
			return;
		}
	}
	//-----------------------------------------------
	
}