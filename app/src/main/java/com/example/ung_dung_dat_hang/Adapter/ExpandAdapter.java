package com.example.ung_dung_dat_hang.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
// a
import com.example.ung_dung_dat_hang.ConnnectInternet.DownloadJSONCallback;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.LoaiSanPham;
import com.example.ung_dung_dat_hang.Model.TrangChu.XulyMenu.XuLyJSONMenu;
import com.example.ung_dung_dat_hang.R;

import java.util.List;

public class ExpandAdapter extends BaseExpandableListAdapter {

    Context context;
    List<LoaiSanPham> loaiSanPhams;
    ViewHolderMenu viewHolderMenu;

    public ExpandAdapter(Context context, List<LoaiSanPham> loaiSanPhams){
        this.context = context;
        this.loaiSanPhams = loaiSanPhams;

        loadSubCategories();
    }

    private void loadSubCategories() {
        XuLyJSONMenu xuLyJSONMenu = new XuLyJSONMenu();

        for (int i = 0; i < loaiSanPhams.size(); i++) {
            int maloaisp = loaiSanPhams.get(i).getMALOAISP();
            final int index = i; // Make the variable effectively final
            xuLyJSONMenu.LayLoaiSanPhamThaoMaLoai(maloaisp, new DownloadJSONCallback() {
                @Override
                public void onSuccess(String data) {
                    List<LoaiSanPham> subCategories = xuLyJSONMenu.ParserJSONMenu(data);
                    loaiSanPhams.get(index).setListCon(subCategories);
                    notifyDataSetChanged(); // Cập nhật UI khi dữ liệu đã được tải
                }

                @Override
                public void onError(String error) {
                    // Xử lý lỗi nếu cần
                }
            });
        }
    }

    @Override
    public int getGroupCount() {
        return loaiSanPhams.size();
    }

    @Override
    public int getChildrenCount(int vitriGroupCha) {
        if (loaiSanPhams.get(vitriGroupCha).getListCon().size() != 0){
            return 1;
        }else {
            return 0;
        }

    }

    @Override
    public Object getGroup(int vitriGroupCha) {
        return loaiSanPhams.get(vitriGroupCha);
    }

    @Override
    public Object getChild(int vitriGroupCha, int vitriGroupCon) {
        return loaiSanPhams.get(vitriGroupCha).getListCon().get(vitriGroupCon);
    }

    @Override
    public long getGroupId(int vitriGroupCha) {
        return loaiSanPhams.get(vitriGroupCha).getMALOAISP();
    }

    @Override
    public long getChildId(int vitriGroupCha, int vitriGroupCon) {
        return loaiSanPhams.get(vitriGroupCha).getListCon().get(vitriGroupCon).getMALOAISP();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public class ViewHolderMenu{
        TextView txtTenLoaiSP;
        ImageView hinhMenu;

    }

    @Override
    public View getGroupView(int vitriGroupCha, boolean isExpanded, View view, ViewGroup viewGroup) {

        View viewGroupCha = view;

        if (viewGroupCha == null) {
            viewHolderMenu = new ViewHolderMenu();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewGroupCha = layoutInflater.inflate(R.layout.custum_lauout_group_cha, viewGroup, false);

            viewHolderMenu.hinhMenu = (ImageView) viewGroupCha.findViewById(R.id.imMenuPlus);
            viewHolderMenu.txtTenLoaiSP = (TextView) viewGroupCha.findViewById(R.id.txtTenLoaiSP);

            viewGroupCha.setTag(viewHolderMenu);
        } else {
            viewHolderMenu = (ViewHolderMenu) viewGroupCha.getTag();
        }


        viewHolderMenu.txtTenLoaiSP.setText(loaiSanPhams.get(vitriGroupCha).getTENLOAISP());

        int demSanPhamCon = loaiSanPhams.get(vitriGroupCha).getListCon().size();
        if (demSanPhamCon > 0) {
            viewHolderMenu.hinhMenu.setVisibility(View.VISIBLE);
        } else {
            viewHolderMenu.hinhMenu.setVisibility(View.INVISIBLE);
        }

        if (isExpanded) {
            viewHolderMenu.hinhMenu.setImageResource(R.drawable.icon_block);
            viewGroupCha.setBackgroundResource(R.color.cologray);
        } else {
            viewHolderMenu.hinhMenu.setImageResource(R.drawable.icon_add);
        }

        viewGroupCha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("loaisp", loaiSanPhams.get(vitriGroupCha).getTENLOAISP() + " - " + loaiSanPhams.get(vitriGroupCha).getMALOAISP());
                return false;
            }
        });


        return viewGroupCha;
    }

    @Override
    public View getChildView(int vitriGroupCha, int vitriGroupCon, boolean isLastChild, View view, ViewGroup viewGroup) {
//        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View viewGroupCon = layoutInflater.inflate(R.layout.custom_layout_group_con, viewGroup, false);
//
//        ExpandableListView expandableListView = (ExpandableListView) viewGroupCon.findViewById(R.id.epMenuCon);
        SecondExpanable secondExpanable = new SecondExpanable(context);
        ExpandAdapter secondAdapter = new ExpandAdapter(context,loaiSanPhams.get(vitriGroupCha).getListCon());
        secondExpanable.setAdapter(secondAdapter);

        secondExpanable.setGroupIndicator(null);
        notifyDataSetChanged();

        return secondExpanable;
    }
    public class SecondExpanable extends ExpandableListView{

        public SecondExpanable(Context context) {
            super(context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            int width = size.x;
            int heiht = size.y;
            Log.d("size",width + " - " + heiht);

//            widthMeasureSpec = MeasureSpec.makeMeasureSpec(900,MeasureSpec.AT_MOST);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(1080, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


//    public class SecondAdapter extends BaseExpandableListAdapter{
//        List<LoaiSanPham> listCon;
//        public SecondAdapter(List<LoaiSanPham> listCon){
//            this.listCon = listCon;
//            XuLyJSONMenu xuLyJSONMenu = new XuLyJSONMenu();
//
//            for (int i = 0; i < listCon.size(); i++) {
//                int maloaisp = listCon.get(i).getMALOAISP();
//                final int index = i; // Make the variable effectively final
//                xuLyJSONMenu.LayLoaiSanPhamThaoMaLoai(maloaisp, new DownloadJSONCallback() {
//                    @Override
//                    public void onSuccess(String data) {
//                        List<LoaiSanPham> subCategories = xuLyJSONMenu.ParserJSONMenu(data);
//                        listCon.get(index).setListCon(subCategories);
//                        notifyDataSetChanged(); // Cập nhật UI khi dữ liệu đã được tải
//                    }
//
//                    @Override
//                    public void onError(String error) {
//                        // Xử lý lỗi nếu cần
//                    }
//                });
//            }
//        }
//
//        @Override
//        public int getGroupCount() {
//            return listCon.size();
//        }
//
//        @Override
//        public int getChildrenCount(int vitriGroupCha) {
//            return listCon.get(vitriGroupCha).getListCon().size();
//        }
//
//        @Override
//        public Object getGroup(int vitriGroupCha) {
//            return listCon.get(vitriGroupCha);
//        }
//
//        @Override
//        public Object getChild(int vitriGroupCha, int vitriGroupCon) {
//            return listCon.get(vitriGroupCha).getListCon().get(vitriGroupCon);
//        }
//
//        @Override
//        public long getGroupId(int vitriGroupCha) {
//            return listCon.get(vitriGroupCha).getMALOAISP();
//        }
//
//        @Override
//        public long getChildId(int vitriGroupCha, int vitriGroupCon) {
//            return listCon.get(vitriGroupCha).getListCon().get(vitriGroupCon).getMALOAISP();
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return false;
//        }
//
//        @Override
//        public View getGroupView(int vitriGroupCha, boolean isExpanded, View view, ViewGroup viewGroup) {
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View viewGroupCha = layoutInflater.inflate(R.layout.custum_lauout_group_cha, viewGroup, false);
//            TextView txtTenLoaiSP = (TextView) viewGroupCha.findViewById(R.id.txtTenLoaiSP);
//            txtTenLoaiSP.setText(listCon.get(vitriGroupCha).getTENLOAISP());
//
//            return viewGroupCha;
//        }
//
//        @Override
//        public View getChildView(int vitriGroupCha, int vitriGroupCon, boolean isLastChild, View view, ViewGroup viewGroup) {
//           TextView tv = new TextView(context);
//           tv.setText(listCon.get(vitriGroupCha).getListCon().get(vitriGroupCon).getTENLOAISP());
//           tv.setPadding(15,5,5,5);
////           tv.setBackgroundColor(Color.YELLOW);
//           tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//
//            return tv;
//        }
//
//        @Override
//        public boolean isChildSelectable(int groupPosition, int childPosition) {
//            return false;
//        }
//    }

}
