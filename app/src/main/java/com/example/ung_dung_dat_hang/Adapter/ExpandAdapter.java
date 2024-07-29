package com.example.ung_dung_dat_hang.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ung_dung_dat_hang.ConnnectInternet.DownloadJSONCallback;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.LoaiSanPham;
import com.example.ung_dung_dat_hang.Model.TrangChu.XulyMenu.XuLyJSONMenu;
import com.example.ung_dung_dat_hang.R;

import java.util.List;

public class ExpandAdapter extends BaseExpandableListAdapter {

    Context context;
    List<LoaiSanPham> loaiSanPhams;

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
        return 1;
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

    @Override
    public View getGroupView(int vitriGroupCha, boolean isExpanded, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewGroupCha = layoutInflater.inflate(R.layout.custum_lauout_group_cha, viewGroup, false);
        TextView txtTenLoaiSP = (TextView) viewGroupCha.findViewById(R.id.txtTenLoaiSP);
        txtTenLoaiSP.setText(loaiSanPhams.get(vitriGroupCha).getTENLOAISP());

        return viewGroupCha;
    }

    @Override
    public View getChildView(int vitriGroupCha, int vitriGroupCon, boolean isLastChild, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View viewGroupCon = layoutInflater.inflate(R.layout.custom_layout_group_con, viewGroup, false);
//
//        ExpandableListView expandableListView = (ExpandableListView) viewGroupCon.findViewById(R.id.epMenuCon);
        SecondExpanable secondExpanable = new SecondExpanable(context);
        SecondAdapter secondAdapter = new SecondAdapter(loaiSanPhams.get(vitriGroupCha).getListCon());
        secondExpanable.setAdapter(secondAdapter);
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

            widthMeasureSpec = MeasureSpec.makeMeasureSpec(900,MeasureSpec.AT_MOST);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(1080, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public class SecondAdapter extends BaseExpandableListAdapter{
        List<LoaiSanPham> listCon;
        public SecondAdapter(List<LoaiSanPham> listCon){
            this.listCon = listCon;
            XuLyJSONMenu xuLyJSONMenu = new XuLyJSONMenu();

            for (int i = 0; i < listCon.size(); i++) {
                int maloaisp = listCon.get(i).getMALOAISP();
                final int index = i; // Make the variable effectively final
                xuLyJSONMenu.LayLoaiSanPhamThaoMaLoai(maloaisp, new DownloadJSONCallback() {
                    @Override
                    public void onSuccess(String data) {
                        List<LoaiSanPham> subCategories = xuLyJSONMenu.ParserJSONMenu(data);
                        listCon.get(index).setListCon(subCategories);
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
            return listCon.size();
        }

        @Override
        public int getChildrenCount(int vitriGroupCha) {
            return listCon.get(vitriGroupCha).getListCon().size();
        }

        @Override
        public Object getGroup(int vitriGroupCha) {
            return listCon.get(vitriGroupCha);
        }

        @Override
        public Object getChild(int vitriGroupCha, int vitriGroupCon) {
            return listCon.get(vitriGroupCha).getListCon().get(vitriGroupCon);
        }

        @Override
        public long getGroupId(int vitriGroupCha) {
            return listCon.get(vitriGroupCha).getMALOAISP();
        }

        @Override
        public long getChildId(int vitriGroupCha, int vitriGroupCon) {
            return listCon.get(vitriGroupCha).getListCon().get(vitriGroupCon).getMALOAISP();
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int vitriGroupCha, boolean isExpanded, View view, ViewGroup viewGroup) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewGroupCha = layoutInflater.inflate(R.layout.custum_lauout_group_cha, viewGroup, false);
            TextView txtTenLoaiSP = (TextView) viewGroupCha.findViewById(R.id.txtTenLoaiSP);
            txtTenLoaiSP.setText(listCon.get(vitriGroupCha).getTENLOAISP());

            return viewGroupCha;
        }

        @Override
        public View getChildView(int vitriGroupCha, int vitriGroupCon, boolean isLastChild, View view, ViewGroup viewGroup) {
           TextView tv = new TextView(context);
           tv.setText(listCon.get(vitriGroupCha).getListCon().get(vitriGroupCon).getTENLOAISP());
           tv.setPadding(15,5,5,5);
//           tv.setBackgroundColor(Color.YELLOW);
           tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


            return tv;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

}
