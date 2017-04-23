package hackaton.cimarrones.com.xikin;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Jonathan on 23/04/2017.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Losoya on 03/04/2017.
 */

public class GridAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    ImageButton boton;
    int pos = 0;
    LayoutInflater inflater;

    public GridAdapter(Context context, ImageButton boton, int pos) {
        this.context = context;
        this.boton = boton;
        this.pos = pos;


    }

    @Override
    public int getCount() {
        return pos;
    }

    @Override
    public Object getItem(int position) {
        return pos;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        ImageButton txtTitle;

        TextView txtPrecio;
        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.formatogrid, parent, false);

        // Locate the TextViews in listview_item.xml
        txtTitle = (ImageButton) itemView.findViewById(R.id.imagenBotonAdapter);

        return itemView;
    }
}