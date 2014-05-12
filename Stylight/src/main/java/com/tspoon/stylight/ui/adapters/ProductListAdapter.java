package com.tspoon.stylight.ui.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.tspoon.stylight.R;
import com.tspoon.stylight.model.Product;
import com.tspoon.stylight.model.ProductPair;
import com.tspoon.stylight.utils.Formatter;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<ProductPair> implements View.OnClickListener {

    private final String TAG = "ProductListAdapter";

    private LayoutInflater mInflator;
    private Context mContext;
    private Picasso mPicasso;

    private final int mColorSale;
    private final int mImageSize;

    public ProductListAdapter(Context context, int resource, List<ProductPair> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflator = LayoutInflater.from(context);
        mPicasso = Picasso.with(context);
        mColorSale = context.getResources().getColor(android.R.color.holo_red_dark);

        int rotationType = context.getResources().getConfiguration().orientation;
        if (rotationType == Configuration.ORIENTATION_LANDSCAPE) {
            mImageSize = context.getResources().getDimensionPixelSize(R.dimen.product_image_size_landscape);
        } else {
            mImageSize = context.getResources().getDimensionPixelSize(R.dimen.product_image_size_portrait);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holderOne, holderTwo;
        if (convertView == null) {
            convertView = mInflator.inflate(R.layout.list_item_product, parent, false);
            holderOne = new ViewHolder();
            holderOne.productContainer = (LinearLayout) convertView.findViewById(R.id.product_container_one);
            holderOne.productImage = (ImageView) holderOne.productContainer.findViewById(R.id.product_image);
            holderOne.productBrand = (TextView) holderOne.productContainer.findViewById(R.id.product_brand);
            holderOne.productName = (TextView) holderOne.productContainer.findViewById(R.id.product_name);
            holderOne.productPrice = (TextView) holderOne.productContainer.findViewById(R.id.product_price);
            holderOne.productPriceSale = (TextView) holderOne.productContainer.findViewById(R.id.product_price_sale);

            holderTwo = new ViewHolder();
            holderTwo.productContainer = (LinearLayout) convertView.findViewById(R.id.product_container_two);
            holderTwo.productImage = (ImageView) holderTwo.productContainer.findViewById(R.id.product_image);
            holderTwo.productBrand = (TextView) holderTwo.productContainer.findViewById(R.id.product_brand);
            holderTwo.productName = (TextView) holderTwo.productContainer.findViewById(R.id.product_name);
            holderTwo.productPrice = (TextView) holderTwo.productContainer.findViewById(R.id.product_price);
            holderTwo.productPriceSale = (TextView) holderTwo.productContainer.findViewById(R.id.product_price_sale);

            convertView.setTag(R.id.key_holder_one, holderOne);
            convertView.setTag(R.id.key_holder_two, holderTwo);
        } else {
            holderOne = (ViewHolder) convertView.getTag(R.id.key_holder_one);
            holderTwo = (ViewHolder) convertView.getTag(R.id.key_holder_two);
        }

        ProductPair pair = getItem(position);

        populateView(pair.getProductOne(), holderOne);
        if (pair.getProductTwo() != null) {
            populateView(pair.getProductTwo(), holderTwo);
            holderTwo.productContainer.setVisibility(View.VISIBLE);
        } else {
            holderTwo.productContainer.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    private void populateView(Product product, ViewHolder holder) {
        String primaryImage = product.getPrimaryImage();
        if (primaryImage != null) {
            if (product.isSale()) {
                mPicasso.load(primaryImage).resize(mImageSize, mImageSize).into(holder.productImage);
            } else {
                mPicasso.load(primaryImage).resize(mImageSize, mImageSize).transform(mGreyscaleTransformation).into(holder.productImage);
            }
        }

        holder.productBrand.setText(product.getBrandName());
        holder.productName.setText(product.getName());

        if (product.isSale()) {
            holder.productPrice.setText(Formatter.formatCurrency(product.getPrice()));
            holder.productPrice.setPaintFlags(holder.productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.productPriceSale.setText(Formatter.formatCurrencyOnSale(product.getPrice(), product.getSavings()));
            holder.productPriceSale.setTextColor(mColorSale);
        } else {
            holder.productPrice.setText(Formatter.formatCurrency(product.getPrice()));
            holder.productPrice.setPaintFlags(holder.productPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.productPriceSale.setText("");
        }

        holder.productContainer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_container_one:
                Toast.makeText(mContext, "Left Item", Toast.LENGTH_SHORT).show();
                break;
            case R.id.product_container_two:
                Toast.makeText(mContext, "Right Item", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private static class ViewHolder {
        LinearLayout productContainer;
        ImageView productImage;
        TextView productBrand;
        TextView productName;
        TextView productPrice;
        TextView productPriceSale;
    }

    private Transformation mGreyscaleTransformation = new Transformation() {
        @Override
        public Bitmap transform(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            Bitmap greyscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(greyscale);
            Paint paint = new Paint();
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0f);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            paint.setColorFilter(filter);
            c.drawBitmap(bitmap, 0, 0, paint);
            bitmap.recycle();
            return greyscale;
        }

        @Override
        public String key() {
            return "greyscale()";
        }
    };
}
