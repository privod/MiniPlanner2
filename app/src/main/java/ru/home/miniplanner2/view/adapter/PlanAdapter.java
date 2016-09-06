package ru.home.miniplanner2.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.j256.ormlite.misc.TransactionManager;

import ru.home.miniplanner2.R;
import ru.home.miniplanner2.Util;
import ru.home.miniplanner2.model.Plan;

/**
 * Created by privod on 23.08.2016.
 */
public class PlanAdapter extends BaseAdapter<PlanAdapter.PlanViewHolder, Plan> {

    public PlanAdapter(Context context, Class<PlanViewHolder> tClass) {
        super(PlanViewHolder.class);
    }

    public class PlanViewHolder extends BaseAdapter.ViewHolder {
        private TextView costTotalTextView;
        private TextView nameTextView;
        private TextView dateRegTextView;
        private FrameLayout avatarLayout;
        private ImageView avatarIcon;
        private ImageView checkedIcon;

        private TextDrawable avatarDrawable;
        private TextDrawable checkedDrawable;

        public PlanViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.text_view_name);
            dateRegTextView = (TextView) itemView.findViewById(R.id.text_view_date_reg);
            costTotalTextView = (TextView) itemView.findViewById(R.id.text_view_cost_total);
            avatarLayout = (FrameLayout) itemView.findViewById(R.id.frame_layout_avatar);
            avatarIcon = (ImageView) itemView.findViewById(R.id.icon_avatar);
            checkedIcon = (ImageView) itemView.findViewById(R.id.icon_check);

            checkedDrawable = TextDrawable.builder().buildRound(" ", ContextCompat.getColor(itemView.getContext(), R.color.material_gray_700));
        }

        // TODO Есть сомнения в технолигичности решения
        public TextDrawable getNewAvatarDrawable(String text) {
            String letter = String.valueOf(Character.toUpperCase(text.charAt(0)));
            ColorGenerator generator = ColorGenerator.MATERIAL;
            avatarDrawable = TextDrawable.builder().buildRound(letter, generator.getColor(letter));

            return avatarDrawable;
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
            holder.avatarIcon.setImageDrawable(holder.getNewAvatarDrawable(plan.getName()));
        }

        if (getArrayChecked().get(position, false)) {
            if (holder.avatarIcon.getDrawable() != holder.checkedDrawable) {
                // TODO Вынести в метод
                holder.avatarIcon.setImageDrawable(holder.checkedDrawable);
                holder.checkedIcon.setVisibility(View.VISIBLE);
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.material_gray_400));
            }
        } else {
            if (holder.avatarIcon.getDrawable() != holder.avatarDrawable) {
                // TODO Вынести в тот же метод
                holder.avatarIcon.setImageDrawable(holder.avatarDrawable);
                holder.checkedIcon.setVisibility(View.GONE);
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        holder.dateRegTextView.setText(Util.dateToString(plan.getDateReg()));
        holder.costTotalTextView.setText(plan.getTotalCost().toPlainString());
    }
}
