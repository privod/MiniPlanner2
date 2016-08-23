package ru.home.miniplanner2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.home.miniplanner2.R;
import ru.home.miniplanner2.model.Plan;

/**
 * Created by privod on 23.08.2016.
 */
public class PlanAdapter extends PlannerBaseAdapter<PlanAdapter.PlanViewHolder, Plan> {

    public class PlanViewHolder extends PlannerBaseAdapter.ViewHolder {
        private TextView costTotalTextView;
        private TextView nameTextView;
        private TextView dateRegTextView;

        public PlanViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.text_view_name);
            dateRegTextView = (TextView) itemView.findViewById(R.id.text_view_date_reg);
            costTotalTextView = (TextView) itemView.findViewById(R.id.text_view_cost_total);
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
        holder.nameTextView.setText(plan.getName());
    }
}
