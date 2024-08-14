package com.example.ung_dung_dat_hang.View.ChiTietSanPham;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.example.ung_dung_dat_hang.R;
import java.util.ArrayList;

public class AnhPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> imageUrls;

    public AnhPagerAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_image_pager, container, false);
        ImageView imageView = itemView.findViewById(R.id.imageView);

        // Use Glide to load image into ImageView
        Glide.with(context).load(imageUrls.get(position)).into(imageView);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
