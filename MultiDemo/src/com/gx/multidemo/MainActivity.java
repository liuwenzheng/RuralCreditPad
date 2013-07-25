package com.gx.multidemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ListView listView;
	List<Msg> list = new ArrayList<Msg>();
	MyAdapter adapter;
	private float startX;
	private float endX;
	float startY;
	float endY;
	Button box, box1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	int itemnum;

	public void initView() {
		for (int i = 0; i < 20; i++) {
			Msg msg = new Msg();
			msg.setTitle("list_view" + i);
			list.add(msg);
		}
		listView = (ListView) findViewById(R.id.list_view);
		ImageView img = (ImageView) findViewById(R.id.img);
		img.startAnimation(new BgAnim().getAnim(img));
		adapter = new MyAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long arg3) {

				Toast.makeText(MainActivity.this, "您选择了第" + pos + "个",
						Toast.LENGTH_LONG).show();
			}
		});
		listView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// 按下
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:

					endX = event.getX();
					endY = event.getY();
					int x = (int) event.getX();
					int y = (int) event.getY();
					itemnum = listView.pointToPosition(x, y);

					System.out.println("position--->" + itemnum);
					ViewGroup item = (ViewGroup) listView.getChildAt(itemnum
							- listView.getFirstVisiblePosition());
					box = (Button) item.findViewById(R.id.delete);
					ViewGroup item1 = (ViewGroup) listView.getChildAt(Const.pos
							- listView.getFirstVisiblePosition());
					box1 = (Button) item1.findViewById(R.id.delete);
					// 判断当前滑动的item是否和上次的删除按钮相同；
					if (Const.pos != itemnum
							&& box1.getVisibility() == View.VISIBLE) {
						System.out.println("222");
						Animation animation = AnimationUtils.loadAnimation(
								MainActivity.this, R.anim.out);
						box1.startAnimation(animation);
						box1.setVisibility(View.GONE);
					}

					Const.pos = itemnum;
					box.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							list.remove(itemnum);
							adapter.notifyDataSetChanged();
							box.setVisibility(View.GONE);
						}
					});
					System.out.println("差值--->Y:" + (endY - startY));
					System.out.println("差值--->X:" + (endX - startX));
					if ((endX - startX) > 50 && Math.abs(endY - startY) <= 50
							&& box.getVisibility() == View.VISIBLE) {
						Animation animation = AnimationUtils.loadAnimation(
								MainActivity.this, R.anim.out);
						box.startAnimation(animation);
						box.setVisibility(View.GONE);
						adapter.notifyDataSetChanged();
					} else if ((endX - startX) < (-30)
							&& box.getVisibility() == View.GONE
							&& Math.abs(endY - startY) <= 50) {
						Animation animation = AnimationUtils.loadAnimation(
								MainActivity.this, R.anim.in);
						box.startAnimation(animation);
						box.setVisibility(View.VISIBLE);
						// System.out.println("抬起事件" + false);
					}

					// 弹起
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return list.size();
		}

		@Override
		public Object getItem(int arg0) {

			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {

			return list.get(arg0).hashCode();
		}

		@Override
		public View getView(final int position, View view, ViewGroup arg2) {

			ViewHolder holder;
			if (view == null) {
				holder = new ViewHolder();
				view = LayoutInflater.from(MainActivity.this).inflate(
						R.layout.activity_main_item, null);
				view.setTag(holder);
				holder.title = (TextView) view.findViewById(R.id.title);
				// holder.ch = (CheckBox) view.findViewById(R.id.ch);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.title.setText(list.get(position).getTitle());

			return view;
		}

		class ViewHolder {
			TextView title;
		}

	}

}
