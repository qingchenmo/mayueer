package com.jlkf.ego.fragment.wellcome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.utils.ToastUti;


/**
 * 欢迎页的第二个子页面 （拍卖）
 * @author max
 */
public class Fragment2 extends Fragment {


	private View view;
	private LayoutInflater inflater;

	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,  Bundle savedInstanceState) {
		this.inflater=inflater;
		if(view==null){
			iniView();
		}
		return view;
	}

	private void iniView() {
		try {
			view = inflater.inflate(R.layout.fragment_wellcome_two, null);


//			String url = getArguments().getString("url");
			String url = "https://www.baidu.com/img/bd_logo1.png";
			ToastUti.show(url);
			Glide.with(getContext()).load(url).into((ImageView) view.findViewById(R.id.iv));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
