package ru.home.miniplanner2.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
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

    Animation animToSide;
    Animation animFromSide;

    public PlanAdapter(Context context, Class<PlanViewHolder> tClass) {
        super(PlanViewHolder.class);

        animToSide = AnimationUtils.loadAnimation(context, R.anim.to_side);
        animFromSide = AnimationUtils.loadAnimation(context, R.anim.from_side);
    }

    public class PlanViewHolder extends BaseAdapter.ViewHolder {
        private TextView costTotalTextView;
        private TextView nameTextView;
        private TextView dateRegTextView;
        private ImageView avatar;
        private ImageView iconChecked;

        public PlanViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            nameTextView = (TextView) itemView.findViewById(R.id.text_view_name);
            dateRegTextView = (TextView) itemView.findViewById(R.id.text_view_date_reg);
            costTotalTextView = (TextView) itemView.findViewById(R.id.text_view_cost_total);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            iconChecked = (ImageView) itemView.findViewById(R.id.check_icon);
        }
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_view, parent, false);

        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position, ViewGroup parent) {

        Plan plan = getItem(position);
        if (!holder.nameTextView.getText().equals(plan.getName())) {
            holder.nameTextView.setText(plan.getName());

            String letter = String.valueOf(Character.toUpperCase(plan.getName().charAt(0)));
            ColorGenerator generator = ColorGenerator.MATERIAL;
            TextDrawable drawable = TextDrawable.builder().buildRound(letter, generator.getColor(letter));
            holder.avatar.setImageDrawable(drawable);
        }

        if (((ListView) parent).isItemChecked(position) && holder.avatar.getVisibility() == View.VISIBLE) {
//            holder.avatar.setVisibility(View.GONE);
//            holder.iconChecked.setVisibility(View.VISIBLE);
//            holder.itemView.setBackgroundColor(Color.LTGRAY);
            avatar_switch(holder.avatar, holder.iconChecked);
        } else {
//            holder.avatar.setVisibility(View.VISIBLE);
//            holder.iconChecked.setVisibility(View.GONE);
//            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            avatar_switch(holder.iconChecked, holder.avatar);
        }

        holder.dateRegTextView.setText(Util.dateToString(plan.getDateReg()));
        holder.costTotalTextView.setText(plan.getTotalCost().toPlainString());
    }

    void avatar_switch(View visible, View notVisible) {
        animToSide.setAnimationListener(new AvatarAnimationListener(visible, notVisible));
        visible.startAnimation(animToSide);
    }

    class AvatarAnimationListener implements Animation.AnimationListener {
        View visible;
        View notVisible;

        public AvatarAnimationListener(View visible, View notVisible) {
            this.visible = visible;
            this.notVisible = notVisible;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            visible.setVisibility(View.GONE);
            notVisible.setVisibility(View.VISIBLE);
            notVisible.startAnimation(animFromSide);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
