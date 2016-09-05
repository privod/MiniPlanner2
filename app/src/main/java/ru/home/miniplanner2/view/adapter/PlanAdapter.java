package ru.home.miniplanner2.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import ru.home.miniplanner2.R;
import ru.home.miniplanner2.Util;
import ru.home.miniplanner2.model.Plan;

/**
 * Created by privod on 23.08.2016.
 */
public class PlanAdapter extends BaseAdapter<PlanAdapter.PlanViewHolder, Plan> {

    public PlanAdapter(Class<PlanViewHolder> tClass) {
        super(PlanViewHolder.class);
    }

    public class PlanViewHolder extends BaseAdapter.ViewHolder {
        private TextView costTotalTextView;
        private TextView nameTextView;
        private TextView dateRegTextView;
        private ImageView avatar;

        public PlanViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.text_view_name);
            dateRegTextView = (TextView) itemView.findViewById(R.id.text_view_date_reg);
            costTotalTextView = (TextView) itemView.findViewById(R.id.text_view_cost_total);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
        }
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_view, parent, false);

        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {

        Plan plan = getItem(position);
        if (!holder.nameTextView.getText().equals(plan.getName())) {
            holder.nameTextView.setText(plan.getName());

            String letter = String.valueOf(Character.toUpperCase(plan.getName().charAt(0)));
            ColorGenerator generator = ColorGenerator.MATERIAL;
            TextDrawable drawable = TextDrawable.builder().buildRound(letter, generator.getColor(letter));
            holder.avatar.setImageDrawable(drawable);
        }
        holder.dateRegTextView.setText(Util.dateToString(plan.getDateReg()));
        holder.costTotalTextView.setText(plan.getTotalCost().toPlainString());
    }
}
