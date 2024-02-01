package nimm.usterkify;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

public class ListAdapter extends BaseAdapter {

    Context context;
    private final String[] values;
    private final String[] numbers;

    public ListAdapter(Context context, String[] values, String[] numbers) {
        this.context = context;
        this.values = values;
        this.numbers = numbers;
    }

    @Override
    public int getCount() {
        return 0;
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
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.activity_listview, parent, false);
            viewHolder.label = (TextView) convertView.findViewById(R.id.label);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.aNametxt);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtName.setText(values[position]);
        viewHolder.label.setText("Version: " + numbers[position]);

        return convertView;
    }

    private static class ViewHolder {

        TextView label;
        TextView txtName;
        ImageView icon;

    }

}
