package com.mfpad.epdlevel2;


import java.io.File;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.widget.Toast;

public class GalleryPath extends Activity 
{
	private static final int SELECT_PICTURE = 1;
	private String selectedImagePath;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*
		File dir = new File(Environment.getExternalStorageDirectory().toString() + "/sdcard/yourfolder");
        Log.d("File path ", dir.getPath());
        String dirPath=dir.getAbsolutePath();
        if(dir.exists() && dir.isDirectory()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // tells your intent to get the contents
            // opens the URI for your image directory on your sdcard
                            //its upto you what data you want image or video.
            intent.setType("image/*");
        //  intent.setType("video/*");
            intent.setData(Uri.fromFile(dir));
        //  intent.setType("media/*");
        //  intent.
            startActivityForResult(intent, 1);
         */
		//saveMediaEntry("/sdcard/1.jpg","test-1","Pic-1 in sdcard",0,0,null);
		
		
		 Intent intent = new Intent();
         intent.setType("image/*");
         intent.setAction(Intent.ACTION_GET_CONTENT);
         startActivityForResult(Intent.createChooser(intent,
                 "Select Picture"), SELECT_PICTURE);
            
      //  }
      //  else
      //  {
        	Toast.makeText(GalleryPath.this, "No file exist to show", Toast.LENGTH_SHORT).show();
            
      //  }   

	}  // OnCreate
	//------------------------------------------------------
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
     {
   // TODO Auto-generated method stub
		File imageFile;
   super.onActivityResult(requestCode, resultCode, data);

   if (resultCode == RESULT_OK) {
       if (requestCode == SELECT_PICTURE) {
           Uri selectedImageUri = data.getData();
           selectedImagePath = getPath(selectedImageUri);
         
           imageFile = new File(getRealPathFromURI(selectedImageUri));
           Toast.makeText(GalleryPath.this, imageFile.toString(), Toast.LENGTH_LONG).show();
       }
   }
   
    if (requestCode == 1) {
        if (data==null) {
        	Toast.makeText(GalleryPath.this, "No image selected", Toast.LENGTH_SHORT).show();
           //showToast("No image selected");
           //finish();
       }
        else
        {
        Uri selectedImageUri = data.getData();

     //  String filemanagerstring = selectedImageUri.getPath();

        //MEDIA GALLERY
      String  selectedImagePath = getPath(selectedImageUri);

        if(selectedImagePath!=null)
        {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(selectedImageUri);
            startActivity(intent);
        }

        else
        {
        	Toast.makeText(GalleryPath.this, "Image path not correct", Toast.LENGTH_SHORT).show();
           // showToast("Image path not correct");
        }


        } // else
       } //if (requestCode == 1)

     } // onActivityResult
	//------------------------------------------------------
	public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
	//------------------------------------------------------
	private String getRealPathFromURI(Uri contentURI) {
	    Cursor cursor = getContentResolver()
	               .query(contentURI, null, null, null, null); 
	    cursor.moveToFirst(); 
	    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
	    return cursor.getString(idx); 
	}
	//------------------------------------------------------
	
	Uri saveMediaEntry(String imagePath,String title,String description,long dateTaken,int orientation,Location loc)
	{
		String displayName="";
		
		ContentValues v = new ContentValues();
		v.put(Images.Media.TITLE, title);
		v.put(Images.Media.DISPLAY_NAME, displayName);
		v.put(Images.Media.DESCRIPTION, description);
		v.put(Images.Media.DATE_ADDED, dateTaken);
		v.put(Images.Media.DATE_TAKEN, dateTaken);
		v.put(Images.Media.DATE_MODIFIED, dateTaken) ;
		v.put(Images.Media.MIME_TYPE, "image/jpeg");
		v.put(Images.Media.ORIENTATION, orientation);
		File f = new File(imagePath) ;
		File parent = f.getParentFile() ;
		String path = parent.toString().toLowerCase() ;
		String name = parent.getName().toLowerCase() ;
		v.put(Images.ImageColumns.BUCKET_ID, path.hashCode());
		v.put(Images.ImageColumns.BUCKET_DISPLAY_NAME, name);
		v.put(Images.Media.SIZE,f.length()) ;
		f = null;
		
		
		//if( targ_loc != null ) {
		if( loc != null ) {
		v.put(Images.Media.LATITUDE, loc.getLatitude());
		v.put(Images.Media.LONGITUDE, loc.getLongitude());
		}
		
		v.put("_data",imagePath) ;
		ContentResolver c = getContentResolver() ;
		return c.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, v);
		}

}