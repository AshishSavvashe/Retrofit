package allnewspaper.parttimepayments.com.retrofit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AndroidVesrionAdapter extends RecyclerView.Adapter<AndroidVesrionAdapter.AndroidViewHolder>  {

    private List<Android> androidList;
    private Context context;


    public AndroidVesrionAdapter(List<Android> androidList, Context context) {
        this.androidList = androidList;
        this.context = context;
    }

    @NonNull
    @Override
    public AndroidVesrionAdapter.AndroidViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_android, null);
        AndroidViewHolder rcv = new AndroidViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final AndroidVesrionAdapter.AndroidViewHolder androidViewHolder, final int i) {
        androidViewHolder.txtversion.setText(androidList.get(i).getVer());
        androidViewHolder.txtdescription.setText(androidList.get(i).getName());
        androidViewHolder.txtname.setText(androidList.get(i).getApi());

        androidViewHolder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putSerializable("version",androidList.get(i).getVer());
                bundle.putSerializable("name",androidList.get(i).getName());
                bundle.putSerializable("api",androidList.get(i).getApi());
                context.startActivity(new Intent(context, DetailsActivity.class).putExtras(bundle));

            }
        });
    }

    @Override
    public int getItemCount() {
        return androidList.size();
    }





    public static class AndroidViewHolder extends RecyclerView.ViewHolder {

        TextView txtversion;
        TextView txtdescription ;
        TextView txtname;
        LinearLayout linear;


        public AndroidViewHolder(@NonNull View itemView) {
            super(itemView);

            txtversion = (TextView) itemView.findViewById(R.id.txtversion);
            txtdescription = (TextView) itemView.findViewById(R.id.txtdescription);
            txtname = (TextView) itemView.findViewById(R.id.txtname);
            linear = (LinearLayout) itemView.findViewById(R.id.linear);


        }
    }


}
