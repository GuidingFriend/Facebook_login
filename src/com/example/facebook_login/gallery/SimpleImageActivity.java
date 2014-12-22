/*******************************************************************************
 * Copyright 2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.example.facebook_login.gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.facebook_login.gallery.Constants;
import com.example.facebook_login.R;
import com.example.facebook_login.gallery.ImageGalleryFragment;
import com.example.facebook_login.gallery.ImagePagerFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class SimpleImageActivity extends FragmentActivity {
	protected ImageLoader imageLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.places_listview);
		Bundle bundle = getIntent().getExtras();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		
		
		String selected_image = bundle.getString("Selected_image");
//		Log.d("SelectedURL",selected_image);
		
		Fragment fragment = new Fragment();
		Bundle bundleforfragment = new Bundle();
		bundle.putString("SelectedURL", selected_image);
		fragment.setArguments(bundle);
		
		int frIndex = getIntent().getIntExtra(Constants.Extra.FRAGMENT_INDEX, 0);
		
		Fragment fr;
		String tag;
		int titleRes;
		tag = ImagePagerFragment.class.getSimpleName();
		fr = getSupportFragmentManager().findFragmentByTag(tag);
		if (fr == null) {
			fr = new ImagePagerFragment();
			fr.setArguments(getIntent().getExtras());
		}
		titleRes = R.string.app_name;
//		switch (frIndex) {
//			default:
//			case ImageGalleryFragment.INDEX:
//				tag = ImageGalleryFragment.class.getSimpleName();
//				fr = getSupportFragmentManager().findFragmentByTag(tag);
//				if (fr == null) {
//					fr = new ImageGalleryFragment();
//				}
//				titleRes = R.string.ac_name_image_gallery;
//				break;
//			case ImagePagerFragment.INDEX:
//				tag = ImagePagerFragment.class.getSimpleName();
//				fr = getSupportFragmentManager().findFragmentByTag(tag);
//				if (fr == null) {
//					fr = new ImagePagerFragment();
//					fr.setArguments(getIntent().getExtras());
//				}
//				titleRes = R.string.app_name;
//				break;
//		}

		setTitle(titleRes);
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fr, tag).commit();
	}
}