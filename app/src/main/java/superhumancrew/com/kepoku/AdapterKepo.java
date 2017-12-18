package superhumancrew.com.kepoku;

/**
 * Created by dedie on 12/4/17.
 */
import android.R;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterKepo extends RecyclerView.Adapter<AdapterKepo.ViewHolder> {

    private ArrayList<Kepo> daftarKepo;
    private Context context;

    public AdapterKepo(ArrayList<Kepo> daftarKepo, Context context) {
        this.daftarKepo = daftarKepo;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTanggal, txtKepo;

        ViewHolder(View v){
            super(v);
            txtTanggal = (TextView)v.findViewById(superhumancrew.com.kepoku.R.id.txtTanggal);
            txtKepo = (TextView)v.findViewById(superhumancrew.com.kepoku.R.id.txtKepo);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(superhumancrew.com.kepoku.R.layout.item_kepo, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String waktu = daftarKepo.get(position).getWaktu();
        final String kepo = daftarKepo.get(position).getKepo();
        holder.txtTanggal.setText(waktu);
        holder.txtKepo.setText(kepo);
    }

    @Override
    public int getItemCount() {
        return daftarKepo.size();
    }
}
