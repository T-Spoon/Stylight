package com.tspoon.stylight.ui.adapters;

import android.content.Context;
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
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.tspoon.stylight.R;
import com.tspoon.stylight.model.Product;
import com.tspoon.stylight.utils.Formatter;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<Product> {

    private final String TAG = "ProductListAdapter";

    private LayoutInflater mInflator;
    private Context mContext;
    private Picasso mPicasso;

    private final int mColorSale;
    private final int mImageWidth;
    private final int mImageHeight;

    public ProductListAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflator = LayoutInflater.from(context);
        mPicasso = Picasso.with(context);
        mColorSale = context.getResources().getColor(android.R.color.holo_red_dark);
        mImageWidth = context.getResources().getDimensionPixelSize(R.dimen.product_image_width);
        mImageHeight = context.getResources().getDimensionPixelSize(R.dimen.product_image_height);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflator.inflate(R.layout.list_item_product, parent, false);
            holder = new ViewHolder();
            holder.productImage = (ImageView) convertView.findViewById(R.id.product_image);
            holder.productBrand = (TextView) convertView.findViewById(R.id.product_brand);
            holder.productName = (TextView) convertView.findViewById(R.id.product_name);
            holder.productPrice = (TextView) convertView.findViewById(R.id.product_price);
            holder.productPriceSale = (TextView) convertView.findViewById(R.id.product_price_sale);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = getItem(position);

        String primaryImage = product.getPrimaryImage();
        if (primaryImage != null) {
            if (product.isSale()) {
                mPicasso.load(primaryImage).resize(mImageWidth, mImageHeight).into(holder.productImage);
            } else {
                mPicasso.load(primaryImage).resize(mImageWidth, mImageHeight).transform(mGreyscaleTransformation).into(holder.productImage);
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

        return convertView;
    }

    private static class ViewHolder {
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
