package hackaton.cimarrones.com.xikin;

/**
 * Created by Jonathan on 23/04/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List<ItemObject> listStorage;
    private Context context;
    private  int imagen;
    private List<Integer> intposibles;

    public CustomAdapter(Context context,List<Integer> posibles) {
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imagen = imagen;
        intposibles = posibles;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.formatogrid, parent, false);
            listViewHolder.imageInListView = (ImageButton)convertView.findViewById(R.id.imagenBotonAdapter);
            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }

        int imageResourceId = intposibles.get(position);
        listViewHolder.imageInListView.setImageResource(imageResourceId);

        return convertView;
    }

    static class ViewHolder{
        ImageView imageInListView;
    }

}